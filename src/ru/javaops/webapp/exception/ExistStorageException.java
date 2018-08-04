package ru.javaops.webapp.exception;

public class ExistStorageException extends StorageExcepion {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " not exist.", uuid);
    }
}
