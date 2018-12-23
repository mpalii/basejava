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
    <h2>${resume.fullName}&nbsp;<a href="getResumes?uuid=${resume.uuid}&action=edit">Edit</a></h2>

    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>

    <p>
    <dl>
        <c:forEach var="section" items="${resume.sections}">
            <jsp:useBean id="section"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.Section>"/>
            <c:set var="currentSection" value="${section.key.name()}"/>

            <c:if test="${(SectionType.OBJECTIVE.name().equals(currentSection)) || (SectionType.PERSONAL.name().equals(currentSection))}">
                <dt><h4>${section.key.title}</h4></dt>
                <dd><%=((TextSection) section.getValue()).getContent()%>
                </dd>
            </c:if>

            <c:if test="${(SectionType.ACHIEVEMENT.name().equals(currentSection)) || (SectionType.QUALIFICATIONS.name().equals(currentSection))}">
                <dt><h4>${section.key.title}</h4></dt>
                <c:set var="listTextSection" value="${section.value}"/>
                <jsp:useBean id="listTextSection"
                             type="ru.javaops.webapp.model.ListTextSection"/>
                <dd>
                    <ul>
                        <c:forEach var="textElement" items="${listTextSection.listContent}">
                            <li>${textElement}</li>
                        </c:forEach>
                    </ul>
                </dd>
            </c:if>

            <br>
        </c:forEach>
    </dl>
    </p>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
