<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Просмотр баланса</title>
    <link rel="stylesheet" href="../stylesheet/style.css">
</head>
<body>
    <div class="title">Клиент: <%=session.getAttribute("clientName")%></div>
    <div class="title">Баланс: <%=request.getAttribute("balance")%></div>
<form action="bankomat/menu.html">
    <input type="submit" value="Продолжить" class="submit">
</form>
</body>
</html>