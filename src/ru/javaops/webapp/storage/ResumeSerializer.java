package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ResumeSerializer {

    void executeWriteFile(OutputStream outputStream, Resume resume) throws IOException;

    Resume executeReadFile(InputStream inputStream) throws IOException;
}
