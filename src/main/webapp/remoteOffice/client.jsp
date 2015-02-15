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
<div class="main">
    <div class="title">Пожалуйста, заполните форму для сохранения клиента</div>
    <form action="/saveClient" method="post">
        <table>
            <tr>
                <td></td>
                <td><input type="hidden" id="id" name="id" value="${(client==null)?-1:client.id}"></td>
                <td ></td>
            </tr>
            <tr>
                <td>Полное имя:</td>
                <td><input type="text" id="name" name="name" class="textField" value="${client.name}"></td>
                <td id="nameError" class="error"></td>
            </tr>
            <tr>
                <td>Город:</td>
                <td><input type="text" id="city" name="city" class="textField" value="${client.city}"></td>
                <td id="cityError" class="error"></td>
            </tr>
            <tr>
                <td>Пол:</td>
                <td>
                    <input type="radio" name="gender" value="female" ${client.gender=="FEMALE"?"checked":""} ${(client==null)?"checked":""}>Женский
                    <input type="radio" name="gender" value="male" ${client.gender=="MALE"?"checked":""}>Мужской
                </td>
                <td></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><input type="text" id="phone" name="phone" class="textField" value="${client.phone}"></td>
                <td id="phoneError" class="error"></td>
            </tr>
            <tr>
                <td>E-mail:</td>
                <td><input type="text" id="email" name="email" class="textField" value="${client.email}"></td>
                <td id="emailError" class="error"></td>
            </tr>
            <tr>
                <td>Овердрафт::</td>
                <td><input type="text" id="overdraft" name="overdraft" class="textField" value="${(client==null)?0:client.overdraft}"}></td>
                <td id="balanceError" class="error"></td>
            </tr>
            <tr>
                <td>Баланс:</td>
                <td><input type="text" id="balance" name="balance" class="textField" value="${(client==null)?0:client.balance}" ${(client==null)?"disabled":""}></td>
                <td id="overdraftError" class="error"></td>
            </tr>
        </table>
        <input type="submit" onclick="return checkClientForm();" value="Сохранить клиента" class="submit">
    </form>
</div>
</body>
</html>