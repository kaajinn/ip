package commands;

import applemazer.*;
import tasks.*;

public class ListCommand extends Command {
    /**
     * Executes the "list" command by listing down all current tasks in the task list.
     * @param tasks   The task list to use.
     * @param storage The storage object containing the filepath which the chatbot saves to and loads from.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) {
        Task task;

        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < tasks.size(); ++i) {
            task = tasks.get(i);
            System.out.println((i+1) + "." + task.getStatusIcon() + task);
        }
        System.out.println(); // Leave empty line.
    }

    /**
     * @return Returns true as the chatbot should continue running after executing the "list" command.
     */
    @Override
    public boolean continueProcessing() {
        return true;
    }
}
