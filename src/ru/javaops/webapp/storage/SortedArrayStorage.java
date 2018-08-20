package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void saveResumeIntoPosition(int index, Resume resume) {
        int insertPosition = -index - 1;
        System.arraycopy(storage, insertPosition, storage, insertPosition + 1, size - insertPosition);
        storage[insertPosition] = resume;
    }

    @Override
    protected Integer executeGetKey(String uuid) {
        Resume key = new Resume(uuid, "MOCK NAME");
        return Arrays.binarySearch(storage, 0, size, key, RESUME_COMPARATOR);
    }

    @Override
    protected void deleteResumeInPosition(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }
}