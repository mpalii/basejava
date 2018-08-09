package ru.javaops.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageExcepion;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static ru.javaops.webapp.storage.AbstractArrayStorage.CAPACITY;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final Resume resume1 = new Resume("UUID_1");
    private static final Resume resume2 = new Resume("UUID_2");
    private static final Resume resume3 = new Resume("UUID_3");
    private static final Resume resume4 = new Resume("UUID_4");

    private AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(resume2);
    }

    @Test(expected = StorageExcepion.class)
    public void saveStorageException() {
        try {
            for (int i = storage.size(); i < CAPACITY; i++) {
                storage.save(new Resume());
            }
        } catch (StorageExcepion ex) {
            fail("Test was failed!");
        }
        storage.save(resume4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        storage.update(resume2);
        assertSame(resume2, storage.get("UUID_2"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {
        storage.update(resume4);
    }

    @Test
    public void delete() {
        storage.delete("UUID_2");
        assertSize(2);
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
    public void get() {
        assertEquals(resume1, storage.get("UUID_1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistStorageException() {
        storage.get("wrong_uuid");
    }

    @Test
    public void getAll() {
        Resume[] expectedResumeArray = { resume1, resume2, resume3 };
        assertArrayEquals(expectedResumeArray, storage.getAll());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}