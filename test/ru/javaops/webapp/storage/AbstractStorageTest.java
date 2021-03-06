package ru.javaops.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.Config;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    Storage storage;
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeDataTest.getResume1();
        RESUME_2 = ResumeDataTest.getResume2();
        RESUME_3 = ResumeDataTest.getResume3();
        RESUME_4 = ResumeDataTest.getResume4();
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
        assertEquals(updatedResume, storage.get("UUID_2"));
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
        expectedList.add(RESUME_3);
        expectedList.add(RESUME_2);
        expectedList.add(RESUME_1);
        List<Resume> actualList = storage.getAllSorted();
        assertEquals(expectedList, actualList);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}