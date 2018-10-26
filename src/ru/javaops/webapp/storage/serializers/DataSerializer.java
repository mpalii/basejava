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
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataSerializer implements ResumeSerializer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String NO_DATA_MARKER = "null";

    @Override
    public void executeWriteFile(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dataOutputStream, contacts.entrySet(), (Map.Entry<ContactType, String> entry) -> {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            writeCollection(dataOutputStream, sections.entrySet(), (Map.Entry<SectionType, Section> entry) -> {
                String sectionType = entry.getKey().name();
                dataOutputStream.writeUTF(sectionType);
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        dataOutputStream.writeUTF(((TextSection) (entry.getValue())).getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        writeCollection(dataOutputStream, ((ListTextSection) (entry.getValue())).getListContent(), dataOutputStream::writeUTF);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        writeCollection(dataOutputStream, ((ListEstablishmentSection) (entry.getValue())).getEstablishmentContent(), establishment -> {
                            dataOutputStream.writeUTF(establishment.getEstablishment().getName());
                            String url = establishment.getEstablishment().getUrl();
                            dataOutputStream.writeUTF(url != null ? url : NO_DATA_MARKER);
                            writeCollection(dataOutputStream, establishment.getPositions(), position -> {
                                String title = position.getTitle();
                                dataOutputStream.writeUTF(title != null ? title : NO_DATA_MARKER);
                                dataOutputStream.writeUTF(position.getDescription());
                                dataOutputStream.writeUTF(position.getStartDate().format(FORMATTER));
                                dataOutputStream.writeUTF(position.getEndDate().format(FORMATTER));
                            });
                        });
                }
            });

        }
    }

    @FunctionalInterface
    private interface InformationWriter<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, InformationWriter<T> writer)
            throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
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
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        String content = dataInputStream.readUTF();
                        resume.addSection(SectionType.valueOf(sectionType), new TextSection(content));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        int listSize = dataInputStream.readInt();
                        List<String> stringList = new ArrayList<>(listSize);
                        for (int j = 0; j < listSize; j++) {
                            stringList.add(dataInputStream.readUTF());
                        }
                        resume.addSection(SectionType.valueOf(sectionType), new ListTextSection(stringList));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        int establishmentListSize = dataInputStream.readInt();
                        List<Establishment> establishmentList = new ArrayList<>(establishmentListSize);
                        for (int j = 0; j < establishmentListSize; j++) {
                            String name = dataInputStream.readUTF();
                            String url = dataInputStream.readUTF();
                            if (url.equals(NO_DATA_MARKER)) {
                                url = null;
                            }
                            int positionListSize = dataInputStream.readInt();
                            List<Establishment.Position> positionList = new ArrayList<>(positionListSize);
                            for (int k = 0; k < positionListSize; k++) {
                                String title = dataInputStream.readUTF();
                                if (title.equals(NO_DATA_MARKER)) {
                                    title = null;
                                }
                                String description = dataInputStream.readUTF();
                                LocalDate startDate = LocalDate.parse(dataInputStream.readUTF(), FORMATTER);
                                LocalDate endDate = LocalDate.parse(dataInputStream.readUTF(), FORMATTER);
                                positionList.add(new Establishment.Position(title, description, startDate, endDate));
                            }
                            establishmentList.add(new Establishment(name, url, positionList));
                        }
                        resume.addSection(SectionType.valueOf(sectionType), new ListEstablishmentSection(establishmentList));
                        break;
                }
            }
            return resume;
        }
    }
}