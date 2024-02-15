import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task firstTask = new Task("Тестовая задача № 1", "", TaskStatus.NEW, taskManager.getNextId());
        taskManager.createTask(firstTask);

        Task secondTask = new Task("Тестовая задача № 2", "", TaskStatus.NEW, taskManager.getNextId());
        taskManager.createTask(secondTask);

        Epic firstEpic = new Epic("Тестовый эпик № 1", "", taskManager.getNextId());
        taskManager.createEpic(firstEpic);

        SubTask firstSubTaskForFirstEpic = new SubTask("Подзадача № 1 для эпика № 1", "", TaskStatus.NEW, taskManager.getNextId(), firstEpic);
        firstEpic.addSubTask(firstSubTaskForFirstEpic);
        taskManager.createSubTask(firstSubTaskForFirstEpic);

        SubTask secondSubTaskForFirstEpic = new SubTask("Подзадача № 2 для эпика № 1", "", TaskStatus.NEW, taskManager.getNextId(), firstEpic);
        firstEpic.addSubTask(secondSubTaskForFirstEpic);
        taskManager.createSubTask(secondSubTaskForFirstEpic);

        Epic secondEpic = new Epic("Тестовый эпик № 2", "", taskManager.getNextId());
        taskManager.createEpic(secondEpic);

        SubTask firstSubTaskForSecondEpic = new SubTask("Подзадача № 1 для эпика № 2", "", TaskStatus.NEW, taskManager.getNextId(), secondEpic);
        secondEpic.addSubTask(firstSubTaskForSecondEpic);
        taskManager.createSubTask(firstSubTaskForSecondEpic);


        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println();

        // Проверка изменения статусов
        Task firstTaskWithChangedStatus = new Task(firstTask.getSubject(), firstTask.getDescription(), TaskStatus.IN_PROGRESS, firstTask.getId());
        taskManager.updateTask(firstTaskWithChangedStatus);

        Task secondTaskWithChangedStatus = new Task(secondTask.getSubject(), secondTask.getDescription(), TaskStatus.DONE, secondTask.getId());
        taskManager.updateTask(secondTaskWithChangedStatus);

        ArrayList<SubTask> subTasks = firstEpic.getSubtasks();
        ArrayList<SubTask> newSubTasks = new ArrayList<>();
        for (SubTask subTask : subTasks) {
            SubTask newSubTask = new SubTask(subTask.getSubject(), subTask.getDescription(), TaskStatus.IN_PROGRESS, subTask.getId(), firstEpic);
            newSubTasks.add(newSubTask);
        }
        for (SubTask newSubTask : newSubTasks) {
            firstEpic.updateSubTask(newSubTask);
            taskManager.updateSubTask(newSubTask);
        }

        subTasks = secondEpic.getSubtasks();
        newSubTasks.clear();
        for (SubTask subTask : subTasks) {
            SubTask newSubTask = new SubTask(subTask.getSubject(), subTask.getDescription(), TaskStatus.DONE, subTask.getId(), secondEpic);
            newSubTasks.add(newSubTask);
        }
        for (SubTask newSubTask : newSubTasks) {
            secondEpic.updateSubTask(newSubTask);
            taskManager.updateSubTask(newSubTask);
        }

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println();

        // Проверка удаления задач
        Task task = taskManager.getTasks().get(0);
        taskManager.removeTaskById(task.getId());

        Epic epic = taskManager.getEpics().get(0);
        taskManager.removeEpicById(epic.getId());

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
    }
}
