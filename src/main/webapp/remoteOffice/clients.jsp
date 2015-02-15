<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Поиск клиента</title>
    <script src="../js/jquery-2.1.3.js" type="text/javascript"></script>
    <script src="../js/script.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../stylesheet/style.css">
    <link rel="stylesheet" href="../stylesheet/remoteOfficeStyle.css">
</head>
<body>
    <div class="title">Найдено:</div>

<table>
    <tr>
        <th>count</th>
        <th>name</th>
        <th>city</th>
        <th>balance</th>
    </tr>
    <c:forEach var="client" items="${clients}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td><a href="/client?id=${client.id}" name="id"><c:out value="${client.name}"/></a></td>
            <td><c:out value="${client.city}"/></td>
            <td><c:out value="${client.balance}"/></td>
        </tr>
    </c:forEach>
</table>
<p>
    <a href="../bankomat/menu.html">меню</a>
</p>
</body>
</html>