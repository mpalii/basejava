package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object executeGetKey(String uuid) {
        return uuid;
    }

    @Override
    protected void executeUpdate(Object key, Resume resume) {
        storage.replace((String) key, resume);
    }

    @Override
    protected void executeSave(Object key, Resume resume) {
        storage.put((String) key, resume);
    }

    @Override
    protected Resume executeGet(Object key) {
        return storage.get(key);
    }

    @Override
    protected void executeDelete(Object key) {
        storage.remove(key);
    }

    @Override
    protected boolean executeIsExistingKey(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected List<Resume> executeStorageAsList() {
        return new LinkedList<>(storage.values());
    }
}
