<%-- 
    Document   : listQuiz
    Created on : Feb 1, 2021, 7:23:34 AM
    Author     : mevrthisbang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${param.subjectID}</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
        <script src= "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="css/style_1.css" rel="stylesheet"/>
        <link href="css/style_2.css" rel="stylesheet"/>
    </head>
    <body>
        <div class="header_bottom" style="background-color:  #d1d1e0;">
            <div class="container">
                <div class="row" >
                    <div class="col-lg-3 col-md-12 col-sm-12 col-xs-12">
                        <!-- logo start -->
                        <div class="logo"> <a href="loadSubject"><img src="img/logo.png" alt="logo" /></a> </div>
                        <!-- logo end -->
                    </div>
                    <div class="col-lg-9 col-md-12 col-sm-12 col-xs-12">
                        <!-- menu start -->
                        <div class="menu_side">
                            <div id="navbar_menu">
                                <ul class="first-ul">
                                    <li> <a class="active" href="loadSubject">Home</a>
                                    </li>
                                    <li><a href="getQuizHistory">Quiz History</a></li>
                                    <li><a href="logout">Logout</a></li>
                            </div>
                            <!-- menu end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="center"  style="padding-bottom: 50px; padding-top: 50px;">
                <h1>List Quiz</h1>
            </div>

            <c:forEach items="${requestScope.listQuiz}" var="quiz">
                <c:url var="getQuizLink" value="loadQuiz">
                    <c:param name="subjectID" value="${quiz.subjectID}"/>
                    <c:param name="quizID" value="${quiz.id}"/>
                </c:url>
                <a href="${getQuizLink}" class="center">
                    <div class="card text-center" style="margin-top: 20px; width: 50%;">

                        <div class="card-body">
                            ${quiz.name}
                        </div>

                    </div></a>
                </c:forEach>
        </div>



    </body>
</html>
