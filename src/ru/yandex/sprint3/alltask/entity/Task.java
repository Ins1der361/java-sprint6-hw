package ru.yandex.sprint3.alltask.entity;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private Status status;
    protected String taskType = "TASK";
    protected int id;
    protected static int counter = 1;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = counter++;
    }

    public Task(int id, String name, String description, Status status) {
        if (counter < id) counter = id;
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;

    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return getId() + "," + taskType + "," + getName() + "," + getStatus() + "," + getDescription() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && name.equals(task.name) && description.equals(task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id);
    }
}

