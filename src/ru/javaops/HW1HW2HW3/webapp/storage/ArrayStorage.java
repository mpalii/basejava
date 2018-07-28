package ru.javaops.HW1HW2HW3.webapp.storage;

import ru.javaops.HW1HW2HW3.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
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

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}