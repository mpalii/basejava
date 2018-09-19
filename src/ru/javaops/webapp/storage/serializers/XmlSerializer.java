package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.Establishment;
import ru.javaops.webapp.model.Link;
import ru.javaops.webapp.model.ListEstablishmentSection;
import ru.javaops.webapp.model.ListTextSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.TextSection;
import ru.javaops.webapp.util.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class XmlSerializer implements ResumeSerializer {
    private XmlParser xmlParser;

    public XmlSerializer() {
        xmlParser = new XmlParser(
                Resume.class,
                Establishment.class,
                Link.class,
                ListEstablishmentSection.class,
                TextSection.class,
                ListTextSection.class,
                Establishment.Position.class
        );
    }

    @Override
    public void executeWriteFile(OutputStream outputStream, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume executeReadFile(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
