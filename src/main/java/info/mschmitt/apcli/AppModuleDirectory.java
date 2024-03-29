package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppModuleDirectory implements Node {
    public static final String NAME = "app";
    private static final List<String> IGNORED_DIRECTORIES = Arrays.asList("build", "release", ".DS_Store");
    private static final List<String> IGNORED_FILES = Arrays.asList("app.iml");
    private final ArrayList<Node> nodes = new ArrayList<>();

    public AppModuleDirectory(Path path) {
        File[] files = path.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File childFile : files) {
            Path childPath = childFile.toPath();
            String childName = childPath.getFileName().toString();
            if (childFile.isDirectory()) {
                if (IGNORED_DIRECTORIES.contains(childName)) {
                    continue;
                }
                if (childName.equals(SrcDirectory.NAME)) {
                    nodes.add(new SrcDirectory(childPath));
                } else {
                    nodes.add(new GenericDirectory(childPath));
                }
            } else {
                if (IGNORED_FILES.contains(childName)) {
                    continue;
                }
                if (childName.endsWith(".iml")) {
                    continue;
                }
                nodes.add(new GenericFile(childPath));
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
