
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final BufferedReader in;
    private final PrintWriter out;

    private long lastId = 65;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "view":
                show(commandRest[1]);
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                help();
                break;
            case "deadline":
                addDeadline(commandRest[1]);
                break;
            case "today":
                today();
                break;
            case "setNewId":
                setNewId(commandRest[1]);
                break;
            case "deleteId":
                deleteId(commandRest[1]);
                break;
            default:
                error(command);
                break;
        }
    }

    private void viewByProject() {
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

    private void viewByDate() {
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

    private void viewByDeadline() {
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

    private void show(String subCommandRest) {
        String list[] = subCommandRest.split(" ");
        String cmd = list[1];

        if(cmd.equalsIgnoreCase("project")) {
            viewByProject();
        }
        else if(cmd.equalsIgnoreCase("date")) {
            viewByDate();
        }
        else if(cmd.equalsIgnoreCase("deadline")) {
            viewByDeadline();
        }
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(projectTask[0], projectTask[1]);
        }
    }

    private void addProject(String name) {
        tasks.put(name, new ArrayList<Task>());
    }

    private void addTask(String project, String description) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        projectTasks.add(new Task(nextId(), description, false));
    }

    private void check(String idString) {
        setDone(idString, true);
    }

    private void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        String id = idString;
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId().equals(id)) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }

    private void help() {
        out.println("Commands:");
        out.println("  view by project");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  deadline <task ID> <date>");
        out.println("  today ");
        out.println("  setNewId <CurrentTaskID> <NewTaskID>");
        out.println("  delete <task ID>");
        out.println("  view by date");
        out.println("  view by deadline");
        out.println();
    }

    private void addDeadline(String subCommandRest) {
        String list[] = subCommandRest.split(" ");
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

    private void setNewId(String subCommandRest) {
        String list[] = subCommandRest.split(" ",2);
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

    private void today() {
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
    private void deleteId(String taskId) {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            List<Task> tasks = new ArrayList<>();
            for (Task task : project.getValue()) {
                if ( !task.getId().equals(taskId) ) {
                    tasks.add(task);
                }
            }
            project.setValue(tasks);
        }
    }
    
    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

    private String nextId() {
        return (char)(lastId++)+"";
    }
}