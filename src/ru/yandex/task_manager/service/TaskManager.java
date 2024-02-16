package ru.yandex.task_manager.service;

import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;


import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int idCounter = 0;

    private static HashMap<Integer, Task> tasks;
    private static HashMap<Integer, Epic> epics;
    private static HashMap<Integer, SubTask> subTasks;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
    }

    private int getNextId() {
        return idCounter++;
    }

    public Task createNewTask(String subject, String description, TaskStatus status) {
        Task newTask = new Task(subject, description, status, getNextId());
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    public Epic createNewEpic(String subject, String description) {
        Epic newEpic = new Epic(subject, description, getNextId());
        epics.put(newEpic.getId(), newEpic);
        return newEpic;
    }

    public SubTask createNewSubTask(String subject, String description, TaskStatus status, int epicId) {
        SubTask newSubTask = new SubTask(subject, description, status, getNextId(), epicId);
        subTasks.put(newSubTask.getId(), newSubTask);
        Epic epic = epics.get(epicId);
        epic.addSubTask(newSubTask);
        return newSubTask;
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void removeTasks() {
        tasks.clear();
    }

    public void removeEpics() {
        subTasks.clear();
        epics.clear();
    }

    public void removeSubTasks() {
        subTasks.forEach((id, subTask) -> removeSubTaskById(id));
        subTasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeEpicById(int epicId) {
        if (!epics.containsKey(epicId)) return;

        ArrayList<Integer> subTasksIds = epics.get(epicId).getSubTasksIds();
        for (Integer subTaskId : subTasksIds) {
            subTasks.remove(subTaskId);
        }
        epics.remove(epicId);
    }

    public void removeSubTaskById(int id) {
        if (!subTasks.containsKey(id)) return;

        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTask(subTask);
        subTasks.remove(id);
    }

    public ArrayList<SubTask> getEpicSubTasks(int epicId) {
        if (!epics.containsKey(epicId)) return null;

        Epic epic = epics.get(epicId);
        ArrayList<Integer> subTasksIds = epic.getSubTasksIds();
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        subTasksIds.forEach((subTaskId) -> epicSubTasks.add(subTasks.get(subTaskId)));
        return epicSubTasks;
    }
}
