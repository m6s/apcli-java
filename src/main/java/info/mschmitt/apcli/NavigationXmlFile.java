package info.mschmitt.apcli;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class NavigationXmlFile implements Node {
    private final Path path;

    public NavigationXmlFile(Path path) {
        this.path = path;
    }

    @Override
    public void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException {
        Path resolvedPath = path.resolve(this.path.getFileName());
        path.toFile().mkdirs();
        byte[] encoded = Files.readAllBytes(this.path);
        String text = new String(encoded, StandardCharsets.UTF_8);
        String fromPackageString = String.join(".", fromPackage);
        String toPackageString = String.join(".", toPackage);
        String target = String.format("android:name=\"%s.", fromPackageString);
        String replacement = String.format("android:name=\"%s.", toPackageString);
        text = text.replace(target, replacement);
        target = String.format("app:argType=\"%s.", fromPackageString);
        replacement = String.format("app:argType=\"%s.", toPackageString);
        text = text.replace(target, replacement);
        Files.write(resolvedPath, text.getBytes());
    }
}
