package ru.yandex.task_manager.service;

import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;


import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int idCounter = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
    }

    public void addNewTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    public void addNewEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
    }

    public void addNewSubTask(SubTask subTask) {
        subTask.setId(getNextId());
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTask(subTask);
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

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        oldEpic.setSubject((epic.getSubject()));
        oldEpic.setDescription(epic.getDescription());
    }

    public void updateSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTask(subTask);
        subTasks.put(subTask.getId(), subTask);
    }

    public void removeTasks() {
        tasks.clear();
    }

    public void removeEpics() {
        subTasks.clear();
        epics.clear();
    }

    public void removeSubTasks() {
        epics.forEach((id, epic) -> epic.removeAllSubTasks());
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
        Epic epic = epics.remove(epicId);
        if (epic == null) return;
        ArrayList<Integer> subTasksIds = epic.getSubTasksIds();
        for( Integer subTaskId : subTasksIds) {
            subTasks.remove(subTaskId);
        }
    }

    public void removeSubTaskById(int id) {
        SubTask subTask = subTasks.remove(id);
        if (subTask == null) return;
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTask(subTask);
    }

    public ArrayList<SubTask> getEpicSubTasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return null;
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (Integer subTaskId : epic.getSubTasksIds()) epicSubTasks.add(subTasks.get(subTaskId));
        return epicSubTasks;
    }

    private int getNextId() {
        return idCounter++;
    }
}
