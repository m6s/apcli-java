package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IdeaDirectory implements Node {
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final Path fileName;

    public IdeaDirectory(Path path) {
        fileName = path.getFileName();
        File[] files = path.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File childFile : files) {
            Path childPath = childFile.toPath();
            String childName = childPath.getFileName().toString();
            if (childFile.isDirectory()) {
                if (childName.equals("codeStyles")) {
                    nodes.add(new GenericDirectory(childPath));
                }
            } else {
                if (childName.equals("codeStyleSettings.xml")) {
                    nodes.add(new GenericFile(childPath));
                }
            }
        }
    }

    @Override
    public void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException {
        Path resolvedPath = path.resolve(fileName);
        boolean mkdir = resolvedPath.toFile().mkdir();
        if (!mkdir) {
            throw new IllegalArgumentException();
        }
        for (Node node : nodes) {
            node.copyTo(resolvedPath, fromPackage, toPackage);
        }
    }
}