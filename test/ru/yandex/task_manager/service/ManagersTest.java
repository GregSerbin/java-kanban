package ru.yandex.task_manager.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    public void managerShouldReturnNonNullManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(historyManager, "Объект всегда должен быть проинициализирован.");
    }

}