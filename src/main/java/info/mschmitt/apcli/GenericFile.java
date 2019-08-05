package info.mschmitt.apcli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GenericFile implements Node {
    private final Path path;

    public GenericFile(Path path) {
        this.path = path;
    }

    @Override
    public void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException {
        Path resolvedPath = path.resolve(this.path.getFileName());
        path.toFile().mkdirs();
        Files.copy(this.path, resolvedPath);
    }
}
