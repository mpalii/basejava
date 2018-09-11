package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    public AbstractPathStorage(String dir) {
        Objects.requireNonNull(dir, "directory must be not null");
        directory = Paths.get(dir);
        if(!Files.isExecutable(directory) || !Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory, or access denied");
        }
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
            executeWriteFile(Files.newOutputStream(path), resume);
        } catch (IOException e) {
            throw new StorageException("File saving exception", resume.getUuid(), e);
        }
    }

    abstract void executeWriteFile(OutputStream outputStream, Resume resume) throws IOException;

    @Override
    protected Resume executeGet(Path path) {
        try {
            return executeReadFile(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("File reading exception", null, e);
        }
    }

    abstract Resume executeReadFile(InputStream inputStream) throws IOException;

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
        return !Files.isDirectory(path);
    }

    @Override
    protected List<Resume> executeStorageAsList() {
        try {
            return  Arrays.asList((Resume[]) Files.list(directory).toArray());
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
