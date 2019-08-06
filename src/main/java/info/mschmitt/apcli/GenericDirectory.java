package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GenericDirectory implements Node {
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final String directory;

    public GenericDirectory(Path path) {
        directory = path.getFileName().toString();
        File[] files = path.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File childFile : files) {
            Path childPath = childFile.toPath();
            if (childFile.isDirectory()) {
                nodes.add(new GenericDirectory(childPath));
            } else {
                nodes.add(new GenericFile(childPath));
            }
        }
    }

    @Override
    public void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException {
        Path resolvedPath = path.resolve(directory);
        for (Node node : nodes) {
            node.copyTo(resolvedPath, fromPackage, toPackage);
        }
    }
}
