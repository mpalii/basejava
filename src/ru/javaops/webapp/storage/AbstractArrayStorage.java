package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int CAPACITY = 10_000;

    int size = 0;
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
            throw new StorageException("Storage overflow", resume.getUuid());
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

    @Override
    protected List<Resume> executeStorageAsList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}