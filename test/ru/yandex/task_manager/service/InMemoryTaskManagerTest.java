package ru.yandex.task_manager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    public void createNewInMemoryTaskManager() {
        inMemoryTaskManager = new InMemoryTaskManager();
    }

    @Test
    public void taskShouldBeAddedToInMemoryTaskManager() {
        Task task = new Task("", "", TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.addNewTask(task);
        assertEquals(1, inMemoryTaskManager.getTasks().size(), "Должен добавляться таск.");
    }

    @Test
    public void subTaskShouldBeAddedToInMemoryTaskManager() {
        Epic epic = new Epic("", "");
        inMemoryTaskManager.addNewEpic(epic);
        SubTask subTask = new SubTask("", "", TaskStatus.IN_PROGRESS, epic.getId());
        inMemoryTaskManager.addNewSubTask(subTask);
        assertEquals(1, inMemoryTaskManager.getSubTasks().size(), "Должен добавляться сабтаск.");
    }

    @Test
    public void epicShouldBeAddedToInMemoryTaskManager() {
        Epic epic = new Epic("", "");
        inMemoryTaskManager.addNewEpic(epic);
        assertEquals(1, inMemoryTaskManager.getEpics().size(), "Должен добавляться эпик.");
    }

    @Test
    public void shouldFindTaskByIdInMemoryTaskManager() {
        Task task = new Task("", "", TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.addNewTask(task);
        Task foundTask = inMemoryTaskManager.getTaskById(0); // ID выдает менеджер, всегда начиная с 0
        assertEquals(task, foundTask, "Таски должны совпадать.");
    }

    @Test
    public void shouldFindSubTaskByIdInMemoryTaskManager() {
        Epic epic = new Epic("", "");
        inMemoryTaskManager.addNewEpic(epic);
        SubTask subTask = new SubTask("", "", TaskStatus.IN_PROGRESS, epic.getId());
        inMemoryTaskManager.addNewSubTask(subTask);
        SubTask foundSubTask = inMemoryTaskManager.getSubTaskById(1); // ID выдает менеджер, начиная с 0 последовательно. 0 - epic, 1 - subtask
        assertEquals(subTask, foundSubTask, "Сабтаски должны совпадать.");
    }

    @Test
    public void shouldFindEpicByIdInMemoryTaskManager() {
        Epic epic = new Epic("", "");
        inMemoryTaskManager.addNewEpic(epic);
        Epic foundEpic = inMemoryTaskManager.getEpicById(0); // ID выдает менеджер, всегда начиная с 0
        assertEquals(epic, foundEpic, "Эпики должны совпадать.");
    }


}