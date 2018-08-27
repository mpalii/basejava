package ru.javaops.webapp;

import ru.javaops.webapp.model.*;

import java.util.LinkedList;
import java.util.List;

public class MainResumeObjectModel {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        // Contacts initialization
        resume.addContact(ContactType.TELEPHONE, new Contact("+7(921) 855-0482", null));
        resume.addContact(ContactType.SKYPE, new Contact("grigory.kislin", null));
        resume.addContact(ContactType.EMAIL, new Contact("gkislin@yandex.ru", null));
        resume.addContact(ContactType.LINKEDIN, new Contact("gkislin", null));
        resume.addContact(ContactType.GITHUB, new Contact("gkislin", null));
        resume.addContact(ContactType.STACKOVERFLOW, new Contact("gkislin", null));
        resume.addContact(ContactType.HOMEPAGE, new Contact("Домашняя страница", new Link("Homepage", "http://gkislin.ru/")));

        // Objective initialization
        String objectiveText = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        SingleTextContainer objectiveTextContainer =  new SingleTextContainer(objectiveText);
        resume.addSection(SectionType.OBJECTIVE, new Section("OBJECTIVE", objectiveTextContainer));

        // Personal initialization
        String personalText = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
        SingleTextContainer personalTextContainer = new SingleTextContainer(personalText);
        resume.addSection(SectionType.PERSONAL, new Section("PERSONAL", personalTextContainer));

        // Achievement initialization
        String achievement1 = "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.";
        String achievement2 = "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.";
        String achievement3 = "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.";
        String achievement4 = "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.";
        String achievement5 = "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).";
        String achievement6 = "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.";
        List<String> achievementsList = new LinkedList<>();
        achievementsList.add(achievement1);
        achievementsList.add(achievement2);
        achievementsList.add(achievement3);
        achievementsList.add(achievement4);
        achievementsList.add(achievement5);
        achievementsList.add(achievement6);
        ListTextContainer achievementTextContainer = new ListTextContainer(achievementsList);

        // Qualifications initializations
        String qualification1 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
        String qualification2 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
        String qualification3 = "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,";
        String qualification4 = "MySQL, SQLite, MS SQL, HSQLDB";
        String qualification5 = "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,";
        String qualification6 = "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,";
        String qualification7 = "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).";
        String qualification8 = "Python: Django.";
        String qualification9 = "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js";
        String qualification10 = "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka";
        String qualification11 = "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.";
        String qualification12 = "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,";
        String qualification13 = "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.";
        String qualification14 = "Отличное знание и опыт применения концепций ООП, SOA, шаблонов";
        String qualification15 = "проектрирования, архитектурных шаблонов, UML, функционального";
        String qualification16 = "программирования";
        String qualification17 = "Родной русский, английский \"upper intermediate\"";
        List<String> qualificationsList = new LinkedList<>();
        qualificationsList.add(qualification1);
        qualificationsList.add(qualification2);
        qualificationsList.add(qualification3);
        qualificationsList.add(qualification4);
        qualificationsList.add(qualification5);
        qualificationsList.add(qualification6);
        qualificationsList.add(qualification7);
        qualificationsList.add(qualification8);
        qualificationsList.add(qualification9);
        qualificationsList.add(qualification10);
        qualificationsList.add(qualification11);
        qualificationsList.add(qualification12);
        qualificationsList.add(qualification13);
        qualificationsList.add(qualification14);
        qualificationsList.add(qualification15);
        qualificationsList.add(qualification16);
        qualificationsList.add(qualification17);
        ListTextContainer qualificationTextContainer = new ListTextContainer(qualificationsList);

        // Experience and Education initialization
        // Variables preparation
        List<Establishment> establishmentList;
        String activity;
        String startDate;
        String endDate;
        Position position;
        Establishment establishment;
        List<Position> positionList;

        // Experience initialization
        establishmentList = new LinkedList<>();

        activity = "Автор проекта. Создание, организация и проведение Java онлайн проектов и стажировок.";
        startDate = "10/2013";
        endDate = "Сейчас";
        position = new Position(activity, startDate, endDate);
        establishment = new Establishment("Java Online Projects", new Link("javaops", "http://javaops.ru/"));
        positionList = new LinkedList<>();
        positionList.add(position);
        establishment.setPositions(positionList);
        establishmentList.add(establishment);

        activity = "Старший разработчик (backend). Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.";
        startDate = "04/2012";
        endDate = "10/2014";
        position = new Position(activity, startDate, endDate);
        establishment = new Establishment("Wrike", new Link("Wrike", "https://www.wrike.com/"));
        positionList = new LinkedList<>();
        positionList.add(position);
        establishment.setPositions(positionList);
        establishmentList.add(establishment);

        EstablishmentContainer establishmentContainer = new EstablishmentContainer(establishmentList);
        resume.addSection(SectionType.EXPERIENCE, new Section("EXPERIENCE", establishmentContainer));

        // Education initialization
        establishmentList = new LinkedList<>();

        activity = "\"Functional Programming Principles in Scala\" by Martin Odersky";
        startDate = "03/2013";
        endDate = "05/2013";
        position = new Position(activity, startDate, endDate);
        establishment = new Establishment("Coursera", new Link("Coursera", "https://www.coursera.org/course/progfun"));
        positionList = new LinkedList<>();
        positionList.add(position);
        establishment.setPositions(positionList);
        establishmentList.add(establishment);

        activity = "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"";
        startDate = "03/2011";
        endDate = "04/2011";
        position = new Position(activity, startDate, endDate);
        establishment = new Establishment("Luxoft", new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"));
        positionList = new LinkedList<>();
        positionList.add(position);
        establishment.setPositions(positionList);
        establishmentList.add(establishment);

        EstablishmentContainer educationContainer = new EstablishmentContainer(establishmentList);
        resume.addSection(SectionType.EDUCATION, new Section("EDUCATION", educationContainer));

        // RESULT
        System.out.println(resume);
    }
}
