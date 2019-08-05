package info.mschmitt.apcli;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyProjectCli {
    public void execute(String[] args) throws IOException {
        OptionParser parser = new OptionParser();
        ArgumentAcceptingOptionSpec<String> fromPackageSpec = parser.accepts("from-package").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> toPackageSpec = parser.accepts("to-package").withRequiredArg();
        OptionSet optionSet = parser.parse(args);
        String fromPackageOption = optionSet.valueOf(fromPackageSpec);
        String toPackageOption = optionSet.valueOf(toPackageSpec);
        List<?> nonOptionArguments = optionSet.nonOptionArguments();
        String fromArg = (String) nonOptionArguments.get(0);
        Path fromDirectory = Paths.get(".").resolve(fromArg);
        String toArg = (String) nonOptionArguments.get(1);
        Path toDirectory = Paths.get(".").resolve(toArg);
        System.out.printf("Copying project from %s to %s and changing package from %s to %s\n", fromDirectory,
                toDirectory, fromPackageOption, toPackageOption);
        ProjectDirectory projectDirectory = new ProjectDirectory(fromDirectory);
        ArrayList fromPackage = new ArrayList<>(Arrays.asList(fromPackageOption.split("\\.")));
        ArrayList toPackage = new ArrayList<>(Arrays.asList(toPackageOption.split("\\.")));
        projectDirectory.copyTo(toDirectory, fromPackage, toPackage);
    }
}
