package ru.javaops.webapp.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, Contact> contacts = new LinkedHashMap<>();
    private final Map<SectionType, Section> sections = new LinkedHashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must be not null");
        Objects.requireNonNull(fullName, "fullName must be not null");
        this.uuid = uuid;
        this.fullName = fullName.trim();
    }

    public void addContact(ContactType contactType, Contact contact) {
        contacts.put(contactType, contact);
    }

    public Contact getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void removeContact(ContactType contactType) {
        contacts.remove(contactType);
    }

    public void addSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    public Section getSection(SectionType sectionType) {
        return  sections.get(sectionType);
    }

    public void removeSection(SectionType sectionType) {
        sections.remove(sectionType);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
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

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!contacts.equals(resume.contacts)) return false;
        return sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fullName).append("\n\n");

        for (Map.Entry<ContactType, Contact> entry : contacts.entrySet()) {
            stringBuilder.append(entry.getKey().getTitle()).append(" ");
            stringBuilder.append(entry.getValue().toString()).append("\n");
        }

        stringBuilder.append("\n");

        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            stringBuilder.append(entry.getKey().getTitle()).append("\n");
            stringBuilder.append(entry.getValue().toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}