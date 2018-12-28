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
                        <c:set var="content" value="${String.join(', ', listTextSection.listContent)}"/>
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
                                <c:forEach var="establishment" items="${listEstablishment.establishmentContent}">
                                    <c:set var="estName" value="${establishment.establishment.name}"/>
                                    <c:set var="estLink" value="${establishment.establishment.url}"/>
                                    <c:set var="positions" value="${establishment.positions}"/>
                                    <jsp:useBean id="positions"
                                                 type="java.util.List<ru.javaops.webapp.model.Establishment.Position>"/>
                                    <table border="1" id="positions" width="100%">
                                        <tr>
                                            <td><b>Название организации:</b></td>
                                            <td><input type="text" name="estName" value="${estName}" width="100%"></td>
                                        </tr>
                                        <tr>
                                            <td>Ссылка на огранизацию:</td>
                                            <td><input type="text" name="estLink" value="${estLink}"></td>
                                        </tr>
                                        <c:forEach var="position" items="${positions}">
                                            <tr>
                                                <td>Должность</td>
                                                <td><input type="text" name="position" value="${position.title}"></td>
                                            </tr>
                                            <tr>
                                                <td>Начало работы</td>
                                                <td><input type="text" name="startDate" value="${position.startDate}"></td>
                                            </tr>
                                            <tr>
                                                <td>Конец работы</td>
                                                <td><input type="text" name="endDate" value="${position.endDate}"></td>
                                            </tr>
                                            <tr>
                                                <td>Описание</td>
                                                <td><input type="text" name="description" value="${position.description}"></td>
                                            </tr>
                                        </c:forEach>

                                    </table>

                                    <input type="button" onclick="add_position();" value="Добавить позицию">

                                    <br>

                                </c:forEach>
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


    <script>
        var p = document.getElementById("positions");

        function add_position() {
            var tr_position = document.createElement('tr');
            var td_title_position = document.createElement('td');
            var td_input_position = document.createElement('td');
            var input_position = document.createElement("input");
            td_title_position.innerText = 'Должность';
            td_input_position.appendChild(input_position);
            tr_position.appendChild(td_title_position);
            tr_position.appendChild(td_input_position);
            p.appendChild(tr_position);

            var tr_start_date = document.createElement('tr');
            var td_title_start_date = document.createElement('td');
            var td_input_start_date = document.createElement('td');
            var input_start_date = document.createElement("input");
            td_title_start_date.innerText = 'Начало работы';
            td_input_start_date.appendChild(input_start_date);
            tr_start_date.appendChild(td_title_start_date);
            tr_start_date.appendChild(td_input_start_date);
            p.appendChild(tr_start_date);

            var tr_end_date = document.createElement('tr');
            var td_title_end_date = document.createElement('td');
            var td_input_end_date = document.createElement('td');
            var input_end_date = document.createElement("input");
            td_title_end_date.innerText = 'Конец работы';
            td_input_end_date.appendChild(input_end_date);
            tr_end_date.appendChild(td_title_end_date);
            tr_end_date.appendChild(td_input_end_date);
            p.appendChild(tr_end_date);

            var tr_description = document.createElement('tr');
            var td_title_description = document.createElement('td');
            var td_input_description = document.createElement('td');
            var input_description = document.createElement("input");
            td_title_description.innerText = 'Начало работы';
            td_input_description.appendChild(input_description);
            tr_description.appendChild(td_title_description);
            tr_description.appendChild(td_input_description);
            p.appendChild(tr_description);
        }
    </script>



</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
