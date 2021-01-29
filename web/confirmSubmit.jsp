<%-- 
    Document   : confirmQuestion
    Created on : Jan 25, 2021, 7:53:22 AM
    Author     : mevrthisbang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Submit</title>
    </head>
    <body>
        <h1></h1>
        <form action="submitQuiz" method="POST">
            <input type="submit" value="Submit all and finish"/>
        </form>
        <form action="loadQuestionForQuiz" method="POST">
            <input type="submit" value="Back to Quiz"/>
        </form>
    </body>
</html>
