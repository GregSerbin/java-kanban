package ru.yandex.task_manager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void tasksShouldBeEqualIfIdsAreEqual() {
        Task task1 = new Task("Заголовок 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task1.setId(1);
        Task task2 = new Task("Заголовок 2", "Описание 2", TaskStatus.IN_PROGRESS);
        task2.setId(1);

        assertEquals(task1, task2, "Таски должны совпадать, если совпадают их id.");
    }

    @Test
    public void subTasksShouldBeEqualIfIdsAreEqual() {
        SubTask subTask1 = new SubTask("Заголовок 1", "Описание 1", TaskStatus.IN_PROGRESS, 1);
        subTask1.setId(1);
        SubTask subTask2 = new SubTask("Заголовок 2", "Описание 2", TaskStatus.IN_PROGRESS, 1);
        subTask2.setId(1);

        assertEquals(subTask1, subTask2, "Подзадачи должны совпадать, если совпадают их id.");
    }

    @Test
    public void epicsShouldBeEqualIfIdsAreEqual() {
        Epic epic1 = new Epic("Заголовок 1", "Описание 1");
        epic1.setId(1);
        Epic epic2 = new Epic("Заголовок 2", "Описание 2");
        epic2.setId(1);

        assertEquals(epic1, epic2, "Эпики должны совпадать, если совпадают их id.");
    }

}