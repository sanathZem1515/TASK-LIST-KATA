package Commands;

import Commands.Command;
import Commands.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Check implements Command {

    private final Map<String, List<Task>> tasks;

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private final PrintWriter out;


    public Check(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void execute(String command) {
        String id = command;
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId().equals(id)) {
                    task.setDone(true);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }
}
