package ru.yandex.sprint3.alltask.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {


    private ArrayList<Subtask> subtasksList = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);


    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);


    }

    public ArrayList<Subtask> getSubtasksList() {
        return subtasksList;
    }


    @Override
    public String toString() {
        taskType = "EPIC";
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksList, epic.subtasksList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksList);
    }
}



