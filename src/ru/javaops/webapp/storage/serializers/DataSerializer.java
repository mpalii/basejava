package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Establishment;
import ru.javaops.webapp.model.ListEstablishmentSection;
import ru.javaops.webapp.model.ListTextSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.Section;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.model.TextSection;

import java.io.DataInput;
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

    // FILE WRITING
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

    // FILE READING
    @Override
    public Resume executeReadFile(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(resume, dataInputStream, DataInput::readUTF);
            readSections(resume, dataInputStream, DataInput::readUTF);
            return resume;
        }
    }

    @FunctionalInterface
    private interface InformationReader<T> {
        T read(DataInputStream dis) throws IOException;
    }

    private void readContacts(Resume resume, DataInputStream dis, InformationReader<String> reader)
            throws IOException {
        int elementsCounter = dis.readInt();
        for (int i = 0; i < elementsCounter; i++) {
            String contactType = reader.read(dis);
            String contactValue = reader.read(dis);
            resume.addContact(ContactType.valueOf(contactType), contactValue);
        }
    }

    private void readSections(Resume resume, DataInputStream dis, InformationReader<String> reader)
            throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            String sectionType = reader.read(dis);
            switch (sectionType) {
                case "OBJECTIVE":
                case "PERSONAL":
                    String content = reader.read(dis);
                    resume.addSection(SectionType.valueOf(sectionType), new TextSection(content));
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    List<String> stringList = readStringsList(dis, reader);
                    resume.addSection(SectionType.valueOf(sectionType), new ListTextSection(stringList));
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
                    List<Establishment> establishmentList = readEstablishmentList(dis, reader);
                    resume.addSection(SectionType.valueOf(sectionType), new ListEstablishmentSection(establishmentList));
                    break;
            }
        }
    }

    private List<String> readStringsList(DataInputStream dis, InformationReader<String> reader) throws IOException {
        int listSize = dis.readInt();
        List<String> stringList = new ArrayList<>(listSize);
        for (int j = 0; j < listSize; j++) {
            stringList.add(reader.read(dis));
        }
        return stringList;
    }

    private List<Establishment> readEstablishmentList(DataInputStream dis, InformationReader<String> reader)
            throws IOException {
        int establishmentListSize = dis.readInt();
        List<Establishment> establishmentList = new ArrayList<>(establishmentListSize);
        for (int i = 0; i < establishmentListSize; i++) {
            String name = reader.read(dis);
            String url = reader.read(dis);
            if (url.equals(NO_DATA_MARKER)) {
                url = null;
            }
            List<Establishment.Position> positionList = readPositionsList(dis, reader);
            establishmentList.add(new Establishment(name, url, positionList));
        }
        return establishmentList;
    }

    private List<Establishment.Position> readPositionsList(DataInputStream dis, InformationReader<String> reader)
            throws IOException {
        int positionListSize = dis.readInt();
        List<Establishment.Position> positionList = new ArrayList<>(positionListSize);
        for (int k = 0; k < positionListSize; k++) {
            String title = reader.read(dis);
            if (title.equals(NO_DATA_MARKER)) {
                title = null;
            }
            String description = reader.read(dis);
            LocalDate startDate = LocalDate.parse(reader.read(dis), FORMATTER);
            LocalDate endDate = LocalDate.parse(reader.read(dis), FORMATTER);
            positionList.add(new Establishment.Position(title, description, startDate, endDate));
        }
        return positionList;
    }
}