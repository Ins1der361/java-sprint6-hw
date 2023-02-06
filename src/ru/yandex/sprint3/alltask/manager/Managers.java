package ru.yandex.sprint3.alltask.manager;


import ru.yandex.sprint3.alltask.entity.Epic;
import ru.yandex.sprint3.alltask.entity.Subtask;
import ru.yandex.sprint3.alltask.entity.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Managers {
    private static InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private static InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    public static InMemoryTaskManager getDefaultManager() {
        return inMemoryTaskManager;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        List<String> allLine = new ArrayList<>();
        if (file.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                while (bufferedReader.ready()) {
                    String line = bufferedReader.readLine();
                    allLine.add(line);
                }
                allLine.remove(0);
                boolean check = true;
                Iterator<String> iterator = allLine.iterator();
                while (iterator.hasNext()) {
                    String line = iterator.next();
                    if (line.isEmpty()) {

                        iterator.remove();
                        check = false;
                    } else {
                        if (check) {
                            Task task = fileBackedTasksManager.fromString(line);
                            if (task instanceof Epic) {
                                fileBackedTasksManager.getEpicHashMap().put(task.getId(), (Epic) task);
                            } else if (task instanceof Subtask) {
                                int idEpic = ((Subtask) task).getIdEpic();
                                System.out.println();
                                fileBackedTasksManager.getEpicHashMap().get(idEpic).getSubtasksList().add((Subtask) task);


                            } else if (task instanceof Task) {

                                fileBackedTasksManager.getTaskHashMap().put(task.getId(), task);
                            }
                        } else {
                            for (int idTask : FileBackedTasksManager.historyFromString(line)) {
                                Task task = fileBackedTasksManager.getTask(idTask);
                                Task task1 = fileBackedTasksManager.getEpic(idTask);
                                Task task3 = fileBackedTasksManager.getSubTask(idTask);
                                if (task != null) {
                                    historyManager.addTask(task);
                                } else if (task1 != null) historyManager.addTask(task1);
                                else if (task3 != null) historyManager.addTask(task3);

                            }

                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return fileBackedTasksManager;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }

}
