package ru.javaops.HW1HW2HW3.webapp.storage;

import ru.javaops.HW1HW2HW3.webapp.model.Resume;

public interface Storage {
    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}
