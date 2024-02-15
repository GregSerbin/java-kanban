import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subtasks;

    public Epic(String subject, String description, int id) {
        super(subject, description, id);
        this.subtasks = new ArrayList<>();
    }

    private void calculateStatus() {
        if (subtasks.isEmpty()) super.status = TaskStatus.NEW;
        int numberOfNewStatuses = 0;
        int numberOfDoneStatuses = 0;

        for (SubTask subtask : subtasks) {
            if (subtask.getStatus().equals(TaskStatus.NEW)) numberOfNewStatuses++;
            if (subtask.getStatus().equals(TaskStatus.DONE)) numberOfDoneStatuses++;
        }

        if (numberOfNewStatuses == subtasks.size()) {
            super.status = TaskStatus.NEW;
        } else if (numberOfDoneStatuses == subtasks.size()) {
            super.status = TaskStatus.DONE;
        } else {
            super.status = TaskStatus.IN_PROGRESS;
        }
    }

    public ArrayList<SubTask> getSubtasks() {
        return subtasks;
    }

    public void addSubTask(SubTask subTask) {
        subtasks.add(subTask);
        calculateStatus();
    }

    public void removeSubTask(SubTask subTask) {
        subtasks.remove(subTask);
        calculateStatus();
    }

    public void updateSubTask(SubTask subTask) {
        subtasks.remove(subTask);
        subtasks.add(subTask);

        int index = subtasks.indexOf(subTask);
        if (index == -1) {
            subtasks.add(subTask);
        } else {
            subtasks.set(index, subTask);
        }

        calculateStatus();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
