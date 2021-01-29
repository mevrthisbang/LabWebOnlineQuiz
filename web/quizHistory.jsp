<%-- 
    Document   : quizHistory
    Created on : Jan 23, 2021, 10:34:04 PM
    Author     : mevrthisbang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz History</title>
    </head>
    <body>
        <h1>Your Quiz History</h1>
        <form action="getQuizHistory" method="POST">
            <input type="input" name="txtName" value="${param.txtName}"/>
            <select></select>
        </form>
    </body>
</html>
