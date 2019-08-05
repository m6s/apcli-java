package info.mschmitt.apcli;

import java.io.IOException;
import java.nio.file.Path;

public interface Node {
    void copyTo(Path path, String fromPackage, String toPackage) throws IOException;
}
