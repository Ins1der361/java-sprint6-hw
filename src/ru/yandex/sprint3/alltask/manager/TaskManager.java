package ru.yandex.sprint3.alltask.manager;

import ru.yandex.sprint3.alltask.entity.Epic;
import ru.yandex.sprint3.alltask.entity.Status;
import ru.yandex.sprint3.alltask.entity.Subtask;
import ru.yandex.sprint3.alltask.entity.Task;

import java.io.IOException;

public interface TaskManager {

    void addEpic(String name, String description) throws ManagerSaveException, IOException;

    void showAllEpic();

    void addSubtaskToEpic(int id, String name, String description) throws ManagerSaveException, IOException;

    void showSubtaskToEpic();

    void addTask(String name, String description) throws ManagerSaveException, IOException;

    void showAllSubtask();

    void showAllTask();

    void removeAllTask() throws ManagerSaveException, IOException;
    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubTask(int id);

    void showEpicById(int id);

    void showTaskById(int id);

    void removeTaskById(int id) throws ManagerSaveException, IOException;

    void removeEpicById(int id) throws ManagerSaveException, IOException;

    void removeAllEpic() throws ManagerSaveException, IOException;

    void removeAllSabtaskByEpicId(int id) throws ManagerSaveException, IOException;

    void updateSubtask(int id_epic, int id_subtask, Status status) throws ManagerSaveException, IOException;

    void removeSubtask(int id_subtask) throws ManagerSaveException, IOException;

    void updateByTask(Task  task) throws ManagerSaveException, IOException;

    void updateByEpic(Epic epic) throws ManagerSaveException, IOException;

}
