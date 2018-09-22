package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Establishment;
import ru.javaops.webapp.model.ListEstablishmentSection;
import ru.javaops.webapp.model.ListTextSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.Section;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.model.TextSection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSerializer implements ResumeSerializer {
    @Override
    public void executeWriteFile(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            int contactsSize = contacts.size();
            dataOutputStream.writeInt(contactsSize);
            if (contactsSize != 0) {
                for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
                    dataOutputStream.writeUTF(pair.getKey().name());
                    dataOutputStream.writeUTF(pair.getValue());
                }
            }

            Map<SectionType, Section> sections = resume.getSections();
            int sectionsSize = sections.size();
            dataOutputStream.writeInt(sectionsSize);
            if (sectionsSize != 0) {
                for (Map.Entry<SectionType, Section> pair : sections.entrySet()) {
                    String sectionType = pair.getKey().name();
                    dataOutputStream.writeUTF(sectionType);
                    if (sectionType.equals(TextSection.class.getSimpleName())) {
                        dataOutputStream.writeUTF(((TextSection) pair.getValue()).getContent());
                    }
                    if (sectionType.equals(ListTextSection.class.getSimpleName())) {
                        List<String> stringList = ((ListTextSection) pair.getValue()).getListContent();
                        dataOutputStream.writeInt(stringList.size());
                        if (stringList.size() != 0) {
                            for (String entry : stringList) {
                                dataOutputStream.writeUTF(entry);
                            }
                        }
                    }
                    if (sectionType.equals(ListEstablishmentSection.class.getSimpleName())) {
                        List<Establishment> establishmentList = ((ListEstablishmentSection) pair.getValue()).getEstablishmentContent();
                        dataOutputStream.writeInt(establishmentList.size());
                        if (establishmentList.size() != 0) {
                            for (Establishment element : establishmentList) {
                                dataOutputStream.writeUTF(element.getEstablishment().getName());
                                dataOutputStream.writeUTF(element.getEstablishment().getUrl());
                                List<Establishment.Position> positionList = element.getPositions();
                                dataOutputStream.writeInt(positionList.size());
                                if (positionList.size() != 0) {
                                    for (Establishment.Position position : positionList) {
                                        dataOutputStream.writeUTF(position.getTitle());
                                        dataOutputStream.writeUTF(position.getDescription());
                                        dataOutputStream.writeUTF(position.getStartDate().format(FORMATTER));
                                        dataOutputStream.writeUTF(position.getEndDate().format(FORMATTER));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume executeReadFile(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                String contactType = dataInputStream.readUTF();
                String contactValue = dataInputStream.readUTF();
                resume.addContact(ContactType.valueOf(contactType), contactValue);
            }

            size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                String sectionType = dataInputStream.readUTF();
                if (sectionType.equals(TextSection.class.getSimpleName())) {
                    String content = dataInputStream.readUTF();
                    resume.addSection(SectionType.valueOf(sectionType), new TextSection(content));
                }
                if (sectionType.equals(ListTextSection.class.getSimpleName())) {
                    int listSize = dataInputStream.readInt();
                    List<String> stringList = new ArrayList<>(listSize);
                    for (int j = 0; j < listSize; j++) {
                        stringList.add(dataInputStream.readUTF());
                    }
                    resume.addSection(SectionType.valueOf(sectionType), new ListTextSection(stringList));
                }
                if (sectionType.equals(ListEstablishmentSection.class.getSimpleName())) {
                    int establishmentListSize = dataInputStream.readInt();
                    List<Establishment> establishmentList = new ArrayList<>(establishmentListSize);
                    for (int j = 0; j < establishmentListSize; j++) {
                        String name = dataInputStream.readUTF();
                        String url = dataInputStream.readUTF();
                        int positionListSize = dataInputStream.readInt();
                        List<Establishment.Position> positionList = new ArrayList<>(positionListSize);
                        for (int k = 0; k < positionListSize; k++) {
                            String title = dataInputStream.readUTF();
                            String description = dataInputStream.readUTF();
                            LocalDate startDate = LocalDate.parse(dataInputStream.readUTF(), FORMATTER);
                            LocalDate endDate = LocalDate.parse(dataInputStream.readUTF(), FORMATTER);
                            positionList.add(new Establishment.Position(title, description, startDate, endDate));
                        }
                        establishmentList.add(new Establishment(name, url, positionList));
                    }
                    resume.addSection(SectionType.valueOf(sectionType), new ListEstablishmentSection(establishmentList));
                }
            }

            return resume;
        }
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
