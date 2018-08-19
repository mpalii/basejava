package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.fail;
import static ru.javaops.webapp.storage.AbstractArrayStorage.CAPACITY;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        try {
            for (int i = storage.size(); i < CAPACITY; i++) {
                storage.save(new Resume("User №" + i));
            }
        } catch (StorageException ex) {
            fail("Test was failed!");
        }
        storage.save(RESUME_4);
    }
}