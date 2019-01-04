<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../../css/styles.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table id="resumes">
        <tr>
            <th>Ф.И.О.</th>
            <th>Позиция (профессия/должность)</th>
            <th colspan="2">Действие</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume"/>
            <tr>
                <td><a href="getResumes?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=resume.getSection(SectionType.OBJECTIVE)%></td>
                <td align="center" width="120px"><a class="button" href="getResumes?uuid=${resume.uuid}&action=delete">Удалить</a></td>
                <td align="center" width="120px"><a class="button" href="getResumes?uuid=${resume.uuid}&action=edit">Редактировать</a></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <p><a class="button" href="getResumes?action=add">Добавить новое резюме</a></p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
