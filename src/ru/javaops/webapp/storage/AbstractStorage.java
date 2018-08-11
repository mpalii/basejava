package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int executeGetKey(String uuid);
    protected abstract void executeUpdate(int key, Resume resume);
    protected abstract void executeSave(int key, Resume resume);
    protected abstract Resume executeGet(int key);
    protected abstract void executeDelete(int key);

    @Override
    public void update(Resume resume) {
        int key = searchExistentKey(resume.getUuid());
        executeUpdate(key, resume);
    }


    @Override
    public void save(Resume resume) {
        int key = searchNonexistentKey(resume.getUuid());
        executeSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        int key = searchExistentKey(uuid);
        return executeGet(key);
    }

    @Override
    public void delete(String uuid) {
        int key = searchExistentKey(uuid);
        executeDelete(key);
    }

    private int searchExistentKey(String uuid) {
        int key = executeGetKey(uuid);
        if (key < 0) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private int searchNonexistentKey(String uuid) {
        int key = executeGetKey(uuid);
        if (key > -1) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}
