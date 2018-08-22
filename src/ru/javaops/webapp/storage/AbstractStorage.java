package ru.javaops.webapp.storage;

        import ru.javaops.webapp.exception.ExistStorageException;
        import ru.javaops.webapp.exception.NotExistStorageException;
        import ru.javaops.webapp.model.Resume;

        import java.util.Collections;
        import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract Object executeGetKey(String uuid);

    protected abstract void executeUpdate(Object key, Resume resume);

    protected abstract void executeSave(Object key, Resume resume);

    protected abstract Resume executeGet(Object key);

    protected abstract void executeDelete(Object key);

    protected abstract boolean executeIsExistingKey(Object key);

    protected abstract List<Resume> executeStorageAsList();

    @Override
    public void update(Resume resume) {
        Object key = searchExistentKey(resume.getUuid());
        executeUpdate(key, resume);
    }

    @Override
    public void save(Resume resume) {
        Object key = searchNonexistentKey(resume.getUuid());
        executeSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = searchExistentKey(uuid);
        return executeGet(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = searchExistentKey(uuid);
        executeDelete(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = executeStorageAsList();
        Collections.sort(list);
        return list;
    }

    private Object searchExistentKey(String uuid) {
        Object key = executeGetKey(uuid);
        if (!executeIsExistingKey(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object searchNonexistentKey(String uuid) {
        Object key = executeGetKey(uuid);
        if (executeIsExistingKey(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}