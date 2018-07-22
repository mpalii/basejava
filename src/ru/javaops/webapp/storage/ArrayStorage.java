package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int capacity = 10_000;
    private int lastEmptyIndex = 0;
    private Resume[] storage = new Resume[capacity];

    public void clear() {
        Arrays.fill(storage, 0, lastEmptyIndex, null);
        lastEmptyIndex = 0;
    }

    public void save(Resume r) {
        if (lastEmptyIndex < capacity) {
            storage[lastEmptyIndex++] = r;
        } else {
            System.out.println("Storage is filled. Your data isn't added.");
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < lastEmptyIndex; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < lastEmptyIndex; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, lastEmptyIndex - (i + 1));
                lastEmptyIndex--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, lastEmptyIndex);
    }

    public int size() {
        return lastEmptyIndex;
    }
}
