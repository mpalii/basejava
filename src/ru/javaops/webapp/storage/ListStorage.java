package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected Integer executeGetKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void executeUpdate(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void executeSave(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume executeGet(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void executeDelete(Integer index) {
        storage.remove((int) index);
    }

    @Override
    protected boolean executeIsExistingKey(Integer index) {
        return (index > -1);
    }

    @Override
    protected List<Resume> executeStorageAsList() {
        return new LinkedList<>(storage);
    }
}











