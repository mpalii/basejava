package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
//        Resume key = storage.get(uuid);
//        return key != null ? key : new Resume(uuid, "MOCK NAME");
        return storage.get(uuid);
    }

    @Override
    protected void executeUpdate(Resume key, Resume resume) {
        storage.replace(key.getUuid(), resume);
    }

    @Override
    protected void executeSave(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume executeGet(Resume key) {
        return storage.get(key.getUuid());
    }

    @Override
    protected void executeDelete(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    protected boolean executeIsExistingKey(Resume key) {
//        return storage.containsKey(((Resume) key).getUuid());
        return key != null;
    }

    @Override
    protected List<Resume> executeStorageAsList() {
        return new LinkedList<>(storage.values());
    }
}
