package Commands;

import Commands.Command;

import java.io.PrintWriter;

public class Error implements Command {

    private final PrintWriter out;

    public Error(PrintWriter out) {
        this.out = out;
    }


    @Override
    public void execute(String command) {

        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }
}
