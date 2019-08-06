package info.mschmitt.apcli;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JavaFile implements Node {
    private final Path path;
    private final List<String> packageName;

    public JavaFile(Path path, List<String> packageName) {
        this.path = path;
        this.packageName = packageName;
    }

    @Override
    public void copyTo(Path path, List<String> fromPackage, List<String> toPackage) throws IOException {
        Path resolvedPath = path.resolve(this.path.getFileName());
        path.toFile().mkdirs();
        byte[] encoded = Files.readAllBytes(this.path);
        String text = new String(encoded, StandardCharsets.UTF_8);
        if (fromPackage.size() <= packageName.size() &&
                fromPackage.equals(packageName.subList(0, fromPackage.size()))) {
            String fromPackageString = String.join(".", fromPackage);
            String toPackageString = String.join(".", toPackage);
            String target = String.format("package %s;", fromPackageString);
            String replacement = String.format("package %s;", toPackageString);
            text = text.replace(target, replacement);
            target = String.format("package %s.", fromPackageString);
            replacement = String.format("package %s.", toPackageString);
            text = text.replace(target, replacement);
            target = String.format("import %s.", fromPackageString);
            replacement = String.format("import %s.", toPackageString);
            text = text.replace(target, replacement);
        }
        Files.write(resolvedPath, text.getBytes());
    }
}
