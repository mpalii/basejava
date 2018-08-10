package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int executeGetIndex(String uuid);
    protected abstract void executeUpdate(int index, Resume resume);
    protected abstract void executeSave(int index, Resume resume);
    protected abstract Resume executeGet(int index);
    protected abstract void executeDelete(int index);

    @Override
    public void update(Resume resume) {
        int index = executeGetIndex(resume.getUuid());
        if (index > -1) {
            executeUpdate(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = executeGetIndex(resume.getUuid());
        if (index > -1) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            executeSave(index, resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = executeGetIndex(uuid);
        if (index > -1) {
            return executeGet(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = executeGetIndex(uuid);
        if (index > -1) {
            executeDelete(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }
}
