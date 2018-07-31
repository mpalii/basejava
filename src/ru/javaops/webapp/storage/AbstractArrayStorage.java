package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10_000;

    protected int size = 0;
    protected Resume[] storage = new Resume[CAPACITY];

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            System.out.println("Storage already contains resume with uuid: " + resume.getUuid() + ".");
            return;
        }
        if (size < CAPACITY) {
            saveResumeIntoPosition(resume, index);
            size++;
        } else {
            System.out.println("Storage is filled. Your data isn't added.");
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
        } else {
            System.out.println("Storage do not contains resume with uuid: " + resume.getUuid() + ".");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            deleteResumeInPosition(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Storage do not contains resume with uuid: " + uuid + ".");
        }
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            return storage[index];
        }
        System.out.println("Storage do not contains resume with uuid: " + uuid + ".");
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveResumeIntoPosition(Resume resume, int index);

    protected abstract void deleteResumeInPosition(int index);
}
