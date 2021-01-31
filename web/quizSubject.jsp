<%-- 
    Document   : quizList
    Created on : Jan 24, 2021, 9:18:51 AM
    Author     : mevrthisbang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz ${requestScope.quizSubject.id} - ${requestScope.quizSubject.name}</title>
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
        <c:if test="${requestScope.quizSubject!=null}">
            <div class="container" style="padding-top: 50px;">
                <div class="center">
                    <h1>Subject Quiz</h1>
                </div>
                <div class="center" style="padding-top: 20px;">
                    Subject name: ${requestScope.quizSubject.name}
                </div>
                <div class="center">
                    Time limit: ${requestScope.quizSubject.quizTime} minutes
                </div>
                <div class="center">
                    Number of Question: ${requestScope.quizSubject.numberOfQuestion} questions
                </div>
                <div class="center">
                    ${requestScope.quizSubject.quizDescription}
                </div>
                <div class="center">

                </div>
                <form action="loadQuestionForQuiz" method="POST" style="padding-top: 20px;">
                    <c:if test="${sessionScope.STUDENTQUIZDETAIL==null}" var="testNull">
                        <div class="center">
                            <input type="submit" value="Start Quiz" class="btn btn-large"/>
                        </div>
                    </c:if>
                    <c:if test="${!testNull&&sessionScope.STUDENTQUIZDETAIL.subjectID ne requestScope.quizSubject.id}">
                        <p>Done the quiz you selected first</p>
                    </c:if>
                    <c:if test="${sessionScope.STUDENTQUIZDETAIL!=null&&sessionScope.STUDENTQUIZDETAIL.subjectID eq requestScope.quizSubject.id&&requestScope.Status==null}" var="testStatus">
                        <div class="center">
                            <input type="submit" value="Continue Attempt" class="btn btn-large"/>
                        </div>

                    </c:if>
                    <c:if test="${!testStatus}">
                        <div class="center">
                            <p>You have done this quiz already. Please check history</p>
                        </div>
                    </c:if>
                    <input type="hidden" value="${requestScope.quizSubject.id}" name="subjectID"/>
                    <input type="hidden" value="${requestScope.quizSubject.quizTime}" name="quizTime"/>
                    <input type="hidden" value="${requestScope.quizSubject.numberOfQuestion}" name="numberOfQuestion"/>
                </form>
            </div>

        </c:if>

    </body>
</html>
