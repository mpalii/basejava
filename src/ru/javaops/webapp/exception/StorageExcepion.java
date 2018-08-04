package ru.javaops.webapp.exception;

public class StorageExcepion extends RuntimeException {
    private final String uuid;

    public StorageExcepion(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
