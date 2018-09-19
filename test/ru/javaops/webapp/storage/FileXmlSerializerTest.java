package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.XmlSerializer;

public class FileXmlSerializerTest extends AbstractStorageTest {
    public FileXmlSerializerTest() {
        super(new FileStorage(STORAGE_DIR, new XmlSerializer()));
    }
}
