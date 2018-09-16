package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.serializers.ResumeSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private ResumeSerializer serializer;

    protected FileStorage(File directory, ResumeSerializer resumeSerializer) {
        Objects.requireNonNull(directory, "directory must be not null");
        Objects.requireNonNull(resumeSerializer, "serializer must be not null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException("directory is not readable/writable");
        }
        this.directory = directory;
        serializer = resumeSerializer;
    }

    @Override
    protected File executeGetSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean executeIsExistingKey(File file) {
        return file.exists();
    }

    @Override
    protected void executeSave(File file, Resume resume) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("Creating exception", resume.getUuid());
            }
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
        executeUpdate(file, resume);
    }

    @Override
    protected void executeUpdate(File file, Resume resume) {
        try {
            serializer.executeWriteFile(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected Resume executeGet(File file) {
        try {
            return serializer.executeReadFile(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO Exception", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> executeStorageAsList() {
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

    @Override
    protected void executeDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Deleting exception", file.getName());
        }
    }

    @Override
    public int size() {
        String[] fileList = directory.list();
        if (fileList == null) {
            throw new StorageException("File list of directory is null", null);
        }
        return fileList.length;
    }
}
