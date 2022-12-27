package Commands.Views;

import Commands.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ViewByProject implements ViewInterface {

    private final Map<String, List<Task>> tasks;
    private final PrintWriter out;

    public ViewByProject(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void getView() {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf("    [%c] %s: %s", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
                if (task.getDeadline()!=null) {
                    out.printf(" %s ", task.getDeadline());
                }
                out.println();
            }
            out.println();
        }
    }
}
