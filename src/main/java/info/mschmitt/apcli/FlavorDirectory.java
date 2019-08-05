package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FlavorDirectory implements Node {
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final Path fileName;

    public FlavorDirectory(Path path) {
        fileName = path.getFileName();
        File[] files = path.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            Path childPath = file.toPath();
            String childName = childPath.getFileName().toString();
            if (file.isDirectory()) {
                if (childName.equals("java")) {
                    nodes.add(new JavaSrcDirectory(childPath));
                } else {
                    nodes.add(new GenericDirectory(childPath));
                }
            } else {
                if (childName.equals("AndroidManifest.xml")) {
                    nodes.add(new ManifestFile(childPath));
                } else {
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
