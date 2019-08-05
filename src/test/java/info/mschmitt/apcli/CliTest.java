package info.mschmitt.apcli;

import joptsimple.OptionException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

public class CliTest {
    @Rule public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void initWithMissingArgument() {
        Cli cli = new Cli();
        OptionException ex = assertThrows(OptionException.class, () -> cli.execute("cp-project", "-f"));
        assertThat(ex).hasMessageThat().contains("requires an argument");
    }

    @Test
    public void copyProject() throws IOException, CliException {
        Path dir1 = testFolder.getRoot().toPath().resolve("dir1");
        Path dir2 = testFolder.getRoot().toPath().resolve("dir2");
        copyRecursively(Paths.get("src/test/data/masterDetail"), dir1);
        dir2.toFile().mkdir();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        new Cli().execute("cp-project", dir1.resolve("myapplication").toString(),
                dir2.resolve("myapplicationcopy").toString(), "--from-package=info.mschmitt.myapplication",
                "--to-package=info.mschmitt.myapplicationcopy");
        baos.flush();
        String allWrittenLines = new String(baos.toByteArray());
        assertThat(allWrittenLines).startsWith("Copying project");
        assertThat(allWrittenLines).endsWith(
                "and changing package from info.mschmitt.myapplication to info.mschmitt.myapplicationcopy\n");
        compare(dir2.resolve("myapplicationcopy"), Paths.get("src/test/data/masterDetailCopy/myapplicationcopy"));
        compare(Paths.get("src/test/data/masterDetailCopy/myapplicationcopy"), dir2.resolve("myapplicationcopy"));
    }

    private void compare(Path actual, Path expected) throws IOException {
        File expectedFile = expected.toFile();
        Assert.assertTrue(expectedFile.toString(), expectedFile.exists());
        File actualFile = actual.toFile();
        if (actualFile.isFile()) {
            Assert.assertTrue(expectedFile.toString(), expectedFile.isFile());
            if (actual.getFileName().toString().endsWith(".java")) {
                // || actual.getFileName().toString().endsWith(".xml")
                List<String> f1 = Files.readAllLines(actual);
                List<String> f2 = Files.readAllLines(expected);
                assertThat(f1).isEqualTo(f2);
            }
            return;
        }
        File[] actualChildFiles = actualFile.listFiles();
        if (actualChildFiles == null) {
            return;
        }
        for (File actualChildFile : actualChildFiles) {
            Path actualChildPath = actualChildFile.toPath();
            compare(actualChildPath, expected.resolve(actualChildPath.getFileName()));
        }
    }

    private void copyRecursively(Path source, Path dest) throws IOException {
        Files.copy(source, dest);
        File[] files = source.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            Path path = file.toPath();
            copyRecursively(path, dest.resolve(path.getFileName()));
        }
    }
}