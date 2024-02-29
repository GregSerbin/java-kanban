package ru.yandex.task_manager.service;

import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    List<Task> getHistory();

    void addNewTask(Task task);

    void addNewEpic(Epic epic);

    void addNewSubTask(SubTask subTask);

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubTasks();

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    void removeTasks();

    void removeEpics();

    void removeSubTasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    void removeTaskById(int id);

    void removeEpicById(int epicId);

    void removeSubTaskById(int id);

    ArrayList<SubTask> getEpicSubTasks(int epicId);
}
