package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void saveResumeIntoPosition(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected int executeGetKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteResumeInPosition(int index) {
        storage[index] = storage[size - 1];
    }

}