package Commands;

import java.util.Date;

public  class Task {
    private String id;
    private final String description;
    private boolean done;
    private Date deadline;
    
    public Task(String id, String description, boolean done) {
        setId(id);
        this.description = description;
        this.done = done;
        this.deadline = null;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(validateId(id)) {
            this.id = id;
        }
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    private boolean validateId(String id) {
        return ((!id.equals(""))
                && (id != null)
                && (id.matches("^[a-zA-Z]*$")));
    }
}