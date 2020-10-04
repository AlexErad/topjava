<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 03.10.2020
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=insert">Add Meal</a>
<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <c:set var = "color" value="${meal.isExcess()?'red':'green'}"/>
        <tr>
            <td style="color:${color}">${f:formatLocalDateTime(meal.getDateTime(), 'dd.MM.yy HH:mm:ss')}</td>
            <td style="color:${color}"><c:out value="${meal.getDescription()}"/></td>
            <td style="color:${color}"><c:out value="${meal.getCalories()}"/></td>
            <td><a href="meals?action=update&id=<c:out value="${meal.getId()}"/>">Update</a> </td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.getId()}"/>">Delete</a> </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
