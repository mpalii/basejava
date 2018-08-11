package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int executeGetKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void executeUpdate(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void executeSave(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume executeGet(int index) {
        return storage.get(index);
    }

    @Override
    protected void executeDelete(int index) {
        storage.remove(index);
    }
}











