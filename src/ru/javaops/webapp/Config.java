package ru.javaops.webapp;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.storage.SqlStorage;
import ru.javaops.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File(getHomeDir(), "config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private Properties props = new Properties();
    private File storageDir;
    private Storage storage;

    private static String getHomeDir() {
        String homeDir = System.getProperty("homeDir");
        File file = new File(homeDir == null ? "." : homeDir);
        if (!file.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            String dbUrl = props.getProperty("db.url");
            String dbUser = props.getProperty("db.user");
            String dbPassword = props.getProperty("db.password");
            storage = new SqlStorage(dbUrl, dbUser, dbPassword);
            Class.forName("org.postgresql.Driver");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file "  + PROPS.getAbsolutePath());
        } catch (ClassNotFoundException e) {
            throw new StorageException("JDBC driver not found");
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
