package ru.javaops.webapp.web;

import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.Section;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResumeObserverServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        Resume resume = getResume(uuid);
        response.getWriter().print(buildHtmlResume(resume));
    }

    private Resume getResume(String uuid) {
        SqlStorage sqlStorage = new SqlStorage(
                "jdbc:postgresql://localhost:5432/resumes",
                "postgres",
                "root"
        );
        return sqlStorage.get(uuid);
    }

    private String buildHtmlResume(Resume resume) {
        String uuid = resume.getUuid();
        String fullName = resume.getFullName();
        Map<ContactType, String> contacts = resume.getContacts();
        Map<SectionType, Section> sections = resume.getSections();

        StringBuilder htmlPage = new StringBuilder();
        htmlPage.
                append("<html>").
                append("<head>").
                append("<title>Resume ").append(resume.getUuid()).append(" info</title>").
                append("</head>").
                append("<body>");

        // Full name, UUID
        htmlPage.
                append("<table border=\"1\">").
                append("<tr>").
                append("<td>").append("ФИО:").append("</td>").
                append("<td>").append(fullName).append("</td>").
                append("</tr>").
                append("<tr>").
                append("<td>").append("Идентификатор:").append("</td>").
                append("<td>").append(uuid).append("</td>").
                append("</tr>").
                append("</table>");

        // Contacts
        if (contacts.size() != 0) {
            htmlPage.
                    append("<table border=\"1\">").
                    append("<caption>Контакты:</caption>");
            for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
                htmlPage.
                        append("<tr>").
                        append("<td>").append(pair.getKey().getTitle()).append("</td>").
                        append("<td>").append(pair.getValue()).append("</td>").
                        append("</tr>");
            }

            htmlPage.
                    append("</table>");
        }

        // Sections
        if (sections.size() != 0) {
            htmlPage.
                    append("<table border=\"1\">").
                    append("<caption>Секции:</caption>");
            for (Map.Entry<SectionType, Section> pair : sections.entrySet()) {
                htmlPage.
                        append("<tr>").
                        append("<td>").append(pair.getKey().getTitle()).append("</td>").
                        append("<td>").append(pair.getValue()).append("</td>").
                        append("</tr>");
            }

            htmlPage.
                    append("</table>");
        }

        htmlPage.
                append("</body>").
                append("</html>");

        return htmlPage.toString();
    }
}
