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
        <p>
        <dl>
            <dt>${SectionType.OBJECTIVE.title}</dt>
            <dd><input type="text" name="objective" size="150" value="${resume.getSection(SectionType.OBJECTIVE)}"></dd>
            <br>
            <dt>${SectionType.PERSONAL.title}</dt>
            <dd><input type="text" name="personal" size="150" value="${resume.getSection(SectionType.PERSONAL)}"></dd>
            <br>
        </dl>

        <h5>Достижения</h5>
        <c:set var="achievement" value="${resume.getSection(SectionType.ACHIEVEMENT)}"/>
        <jsp:useBean id="achievement"
                     type="ru.javaops.webapp.model.ListTextSection"/>
        <c:forEach var="achievementText" items="${achievement.listContent}">
            <input type="text" name="achievementSection" size="150" value="${achievementText}"><br>
        </c:forEach>
        <input type="text" name="achievementSection" size="150" placeholder="Type to add new achievement"><br>

        <h5>Квалификация</h5>
        <c:set var="qualification" value="${resume.getSection(SectionType.QUALIFICATIONS)}"/>
        <jsp:useBean id="qualification"
                     type="ru.javaops.webapp.model.ListTextSection"/>
        <c:forEach var="qualificationText" items="${qualification.listContent}">
            <input type="text" name="qualificationSection" size="150" value="${qualificationText}"><br>
        </c:forEach>
        <input type="text" name="qualificationSection" size="150" placeholder="Type to add new qualification"><br>

        </p>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
