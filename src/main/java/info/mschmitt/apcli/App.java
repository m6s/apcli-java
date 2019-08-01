package info.mschmitt.apcli;

import org.apache.commons.cli.*;

public class App {
    private static final Option t = new Option("t", false, "display current time");
    private static final Option help = new Option("help", false, "print this message");

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(t);
        options.addOption(help);
        CommandLineParser parser = new DefaultParser();
        CommandLine line;
        try {
            line = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println(exp.getMessage());
            return;
        }
        if (line.hasOption(help.getOpt())) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("apcli", options);
        } else if (line.hasOption(t.getOpt())) {
            System.out.println("Hello t");
        } else {
            System.out.println("Hello world!");
        }
    }
}
