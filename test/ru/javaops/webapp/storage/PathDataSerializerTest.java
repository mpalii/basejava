package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.DataSerializer;

public class PathDataSerializerTest extends AbstractStorageTest {
    public PathDataSerializerTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataSerializer()));
    }
}
