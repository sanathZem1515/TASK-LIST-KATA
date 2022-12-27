package Commands;

import Commands.Command;
import Commands.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class SetNewId implements Command {

    private final Map<String, List<Task>> tasks;


    private final PrintWriter out;


    public SetNewId(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void execute(String command) {

        String list[] = command.split(" ",2);
        try {
            String currId = list[0];
            String newId = list[1];

            for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
                for (Task task : project.getValue()) {
                    if (task.getId().equals(currId)) {
                        task.setId(newId);
                        return;
                    }
                }
            }

        }
        catch (Exception e) {
            out.println(e.getStackTrace());
        }
    }
}
