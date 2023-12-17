import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.cli.*;

/**
 * Class for analyzing command-line parameters and for serialization notebook data to a
 * notebook.json file.
 * The Apache Commons CLI library is used for command line parsing.
 * The Gson library is used for serialization.
 */

public class Main {
    /**
     * A method that works with command line data.
     *
     * @param args Сommand line arguments
     */
    public static void main(String[] args) throws ParseException, FileNotFoundException,
            java.text.ParseException {
        File file = new File("notebook.json");
        Notebook notebook;
        if (file.exists()) {
            Type typeToken = new TypeToken<Notebook>() { }.getType();
            notebook = new Gson().fromJson(new FileReader("notebook.json"), typeToken);
            if (notebook == null) {
                notebook = new Notebook();
            }
        } else {
            notebook = new Notebook();
        }

        Options options = new Options();

        Option option = new Option("add", true, "add note");
        option.setArgs(2);
        options.addOption(option);

        option = new Option("rm", true, "remove note");
        option.setArgs(1);
        options.addOption(option);

        option = new Option("show", false, "remove note");
        options.addOption(option);

        options.addOption(Option.builder("select").hasArgs().build());

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

        if (commandLine.hasOption("add")) {
            notebook.addNote(commandLine.getOptionValues("add")[0],
                    commandLine.getOptionValues("add")[1]);
        } else if (commandLine.hasOption("rm")) {
            notebook.removeNote(commandLine.getOptionValue("rm"));
        } else if (commandLine.hasOption("show")) {
            notebook.showAllNotes();
        } else if (commandLine.hasOption("select")) {
            ArrayList<String> keywords =
                    new ArrayList<>(Arrays.asList(commandLine.getOptionValues("select")));
            keywords.remove(0);
            keywords.remove(0);
            notebook.showSomeNotes(commandLine.getOptionValues("select")[0],
                    commandLine.getOptionValues("select")[1], keywords);
        }

        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("notebook.json")) {
            gson.toJson(notebook, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
