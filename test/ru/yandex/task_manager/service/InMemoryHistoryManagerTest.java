package ru.yandex.task_manager.service;

import org.junit.jupiter.api.Test;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    public void changedTaskShouldNotBeEqualInInMemoryHistoryManager() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Subject", "Description", TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.addNewTask(task);
        inMemoryTaskManager.getTaskById(0); // ID всегда назначается по порядку с 0

        task.setDescription("New Description");
        inMemoryTaskManager.addNewTask(task);
        inMemoryTaskManager.getTaskById(1);
        List<Task> tasks = inMemoryTaskManager.getHistory();

        assertEquals(tasks.get(0), tasks.get(1), "Версии измененной таски в истории не должны совпадать.");
    }
}