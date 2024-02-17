package ru.yandex.task_manager.model;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String subject, String description, TaskStatus status, int epicId) {
        super(subject, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
