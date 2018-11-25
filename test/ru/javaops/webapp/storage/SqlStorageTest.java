package ru.javaops.webapp.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

class SqlStorageTest {
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

    public SqlStorageTest() {
        this.storage = new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_1);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void update() {
        Resume updatedResume = new Resume("UUID_2", "New name");
        storage.update(updatedResume);
        assertEquals(updatedResume, storage.get("UUID_2"));
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertSize(4);
    }

    @Test
    void get() {
        assertEquals(RESUME_1, storage.get("UUID_1"));
    }

    @Test
    void delete() {
        storage.delete("UUID_2");
        assertSize(2);
    }

    @Test
    void getAllSorted() {
        List<Resume> expectedList = new ArrayList<>();
        expectedList.add(RESUME_3);
        expectedList.add(RESUME_2);
        expectedList.add(RESUME_1);
        List<Resume> actualList = storage.getAllSorted();
        assertEquals(expectedList, actualList);
    }

    @Test
    void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}