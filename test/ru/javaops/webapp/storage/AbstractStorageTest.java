package ru.javaops.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected Storage storage;
    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume("UUID_1");
        RESUME_2 = new Resume("UUID_2");
        RESUME_3 = new Resume("UUID_3");
        RESUME_4 = new Resume("UUID_4");
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_2);
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        storage.update(RESUME_2);
        assertSame(RESUME_2, storage.get("UUID_2"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(RESUME_2);
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get("UUID_1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistStorageException() {
        storage.get("wrong_uuid");
    }

    @Test
    public void delete() {
        storage.delete("UUID_2");
        assertSize(2);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageException() {
        storage.delete("UUID_4");
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void getAllSorted() {
        Resume[] expectedArray = { RESUME_1, RESUME_2, RESUME_3 };
        Resume[] actualArray = storage.getAllSorted().toArray(new Resume[0]);
        assertArrayEquals(expectedArray, actualArray);
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}