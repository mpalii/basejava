<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Delete operation</th>
            <th>Edit operation</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume"/>
            <tr>
                <td><a href="getResumes?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%></td>
                <td><a href="getResumes?uuid=${resume.uuid}&action=delete">delete</a></td>
                <td><a href="getResumes?uuid=${resume.uuid}&action=edit">edit</a></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <a href="getResumes?action=add">Добавить новое резюме</a>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
