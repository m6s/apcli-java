package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NavigationResDirectory implements Node {
    private final ArrayList<Node> nodes = new ArrayList<>();

    public NavigationResDirectory(Path path) {
        File[] files = path.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            Path childPath = file.toPath();
            String childName = childPath.getFileName().toString();
            if (!file.isDirectory() && childName.endsWith(".xml")) {
                nodes.add(new NavigationXmlFile(childPath));
            }
        }
    }

    @Override
    public void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException {
        Path resolvedPath = path.resolve("navigation");
        for (Node node : nodes) {
            node.copyTo(resolvedPath, fromPackage, toPackage);
        }
    }
}
