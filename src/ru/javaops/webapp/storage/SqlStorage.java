package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.ListTextSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.Section;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.model.TextSection;
import ru.javaops.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executePreparedStatement("DELETE FROM resume", preparedStatement -> {
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.execute();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM text_section WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.execute();
            }
            insertContact(connection, resume);
            insertSection(connection, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            insertContact(connection, resume);
            insertSection(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executePreparedStatement("" +
                        "SELECT * FROM resume r " +
                        "  LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        "   WHERE r.uuid = ?" +
                        "UNION " +
                        "SELECT * FROM resume r " +
                        "  LEFT JOIN text_section ts " +
                        "    ON r.uuid = ts.resume_uuid " +
                        "   WHERE r.uuid = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    preparedStatement.setString(2, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));

                    do {
                        String value = resultSet.getString("value");
                        if (value != null) {
                            String type = resultSet.getString("type");
                            switch (type) {
                                case "TELEPHONE":
                                case "SKYPE":
                                case "EMAIL":
                                case "LINKEDIN":
                                case "GITHUB":
                                case "STACKOVERFLOW":
                                case "HOMEPAGE":
                                    resume.addContact(ContactType.valueOf(type), value);
                                    break;
                                case "OBJECTIVE":
                                case "PERSONAL":
                                    resume.addSection(SectionType.valueOf(type), new TextSection(value));
                                    break;
                                case "ACHIEVEMENT":
                                case "QUALIFICATIONS":
                                    resume.addSection(SectionType.valueOf(type), new ListTextSection(value.split("\n")));
                                    break;
                            }
                        }
                    } while (resultSet.next());

                    return resume;
                });
    }


    @Override
    public void delete(String uuid) {
        sqlHelper.executePreparedStatement("DELETE FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid ASC")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String fullName = resultSet.getString("full_name");
                    resumeMap.put(uuid, new Resume(uuid, fullName));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String resumeUuid = resultSet.getString("resume_uuid");
                    String type = resultSet.getString("type");
                    String value = resultSet.getString("value");
                    Resume resume = resumeMap.get(resumeUuid);
                    resume.addContact(ContactType.valueOf(type), value);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM text_section")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String resumeUuid = resultSet.getString("resume_uuid");
                    String type = resultSet.getString("type");
                    String value = resultSet.getString("value");
                    Resume resume = resumeMap.get(resumeUuid);
                    switch (type) {
                        case "OBJECTIVE":
                        case "PERSONAL":
                            resume.addSection(SectionType.valueOf(type), new TextSection(value));
                            break;
                        case "ACHIEVEMENT":
                        case "QUALIFICATIONS":
                            resume.addSection(SectionType.valueOf(type), new ListTextSection(value.split("\n")));
                            break;
                    }
                }
            }
            return new ArrayList<>(resumeMap.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.executePreparedStatement("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    private void insertContact(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                preparedStatement.setString(3, entry.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void insertSection(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  text_section (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, resume.getUuid());
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                String sectionType = entry.getKey().name();
                preparedStatement.setString(2, sectionType);
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        preparedStatement.setString(3, ((TextSection) (entry.getValue())).getContent());
                        preparedStatement.addBatch();
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        String data = String.join("\n", ((ListTextSection) (entry.getValue())).getListContent());
                        preparedStatement.setString(3, data);
                        preparedStatement.addBatch();
                        break;
                }
            }
            preparedStatement.executeBatch();
        }
    }
}
