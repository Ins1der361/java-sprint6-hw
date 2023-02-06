package ru.yandex.sprint3.alltask.manager;

import ru.yandex.sprint3.alltask.entity.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private File file;
    private static String HEAD = "id,type,name,status,description,epic" + "\n";

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {


        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException e) {

            throw new ManagerSaveException("Не удалось найти файл для записи данных");


        }

        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEAD);
            for (Task task : Managers.getDefaultManager().getTaskHashMap().values()) {
                writer.write(toString(task));
            }
            for (Epic epic : Managers.getDefaultManager().getEpicHashMap().values()) {
                writer.write(toString(epic));
                for (Subtask subTask : epic.getSubtasksList()
                ) {
                    writer.write(toString(subTask));
                }
            }
            writer.write("\n");
            writer.write("\n" + historyToString(Managers.getDefaultHistory()));
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в  файл ");
        }
    }

    @Override
    public void addEpic(String name, String description) {
        super.addEpic(name, description);
        save();

    }

    @Override
    public void addSubtaskToEpic(int id, String name, String description) {
        super.addSubtaskToEpic(id, name, description);
        save();

    }


    @Override
    public void addTask(String name, String description) {
        super.addTask(name, description);
        save();

    }

    @Override
    public Task getTask(int id) {
        return super.getTask(id);
    }

    @Override
    public Epic getEpic(int id) {
        return super.getEpic(id);
    }

    @Override
    public Subtask getSubTask(int id) {
        return super.getSubTask(id);
    }

    @Override
    public void showEpicById(int id) {
        super.showEpicById(id);
        save();
    }

    @Override
    public void showTaskById(int id) {
        super.showTaskById(id);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }


    @Override
    public void removeAllSabtaskByEpicId(int id) {
        super.removeAllSabtaskByEpicId(id);
        save();
    }

    @Override
    public void updateSubtask(int id_epic, int id_subtask, Status status) {
        super.updateSubtask(id_epic, id_subtask, status);
        save();
    }

    @Override
    public void removeSubtask(int id_subtask) {
        super.removeSubtask(id_subtask);
        save();
    }

    @Override
    public void updateByTask(Task task) {
        super.updateByTask(task);
        save();

    }

    @Override
    public void updateByEpic(Epic epic) {
        super.updateByEpic(epic);
        save();

    }


    public String toString(Task task) {
        if (task instanceof Epic epic) {
            return epic.toString();
        } else if (task instanceof Subtask subtask) {
            return subtask.toString();
        } else {
            return task.toString();
        }
    }

    public Task fromString(String value) {
        String[] parts = value.split(",");
        if (TypeTask.TASK.toString().equals(parts[1])) {
            return new Task(Integer.parseInt(parts[0]), parts[2], parts[4], Status.valueOf(parts[3]));
        } else if (TypeTask.EPIC.toString().equals(parts[1])) {
            return new Epic(Integer.parseInt(parts[0]), parts[2], parts[4], Status.valueOf(parts[3]));
        } else {

            return new Subtask(Integer.parseInt(parts[0]), parts[2], parts[4], Status.valueOf(parts[3]), Integer.parseInt(parts[5]));
        }

    }

    static String historyToString(HistoryManager manager) {
        return manager.toString();

    }

    static List<Integer> historyFromString(String value) {
        List<Integer> historyTaskID = new ArrayList<>();
        if (value != null) {
            String[] parts = value.split(",");
            for (String id : parts) {
                historyTaskID.add(Integer.parseInt(id));
            }
        }
        return historyTaskID;
    }

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = Managers.getDefaultManager();
        //  FileBackedTasksManager inMemoryTaskManager = Managers.loadFromFile(new File("test.csv"));
        inMemoryTaskManager.addTask("Задача 1", "задача 1");
        inMemoryTaskManager.addTask("Задача 2", "задача 2");
        //Создание Эпиков
        inMemoryTaskManager.addEpic("Эпик 1", "Описание 1");
        inMemoryTaskManager.addEpic("Эпик 2", "Описание 2");
        //Создание Подзадачи для Эпика 1
        inMemoryTaskManager.addSubtaskToEpic(3, "подзадача 1-1", "Описание подзадача 1");
        inMemoryTaskManager.addSubtaskToEpic(3, "подзадача 2-1", "Описание подзадача 2");
        inMemoryTaskManager.addSubtaskToEpic(3, "подзадача 3-1", "Описание подзадача 3");
        inMemoryTaskManager.addSubtaskToEpic(3, "подзадача 3-1", "Описание подзадача 3");
        System.out.println("Вывод всех добавленных задач: ");
        inMemoryTaskManager.showSubTaskById(7);
        inMemoryTaskManager.showEpicById(3);
        inMemoryTaskManager.showTaskById(1);
        inMemoryTaskManager.showSubTaskById(5);
        inMemoryTaskManager.showTaskById(2);
        inMemoryTaskManager.showEpicById(4);
        System.out.println("Вывод истории: ");
        System.out.println(Managers.getDefaultHistory().getHistory());
        System.out.println(Managers.getDefaultHistory().toString());
        FileBackedTasksManager fileBackedTasksManager = Managers.loadFromFile(new File("test.csv"));
        fileBackedTasksManager.save();
        System.out.println(Managers.getDefaultHistory().getHistory());
        System.out.println(Managers.getDefaultHistory().toString());

        Task task22 = fileBackedTasksManager.getTask(2);
        System.out.println(task22);
        task22.setDescription("222");
        fileBackedTasksManager.updateByTask(task22);
        fileBackedTasksManager.save();
        fileBackedTasksManager.showAllTask();

        // Надеюсь правильно
    }
}

