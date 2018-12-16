package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Establishment;
import ru.javaops.webapp.model.ListEstablishmentSection;
import ru.javaops.webapp.model.ListTextSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.model.TextSection;
import ru.javaops.webapp.util.DateUtil;

import java.time.Month;

final class ResumeDataTest {
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        // RESUME_1 initialization
        RESUME_1 = new Resume("UUID_1", "Vasyl Gym");
        RESUME_1.addContact(ContactType.TELEPHONE, "555-55-55");
        RESUME_1.addContact(ContactType.EMAIL, "email@mail.com");
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Персональный тренер по фитнесу"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection(
                "Хopoшaя физичeckaя фopma, вынocливый, цeлeycтpemлeнный, cтpeccoycтoйчивый, тpyдoлюбивый"
        ));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListTextSection(
                "Проведение соревнований пo силовым видам спорта",
                "Участие в подготовке чемпионов и призеров международных соревнований, кандидатов в мастера спорта, мастеров спорта",
                "проведение занятий по силовому шейпингу",
                "проведение групповых занятий по аэробике"
        ));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListTextSection(
                "«Fitness-pro» г. Санкт-Петербург",
                "«Спортивная диетология» г. Москва",
                "«Говорим свободно на английском» г. Москва"
        ));
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("FITTERRA", "",
                                new Establishment.Position(
                                        "Trainer",
                                        "Work in fitness club",
                                        DateUtil.of(2015, Month.OCTOBER),
                                        DateUtil.NOW
                                )
                        )
                )
        );

        // RESUME_2 initialization
        RESUME_2 = new Resume("UUID_2", "Serhii Nesterov");
        RESUME_2.addContact(ContactType.TELEPHONE, "999-98-78");
        RESUME_2.addContact(ContactType.HOMEPAGE, "homepage.com");
        RESUME_2.addSection(SectionType.OBJECTIVE, new TextSection("Наладчик трубопрокатных станков"));
        RESUME_2.addSection(SectionType.PERSONAL, new TextSection(
                "Коммуникабельный, ответственный, пунктуальный, внимательный"
        ));
        RESUME_2.addSection(SectionType.ACHIEVEMENT, new ListTextSection(
                "Выполнение ремонтов и модернизации оборудования",
                "обеспечение технической эксплуатации, своевременной и качественной подготовки производства, ремонта и модернизации оборудования, его эффективности работы",
                "координация сотрудничества с контролирующими и инспектирующими органами"
        ));
        RESUME_2.addSection(SectionType.QUALIFICATIONS, new ListTextSection(
                "MS Office",
                "MS Excel",
                "Scad SYSTEMS",
                "EPLAN"
        ));
        RESUME_2.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("InterPIPE", "http://interpipe.biz/",
                                new Establishment.Position(
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
        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("Инженер-сметчик"));
        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("Внимание к деталям, стрессоустойчивость"));
        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListTextSection(
                "Ведение объектов строительства финансируемых из государственного бюджета",
                "Защита выполненных работ перед заказчиком",
                "Составление калькуляций"
        ));
        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListTextSection(
                "Строительные технологии смета",
                "Смета ПИР",
                "Тендер-контракт интеллектуальные системы",
                "ГрандСмета РФ"
        ));
        RESUME_3.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("DONBUDMONTAG", "",
                                new Establishment.Position(
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
        RESUME_4.addSection(SectionType.OBJECTIVE, new TextSection("Мастер маникюра"));
        RESUME_4.addSection(SectionType.PERSONAL, new TextSection("Выдержка, умение находить оригинальные идеи"));
        RESUME_4.addSection(SectionType.ACHIEVEMENT, new ListTextSection(
                "Аппаратный маникюр",
                "Классически маникюр",
                "Технология стемпинг"
        ));
        RESUME_4.addSection(SectionType.QUALIFICATIONS, new ListTextSection(
                "МАСТЕР ПОД КЛЮЧ - г. Киев",
                "Стильные дизайны - г. Днепр"
        ));
        RESUME_4.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("NOVA POSHTA", "novaposhta.ua",
                                new Establishment.Position(
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
