package info.mschmitt.apcli;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class Cli {
    public static void main(String[] args) {
        int error = 0;
        try {
            error = new Cli().execute(args, System.out, System.err);
        } catch (IOException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        System.exit(error);
    }

    public int execute(String[] args, PrintStream out, PrintStream err) throws IOException {
        if (args.length == 0) {
            err.println("You must specify exactly one command. Possible commands: cp-project");
            return -1;
        }
        String command = args[0];
        if (command.equals("cp-project")) {
            new CopyProjectCli().execute(Arrays.copyOfRange(args, 1, args.length), out, err);
        } else {
            err.printf("Unknown command: %s\n", command);
            return -1;
        }
        return 0;
    }
}
