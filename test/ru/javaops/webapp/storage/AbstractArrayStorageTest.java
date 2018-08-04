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
import static org.junit.jupiter.api.Assertions.fail;

class AbstractArrayStorageTest {
    private Storage storage;
    private int storageMaxCapacity = 10_000;
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
        assertEquals(4, storage.size());
    }

    @Test
    void saveExistStorageException() {
        assertThrows(ExistStorageException.class, () -> {
            storage.save(resume2);
        });
    }

    @Test
    void saveStorageException() {
        try {
            for (int i = storage.size(); i < storageMaxCapacity; i++) {
                storage.save(new Resume());
            }
        } catch (StorageExcepion ex) {
            fail();
        }
        assertThrows(StorageExcepion.class, () -> {
            storage.save(new Resume());
        });

//        Another way of realisation:
//        assertDoesNotThrow(() -> {
//            for (int i = storage.size(); i < storageMaxCapacity; i++) {
//                storage.save(new Resume());
//            }
//        });
//        assertThrows(StorageExcepion.class, () -> {
//            storage.save(new Resume());
//        });
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        assertDoesNotThrow(() -> {
            storage.update(resume2);
        });
    }

    @Test
    void updateNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(resume4);
        });
    }

    @Test
    void delete() {
        storage.delete("UUID_2");
        assertEquals(2, storage.size());
    }

    @Test
    void deleteNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.delete("UUID_4");
        });
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }

    @Test
    void get() {
        assertEquals(resume1, storage.get("UUID_1"));
    }

    @Test
    void getNotExistStorageException() {
        assertThrows(NotExistStorageException.class,() -> {
            storage.get("wrong_uuid");
        });
    }

    @Test
    void getAll() {
        Resume[] expectedResumeArray = { resume1, resume2, resume3 };
        assertArrayEquals(expectedResumeArray, storage.getAll());
    }
}