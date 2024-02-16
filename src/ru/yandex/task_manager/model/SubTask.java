package ru.yandex.task_manager.model;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String subject, String description, TaskStatus status, int subTaskId, int epicId) {
        super(subject, description, status, subTaskId);
        this.epicId = epicId;
        this.id = subTaskId;
    }

    public void setStatus(TaskStatus status) {
        super.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
