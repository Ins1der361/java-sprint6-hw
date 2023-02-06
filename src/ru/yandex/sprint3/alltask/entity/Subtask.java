package ru.yandex.sprint3.alltask.entity;

public class Subtask extends Task {
    protected int idEpic;

    public Subtask(String name, String description, Status status, int idEpic) {
        super(name, description, status);
        this.idEpic = idEpic;
    }

    public Subtask(int id, String name, String description, Status status, int idEpic) {
        super(id, name, description, status);
        this.idEpic = idEpic;
    }


    public int getIdEpic() {
        return idEpic;
    }


    @Override
    public String toString() {
        return getId() + "," + TypeTask.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription() + "," + getIdEpic() + "\n";
    }
}


