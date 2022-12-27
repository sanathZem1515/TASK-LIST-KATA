package Commands.Views;

import Commands.Command;
import Commands.Task;
import Factory.ViewFactory;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class View implements Command {
    private final Map<String, List<Task>> tasks;
    ViewFactory viewFactory;
    private final PrintWriter out;
    public View(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        viewFactory = new ViewFactory(tasks, out);
        this.out = out;
    }

    @Override
    public void execute(String command) {
        String list[] = command.split(" ");
        String cmd = list[1];
        viewFactory.getEntity(cmd).getView();
    }
}
