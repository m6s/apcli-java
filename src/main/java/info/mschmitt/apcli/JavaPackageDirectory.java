package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JavaPackageDirectory implements Node {
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final Path fileName;
    private final ArrayList<String> packageName;

    public JavaPackageDirectory(Path path, List<String> parentPackageName) {
        fileName = path.getFileName();
        packageName = new ArrayList<>(parentPackageName);
        packageName.add(fileName.toString());
        File[] files = path.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File childFile : files) {
            Path childPath = childFile.toPath();
            String childName = childPath.getFileName().toString();
            if (childFile.isDirectory()) {
                nodes.add(new JavaPackageDirectory(childPath, packageName));
            } else if (childName.endsWith(".java")) {
                nodes.add(new JavaFile(childPath, packageName));
            }
        }
    }

    @Override
    public void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException {
        Path resolvedPath = path;
        if (fromPackage.equals(packageName)) {
            for (int i = 0; i < packageName.size() - 1; i++) {
                resolvedPath = resolvedPath.getParent();
            }
            for (String s : toPackage) {
                resolvedPath = resolvedPath.resolve(s);
            }
        } else {
            resolvedPath = resolvedPath.resolve(fileName);
        }
        for (Node node : nodes) {
            node.copyTo(resolvedPath, fromPackage, toPackage);
        }
    }
}
