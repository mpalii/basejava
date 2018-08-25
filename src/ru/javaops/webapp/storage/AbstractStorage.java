package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    //    protected final Logger log = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK executeGetKey(String uuid);

    protected abstract void executeUpdate(SK key, Resume resume);

    protected abstract void executeSave(SK key, Resume resume);

    protected abstract Resume executeGet(SK key);

    protected abstract void executeDelete(SK key);

    protected abstract boolean executeIsExistingKey(SK key);

    protected abstract List<Resume> executeStorageAsList();

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK key = searchExistentKey(resume.getUuid());
        executeUpdate(key, resume);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK key = searchNonexistentKey(resume.getUuid());
        executeSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = searchExistentKey(uuid);
        return executeGet(key);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = searchExistentKey(uuid);
        executeDelete(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> list = executeStorageAsList();
        Collections.sort(list);
        return list;
    }

    private SK searchExistentKey(String uuid) {
        SK key = executeGetKey(uuid);
        if (!executeIsExistingKey(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK searchNonexistentKey(String uuid) {
        SK key = executeGetKey(uuid);
        if (executeIsExistingKey(key)) {
            LOG.warning("Resume " + uuid + " already exists");
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}