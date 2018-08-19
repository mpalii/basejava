package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void saveResumeIntoPosition(int index, Resume resume) {
        int insertPosition = -index - 1;
        System.arraycopy(storage, insertPosition, storage, insertPosition + 1, size - insertPosition);
        storage[insertPosition] = resume;
    }

    @Override
    protected Integer executeGetKey(String uuid) {
        Resume key = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, key, RESUME_COMPARATOR);
    }

    @Override
    protected void deleteResumeInPosition(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }
}



/*--------- SOME NOTES ---------*/
//    private static class ResumeComparator implements Comparator<Resume> {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    }

//    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    };

//    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

//    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);
