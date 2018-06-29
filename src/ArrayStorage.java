import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int lastElementIndex = -1;
    private Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
        lastElementIndex = -1;
    }

    void save(Resume r) {
        if (lastElementIndex < 10000 - 1) {
            storage[++lastElementIndex] = r;
        } else {
            System.out.println("Storage is filled. Your data isn't added.");
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i <= lastElementIndex; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i <= lastElementIndex; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, lastElementIndex - i);
                lastElementIndex--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (lastElementIndex > -1) {
            return Arrays.copyOfRange(storage, 0, lastElementIndex + 1);
        } else {
            return new Resume[0];
        }
    }

    int size() {
        return lastElementIndex + 1;
    }
}
