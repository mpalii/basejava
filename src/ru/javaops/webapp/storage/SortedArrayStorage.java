package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index > -1) {
            System.out.println("Storage already contains resume with uuid: " + r.getUuid() + ".");
            return;
        }
        if (size < CAPACITY) {
            int insertIndex = (index + 1) * (-1);
            Resume[] tempArr = new Resume[size - insertIndex];
            System.arraycopy(storage, insertIndex, tempArr, 0, tempArr.length);
            storage[insertIndex] = r;
            System.arraycopy(tempArr, 0, storage, insertIndex + 1, tempArr.length);
            size++;
        } else {
            System.out.println("Storage is filled. Your data isn't added.");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        if (storage.length == 0) {
            return -1;
        }
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
