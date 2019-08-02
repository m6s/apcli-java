package info.mschmitt.apcli;

import joptsimple.OptionException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

public class AppTest {
    @Test
    public void initWithMissingArgument() {
        OptionException ex = assertThrows(OptionException.class, () -> new App("cp-project", "-f"));
        assertThat(ex).hasMessageThat().contains("requires an argument");
    }

    @Test
    public void init() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        App app = new App("cp-project", "--from-package=com.x.y", "--to-package=com.x.z");
        baos.flush();
        String allWrittenLines = new String(baos.toByteArray());
        assertThat(allWrittenLines).isEqualTo("Copying project and changing package from com.x.y to com.x.z\n");
    }
}