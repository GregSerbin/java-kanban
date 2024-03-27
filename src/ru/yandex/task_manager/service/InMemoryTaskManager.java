package ru.yandex.task_manager.service;

import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int idCounter = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;

    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void addNewTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void addNewEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addNewSubTask(SubTask subTask) {
        subTask.setId(getNextId());
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTask(subTask);
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        oldEpic.setSubject((epic.getSubject()));
        oldEpic.setDescription(epic.getDescription());
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTask(subTask);
        subTasks.put(subTask.getId(), subTask);
    }

    @Override
    public void removeTasks() {
        for (Integer taskId : tasks.keySet()) {
            historyManager.remove(taskId);
        }
        tasks.clear();
    }

    @Override
    public void removeEpics() {
        for (Integer subTaskId : subTasks.keySet()) {
            historyManager.remove(subTaskId);
        }
        for (Integer epicId : epics.keySet()) {
            historyManager.remove(epicId);
        }
        subTasks.clear();
        epics.clear();
    }

    @Override
    public void removeSubTasks() {
        epics.forEach((id, epic) -> epic.removeAllSubTasks());
        for (Integer subTaskId : subTasks.keySet()) {
            historyManager.remove(subTaskId);
        }
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpicById(int epicId) {
        Epic epic = epics.remove(epicId);
        historyManager.remove(epicId);
        if (epic == null) return;
        ArrayList<Integer> subTasksIds = epic.getSubTasksIds();
        for (Integer subTaskId : subTasksIds) {
            subTasks.remove(subTaskId);
            historyManager.remove(subTaskId);
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        SubTask subTask = subTasks.remove(id);
        if (subTask == null) return;
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTask(subTask);
        historyManager.remove(id);
    }

    @Override
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
