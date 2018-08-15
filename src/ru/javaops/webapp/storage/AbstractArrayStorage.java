package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageExcepion;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int CAPACITY = 10_000;

    protected int size = 0;
    protected Resume[] storage = new Resume[CAPACITY];

    protected abstract void saveResumeIntoPosition(int index, Resume resume);
    protected abstract void deleteResumeInPosition(int index);
    protected abstract Integer executeGetKey(String uuid);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean executeIsExistingKey(Object index) {
        return ((Integer) index > -1);
    }

    @Override
    protected void executeUpdate(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void executeSave(Object index, Resume resume) {
        if (size < CAPACITY) {
            saveResumeIntoPosition((Integer) index, resume);
            size++;
        } else {
            throw new StorageExcepion("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected Resume executeGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void executeDelete(Object index) {
        deleteResumeInPosition((Integer) index);
        storage[size - 1] = null;
        size--;
    }

}