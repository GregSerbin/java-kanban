import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;
import ru.yandex.task_manager.service.TaskManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        taskManager.createNewTask("Тестовая задача № 1", "", TaskStatus.NEW);
        taskManager.createNewTask("Тестовая задача № 2", "", TaskStatus.NEW);

        Epic firstEpic = taskManager.createNewEpic("Тестовый эпик № 1", "");
        taskManager.createNewSubTask("Подзадача № 1 для эпика № 1", "", TaskStatus.NEW, firstEpic.getId());
        taskManager.createNewSubTask("Подзадача № 2 для эпика № 1", "", TaskStatus.NEW, firstEpic.getId());

        Epic secondEpic = taskManager.createNewEpic("Тестовый эпик № 2", "");
        taskManager.createNewSubTask("Подзадача № 1 для эпика № 2", "", TaskStatus.NEW, secondEpic.getId());

        printTaskManagerEntities(taskManager);

        // Проверка изменения статусов
        Task firstTaskWithChangedStatus = taskManager.getTasks().get(0);
        firstTaskWithChangedStatus.setStatus(TaskStatus.IN_PROGRESS);

        Task secondTaskWithChangedStatus = taskManager.getTasks().get(1);
        secondTaskWithChangedStatus.setStatus(TaskStatus.DONE);

        firstEpic = taskManager.getEpics().get(0);
        ArrayList<SubTask> subTasks = firstEpic.getSubTasks();
        subTasks.forEach(subTask -> subTask.setStatus(TaskStatus.DONE));

        secondEpic = taskManager.getEpics().get(1);
        subTasks = secondEpic.getSubTasks();
        subTasks.forEach(subTask -> subTask.setStatus(TaskStatus.DONE));

        printTaskManagerEntities(taskManager);

        // Проверка удаления задач
        Task task = taskManager.getTasks().get(0);
        taskManager.removeTaskById(task.getId());

        Epic epic = taskManager.getEpics().get(0);
        taskManager.removeEpicById(epic.getId());

        printTaskManagerEntities(taskManager);
    }

    private static void printTaskManagerEntities(TaskManager taskManager) {
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());
        System.out.println();
    }
}
