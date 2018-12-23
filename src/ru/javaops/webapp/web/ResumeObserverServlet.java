package ru.javaops.webapp.web;

import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.ListTextSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.model.TextSection;
import ru.javaops.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeObserverServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        String objective = request.getParameter("objective");
        resume.addSection(SectionType.OBJECTIVE, new TextSection(objective));
        String personal = request.getParameter("personal");
        resume.addSection(SectionType.PERSONAL, new TextSection(personal));

        String[] rawAchievements = request.getParameterValues("achievementSection");
        resume.addSection(SectionType.ACHIEVEMENT, getCheckedForEmptyPositionsListTextSection(rawAchievements));

        String[] rawQualifications = request.getParameterValues("qualificationSection");
        resume.addSection(SectionType.QUALIFICATIONS, getCheckedForEmptyPositionsListTextSection(rawQualifications));

        storage.update(resume);
        response.sendRedirect("getResumes");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume resume;

        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("getResumes");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.
                getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").
                forward(request, response);

    }

    private ListTextSection getCheckedForEmptyPositionsListTextSection(String[] rawData) {
        List<String> checkedData = new ArrayList<>();
        for (String entry : rawData) {
            if (entry != null && !entry.isEmpty()) {
                checkedData.add(entry);
            }
        }
        return new ListTextSection(checkedData);
    }
}
