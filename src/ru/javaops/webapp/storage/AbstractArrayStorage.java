package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10_000;

    protected int size = 0;
    protected Resume[] storage = new Resume[CAPACITY];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int resumeIndex = getIndex(r.getUuid());
        if (resumeIndex > -1) {
            storage[resumeIndex] = r;
        } else {
            System.out.println("Storage do not contains resume with uuid: " + r.getUuid() + ".");
        }
    }

    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex > -1) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, size - (resumeIndex + 1));
            size--;
        } else {
            System.out.println("Storage do not contains resume with uuid: " + uuid + ".");
        }
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex > -1) {
            return storage[resumeIndex];
        } else {
            System.out.println("Storage do not contains resume with uuid: " + uuid + ".");
            return null;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);
}
