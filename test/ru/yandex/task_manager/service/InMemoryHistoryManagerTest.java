package ru.yandex.task_manager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager inMemoryHistoryManager;

    @BeforeEach
    void createInMemoryTaskManager() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }

    @Test
    public void taskShouldBeAddedToTheEndOfHistory() {

        ArrayList<Task> originalTasks = new ArrayList<>();
        for (int i = 0; i < 2; i++) { // создаем 2 задачи для теста
            Task task = new Task("Заголовок задачи № " + i, "Описание задачи № " + i, TaskStatus.NEW);
            task.setId(i);
            originalTasks.add(task);

            inMemoryHistoryManager.add(task);
        }

        List<Task> tasksFromHistoryManager = inMemoryHistoryManager.getHistory();
        for (int i = 0; i < originalTasks.size(); i++) {
            assertEquals(originalTasks.get(i).getId(), tasksFromHistoryManager.get(i).getId(), "Новые задачи в истории должны добавляться в конец.");
        }
    }

    @Test
    public void maxHistorySizeShouldBe10() {
        int numberOfTasksMoreThan10 = 20;
        for (int i = 0; i < numberOfTasksMoreThan10; i++) {
            Task task = new Task("Заголовок задачи № " + i, "Описание задачи № " + i, TaskStatus.NEW);
            inMemoryHistoryManager.add(task);
        }

        assertEquals(10,inMemoryHistoryManager.getHistory().size(), "Максимальный размер истории должен быть равен 10.");
    }


}