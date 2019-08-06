package info.mschmitt.apcli;

import joptsimple.OptionException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class Cli {
    public static void main(String[] args) {
        try {
            new Cli().execute(args, System.out);
        } catch (OptionException | CliException | IOException ex) {
            System.err.println(ex.getLocalizedMessage());
            System.exit(-1);
        }
    }

    public void execute(String[] args, PrintStream out) throws CliException, IOException {
        if (args.length == 0) {
            throw new CliException("You must specify exactly one command. Possible commands: cp-project");
        }
        String command = args[0];
        if (command.equals("cp-project")) {
            new CopyProjectCli().execute(Arrays.copyOfRange(args, 1, args.length), out);
        } else {
            throw new CliException(String.format("Unknown command: %s", command));
        }
    }
}
