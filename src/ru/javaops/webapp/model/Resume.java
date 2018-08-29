package ru.javaops.webapp.model;

import java.util.*;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must be not null");
        Objects.requireNonNull(fullName, "fullName must be not null");
        this.uuid = uuid;
        this.fullName = fullName.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void addContact(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    public void addSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.getFullName());
        return (result == 0) ? uuid.compareTo(o.getUuid()) : result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fullName).append("\n\n");

        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            stringBuilder.append(entry.getKey().getTitle()).append(" ");
            stringBuilder.append(entry.getValue()).append("\n");
        }

        stringBuilder.append("\n");

        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            stringBuilder.append(entry.getKey().getTitle()).append("\n");
            stringBuilder.append(entry.getValue().toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}