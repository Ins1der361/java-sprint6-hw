package ru.yandex.sprint3.alltask.manager;

import ru.yandex.sprint3.alltask.entity.Task;

import java.util.List;

public interface HistoryManager {

    void addTask(Task task);

    void remove(int id);

    List<Task> getHistory();



}
