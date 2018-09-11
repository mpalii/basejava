//package ru.javaops.webapp.storage;
//
//import ru.javaops.webapp.exception.StorageException;
//import ru.javaops.webapp.model.Resume;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Objects;
//
//public class AbstractPathStorage extends AbstractStorage<Path> {
//    private Path directory;
//
//    public AbstractPathStorage(String dir) {
//        Objects.requireNonNull(dir, "directory must be not null");
//        directory = Paths.get(dir);
//        if(!Files.isExecutable(directory) || !Files.isReadable(directory) || !Files.isWritable(directory)) {
//            throw new IllegalArgumentException(dir + " is not directory, or access denied");
//        }
//    }
//
//    @Override
//    protected Path executeGetSearchKey(String uuid) {
//        return new Path(directory, uuid);
//    }
//
//    @Override
//    protected void executeUpdate(Path key, Resume resume) {
//
//    }
//
//    @Override
//    protected void executeSave(Path key, Resume resume) {
//
//    }
//
//    @Override
//    protected Resume executeGet(Path key) {
//        return null;
//    }
//
//    @Override
//    protected void executeDelete(Path key) {
//
//    }
//
//    @Override
//    protected boolean executeIsExistingKey(Path key) {
//        return false;
//    }
//
//    @Override
//    protected List<Resume> executeStorageAsList() {
//        return null;
//    }
//
//    @Override
//    public void clear() {
//        try {
//            Files.list(directory).forEach(this::executeDelete);
//        } catch (IOException e) {
//            throw new StorageException("Path delete error", null);
//        }
//    }
//
//    @Override
//    public int size() {
//        return 0;
//    }
//}
