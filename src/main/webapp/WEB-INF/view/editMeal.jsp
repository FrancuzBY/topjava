<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h2>Edit meal</h2>
<form:form action="saveMeal" modelAttribute="meals">

    <form:hidden path="id"/>

    DateTime <form:input path="name"/>
    <br><br>
    Description <form:input path="surname"/>
    <br><br>
    Calories <form:input path="department"/>
    <br><br>
    <input type="submit" value="Save">
    <input type="submit" value="Cancel">

</form:form>
</body>
</html>
