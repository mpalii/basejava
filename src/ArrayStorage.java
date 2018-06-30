import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int storageCapacity = 10000;
    private int lastEmptyIndex = 0;
    private Resume[] storage = new Resume[storageCapacity];

    void clear() {
        Arrays.fill(storage, 0, lastEmptyIndex, null);
        lastEmptyIndex = 0;
    }

    void save(Resume r) {
        if (lastEmptyIndex < storageCapacity) {
            storage[lastEmptyIndex++] = r;
        } else {
            System.out.println("Storage is filled. Your data isn't added.");
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < lastEmptyIndex; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < lastEmptyIndex; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, lastEmptyIndex);
                lastEmptyIndex--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, lastEmptyIndex);
    }

    int size() {
        return lastEmptyIndex;
    }
}
