package ru.javaops.webapp.exception;

public class NotExistStorageException extends StorageExcepion {
    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " already exists.", uuid);
    }
}
