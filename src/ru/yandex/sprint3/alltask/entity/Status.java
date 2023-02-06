package ru.yandex.sprint3.alltask.entity;

public enum Status {

    NEW("NEW"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String value;

    Status(String value) {
        this.value = value;
    }



}
