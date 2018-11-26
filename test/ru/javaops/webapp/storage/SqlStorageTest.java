package ru.javaops.webapp.storage;

import ru.javaops.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    private static final String URL = Config.get().getDbUrl();
    private static final String USER = Config.get().getDbUser();
    private static final String PASSWORD = Config.get().getDbPassword();

    public SqlStorageTest() {
        super(new SqlStorage(URL, USER, PASSWORD));
    }
}
