package ru.yandex.task_manager.service;

import ru.yandex.task_manager.model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();
}