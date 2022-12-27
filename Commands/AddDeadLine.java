package Commands;

import Commands.Command;
import Commands.Task;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddDeadLine implements Command {

    private final Map<String, List<Task>> tasks;


    private final PrintWriter out;


    public AddDeadLine(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void execute(String command) {
        String list[] = command.split(" ");
        try {
            String taskId = list[0];
            Date deadline = new SimpleDateFormat("dd/mm/yyyy").parse(list[1]);
            setDeadline(taskId,deadline);
        }
        catch(Exception e){
            out.print(e.getStackTrace());
        }
    }

    private void setDeadline(String taskId,Date deadline) {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId().equals(taskId)) {
                    task.setDeadline(deadline);
                    return;
                }
            }
        }
    }
}
