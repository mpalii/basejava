package ru.javaops.webapp.storage;

public class PathObjectStreamStorageTest extends AbstractStorageTest {
    public PathObjectStreamStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), ObjectStreamStorage.create()));
    }
}
