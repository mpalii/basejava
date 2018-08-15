package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void saveResumeIntoPosition(int index, Resume resume) {
        int insertPosition = -index - 1;
        System.arraycopy(storage, insertPosition, storage, insertPosition + 1, size - insertPosition);
        storage[insertPosition] = resume;
    }

    @Override
    protected Integer executeGetKey(String uuid) {
        Resume key = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, key);
    }

    @Override
    protected void deleteResumeInPosition(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }
}
