package ru.yandex.task_manager.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, TaskStatus> subtasks;

    public Epic(String subject, String description) {
        super(subject, description);
        this.subtasks = new HashMap<>();
    }

    public ArrayList<Integer> getSubTasksIds() {
        return new ArrayList<>(subtasks.keySet());
    }

    public void addSubTask(SubTask subTask) {
        subtasks.put(subTask.getId(), subTask.getStatus());
        calculateStatus();
    }

    public void removeSubTask(SubTask subTask) {
        subtasks.remove(subTask.getId());
        calculateStatus();
    }

    public void removeAllSubTasks() {
        subtasks.clear();
        calculateStatus();
    }

    public TaskStatus getStatus() {
        calculateStatus();
        return status;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", subtasks=" + subtasks +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    private void calculateStatus() {
        if (subtasks.isEmpty()) {
            status = TaskStatus.NEW;
            return;
        }
        int numberOfNewStatuses = 0;
        int numberOfDoneStatuses = 0;

        for (TaskStatus subTaskStatus : subtasks.values()) {
            if (subTaskStatus.equals(TaskStatus.NEW)) numberOfNewStatuses++;
            else if (subTaskStatus.equals(TaskStatus.DONE)) numberOfDoneStatuses++;
            else {
                super.status = TaskStatus.IN_PROGRESS;
                return;
            }
        }

        if (numberOfNewStatuses == subtasks.size()) super.status = TaskStatus.NEW;
        else if (numberOfDoneStatuses == subtasks.size()) super.status = TaskStatus.DONE;
        else super.status = TaskStatus.IN_PROGRESS;

    }

}
