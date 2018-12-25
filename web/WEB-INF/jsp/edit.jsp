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
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>

        <h3>Контакты:</h3>
        <p>
        <dl>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
                <br>
            </c:forEach>
        </dl>
        </p>


        <h3>Секции:</h3>
        <dl>

            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.Section>"/>
                <c:set var="sectionName" value="${sectionEntry.key.name()}"/>

                <c:choose>

                    <c:when test="${sectionName=='OBJECTIVE' || sectionName=='PERSONAL'}">
                        <dt>${SectionType.valueOf(sectionName).getTitle()}</dt>
                        <dd><input type="text" name="${sectionName}" size="80"
                                   value="${resume.getSection(SectionType.valueOf(sectionName))}"></dd>
                        <br>
                    </c:when>

                    <c:when test="${sectionName=='ACHIEVEMENT' || sectionName=='QUALIFICATIONS'}">
                        <c:set var="listTextSection" value="${resume.getSection(SectionType.valueOf(sectionName))}"/>
                        <jsp:useBean id="listTextSection"
                                     type="ru.javaops.webapp.model.ListTextSection"/>
                        <c:set var="content" value="${String.join(', ', listTextSection.listContent)}"/>
                        <dt>${SectionType.valueOf(sectionName).getTitle()}</dt>
                        <dd><textarea name="${sectionName}" cols="80" rows="5">${content}</textarea></dd>
                        <br>
                    </c:when>

                    <%--ESTABLISHMENT SECTION--%>

                </c:choose>

            </c:forEach>

        </dl>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
