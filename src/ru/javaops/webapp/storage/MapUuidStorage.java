package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
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
    protected String executeGetSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void executeUpdate(String key, Resume resume) {
        storage.replace(key, resume);
    }

    @Override
    protected void executeSave(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected Resume executeGet(String key) {
        return storage.get(key);
    }

    @Override
    protected void executeDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected boolean executeIsExistingKey(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected List<Resume> executeStorageAsList() {
        return new LinkedList<>(storage.values());
    }
}
