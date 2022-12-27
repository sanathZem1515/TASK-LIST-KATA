package Commands;

import Commands.Command;
import Commands.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Help implements Command {

    private final Map<String, List<Task>> tasks;
    private final PrintWriter out;


    public Help(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void execute(String command) {
        out.println("Commands:");
        out.println("  view by project");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  deadline <task ID> <date>");
        out.println("  today ");
        out.println("  setNewId <CurrentTaskID> <NewTaskID>");
        out.println("  delete <task ID>");
        out.println("  view by date");
        out.println("  view by deadline");
        out.println();
    }
}
