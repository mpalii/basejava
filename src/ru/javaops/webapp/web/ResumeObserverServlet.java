package ru.javaops.webapp.web;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.SqlStorage;
import ru.javaops.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeObserverServlet extends HttpServlet {
    private Storage storage = new SqlStorage(
            "jdbc:postgresql://localhost:5432/resumes",
            "postgres",
            "root"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print(buildHtmlResume(storage.getAllSorted()));
    }

    private String buildHtmlResume(List<Resume> resumeList) {

        StringBuilder htmlPage = new StringBuilder();
        htmlPage.
                append("<html>").
                append("<head>").
                append("<title>Resumes info</title>").
                append("</head>").
                append("<body>");

        if (resumeList == null || resumeList.size() == 0) {
            htmlPage.append("<p>No resumes!</p>");
        } else {
            htmlPage.append("<table border=\"1\">");
            for (Resume resume : resumeList) {
                htmlPage.
                        append("<tr>").
                        append("<td>").
                        append(resume.getUuid()).
                        append("</td>").
                        append("<td>").
                        append(resume.getFullName()).
                        append("</td>").
                        append("</tr>");
            }
            htmlPage.append("</table>");
        }

        htmlPage.
                append("</body>").
                append("</html>");

        return htmlPage.toString();
    }
}
