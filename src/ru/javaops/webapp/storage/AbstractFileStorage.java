package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must be not null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException("directory is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File executeGetKey(String uuid) {
        return new File(directory, uuid);
    }

    // TO DO without creating of file
    @Override
    protected void executeUpdate(File file, Resume resume) {
        try {
            executeWriteFile(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected void executeSave(File file, Resume resume) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("Creating exception", resume.getUuid());
            }
            executeUpdate(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    abstract void executeWriteFile(File file, Resume resume) throws IOException;

    // TO DO with reference to abstract method
    @Override
    protected Resume executeGet(File file) {
        try {
            return executeReadFile(file);
        } catch (IOException e) {
            throw new StorageException("IO Exception", file.getName(), e);
        }
    }

    abstract Resume executeReadFile(File file) throws IOException;

    // TO DO
    @Override
    protected void executeDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Deleting exception", file.getName());
        }
    }

    @Override
    protected boolean executeIsExistingKey(File file) {
        return file.exists();
    }

    // TO DO with executeREAD abstraction
    @Override
    protected List<Resume> executeStorageAsList() {
        File currentFile = null;
        List<Resume> resumeList = new LinkedList<>();
        File[] fileList = directory.listFiles();
        if (fileList == null) {
            throw new StorageException("File list of directory is null", null);
        }
        for (File file : fileList) {
            resumeList.add(executeGet(file));
        }

        return resumeList;
    }

    // TO DO
    @Override
    public void clear() {
        File[] fileList = directory.listFiles();
        if (fileList == null) {
            throw new StorageException("File list of directory is null", null);
        }
        for (File file : fileList) {
            executeDelete(file);
        }
    }

    // TO DO
    @Override
    public int size() {
        String[] fileList = directory.list();
        if (fileList == null) {
            throw new StorageException("File list of directory is null", null);
        }
        return fileList.length;
    }
}
