package ru.yandex.task_manager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void taskShouldBeRemovedFromInMemoryTaskManagerAndFromInMemoryHistoryManager() {
        Task newTask = new Task("Task subject", "Task description", TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.addNewTask(newTask);
        inMemoryTaskManager.getTaskById(newTask.getId());
        inMemoryTaskManager.removeTaskById(newTask.getId());

        for (Task task : inMemoryTaskManager.getTasks()) {
            assertNotEquals(newTask.getId(), task.getId());
        }

        for (Task task : inMemoryTaskManager.getHistory()) {
            assertNotEquals(newTask.getId(), task.getId());
        }
    }

    @Test
    public void epicWithSubTasksShouldBeRemovedFromInMemoryTaskManagerAndFromInMemoryHistoryManager() {
        Epic newEpic = new Epic("Epic subject", "Epic description");
        inMemoryTaskManager.addNewEpic(newEpic);
        SubTask newSubTask = new SubTask("Subtask subject", "Subtask description", TaskStatus.IN_PROGRESS, newEpic.getId());
        inMemoryTaskManager.addNewSubTask(newSubTask);
        inMemoryTaskManager.getEpicById(newEpic.getId());
        inMemoryTaskManager.getSubTaskById(newSubTask.getId());

        inMemoryTaskManager.removeEpicById(newEpic.getId());

        for (Epic epic : inMemoryTaskManager.getEpics()) {
            assertNotEquals(newEpic.getId(), epic.getId());
        }
        for (SubTask subTask : inMemoryTaskManager.getSubTasks()) {
            assertNotEquals(newSubTask.getId(), subTask.getId());
        }

        for (Task task : inMemoryTaskManager.getHistory()) {
            assertNotEquals(newEpic.getId(), task.getId());
            assertNotEquals(newSubTask.getId(), task.getId());
        }
    }

    @Test
    public void subTasksShouldBeRemovedFromInMemoryTaskManagerAndFromInMemoryHistoryManager() {
        Epic newEpic = new Epic("Epic subject", "Epic description");
        inMemoryTaskManager.addNewEpic(newEpic);
        SubTask newSubTask = new SubTask("Subtask subject", "Subtask description", TaskStatus.IN_PROGRESS, newEpic.getId());
        inMemoryTaskManager.addNewSubTask(newSubTask);
        inMemoryTaskManager.getSubTaskById(newSubTask.getId());

        inMemoryTaskManager.removeSubTaskById(newSubTask.getId());

        for (SubTask subTask : inMemoryTaskManager.getSubTasks()) {
            assertNotEquals(newSubTask.getId(), subTask.getId());
        }

        for (Task task : inMemoryTaskManager.getHistory()) {
            assertNotEquals(newSubTask.getId(), task.getId());
        }
    }

    @Test
    public void tasksShouldBeRemovedFromInMemoryTaskManagerAndFromInMemoryHistoryManager() {
        ArrayList<Task> originalTasks = new ArrayList<>();
        for (int i = 0; i < 2; i++) { // создаем 2 задачи для теста
            Task task = new Task("Заголовок задачи № " + i, "Описание задачи № " + i, TaskStatus.NEW);
            inMemoryTaskManager.addNewTask(task);
            inMemoryTaskManager.getTaskById(task.getId());
            originalTasks.add(task);
        }

        inMemoryTaskManager.removeTasks();
        List<Task> tasksFromHistoryManager = inMemoryTaskManager.getHistory();

        assertEquals(0, inMemoryTaskManager.getTasks().size(), "Все таски должны быть удалены из менеджера задач");
        assertFalse(tasksFromHistoryManager.containsAll(originalTasks), "Все таски должны быть удалены из истории просмотра задач");
    }

    @Test
    public void epicsAndSubTasksShouldBeRemovedFromInMemoryTaskManagerAndFromInMemoryHistoryManager() {
        Epic newEpic = new Epic("Epic subject", "Epic description");
        inMemoryTaskManager.addNewEpic(newEpic);
        inMemoryTaskManager.getEpicById(newEpic.getId());
        SubTask newSubTask = new SubTask("Subtask subject", "Subtask description", TaskStatus.IN_PROGRESS, newEpic.getId());
        inMemoryTaskManager.addNewSubTask(newSubTask);
        inMemoryTaskManager.getSubTaskById(newSubTask.getId());

        inMemoryTaskManager.removeEpics();
        List<Task> tasksFromHistoryManager = inMemoryTaskManager.getHistory();

        assertEquals(0, inMemoryTaskManager.getEpics().size(), "Все эпики должны быть удалены из менеджера задач");
        assertEquals(0, inMemoryTaskManager.getSubTasks().size(), "Все сабтаски должны быть удалены из менеджера задач");
        assertFalse(tasksFromHistoryManager.contains(newSubTask), "Все сабтаски должны быть удалены из истории просмотра задач");
        assertFalse(tasksFromHistoryManager.contains(newEpic), "Все эпики должны быть удалены из истории просмотра задач");
    }
}