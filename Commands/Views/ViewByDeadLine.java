package Commands.Views;

import Commands.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ViewByDeadLine implements ViewInterface{

    private final Map<String, List<Task>> tasks;

    private final PrintWriter out;


    public ViewByDeadLine(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void getView() {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                if (task.getDeadline()!=null) {
                    out.printf("    [%c] %s: %s", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
                    out.printf(" %s ", task.getDeadline().getDate());
                    out.println();
                }
            }
            out.println();
        }
    }
}
