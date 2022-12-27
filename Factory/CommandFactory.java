package Factory;

import Commands.*;
import Commands.Error;
import Commands.Views.View;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class CommandFactory {

    private final Map<String, List<Task>> tasks;

    private final PrintWriter out;

    public CommandFactory(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    public Command getEntity(String command) {
        switch (command) {
            case "view":
                return new View(tasks,out);
            case "add":
                return new Add(tasks,out);
            case "check":
                return new Check(tasks,out);
            case "uncheck":
                return new Uncheck(tasks,out);
            case "help":
                return new Help(tasks,out);
            case "deadline":
                return new AddDeadLine(tasks,out);
            case "today":
                return new Today(tasks,out);
            case "setNewId":
                return new SetNewId(tasks,out);
            case "deleteId":
                return new DeleteId(tasks,out);
            default:
                return new Error(out);
        }
    }
}
