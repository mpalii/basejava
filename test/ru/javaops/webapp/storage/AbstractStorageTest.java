package ru.javaops.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    Storage storage;
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume("UUID_1", "Vasyl Gym");
        RESUME_1.addContact(ContactType.TELEPHONE, "555-55-55");
        List<Position> positionList = new LinkedList<>();
        positionList.add(new Position(
            "Trainer",
                "Work in fitness club",
                DateUtil.of(2015, Month.OCTOBER),
                DateUtil.now()
        ));
        List<Establishment> establishmentList = new LinkedList<>();
        establishmentList.add(new Establishment("FITTERRA", null, positionList));
        RESUME_1.addSection(SectionType.EXPERIENCE, new ListEstablishmentSection(establishmentList));


        RESUME_2 = new Resume("UUID_2", "Serhii Soloviev");
        RESUME_2.addContact(ContactType.TELEPHONE, "999-98-78");
        positionList = new LinkedList<>();
        positionList.add(new Position(
                "Engineer",
                "Repair of mechanic equipment",
                DateUtil.of(2011, Month.NOVEMBER),
                DateUtil.now()
        ));
        establishmentList = new LinkedList<>();
        establishmentList.add(new Establishment("InterPIPE", "http://interpipe.biz/", positionList));
        RESUME_2.addSection(SectionType.EXPERIENCE, new ListEstablishmentSection(establishmentList));

        RESUME_3 = new Resume("UUID_3", "Maksym Palii");
        RESUME_3.addContact(ContactType.TELEPHONE, "654-00-48");
        positionList = new LinkedList<>();
        positionList.add(new Position(
                "Cost estimator",
                "Cost planning",
                DateUtil.of(2012, Month.DECEMBER),
                DateUtil.now()
        ));
        establishmentList = new LinkedList<>();
        establishmentList.add(new Establishment("DONBUDMONTAG", null, positionList));
        RESUME_3.addSection(SectionType.EXPERIENCE, new ListEstablishmentSection(establishmentList));

        RESUME_4 = new Resume("UUID_4", "Pryntseva Olha");
        RESUME_4.addContact(ContactType.TELEPHONE, "784-10-08");
        positionList = new LinkedList<>();
        positionList.add(new Position(
                "Operator",
                "Solving of customer problems",
                DateUtil.of(2014, Month.MARCH),
                DateUtil.now()
        ));
        establishmentList = new LinkedList<>();
        establishmentList.add(new Establishment("NOVA POSHTA", "novaposhta.ua", positionList));
        RESUME_4.addSection(SectionType.EXPERIENCE, new ListEstablishmentSection(establishmentList));
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