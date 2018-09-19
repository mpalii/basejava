package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.XmlSerializer;

public class PathXmlSerializerTest extends AbstractStorageTest {
    public PathXmlSerializerTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlSerializer()));
    }
}
