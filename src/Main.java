import ru.yandex.task_manager.model.Epic;
import ru.yandex.task_manager.model.SubTask;
import ru.yandex.task_manager.model.Task;
import ru.yandex.task_manager.model.TaskStatus;
import ru.yandex.task_manager.service.Managers;
import ru.yandex.task_manager.service.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager inMemoryTaskManager = Managers.getDefault();

        Task task1 = new Task("Тестовая задача № 1", "", TaskStatus.NEW);
        inMemoryTaskManager.addNewTask(task1);
        Task task2 = new Task("Тестовая задача № 2", "", TaskStatus.NEW);
        inMemoryTaskManager.addNewTask(task2);

        Epic epic1 = new Epic("Тестовый эпик № 1", "");
        inMemoryTaskManager.addNewEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача № 1 для эпика № 1", "", TaskStatus.NEW, epic1.getId());
        inMemoryTaskManager.addNewSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача № 2 для эпика № 1", "", TaskStatus.NEW, epic1.getId());
        inMemoryTaskManager.addNewSubTask(subTask2);

        Epic epic2 = new Epic("Тестовый эпик № 2", "");
        inMemoryTaskManager.addNewEpic(epic2);
        SubTask subTask3 = new SubTask("Подзадача № 1 для эпика № 2", "", TaskStatus.NEW, epic2.getId());
        inMemoryTaskManager.addNewSubTask(subTask3);

        System.out.println("Изначальный список задач:");
        printTaskManagerEntities(inMemoryTaskManager);


        System.out.println("Проверка изменения статусов:");

        Task task1Modified = inMemoryTaskManager.getTasks().get(0);
        task1Modified.setStatus(TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateTask(task1Modified);

        Task task2Modified = inMemoryTaskManager.getTasks().get(1);
        task2Modified.setStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateTask(task2Modified);

        Epic epic1Modified = inMemoryTaskManager.getEpics().get(0);
        for (Integer subTaskId : epic1Modified.getSubTasksIds()) {
            SubTask subTask = inMemoryTaskManager.getSubTaskById(subTaskId);
            subTask.setStatus(TaskStatus.DONE);
            inMemoryTaskManager.updateSubTask(subTask);
        }
        inMemoryTaskManager.updateEpic(epic1Modified);

        Epic epic2Modified = inMemoryTaskManager.getEpics().get(1);
        for (Integer subTaskId : epic2Modified.getSubTasksIds()) {
            SubTask subTask = inMemoryTaskManager.getSubTaskById(subTaskId);
            subTask.setStatus(TaskStatus.IN_PROGRESS);
            inMemoryTaskManager.updateSubTask(subTask);
        }
        inMemoryTaskManager.updateEpic(epic2Modified);

        printTaskManagerEntities(inMemoryTaskManager);


        System.out.println("Проверка удаления и добавления подзадачи из эпика");

        epic1Modified = inMemoryTaskManager.getEpics().get(0);
        Integer subTaskForDeletionId = epic1Modified.getSubTasksIds().get(0);
        System.out.println("Должна удалиться подзадача с параметрами: " + inMemoryTaskManager.getSubTaskById(subTaskForDeletionId));
        inMemoryTaskManager.removeSubTaskById(subTaskForDeletionId);
        System.out.println(epic1Modified);
        System.out.println(inMemoryTaskManager.getSubTasks());

        SubTask newSubTask = new SubTask("Новая подзадача для эпика", "", TaskStatus.IN_PROGRESS, epic1Modified.getId());
        inMemoryTaskManager.addNewSubTask(newSubTask);
        System.out.println("Должна добавиться подзадача с параметрами: " + newSubTask);
        System.out.println(epic1Modified);
        System.out.println(inMemoryTaskManager.getSubTasks());
        System.out.println();


        System.out.println("Проверка удаления:");

        Task task = inMemoryTaskManager.getTasks().get(0);
        inMemoryTaskManager.removeTaskById(task.getId());

        Integer subTaskId = epic1Modified.getSubTasksIds().get(0);
        inMemoryTaskManager.removeSubTaskById(subTaskId);

        Epic epic = inMemoryTaskManager.getEpics().get(1);
        inMemoryTaskManager.removeEpicById(epic.getId());

        printTaskManagerEntities(inMemoryTaskManager);
    }

    private static void printTaskManagerEntities(TaskManager inMemoryTaskManager) {
        System.out.println(inMemoryTaskManager.getTasks());
        System.out.println(inMemoryTaskManager.getEpics());
        System.out.println(inMemoryTaskManager.getSubTasks());
        System.out.println();
    }
}
