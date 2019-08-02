package info.mschmitt.apcli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.List;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        OptionParser parser = new OptionParser("f:t:");
        OptionSet optionSet;
        try {
            optionSet = parser.parse(args);
        } catch (joptsimple.OptionException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        String from = (String) optionSet.valueOf("f");
        String to = (String) optionSet.valueOf("t");
        List<?> nonOptionArguments = optionSet.nonOptionArguments();
        if (nonOptionArguments.size() != 1) {
            System.err.println("You must specify exactly one command. Possible commands: project");
            return;
        }
        String command = (String) nonOptionArguments.get(0);
        if (!Objects.equals(command, "project")) {
            System.err.printf("Unknown command: %s\n", command);
            return;
        }
        System.out.printf("Copying project and changing package from %s to %s\n", from, to);
    }
}
