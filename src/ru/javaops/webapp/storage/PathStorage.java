package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.serializers.ResumeSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        // метод resolve() склеивает один путь с другим
        return directory.resolve(uuid);
    }

    @Override
    protected void executeSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path: " + path, getFileName(path), e);
        }
        executeUpdate(path, resume);
    }

    @Override
    protected void executeUpdate(Path path, Resume resume) {
        try {
            serializer.executeWriteFile(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("File saving exception: " + path, getFileName(path), e);
        }
    }

    @Override
    protected Resume executeGet(Path path) {
        try {
            return serializer.executeReadFile(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File reading exception", e);
        }
    }

    @Override
    protected void executeDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Deleting exception", getFileName(path), e);
        }
    }

    @Override
    protected boolean executeIsExistingKey(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> executeStorageAsList() {
        // map - преобразовать елемент стрима; collect - накопить элементы потока
        return getFileList().map(this::executeGet).collect(Collectors.toList());

        /*
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
        */
    }

    @Override
    public void clear() {
        getFileList().forEach(this::executeDelete);
    }

    @Override
    public int size() {
        return (int) getFileList().count();
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFileList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }
}
