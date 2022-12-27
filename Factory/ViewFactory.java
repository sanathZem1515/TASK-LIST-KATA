package Factory;

import Commands.*;
import Commands.Views.ViewByDate;
import Commands.Views.ViewByDeadLine;
import Commands.Views.ViewByProject;
import Commands.Views.ViewInterface;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ViewFactory {

    private final Map<String, List<Task>> tasks;
    private final PrintWriter out;

    public ViewFactory(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }
    public ViewInterface getEntity(String cmd) {
        if(cmd.equalsIgnoreCase("project")) {
            return new ViewByProject(tasks, out);
        }
        else if(cmd.equalsIgnoreCase("date")) {
            return new ViewByDate(tasks,out);
        }
        else if(cmd.equalsIgnoreCase("deadline")) {
            return new ViewByDeadLine(tasks,out);
        }
        return new ViewByProject(tasks, out);
    }
}
