<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="ru.javaops.webapp.model.TextSection" %>
<%@ page import="ru.javaops.webapp.model.ListTextSection" %>
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

    <h2>${resume.fullName}&nbsp;<a class="button" href="getResumes?uuid=${resume.uuid}&action=edit">Редактировать</a>
    </h2>

    <%--Contacts table--%>
    <table>
        <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<ru.javaops.webapp.model.ContactType, java.lang.String>"/>
            <tr>
        <td>${contactEntry.key.toHtml(contactEntry.getValue())}<td/>
            </tr>
            </c:forEach>
    </table>

    <%--Sections table--%>
    <table>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.Section>"/>
            <c:set var="sectionName" value="${sectionEntry.key.name()}"/>

            <c:choose>
                <%--Objective or Personal sections--%>
                <c:when test="${sectionName=='OBJECTIVE' || sectionName=='PERSONAL'}">
                    <c:set var="textSection" value="${sectionEntry.getValue()}"/>
                    <jsp:useBean id="textSection"
                                 type="ru.javaops.webapp.model.TextSection"/>
                    <tr>
                        <td><h4>${SectionType.valueOf(sectionName).getTitle()}</h4></td>
                        <td valign="top"><p>${textSection.content}</p></td>
                    </tr>
                </c:when>

                <%--Achievement or Qualification section--%>
                <c:when test="${sectionName=='ACHIEVEMENT' || sectionName=='QUALIFICATIONS'}">
                    <c:set var="listTextSection" value="${sectionEntry.value}"/>
                    <jsp:useBean id="listTextSection"
                                 type="ru.javaops.webapp.model.ListTextSection"/>
                    <tr>
                        <td valign="top"><h4>${SectionType.valueOf(sectionName).getTitle()}</h4></td>
                        <td>
                            <ul>
                                <c:forEach var="textElement" items="${listTextSection.listContent}">
                                    <li>${textElement}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>

                <%--Experience or Education section--%>
                <c:when test="${sectionName=='EXPERIENCE' || sectionName=='EDUCATION'}">
                    <tr>
                        <td valign="top"><h4>${SectionType.valueOf(sectionName).getTitle()}</h4></td>
                        <td>
                            <ul style="list-style-type:none">
                                    <c:set var="listEstablishment" value="${sectionEntry.value}"/>
                                    <jsp:useBean id="listEstablishment"
                                                 type="ru.javaops.webapp.model.ListEstablishmentSection"/>
                                    <c:forEach var="establishment" items="${listEstablishment.establishmentContent}">
                                        <c:set var="estURL" value="${establishment.establishment.url}"/>
                                        <c:set var="estName" value="${establishment.establishment.name}"/>
                                        <c:set var="positionList" value="${establishment.positions}"/>
                                         <li>
                                            <c:choose>
                                                    <c:when test="${estURL==null || estURL.length()==0 }">
                                                        <p>${estName}</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p><a href="${estURL}">${estName}</a></p>
                                                    </c:otherwise>
                                            </c:choose>

                                            <table>
                                                <c:forEach var="position" items="${positionList}">
                                                    <tr>
                                                        <td valign="top">${position.startDate}
                                                            - ${position.endDate=='3000-01-01' ? 'настоящее время' : position.endDate}</td>
                                                        <td>
                                                            <div >
                                                                <p>${position.title}<p/>
                                                                <p>${position.description}</p>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>

                                    </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>

            </c:choose>
        </c:forEach>
    </table>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
