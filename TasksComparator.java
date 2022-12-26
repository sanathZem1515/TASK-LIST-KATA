import java.util.Comparator;

public class TasksComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1,Task task2) {
        if(task1.getDeadline() == null) {
            return 1;
        }
        return task1.getDeadline().compareTo(task2.getDeadline());
    }
}
