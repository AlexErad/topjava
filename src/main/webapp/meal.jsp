<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 04.10.2020
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add/Edit Meal</title>
</head>
<body>
<script>
    $(function () {
        $('input[name=dob]').datepicker();
    });
</script>
<form method="POST" action='meals.jsp' name="frmAddEditMeal">
    Date/Time : <input
        type="text" name="dob"
        value="${f:formatLocalDateTime(meal.getDateTime(), 'dd.MM.yy hh:mm:ss')}"/>"/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.getDescription()}" />"/> <br/>
    Date Time : <input
        type="text" name="dateTime"
        value="<c:out value="${meal.getDateTime()}" />"/> <br/>
</form>
</body>
</html>
