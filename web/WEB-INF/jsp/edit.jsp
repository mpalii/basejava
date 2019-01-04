<%@ page import="java.lang.String" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="getResumes" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}"/>

        <table border="1" style="margin: auto">
            <tr>
                <td width="150px"><p>Имя:</p></td>
                <td><input type="text" size="50" name="fullName" value="${resume.fullName}"></td>
            </tr>

            <c:forEach var="contact" items="${ContactType.values()}">
                <jsp:useBean id="contact"
                             type="ru.javaops.webapp.model.ContactType"/>
                <tr>
                    <td>${contact.title}</td>
                    <td><input type="text" size="50" name="${contact.name()}" value="${resume.getContact(contact)}">
                    </td>
                </tr>
            </c:forEach>
        </table>

        <br><br>

        <table border="1" style="margin: auto">
            <c:forEach var="section" items="${resume.sections}">
                <jsp:useBean id="section"
                             type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.Section>"/>
                <c:set var="sectionName" value="${section.key.name()}"/>

                <c:choose>
                    <%--Objective or Personal sections--%>
                    <c:when test="${sectionName=='OBJECTIVE' || sectionName=='PERSONAL'}">
                        <tr>
                            <td width="150px"><p>${SectionType.valueOf(sectionName).getTitle()}</p></td>
                            <td><input type="text" name="${sectionName}" size="80"
                                       value="${resume.getSection(SectionType.valueOf(sectionName))}"></td>
                        </tr>
                    </c:when>

                    <%--Achievement or Qualification section--%>
                    <c:when test="${sectionName=='ACHIEVEMENT' || sectionName=='QUALIFICATIONS'}">
                        <c:set var="listTextSection" value="${resume.getSection(SectionType.valueOf(sectionName))}"/>
                        <jsp:useBean id="listTextSection"
                                     type="ru.javaops.webapp.model.ListTextSection"/>
                        <c:set var="content" value="${String.join('&#13;&#10;', listTextSection.listContent)}"/>
                        <tr>
                            <td>${SectionType.valueOf(sectionName).getTitle()}</td>
                            <td><textarea name="${sectionName}" cols="80" rows="10">${content}</textarea></td>
                        </tr>
                    </c:when>

                    <%--Experience or Education section--%>
                    <c:when test="${sectionName=='EXPERIENCE' || sectionName=='EDUCATION'}">
                        <c:set var="listEstablishment" value="${resume.getSection(SectionType.valueOf(sectionName))}"/>
                        <jsp:useBean id="listEstablishment"
                                     type="ru.javaops.webapp.model.ListEstablishmentSection"/>
                        <tr>
                            <td>${SectionType.valueOf(sectionName).getTitle()}</td>
                            <td>
                                <table width="100%">
                                    <c:forEach var="establishment" items="${listEstablishment.establishmentContent}">
                                        <tr>
                                            <td>
                                                <p>${establishment.establishment.name}</p>
                                            </td>
                                            <td align="center" width="120px">
                                                <a class="button" href="getEstablishment?uuid=${resume.uuid}&action=delete&sectionType=${sectionName}&establishmentUUID=${establishment.uuid}">Удалить</a>
                                            </td>
                                            <td align="center" width="120px">
                                                <a class="button" href="getEstablishment?uuid=${resume.uuid}&action=edit&sectionType=${sectionName}&establishmentUUID=${establishment.uuid}">Редактировать</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <input type="button" onclick="location.href = 'getEstablishment?uuid=${resume.uuid}&action=add&sectionType=${sectionName}&establishmentUUID=null';"
                                       value="Добавить организацию в секцию '${SectionType.valueOf(sectionName).title}'">
                                <br>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>

            </c:forEach>
        </table>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
    <br>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
