import Commands.Command;
import Factory.CommandFactory;
import Commands.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final BufferedReader in;
    private final PrintWriter out;

    CommandFactory commandFactory;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        commandFactory = new CommandFactory(tasks,out);

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
        try {
            String command = commandRest[0];
            Command cmd = commandFactory.getEntity(command);
            if(commandRest.length>1) {
                cmd.execute(commandRest[1]);
            }
            else {
                cmd.execute("");
            }
        }
        catch(Exception exe) {
            out.print(exe.getStackTrace());
        }
    }



    private void show(String subCommandRest) {

    }
}