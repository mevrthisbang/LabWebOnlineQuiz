<%-- 
    Document   : quizResult
    Created on : Jan 25, 2021, 8:32:48 PM
    Author     : mevrthisbang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link class="jsbin" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous"/>
<link href="css/style_1.css" rel="stylesheet"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>
    </head>
    <body>
        <div class="header_bottom" style="background-color: #F0EEEE;">
            <div class="container">
                <div class="row" >
                    <div class="col-lg-3 col-md-12 col-sm-12 col-xs-12">
                        <!-- logo start -->
                        <div class="logo"> <a href="loadQuestion"><img src="img/logo.png" alt="logo" /></a> </div>
                        <!-- logo end -->
                    </div>
                    <div class="col-lg-9 col-md-12 col-sm-12 col-xs-12">
                        <!-- menu start -->
                        <div class="menu_side">
                            <div id="navbar_menu">
                                <ul class="first-ul">
                                    <li> <a class="active" href="loadQuestion">Home</a>
                                    </li>
                                    <li><a href="createForm.jsp">Create new Question</a></li>
                                    <li><a href="logout">Logout</a></li>
                            </div>
                            <!-- menu end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container" style="margin-top: 50px;">
            <div class="center">
                <h1>${requestScope.QUIZ.name} - ${requestScope.QUIZ.subjectID}</h1>
            </div>
            <div class="center">
                <p>${requestScope.QUIZ.description}</p>
            </div>
            <div class="center">
                <p>Number of Question: ${requestScope.QUIZ.numberOfQuestion}</p>
            </div>
            <div class="center">
                <p>Time limit: ${requestScope.QUIZ.quizTime} minutes</p>
            </div>
            <div class="center">
                <table border="1">
                    <thead>
                        <tr>
                            <th>Number of Correct Question</th>
                            <th>Score</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${requestScope.score}</td>
                            <td>${requestScope.numberOfCorrect}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="center">
                <a href="loadSubject" class="btn">Back to HomePage?</a>
            </div>
        </div>

    </body>
</html>
