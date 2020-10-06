<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %><%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 04.10.2020
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Add/Edit Meal</title>
</head>
<body>

<form method="POST" action='meals' name="frmAddEditMeal">
    <input type="hidden" readonly="readonly" name="id"
           value="<c:out value="${meal.id}" />"/> <br/>
    Date/Time : <input
        type="text" name="dateTime"
        value="${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy hh:mm:ss')}"/><br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Submit"/>
    <button type="button" class="btn btn-primary" onclick="window.history.back();">Cancel</button>
</form>
</body>
</html>
