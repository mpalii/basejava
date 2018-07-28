package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage  {
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (getIndex(r.getUuid()) > -1) {
            System.out.println("Storage already contains resume with uuid: " + r.getUuid() + ".");
            return;
        }
        if (size < CAPACITY) {
            storage[size++] = r;
        } else {
            System.out.println("Storage is filled. Your data isn't added.");
        }
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

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}