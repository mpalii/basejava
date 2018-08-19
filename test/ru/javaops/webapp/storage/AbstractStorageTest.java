package ru.javaops.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    Storage storage;
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume("UUID_1", "AAA");
        RESUME_2 = new Resume("UUID_2", "BBB");
        RESUME_3 = new Resume("UUID_3", "AAA");
        RESUME_4 = new Resume("UUID_4", "DDD");
    }

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume("UUID_2", "New name");
        storage.update(updatedResume);
        assertEquals(updatedResume.getFullName(), storage.get("UUID_2").getFullName());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {
        Resume updatedResume = new Resume("WRONG NAME", "WRONG UUID");
        storage.update(updatedResume);
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
        storage.get("wrong_name");
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
        List<Resume> expectedList = new ArrayList<>();
        expectedList.add(RESUME_1);
        expectedList.add(RESUME_3);
        expectedList.add(RESUME_2);
        List<Resume> actualList = storage.getAllSorted();
        assertEquals(expectedList, actualList);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}