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
    private static final String EMPTY = "";

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
                            dataOutputStream.writeUTF(url != null ? url : EMPTY);
                            writeCollection(dataOutputStream, establishment.getPositions(), position -> {
                                String title = position.getTitle();
                                dataOutputStream.writeUTF(title != null ? title : EMPTY);
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

            readContacts(resume, dataInputStream, DataInputStream::readInt, DataInput::readUTF);
            readSections(resume, dataInputStream, DataInputStream::readInt, DataInput::readUTF, dataInputStream1 -> {
                String name = dataInputStream1.readUTF();
                String url = dataInputStream1.readUTF();
                if (url.equals(EMPTY)) {
                    url = null;
                }
                List<Establishment.Position> positionList = readList(dataInputStream1, dataInputStream11 -> {
                    String title = dataInputStream11.readUTF();
                    if (title.equals(EMPTY)) {
                        title = null;
                    }
                    String description = dataInputStream11.readUTF();
                    LocalDate startDate = LocalDate.parse(dataInputStream11.readUTF(), FORMATTER);
                    LocalDate endDate = LocalDate.parse(dataInputStream11.readUTF(), FORMATTER);
                    return new Establishment.Position(title, description, startDate, endDate);
                }, DataInputStream::readInt);
                return new Establishment(name, url, positionList);
            });

            return resume;
        }
    }

    @FunctionalInterface
    private interface InformationReader<T> {
        T read(DataInputStream dataInputStream) throws IOException;
    }

    private <T> T readInformation(DataInputStream dis, InformationReader<T> reader) throws IOException {
        return reader.read(dis);
    }

    private <T> List<T> readList(DataInputStream dataInputStream, InformationReader<T> tReader,
                                 InformationReader<Integer> integerInformationReader) throws IOException {
        int size = integerInformationReader.read(dataInputStream);
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(tReader.read(dataInputStream));
        }
        return list;
    }

    private void readContacts(Resume resume, DataInputStream dis, InformationReader<Integer> integerInformationReader,
                              InformationReader<String> stringInformationReader) throws IOException {
        int size = integerInformationReader.read(dis);
        for (int i = 0; i < size; i++) {
            String contactType = readInformation(dis, stringInformationReader);
            String contactValue = readInformation(dis, stringInformationReader);
            resume.addContact(ContactType.valueOf(contactType), contactValue);
        }
    }

    private void readSections(Resume resume, DataInputStream dis, InformationReader<Integer> intReader,
                              InformationReader<String> strReader, InformationReader<Establishment> estReader)
            throws IOException {
        int size = intReader.read(dis);
        for (int i = 0; i < size; i++) {
            String sectionType = strReader.read(dis);
            switch (sectionType) {
                case "OBJECTIVE":
                case "PERSONAL":
                    String content = strReader.read(dis);
                    resume.addSection(SectionType.valueOf(sectionType), new TextSection(content));
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    List<String> stringList = readList(dis, strReader, intReader);
                    resume.addSection(SectionType.valueOf(sectionType), new ListTextSection(stringList));
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
                    List<Establishment> establishmentList = readList(dis, estReader, intReader);
                    resume.addSection(SectionType.valueOf(sectionType), new ListEstablishmentSection(establishmentList));
                    break;
            }
        }
    }
}