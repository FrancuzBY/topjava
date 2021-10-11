<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="../../index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<br>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>

    <c:forEach var="meal" items="${meals}">

        <c:url var="updateButton" value="/updateInfo">
            <c:param name="empId" value="${meal.id}"/>
        </c:url>

        <c:url var="deleteButton" value="/deleteMeal">
            <c:param name="empId" value="${meal.id}"/>
        </c:url>

        <tr>
            <td>$(meal.getDateTime())</td>
            <td>$(meal.getDescription())</td>
            <td>$(meal.getCalories())</td>
            <td><input type="button" value="Update"
                       onclick="window.location.href = '${updateButton}'"/>
                <input type="button" value="Delete"
                       onclick="window.location.href = '${deleteButton}'"/></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>