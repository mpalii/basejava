package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.JsonSerializer;

public class PathJsonSerializerTest extends AbstractStorageTest {
    public PathJsonSerializerTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonSerializer()));
    }
}
