package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IdeaDirectory implements Node {
    public static final String NAME = ".idea";
    private final ArrayList<Node> nodes = new ArrayList<>();

    public IdeaDirectory(Path path) {
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
        Path resolvedPath = path.resolve(NAME);
        for (Node node : nodes) {
            node.copyTo(resolvedPath, fromPackage, toPackage);
        }
    }
}
