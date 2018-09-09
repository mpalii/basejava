package ru.javaops.webapp;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Establishment;
import ru.javaops.webapp.model.ListEstablishmentSection;
import ru.javaops.webapp.model.ListTextSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.model.TextSection;
import ru.javaops.webapp.util.DateUtil;

import java.time.Month;

import static ru.javaops.webapp.model.Establishment.Position;

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
        resume.addSection(SectionType.ACHIEVEMENT, new ListTextSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        ));

        // Qualification initialization
        resume.addSection(SectionType.QUALIFICATIONS, new ListTextSection(
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
        ));

        // Experience initialization
        resume.addSection(SectionType.EXPERIENCE,
                new ListEstablishmentSection(
                        new Establishment("Java Online Projects", "http://javaops.ru",
                                new Position(
                                        "Автор проекта.",
                                        "Создание, организация и проведение Java онлайн проектов и стажировок.",
                                        DateUtil.of(2013, Month.OCTOBER),
                                        DateUtil.NOW
                                )
                        ),
                        new Establishment("Wrike", "https://www.wrike.com",
                                new Position(
                                        "Старший разработчик (backend).",
                                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                                        DateUtil.of(2014, Month.OCTOBER),
                                        DateUtil.of(2016, Month.JANUARY)
                                )
                        ),
                        new Establishment("RIT Center", null,
                                new Position(
                                        "Java архитектор",
                                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                                        DateUtil.of(2012, Month.APRIL),
                                        DateUtil.of(2014, Month.OCTOBER)
                                )
                        ),
                        new Establishment("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                                new Position(
                                        "Ведущий программист",
                                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                                        DateUtil.of(2010, Month.DECEMBER),
                                        DateUtil.of(2012, Month.APRIL)
                                )
                        )
                )
        );

        // Education initialization
        resume.addSection(SectionType.EDUCATION,
                new ListEstablishmentSection(
                        new Establishment("Coursera", "https://www.coursera.org/course/progfun",
                                new Position(
                                        null,
                                        "\"Functional Programming Principles in Scala\" by Martin Odersky",
                                        DateUtil.of(2013, Month.MARCH),
                                        DateUtil.of(2013, Month.MAY)
                                )
                        ),
                        new Establishment("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                                new Position(
                                        null,
                                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                                        DateUtil.of(2011, Month.MARCH),
                                        DateUtil.of(2011, Month.APRIL)
                                )
                        ),
                        new Establishment("Siemens AG", "http://www.siemens.ru/",
                                new Position(
                                        null,
                                        "3 месяца обучения мобильным IN сетям (Берлин)",
                                        DateUtil.of(2005, Month.JANUARY),
                                        DateUtil.of(2005, Month.APRIL)
                                )
                        ),
                        new Establishment("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                                new Position(
                                        null,
                                        "Аспирантура (программист С, С++)",
                                        DateUtil.of(1993, Month.SEPTEMBER),
                                        DateUtil.of(1996, Month.JULY)
                                ),
                                new Position(
                                        null,
                                        "Инженер (программист Fortran, C)",
                                        DateUtil.of(1987, Month.SEPTEMBER),
                                        DateUtil.of(1993, Month.JULY)
                                )
                        )
                )
        );

        // RESULT
        System.out.println(resume);
    }
}
