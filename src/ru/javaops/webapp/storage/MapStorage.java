package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class MapStorage extends AbstractStorage {

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected int executeGetIndex(String uuid) {
        return 0;
    }

    @Override
    protected void executeUpdate(int index, Resume resume) {

    }

    @Override
    protected void executeSave(int index, Resume resume) {

    }

    @Override
    protected Resume executeGet(int index) {
        return null;
    }

    @Override
    protected void executeDelete(int index) {

    }


}
