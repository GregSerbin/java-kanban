import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;
import ru.yandex.task_manager.service.TaskManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Тестовая задача № 1", "", TaskStatus.NEW);
        taskManager.addNewTask(task1);
        Task task2 = new Task("Тестовая задача № 2", "", TaskStatus.NEW);
        taskManager.addNewTask(task2);

        Epic epic1 = new Epic("Тестовый эпик № 1", "");
        taskManager.addNewEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача № 1 для эпика № 1", "", TaskStatus.NEW, epic1.getId());
        taskManager.addNewSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача № 2 для эпика № 1", "", TaskStatus.NEW, epic1.getId());
        taskManager.addNewSubTask(subTask2);

        Epic epic2 = new Epic("Тестовый эпик № 2", "");
        taskManager.addNewEpic(epic2);
        SubTask subTask3 = new SubTask("Подзадача № 1 для эпика № 2", "", TaskStatus.NEW, epic2.getId());
        taskManager.addNewSubTask(subTask3);

        System.out.println("Изначальный список задач:");
        printTaskManagerEntities(taskManager);


        System.out.println("Проверка изменения статусов:");

        Task task1Modified = taskManager.getTasks().get(0);
        task1Modified.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task1Modified);

        Task task2Modified = taskManager.getTasks().get(1);
        task2Modified.setStatus(TaskStatus.DONE);
        taskManager.updateTask(task2Modified);

        Epic epic1Modified = taskManager.getEpics().get(0);
        for (Integer subTaskId : epic1Modified.getSubTasksIds()) {
            SubTask subTask = taskManager.getSubTaskById(subTaskId);
            subTask.setStatus(TaskStatus.DONE);
            taskManager.updateSubTask(subTask);
        }
        taskManager.updateEpic(epic1Modified);

        Epic epic2Modified = taskManager.getEpics().get(1);
        for (Integer subTaskId : epic2Modified.getSubTasksIds()) {
            SubTask subTask = taskManager.getSubTaskById(subTaskId);
            subTask.setStatus(TaskStatus.IN_PROGRESS);
            taskManager.updateSubTask(subTask);
        }
        taskManager.updateEpic(epic2Modified);

        printTaskManagerEntities(taskManager);


        System.out.println("Проверка удаления и добавления подзадачи из эпика");

        epic1Modified = taskManager.getEpics().get(0);
        Integer subTaskForDeletionId = epic1Modified.getSubTasksIds().get(0);
        System.out.println("Должна удалиться подзадача с параметрами: " + taskManager.getSubTaskById(subTaskForDeletionId));
        taskManager.removeSubTaskById(subTaskForDeletionId);
        System.out.println(epic1Modified);
        System.out.println(taskManager.getSubTasks());

        SubTask newSubTask = new SubTask("Новая подзадача для эпика", "", TaskStatus.IN_PROGRESS, epic1Modified.getId());
        taskManager.addNewSubTask(newSubTask);
        System.out.println("Должна добавиться подзадача с параметрами: " + newSubTask);
        System.out.println(epic1Modified);
        System.out.println(taskManager.getSubTasks());
        System.out.println();


        System.out.println("Проверка удаления:");

        Task task = taskManager.getTasks().get(0);
        taskManager.removeTaskById(task.getId());

        Integer subTaskId = epic1Modified.getSubTasksIds().get(0);
        taskManager.removeSubTaskById(subTaskId);

        Epic epic = taskManager.getEpics().get(1);
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
