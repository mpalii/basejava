package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
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
    protected Resume executeGetKey(String uuid) {
        Resume key = storage.get(uuid);
        return key != null ? key : new Resume(uuid, "MOCK NAME");
    }

    @Override
    protected void executeUpdate(Object key, Resume resume) {
        storage.replace(((Resume) key).getUuid(), resume);
    }

    @Override
    protected void executeSave(Object key, Resume resume) {
        storage.put(((Resume) key).getUuid(), resume);
    }

    @Override
    protected Resume executeGet(Object key) {
        return storage.get(((Resume) key).getUuid());
    }

    @Override
    protected void executeDelete(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected boolean executeIsExistingKey(Object key) {
        return storage.containsKey(((Resume) key).getUuid());
    }

    @Override
    protected List<Resume> executeStorageAsList() {
        return new LinkedList<>(storage.values());
    }
}
