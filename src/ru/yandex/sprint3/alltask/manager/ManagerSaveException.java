package ru.yandex.sprint3.alltask.manager;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String message) {
        super(message);
    }
}
