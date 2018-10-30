package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Establishment;
import ru.javaops.webapp.model.ListEstablishmentSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.util.DateUtil;

import java.time.Month;

import static ru.javaops.webapp.model.Establishment.Position;

final class ResumeDataTest {
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        // RESUME_1 initialization
        RESUME_1 = new Resume("UUID_1", "Vasyl Gym");
        RESUME_1.addContact(ContactType.TELEPHONE, "555-55-55");
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("FITTERRA", "",
                                new Position(
                                        "Trainer",
                                        "Work in fitness club",
                                        DateUtil.of(2015, Month.OCTOBER),
                                        DateUtil.NOW
                                )
                        )
                )
        );

        // RESUME_2 initialization
        RESUME_2 = new Resume("UUID_2", "Serhii Soloviev");
        RESUME_2.addContact(ContactType.TELEPHONE, "999-98-78");
        RESUME_2.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("InterPIPE", "http://interpipe.biz/",
                                new Position(
                                        "Engineer",
                                        "Repair of mechanic equipment",
                                        DateUtil.of(2011, Month.NOVEMBER),
                                        DateUtil.NOW
                                )
                        )
                )
        );

        // RESUME_3 initialization
        RESUME_3 = new Resume("UUID_3", "Maksym Palii");
        RESUME_3.addContact(ContactType.TELEPHONE, "654-00-48");
        RESUME_3.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("DONBUDMONTAG", "",
                                new Position(
                                        "Cost estimator",
                                        "Cost planning",
                                        DateUtil.of(2012, Month.DECEMBER),
                                        DateUtil.NOW
                                )
                        )
                )
        );

        // RESUME_4 initialization
        RESUME_4 = new Resume("UUID_4", "Pryntseva Olha");
        RESUME_4.addContact(ContactType.TELEPHONE, "784-10-08");
        RESUME_4.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("NOVA POSHTA", "novaposhta.ua",
                                new Position(
                                        "Operator",
                                        "Solving of customer problems",
                                        DateUtil.of(2014, Month.MARCH),
                                        DateUtil.NOW
                                )
                        )
                )
        );
    }

    static Resume getResume1() {
        return RESUME_1;
    }

    static Resume getResume2() {
        return RESUME_2;
    }

    static Resume getResume3() {
        return RESUME_3;
    }

    static Resume getResume4() {
        return RESUME_4;
    }

    private ResumeDataTest() {

    }
}
