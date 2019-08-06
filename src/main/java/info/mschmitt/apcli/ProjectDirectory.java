package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectDirectory implements Node {
    private static final List<String> IGNORED_DIRECTORIES =
            Arrays.asList(".gradle", "build", "release", ".DS_Store", ".git");
    private static final List<String> IGNORED_FILES = Arrays.asList("local.properties");
    private final ArrayList<Node> nodes = new ArrayList<>();

    public ProjectDirectory(Path path) {
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
                if (childName.equals(IdeaDirectory.NAME)) {
                    nodes.add(new IdeaDirectory(childPath));
                } else if (childName.equals(AppModuleDirectory.NAME)) {
                    // TODO Infer module name from settings.gradle
                    nodes.add(new AppModuleDirectory(childPath));
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
        boolean mkdir = path.toFile().mkdir();
        if (!mkdir) {
            throw new IOException();
        }
        for (Node node : nodes) {
            node.copyTo(path, fromPackage, toPackage);
        }
    }
}
