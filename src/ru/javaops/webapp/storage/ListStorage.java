package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public List<Resume> getAllSorted() {
        return new LinkedList<>(storage).
                stream().
                sorted().
                collect(Collectors.toList());
    }

    @Override
    protected Integer executeGetKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void executeUpdate(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void executeSave(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume executeGet(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void executeDelete(Object index) {
        storage.remove((int) index);
    }

    @Override
    protected boolean executeIsExistingKey(Object index) {
        return ((Integer) index > -1);
    }
}











