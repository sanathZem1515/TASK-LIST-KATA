package Commands;

import Commands.Command;
import Commands.Task;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteId implements Command {

    private final Map<String, List<Task>> tasks;


    private final PrintWriter out;


    public DeleteId(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void execute(String command) {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            List<Task> tasks = new ArrayList<>();
            for (Task task : project.getValue()) {
                if ( !task.getId().equals(command) ) {
                    tasks.add(task);
                }
            }
            project.setValue(tasks);
        }
    }
}
