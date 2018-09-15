package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private ResumeSerializer serializer;

    public PathStorage(String dir, ResumeSerializer resumeSerializer) {
        Objects.requireNonNull(dir, "directory must be not null");
        Objects.requireNonNull(resumeSerializer, "serializer must be not null");
        directory = Paths.get(dir);
        if (!Files.isExecutable(directory) || !Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory, or access denied");
        }
        serializer = resumeSerializer;
    }

    @Override
    protected Path executeGetSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void executeSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("File saving exception", resume.getUuid(), e);
        }
        executeUpdate(path, resume);
    }

    @Override
    protected void executeUpdate(Path path, Resume resume) {
        try {
            serializer.executeWriteFile(Files.newOutputStream(path), resume);
        } catch (IOException e) {
            throw new StorageException("File saving exception", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume executeGet(Path path) {
        try {
            return serializer.executeReadFile(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("File reading exception", null, e);
        }
    }

    @Override
    protected void executeDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Deleting exception", null, e);
        }
    }

    @Override
    protected boolean executeIsExistingKey(Path path) {
//        return !Files.isDirectory(path);
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> executeStorageAsList() {
        try {
            Object[] arr = Files.list(directory).toArray();
            List<Resume> resultList = new ArrayList<>();
            for (Object element : arr) {
                resultList.add(executeGet((Path) element));
            }
            return resultList;
        } catch (IOException e) {
            throw new StorageException("IOException", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::executeDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Path exception", null, e);
        }
    }
}
