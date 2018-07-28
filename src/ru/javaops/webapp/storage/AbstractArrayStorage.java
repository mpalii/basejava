package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10_000;

    protected int size = 0;
    protected Resume[] storage = new Resume[CAPACITY];

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

    protected abstract int getIndex(String uuid);
}
