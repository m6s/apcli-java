package info.mschmitt.apcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaSrcDirectory implements Node {
    private final ArrayList<Node> nodes = new ArrayList<>();


    public JavaSrcDirectory(Path path) {
        File[] files = path.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File childFile : files) {
            Path childPath = childFile.toPath();
            if (childFile.isDirectory()) {
                nodes.add(new JavaPackageDirectory(childPath, Collections.emptyList()));
            } else {
                nodes.add(new JavaFile(childPath, Collections.emptyList()));
            }
        }
    }

    @Override
    public void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException {
        Path resolvedPath = path.resolve("java");
        for (Node node : nodes) {
            node.copyTo(resolvedPath, fromPackage, toPackage);
        }
    }
}
