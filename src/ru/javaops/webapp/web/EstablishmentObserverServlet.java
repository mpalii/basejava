package ru.javaops.webapp.web;

import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.Establishment;
import ru.javaops.webapp.model.ListEstablishmentSection;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.Section;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstablishmentObserverServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String establishmentName = request.getParameter("establishmentName");
        String establishmentUrl = request.getParameter("establishmentUrl");
        String resumeUuid = request.getParameter("resumeUuid");
        String establishmentUuid = request.getParameter("establishmentUuid");
        String sectionType = request.getParameter("sectionType");
        Resume resume = storage.get(resumeUuid);
        ListEstablishmentSection establishments = (ListEstablishmentSection) resume.getSection(SectionType.valueOf(sectionType));
        List<Establishment.Position> positions = new ArrayList<>();
        String[] titles = request.getParameterValues("position");
        String[] startDate = request.getParameterValues("startDate");
        String[] endDate = request.getParameterValues("endDate");
        String[] description = request.getParameterValues("description");
        if (titles != null) {
            for (int i = 0; i < titles.length; i++) {
                if (titles[i] != null && titles[i].length() != 0) {
                    positions.add(new Establishment.Position(titles[i], description[i], LocalDate.parse(startDate[i]), LocalDate.parse(endDate[i])));
                }
            }
        }
        Establishment establishment = new Establishment(establishmentUuid, establishmentName, establishmentUrl, positions);
        establishments.updateEstablishment(establishment);
        storage.update(resume);
        response.sendRedirect("getResumes?uuid=" + resumeUuid + "&action=view");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        String sectionType = request.getParameter("sectionType");
        String establishmentUUID = request.getParameter("establishmentUUID");
        Resume resume = storage.get(uuid);
        ListEstablishmentSection section = (ListEstablishmentSection) resume.getSection(SectionType.valueOf(sectionType));
        switch (action) {
            case "delete":
                section.deleteEstablishmentByUuid(establishmentUUID);
                storage.update(resume);
                response.sendRedirect("getResumes?uuid=" + uuid + "&action=edit");
                break;
            case "edit":
                Establishment establishment = section.getEstablishmentByUuid(establishmentUUID);
                request.setAttribute("resume", resume);
                request.setAttribute("sectionType", sectionType);
                request.setAttribute("establishment", establishment);
                request.getRequestDispatcher("/WEB-INF/jsp/establishment.jsp").forward(request, response);
                break;
            case "add":
                section.getEstablishmentContent().add(new Establishment("Новое учреждение", null, new ArrayList<>()));
                storage.update(resume);
                response.sendRedirect("getResumes?uuid=" + uuid + "&action=edit");
                break;
        }
    }
}
