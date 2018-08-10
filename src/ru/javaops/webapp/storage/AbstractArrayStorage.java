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
    protected void executeUpdate(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void executeSave(int index, Resume resume) {
        if (size < CAPACITY) {
            saveResumeIntoPosition(resume, index);
            size++;
        } else {
            throw new StorageExcepion("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected Resume executeGet(int index) {
        return storage[index];
    }

    @Override
    protected void executeDelete(int index) {
        deleteResumeInPosition(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void saveResumeIntoPosition(Resume resume, int index);

    protected abstract void deleteResumeInPosition(int index);
}