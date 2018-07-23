package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int capacity = 10_000;
    private int size = 0;
    private Resume[] storage = new Resume[capacity];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (getIndexOfResume(r.getUuid()) > -1) {
            System.out.println("Storage already contains resume with uuid: " + r.getUuid() + ".");
            return;
        }
        if (size < capacity) {
            storage[size++] = r;
        } else {
            System.out.println("Storage is filled. Your data isn't added.");
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndexOfResume(uuid);
        if (resumeIndex > -1) {
            return storage[resumeIndex];
        } else {
            System.out.println("Storage do not contains resume with uuid: " + uuid + ".");
            return null;
        }
    }

    public void update(Resume r) {
        int resumeIndex = getIndexOfResume(r.getUuid());
        if (resumeIndex > -1) {
            storage[resumeIndex] = r;
        } else {
            System.out.println("Storage do not contains resume with uuid: " + r.getUuid() + ".");
        }
    }

    public void delete(String uuid) {
        int resumeIndex = getIndexOfResume(uuid);
        if (resumeIndex > -1) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, size - (resumeIndex + 1));
            size--;
        } else {
            System.out.println("Storage do not contains resume with uuid: " + uuid + ".");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int getIndexOfResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) return i;
        }
        return -1;
    }
}