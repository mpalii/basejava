package ru.javaops.webapp.storage;

public class FileObjectStreamStorageTest extends AbstractStorageTest {
    public FileObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, ObjectStreamStorage.create()));
    }
}
