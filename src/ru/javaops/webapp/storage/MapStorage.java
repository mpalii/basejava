package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    /*-----------TO DO-----------*/

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resultArr = storage.values().toArray(new Resume[0]);
        Arrays.sort(resultArr);
        return resultArr;
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
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getKey().equals(key)) {
                entry.setValue(resume);
                return;
            }
        }
    }

    @Override
    protected void executeSave(Object key, Resume resume) {
        storage.put((String)key, resume);
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


}
