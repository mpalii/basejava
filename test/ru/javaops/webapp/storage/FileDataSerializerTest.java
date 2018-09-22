package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.DataSerializer;

public class FileDataSerializerTest extends AbstractStorageTest {
    public FileDataSerializerTest() {
        super(new FileStorage(STORAGE_DIR, new DataSerializer()));
    }
}
