package info.mschmitt.apcli;

import org.apache.commons.cli.*;

public class App {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("t", false, "display current time");
        options.addOption("help", false, "print this message");
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            return;
        }
        if (line.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("apcli", options);
        } else if (line.hasOption("t")) {
            System.out.println("Hello t");
        } else {
            System.out.println("Hello world!");
        }
    }
}
