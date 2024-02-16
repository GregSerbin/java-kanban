package ru.yandex.task_manager.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, SubTask> subtasks;

    public Epic(String subject, String description, int epicId) {
        super(subject, description, epicId);
        this.id = epicId;
        this.subtasks = new HashMap<>();
        status = TaskStatus.NEW;
    }

    private void calculateStatus() {
        if (subtasks.isEmpty()) status = TaskStatus.NEW;
        int numberOfNewStatuses = 0;
        int numberOfDoneStatuses = 0;

        for (SubTask subTask : subtasks.values()) {
            if (subTask.getStatus().equals(TaskStatus.NEW)) numberOfNewStatuses++;
            if (subTask.getStatus().equals(TaskStatus.DONE)) numberOfDoneStatuses++;
        }

        if (numberOfNewStatuses == subtasks.size()) {
            super.status = TaskStatus.NEW;
        } else if (numberOfDoneStatuses == subtasks.size()) {
            super.status = TaskStatus.DONE;
        } else {
            super.status = TaskStatus.IN_PROGRESS;
        }
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Integer> getSubTasksIds() {
        return new ArrayList<>(subtasks.keySet());
    }

    public void addSubTask(SubTask subTask) {
        subtasks.put(subTask.getId(), subTask);
        calculateStatus();
    }

    public void removeSubTask(SubTask subTask) {
        subtasks.remove(subTask.getId());
        calculateStatus();
    }

    public TaskStatus getStatus() {
        calculateStatus();
        return status;
    }


    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
