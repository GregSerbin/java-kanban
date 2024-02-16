package ru.yandex.task_manager.model;

import java.util.Objects;

public class Task {
    protected String subject;
    protected String description;
    protected TaskStatus status;
    protected int id;

    public Task(String subject, String description, TaskStatus status, int taskId) {
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.id = taskId;
    }

    public Task(String subject, String description, int taskId) {
        this.subject = subject;
        this.description = description;
        this.id = taskId;
        this.status = TaskStatus.NEW;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
