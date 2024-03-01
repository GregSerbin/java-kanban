package ru.yandex.task_manager.service;

public class Managers {

    private static InMemoryHistoryManager inMemoryHistoryManager;

    private Managers() {
    }

    public static HistoryManager getDefaultHistory() {
        if (inMemoryHistoryManager == null) inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}
