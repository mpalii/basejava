package ru.javaops.webapp;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Establishment;
import ru.javaops.webapp.model.ListEstablishmentSection;
import ru.javaops.webapp.model.ListTextSection;
import ru.javaops.webapp.model.Position;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.model.TextSection;
import ru.javaops.webapp.util.DateUtil;

import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainResumeObjectModel {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        // Contacts initialization
        resume.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "gkislin");
        resume.addContact(ContactType.GITHUB, "gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "gkislin");
        resume.addContact(ContactType.HOMEPAGE, "Домашняя страница");

        // Objective initialization
        resume.addSection(SectionType.OBJECTIVE,
                new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        // Personal initialization
        resume.addSection(SectionType.PERSONAL,
                new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        // Achievement initialization
        List<String> achievementsList = Arrays.asList(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        );

        resume.addSection(SectionType.ACHIEVEMENT, new ListTextSection(achievementsList));

        // Qualification initialization
        List<String> qualificationsList = Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов",
                "проектрирования, архитектурных шаблонов, UML, функционального",
                "программирования",
                "Родной русский, английский \"upper intermediate\""
        );

        resume.addSection(SectionType.QUALIFICATIONS, new ListTextSection(qualificationsList));

        // Experience initialization
        List<Establishment> establishmentList = new LinkedList<>();

        List<Position> listPositions = new LinkedList<>();
        listPositions.add(new Position("Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                DateUtil.of(2013, Month.OCTOBER),
                DateUtil.now())
        );

        establishmentList.add(
                new Establishment(
                        "Java Online Projects",
                        "http://javaops.ru",
                        listPositions
                )
        );

        listPositions = new LinkedList<>();
        listPositions.add(new Position("Старший разработчик (backend).",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                DateUtil.of(2012, Month.APRIL),
                DateUtil.of(2014, Month.OCTOBER))
        );

        establishmentList.add(
                new Establishment(
                        "Wrike",
                        "https://www.wrike.com",
                        listPositions
                )
        );

        resume.addSection(SectionType.EXPERIENCE, new ListEstablishmentSection(establishmentList));

        // Education initialization
        establishmentList = new LinkedList<>();

        listPositions = new LinkedList<>();
        listPositions.add(new Position(null,
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                DateUtil.of(2013, Month.MARCH),
                DateUtil.of(2013, Month.MAY))
        );

        establishmentList.add(
                new Establishment(
                        "Coursera",
                        "https://www.coursera.org/course/progfun",
                        listPositions
                )
        );

        listPositions = new LinkedList<>();
        listPositions.add(new Position(null,
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                DateUtil.of(2011, Month.MARCH),
                DateUtil.of(2011,  Month.APRIL))
        );

        establishmentList.add(
                new Establishment(
                        "Luxoft",
                        "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        listPositions
                )
        );

        resume.addSection(SectionType.EDUCATION, new ListEstablishmentSection(establishmentList));

        // RESULT
        System.out.println(resume);
    }
}
