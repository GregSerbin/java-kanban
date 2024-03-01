package ru.yandex.task_manager.service;

import ru.yandex.task_manager.model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class InMemoryHistoryManager implements HistoryManager {

    private final int MAX_TASKS_IN_HISTORY = 10;
    LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task == null) return;
        if (history.size() == MAX_TASKS_IN_HISTORY) history.removeFirst();

        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
