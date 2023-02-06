package ru.yandex.sprint3.alltask.manager;

import ru.yandex.sprint3.alltask.entity.Epic;
import ru.yandex.sprint3.alltask.entity.Status;
import ru.yandex.sprint3.alltask.entity.Subtask;
import ru.yandex.sprint3.alltask.entity.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager {
    Scanner sc = new Scanner(System.in);
    private static HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private static HashMap<Integer, Epic> epicHashMap = new HashMap<>();

    public HashMap<Integer, Epic> getEpicHashMap() {
        return epicHashMap;
    }

    public HashMap<Integer, Task> getTaskHashMap() {
        return taskHashMap;
    }

    public void addEpic(String name, String description) {
        Epic epic = new Epic(name, description, Status.NEW);
        addToDatabase(epic);
    }

    public void showAllEpic() {
        for (Integer idEpic : epicHashMap.keySet()) {
            System.out.println(epicHashMap.get(idEpic));
        }
    }

    public void addSubtaskToEpic(int id, String name, String description) {
        Epic epic = epicHashMap.get(id);
        epic.getSubtasksList().add(new Subtask(name, description, Status.NEW, epic.getId()));
        System.out.println("Задача добавлена");
    }

    public void showSubtaskToEpic() {
        try {
            System.out.println("Введите id Epic");
            for (Subtask subtask : epicHashMap.get(sc.nextInt()).getSubtasksList()) {
                System.out.println(subtask.getId() + " " + subtask.getName() + " " + subtask.getDescription() + " " + subtask.getStatus());
            }

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("Неверно введенный id");

        }
    }

    public void addTask(String name, String description) {
        Task task = new Task(name, description, Status.NEW);
        addToDatabase(task);
        System.out.println("Задача добавлена");
    }

    public void showAllSubtask() {
        for (int exp : epicHashMap.keySet()) {
            for (Subtask sub : epicHashMap.get(exp).getSubtasksList()) {
                System.out.println(sub);
            }
        }
    }

    public void showAllTask() {
        for (Integer idTask : taskHashMap.keySet()) {
            System.out.println(taskHashMap.get(idTask));
        }
    }

    public void removeAllTask() {
        taskHashMap.values().forEach(item -> {
            Managers.getDefaultHistory().remove(Managers.getDefaultHistory().getHistory().indexOf(item));
        });
        taskHashMap.clear();
        System.out.println("Все задачи удалены");
    }

    @Override
    public Task getTask(int id) {
        return taskHashMap.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        return epicHashMap.get(id);
    }

    @Override
    public Subtask getSubTask(int id) {
        Subtask sub1 = null;
        for (int exp : epicHashMap.keySet()) {
            for (Subtask sub : epicHashMap.get(exp).getSubtasksList()) {
                if (sub.getId() == id) {

                    sub1 = sub;
                }
            }
        }
        return sub1;
    }

    public void showEpicById(int id) {
        Epic epic = epicHashMap.get(id);
        Managers.getDefaultHistory().addTask(epic);
        System.out.println(epic);
    }

    public void showTaskById(int id) {
        Task task;
        try {
            task = taskHashMap.get(id);
            if (task == null) {
                System.out.println("Вы ввели не существующий id");
            } else {
                Managers.getDefaultHistory().addTask(task);
                System.out.println(task);
            }

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("Exception was processed. Program continues");
        }

    }

    public void showSubTaskById(int id) {
        for (int exp : epicHashMap.keySet()) {
            for (Subtask sub : epicHashMap.get(exp).getSubtasksList()) {
                if (sub.getId() == id) {
                    Managers.getDefaultHistory().addTask(sub);
                    System.out.println(sub);
                }
            }
        }
    }

    public void removeTaskById(int id) {


        for (Task ts : Managers.getDefaultHistory().getHistory()) {

            if (ts instanceof Epic) {
                System.out.println("1445");

            } else {
                if (id == ts.getId()) {
                    Managers.getDefaultHistory().remove(ts.getId());
                }
            }


        }
        taskHashMap.remove(id);

        System.out.println("Удалено");
    }

    public void removeEpicById(int id) {
        Managers.getDefaultHistory().remove(id);
        for (Task ts : Managers.getDefaultHistory().getHistory()) {
            Subtask subtask;
            if (ts instanceof Subtask) {
                subtask = (Subtask) ts;
                if (subtask.getIdEpic() == id) {
                    Managers.getDefaultHistory().remove(ts.getId());
                }

            }


        }
        epicHashMap.remove(id);
        System.out.println("Удалено");
    }

    public void removeAllEpic() {
        epicHashMap.values().forEach(item -> {
            Managers.getDefaultHistory().remove(Managers.getDefaultHistory().getHistory().indexOf(item));
        });
        epicHashMap.clear();
        System.out.println("Все задачи Epic удалены");
    }


    public void removeAllSabtaskByEpicId(int id) {
        epicHashMap.get(id).getSubtasksList().clear();
        System.out.println("Удалено");

    }

    public void updateSubtask(int id_epic, int id_subtask, Status status) {
        Epic epic = epicHashMap.get(id_epic);

        Subtask subtask1 = epic.getSubtasksList().stream().filter(subtask2 -> id_subtask == subtask2.getId()).findAny().orElse(null);

        epic.getSubtasksList().removeIf(subtask2 -> subtask2.getId() == id_subtask);
        if (subtask1 != null) {
            subtask1.setStatus(status);
            epic.getSubtasksList().add(subtask1);

            changeStatus(epic);
        } else {
            System.out.println("Нулевое значение");
        }
    }

    public void removeSubtask(int id_subtask) {
        Subtask subtask1 = null;
        for (int integer : epicHashMap.keySet()) {
            subtask1 = epicHashMap.get(integer).getSubtasksList().stream().filter(subtask2 -> id_subtask == subtask2.getId()).findAny().orElse(null);
            epicHashMap.get(integer).getSubtasksList().removeIf(subtask2 -> subtask2.getId() == id_subtask);
        }
        if (subtask1 != null) {
            Epic epic = epicHashMap.get(subtask1.getIdEpic());
            System.out.println("Задача удалена");
            changeStatus(epic);

        } else {
            System.out.println("Вы не выбрали задачу на удаление");
        }


    }

    public void changeStatus(Epic epic) {
        long count = epic.getSubtasksList().stream().filter(subtask -> subtask.getStatus().equals(Status.DONE)).count();
        if (count == epic.getSubtasksList().size()) {
            epic.setStatus(Status.DONE);

        } else {
            epic.setStatus(Status.NEW);

        }
        for (Subtask sub : epic.getSubtasksList()) {
            if (sub.getStatus().equals(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    public void updateByTask(Task task) {
        taskHashMap.remove(task.getId());
        taskHashMap.put(task.getId(), task);
    }

    public void updateByEpic(Epic epic) {
        epicHashMap.remove(epic.getId());
        epicHashMap.put(epic.getId(), epic);
    }

    private void addToDatabase(Task task) {
        if (task instanceof Epic) {

            epicHashMap.put(task.getId(), (Epic) task);
        } else {
            taskHashMap.put(task.getId(), task);
        }
    }

}
