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
    <jsp:useBean id="establishment" type="ru.javaops.webapp.model.Establishment" scope="request"/>
    <title>Резюме ${resume.fullName}. Редактирование секции "Учреждение".</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="getEstablishment" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="resumeUuid" value="${resume.uuid}">
        <input type="hidden" name="establishmentUuid" value="${establishment.uuid}">
        <input type="hidden" name="sectionType" value="${sectionType}">

        <table border="1" style="margin: auto">
            <tr>
                <td width="250px">Название организации:</td>
                <td><input type="text" size="100" name="establishmentName" value="${establishment.establishment.name}">
                </td>
            </tr>
            <tr>
                <td width="250px">Ссылка на организацию:</td>
                <td><input type="text" size="100" name="establishmentUrl" value="${establishment.establishment.url}">
                </td>
            </tr>
        </table>

        <br><br>

        <div id="positions">
            <c:forEach var="position" items="${establishment.positions}">
                <table border="1" style="margin: auto">
                    <tr>
                        <td>Должность</td>
                        <td><input type="text" name="position" value="${position.title}"></td>
                    </tr>
                    <tr>
                        <td>Начало работы</td>
                        <td><input type="date" name="startDate" value="${position.startDate}"></td>
                    </tr>
                    <tr>
                        <td>Конец работы</td>
                        <td><input type="date" name="endDate" value="${position.endDate}"></td>
                    </tr>
                    <tr>
                        <td>Описание</td>
                        <td><input type="text" name="description" value="${position.description}"></td>
                    </tr>
                </table>
                <br>
            </c:forEach>
            <br>
        </div>

        <br>
        <div style="padding: auto; width: 300px">
            <input align="center" class="button" type="button" onclick="add_position();" value="Добавить позицию">
            <button class="button" type="submit">Сохранить</button>
        </div>

    </form>

    <script>
        var p = document.getElementById("positions");

        function add_position() {

            var main_table = document.createElement('table');
            main_table.setAttribute("border", "1");
            main_table.setAttribute("style", "margin: auto");

            var tr_position = document.createElement('tr');
            var td_title_position = document.createElement('td');
            var td_input_position = document.createElement('td');
            var input_position = document.createElement("input");
            input_position.setAttribute("type", "text");
            input_position.setAttribute("name", "position");
            td_title_position.innerText = 'Должность';
            td_input_position.appendChild(input_position);
            tr_position.appendChild(td_title_position);
            tr_position.appendChild(td_input_position);
            main_table.appendChild(tr_position)
            // p.appendChild(tr_position);

            var tr_start_date = document.createElement('tr');
            var td_title_start_date = document.createElement('td');
            var td_input_start_date = document.createElement('td');
            var input_start_date = document.createElement("input");
            input_start_date.setAttribute("type", "date");
            input_start_date.setAttribute("name", "startDate");
            td_title_start_date.innerText = 'Начало работы';
            td_input_start_date.appendChild(input_start_date);
            tr_start_date.appendChild(td_title_start_date);
            tr_start_date.appendChild(td_input_start_date);
            main_table.appendChild(tr_start_date);
            // p.appendChild(tr_start_date);

            var tr_end_date = document.createElement('tr');
            var td_title_end_date = document.createElement('td');
            var td_input_end_date = document.createElement('td');
            var input_end_date = document.createElement("input");
            input_end_date.setAttribute("type", "date");
            input_end_date.setAttribute("name", "endDate");
            td_title_end_date.innerText = 'Конец работы';
            td_input_end_date.appendChild(input_end_date);
            tr_end_date.appendChild(td_title_end_date);
            tr_end_date.appendChild(td_input_end_date);
            main_table.appendChild(tr_end_date)
            // p.appendChild(tr_end_date);

            var tr_description = document.createElement('tr');
            var td_title_description = document.createElement('td');
            var td_input_description = document.createElement('td');
            var input_description = document.createElement("input");
            input_description.setAttribute("type", "text");
            input_description.setAttribute("name", "description");
            td_title_description.innerText = 'Описание';
            td_input_description.appendChild(input_description);
            tr_description.appendChild(td_title_description);
            tr_description.appendChild(td_input_description);
            main_table.appendChild(tr_description);
            // p.appendChild(tr_description);

            p.appendChild(main_table);
            p.appendChild(document.createElement('br'));
            p.appendChild(document.createElement('br'));
        }
    </script>


</section>
<br><br>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
