package Commands;

import Commands.Command;
import Commands.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Today implements Command {

    private final Map<String, List<Task>> tasks;


    private final PrintWriter out;


    public Today(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }
    @Override
    public void execute(String command) {
        out.printf("*****   Tasks Due   *****");
        out.println();
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (!task.isDone() ) {
                    out.printf(" %s %s",task.getId(),task.getDescription());
                }
                out.println();
            }
        }
    }
}
