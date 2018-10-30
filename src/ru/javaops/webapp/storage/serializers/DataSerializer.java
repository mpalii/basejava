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

            readInfo(dataInputStream, () -> {
                String contactType = dataInputStream.readUTF();
                String contactValue = dataInputStream.readUTF();
                resume.addContact(ContactType.valueOf(contactType), contactValue);
            });

            readInfo(dataInputStream, () -> {
                String sectionType = dataInputStream.readUTF();
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        String content = dataInputStream.readUTF();
                        resume.addSection(SectionType.valueOf(sectionType), new TextSection(content));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> stringList = readList(dataInputStream, DataInput::readUTF);
                        resume.addSection(SectionType.valueOf(sectionType), new ListTextSection(stringList));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Establishment> establishmentList = readList(dataInputStream, dis ->
                                new Establishment(dis.readUTF(), dis.readUTF(),
                                        readList(dis, dataInputStream1 ->
                                                new Establishment.Position(dataInputStream1.readUTF(),
                                                        dataInputStream1.readUTF(),
                                                        LocalDate.parse(dataInputStream1.readUTF(), FORMATTER),
                                                        LocalDate.parse(dataInputStream1.readUTF(), FORMATTER)
                                                )
                                        )
                                )
                        );
                        resume.addSection(SectionType.valueOf(sectionType), new ListEstablishmentSection(establishmentList));
                        break;
                }
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface InformationReader<T> {
        T read(DataInputStream dataInputStream) throws IOException;
    }

    @FunctionalInterface
    private interface Executor {
        void execute() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dataInputStream, InformationReader<T> reader) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(reader.read(dataInputStream));
        }
        return list;
    }

    private void readInfo(DataInputStream dis, Executor executor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            executor.execute();
        }
    }
}