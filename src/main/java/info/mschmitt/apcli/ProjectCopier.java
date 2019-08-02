package info.mschmitt.apcli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class ProjectCopier {
    public ProjectCopier(String[] args) {
        OptionParser parser = new OptionParser();
        parser.accepts("from-package").withRequiredArg();
        parser.accepts("to-package").withRequiredArg();
        parser.accepts("orig").withRequiredArg();
        OptionSet optionSet;
        optionSet = parser.parse(args);
        String from = (String) optionSet.valueOf("from-package");
        String to = (String) optionSet.valueOf("to-package");
        System.out.printf("Copying project and changing package from %s to %s\n", from, to);
    }
}
