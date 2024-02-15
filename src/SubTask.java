public class SubTask extends Task {

    private Epic epic;

    public SubTask(String subject, String description, TaskStatus status, int id, Epic epic) {
        super(subject, description, status, id);
        this.epic = epic;
    }

    public void setStatus(TaskStatus status) {
        super.status = status;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
