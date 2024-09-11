package applemazer;

import java.util.ArrayList;
import java.util.Scanner;

import commands.Command;
import tasks.Task;
import tasks.DuplicateHandler;

/**
 * Main class that runs the Applemazer chatbot.
 */
public class Applemazer {
    protected static TaskList tasks;
    protected static Scanner sc = new java.util.Scanner(System.in); // For text-based UI.
    private static Storage storage;
    private final Parser parser; // For text-based UI.
    private final Ui ui;
    private final DuplicateHandler duplicateHandler;
    private boolean isProcessing = true;

    /**
     * Constructor for the Applemazer chatbot.
     * @param filePath The file to load a task list from.
     */
    public Applemazer(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        ArrayList<Task> savedList = storage.loadTaskList();
        assert savedList != null : "The task list is null.";
        tasks = new TaskList(savedList);
        duplicateHandler = new DuplicateHandler(tasks);
        parser = new Parser(sc, duplicateHandler);
    }

    /**
     * Main processing loop for the text-based version of the Applemazer chatbot.
     * <p>
     * Executes commands based on user input.
     */
    private void process() {
        System.out.println(ui.greeting());
        boolean isProcessing = true;
        while (isProcessing) {
            String command = sc.next();
            try {
                Command c = parser.parse(command);
                System.out.print(c.execute(tasks, storage, ui, duplicateHandler));
                isProcessing = c.continueProcessing();
            } catch (Exception e) {
                System.err.println(e.getMessage()); // Catches parsing errors.
            }
        }
        sc.close();
        System.out.println(ui.farewell());
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        Scanner sc = new Scanner(input + " ");
        Parser parser = new Parser(sc, duplicateHandler);
        String command = sc.next();
        try {
            Command c = parser.parse(command);
            isProcessing = c.continueProcessing();
            return c.execute(tasks, storage, ui, duplicateHandler);
        } catch (Exception e) {
            return e.getMessage() + "\n"; // Catches parsing errors.
        }
    }

    /**
     * @return {@code Ui} object currently being used by the chatbot.
     */
    public Ui getUi() {
        return this.ui;
    }

    /**
     * @return Returns {@code true} if the chatbot should shut down after
     *         executing a particular command.
     */
    public boolean shouldExit() {
        return !this.isProcessing;
    }

    /**
     * Entry point of the text-based version of the Applemazer chatbot program.
     * @param args Unused CLI arguments.
     */
    public static void main(String[] args) {
        Applemazer applemazer = new Applemazer("./data/Applemazer.ser");
        applemazer.process();
    }
}
