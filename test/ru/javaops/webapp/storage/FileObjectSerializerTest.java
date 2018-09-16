package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.ObjectSerializer;

public class FileObjectSerializerTest extends AbstractStorageTest {
    public FileObjectSerializerTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectSerializer()));
    }
}
