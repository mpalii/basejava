package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.JsonSerializer;

public class FileJsonSerializerTest extends AbstractStorageTest {
    public FileJsonSerializerTest() {
        super(new FileStorage(STORAGE_DIR, new JsonSerializer()));
    }
}
