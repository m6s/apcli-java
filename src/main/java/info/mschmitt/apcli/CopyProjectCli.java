package info.mschmitt.apcli;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyProjectCli {
    public int execute(String[] args, PrintStream out, PrintStream err) throws IOException {
        OptionParser parser = new OptionParser();
        ArgumentAcceptingOptionSpec<String> fromPackageSpec =
                parser.accepts("from-package").withRequiredArg().required();
        ArgumentAcceptingOptionSpec<String> toPackageSpec = parser.accepts("to-package").withRequiredArg().required();
        OptionSet optionSet;
        try {
            optionSet = parser.parse(args);
        } catch (OptionException ex) {
            err.println(ex.getLocalizedMessage());
            return -1;
        }
        String fromPackageOption = optionSet.valueOf(fromPackageSpec);
        String toPackageOption = optionSet.valueOf(toPackageSpec);
        List<?> nonOptionArguments = optionSet.nonOptionArguments();
        if (nonOptionArguments.size() != 2) {
            err.println("Command cp-project requires source and destination directories. Usage:");
            err.println("apcli cp-project <options> <source> <dest>");
            err.println("Options:");
            parser.printHelpOn(err);
            return -1;
        }
        String fromArg = (String) nonOptionArguments.get(0);
        Path fromDirectory = Paths.get(".").resolve(fromArg);
        String toArg = (String) nonOptionArguments.get(1);
        Path toDirectory = Paths.get(".").resolve(toArg);
        out.printf("Copying project from %s to %s and changing package from %s to %s\n", fromDirectory, toDirectory,
                fromPackageOption, toPackageOption);
        ProjectDirectory projectDirectory = new ProjectDirectory(fromDirectory);
        ArrayList<String> fromPackage = new ArrayList<>(Arrays.asList(fromPackageOption.split("\\.")));
        ArrayList<String> toPackage = new ArrayList<>(Arrays.asList(toPackageOption.split("\\.")));
        projectDirectory.copyTo(toDirectory, fromPackage, toPackage);
        return -1;
    }
}
