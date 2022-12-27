package Commands.Views;

import Commands.Task;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViewByDate implements ViewInterface{

    private final Map<String, List<Task>> tasks;


    private final PrintWriter out;


    public ViewByDate(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void getView() {
        List<Task> tasksList = new ArrayList<>();
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                tasksList.add(task);
            }
        }

        Collections.sort(tasksList,new TasksComparator());

        for(Task task : tasksList) {
            out.printf("    [%c] %s: %s", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            out.printf(" %s ", task.getDeadline());
            out.println();
        }
    }
}
