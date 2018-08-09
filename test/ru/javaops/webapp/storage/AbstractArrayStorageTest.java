package ru.javaops.webapp.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageExcepion;
import ru.javaops.webapp.model.Resume;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.webapp.storage.AbstractArrayStorage.CAPACITY;

abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final Resume resume1 = new Resume("UUID_1");
    private static final Resume resume2 = new Resume("UUID_2");
    private static final Resume resume3 = new Resume("UUID_3");
    private static final Resume resume4 = new Resume("UUID_4");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    void save() {
        storage.save(resume4);
        assertSize(4);
    }

    @Test
    void saveExistStorageException() {
        assertThrows(ExistStorageException.class, () -> storage.save(resume2));
    }

    @Test
    void saveStorageException() {
        assertDoesNotThrow(() -> {
            for (int i = storage.size(); i < CAPACITY; i++) {
                storage.save(new Resume());
            }
        });
        assertThrows(StorageExcepion.class, () -> storage.save(new Resume()));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void update() {
        assertDoesNotThrow(() -> storage.update(resume2));
    }

    @Test
    void updateNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.update(resume4));
    }

    @Test
    void delete() {
        storage.delete("UUID_2");
        assertSize(2);
    }

    @Test
    void deleteNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("UUID_4"));
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void get() {
        assertEquals(resume1, storage.get("UUID_1"));
    }

    @Test
    void getNotExistStorageException() {
        assertThrows(NotExistStorageException.class,() -> storage.get("wrong_uuid"));
    }

    @Test
    void getAll() {
        Resume[] expectedResumeArray = { resume1, resume2, resume3 };
        assertArrayEquals(expectedResumeArray, storage.getAll());
    }

    private void assertSize(int size){
        assertEquals(size, storage.size());
    }
}