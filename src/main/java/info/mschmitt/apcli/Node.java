package info.mschmitt.apcli;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Node {
    void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException;
}
