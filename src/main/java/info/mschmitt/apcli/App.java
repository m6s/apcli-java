package info.mschmitt.apcli;

import joptsimple.OptionException;

import java.util.Arrays;

public class App {
    public App(String... args) {
        if (args.length == 0) {
            System.err.println("You must specify exactly one command. Possible commands: project");
            return;
        }
        String command = args[0];
        if (command.equals("cp-project")) {
            new ProjectCopier(Arrays.copyOfRange(args, 1, args.length));
        } else {
            System.err.printf("Unknown command: %s\n", command);
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        try {
            new App(args);
        } catch (OptionException ex) {
            System.err.printf("Unknown command: %s\n", ex.getMessage());
            System.exit(-1);
        }
    }
}
