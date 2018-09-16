package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.ObjectSerializer;

public class PathObjectSerializerTest extends AbstractStorageTest {
    public PathObjectSerializerTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectSerializer()));
    }
}
