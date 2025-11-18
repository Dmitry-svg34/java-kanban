package taskmanager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class CsvTaskHelper {

    private CsvTaskHelper() {
    }

    public static String parseToString(Task task) {
        StringBuilder line = new StringBuilder();
        line.append(task.getId());
        line.append(",");
        line.append(task.getType());
        line.append(",");
        line.append(task.getName());
        line.append(",");
        line.append(task.getTaskStatus());
        line.append(",");
        line.append(task.getDescription());
        line.append(",");

        if (task instanceof SubTask subTask) {
            line.append(subTask.getEpicId());
        }
        return line.toString();
    }

    public static Task parseFromString(String line) {
        String[] massiveTransform = line.split(",");
        int id = Integer.parseInt(massiveTransform[0]);
        String type = massiveTransform[1].trim();
        String name = massiveTransform[2].trim();
        String description = massiveTransform[3].trim();
        String status = massiveTransform[4].trim();
        int epicIdSub = -1;
        if (massiveTransform.length > 5 && !massiveTransform[5].trim().isEmpty()) {
            epicIdSub = Integer.parseInt(massiveTransform[5]);
        }

        TaskType taskType = TaskType.valueOf(type);
        switch (taskType) {
            case TASK:
                Task task = new Task(name, description, TaskStatus.NEW);
                task.setId(id);
                task.setTaskStatus(TaskStatus.valueOf(status));
                return task;

            case EPIC:
                Epic epic = new Epic(name, description);
                epic.setId(id);
                epic.setTaskStatus(TaskStatus.valueOf(status));
                return epic;

            case SUBTASK:
                SubTask subTask = new SubTask(name, description, epicIdSub);
                subTask.setId(id);
                subTask.setEpicId(epicIdSub);
                subTask.setTaskStatus(TaskStatus.NEW);
                subTask.setTaskStatus(TaskStatus.valueOf(status));
                return subTask;

            default:
                System.out.println("Неизвестный тип задачи: " + type);
                return null;
        }
    }

}
