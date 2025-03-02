<%-- 
    Document   : quizList
    Created on : Jan 24, 2021, 9:18:51 AM
    Author     : mevrthisbang
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${sessionScope.USER!=null&& sessionScope.USER.role eq 'student'}" var="testRole">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${requestScope.quizDetail.name} - ${requestScope.quizDetail.subjectID}</title>
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
        <c:if test="${requestScope.quizDetail!=null}">
            <div class="container" style="padding-top: 50px;">
                <div class="center">
                    <h1>${requestScope.quizDetail.name}</h1>
                </div>
                <div class="center"  style="padding-top: 20px;">
                    Time limit: ${requestScope.quizTime} minutes
                </div>
                <div class="center">
                    Number of Question: ${requestScope.numberOfQuestion} questions
                </div>
                <div class="center">
                    Only 1 attempt
                </div>
                <div class="center">

                </div>
                <c:if test="${requestScope.StudentQuizDetail==null}" var="testNull">
                    <form action="loadQuestionForQuiz" method="POST" style="padding-top: 20px;">
                        <div class="center">
                            <input type="submit" value="Start Quiz" class="btn btn-large"/>
                        </div>
                        <input type="hidden" value="${param.subjectID}" name="subjectID"/>
                        <input type="hidden" value="${param.quizID}" name="quizID"/>

                        <input type="hidden" value="${requestScope.quizTime}" name="quizTime"/>
                        <input type="hidden" value="${requestScope.numberOfQuestion}" name="numberOfQuestion"/>
                    </form>
                </c:if>
                <c:if test="${!testNull&&requestScope.StudentQuizDetail.status eq 'Completed'}">
                    <div class="center">
                        <a href="loadSubject" class="btn btn-primary">Back to Homepage</a>
                    </div>
                    <table border="1" class="table table-bordered" style="background-color: #707070; color: #fff; margin-top: 50px; text-align: center;">
                        <thead>
                            <tr>
                                <th>Number Of Correct</th>
                                <th>Score</th>
                                <th>Take Quiz Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${requestScope.StudentQuizDetail.numberOfCorrect}/${requestScope.numberOfQuestion}</td>
                                <td>${requestScope.StudentQuizDetail.score}</td>
                                <td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${requestScope.StudentQuizDetail.startedAt}"/></td>
                            </tr>
                        </tbody>
                    </table>

                </c:if>
                <c:if test="${!testNull&&requestScope.StudentQuizDetail.status eq 'In Progress'}">
                    <form action="loadQuestionForQuiz" method="POST" style="padding-top: 20px;">
                        <div class="center">
                            <input type="submit" value="Continue Attemp" class="btn btn-large"/>
                        </div>
                        <input type="hidden" value="${param.subjectID}" name="subjectID"/>
                        <input type="hidden" value="${param.quizID}" name="quizID"/>

                        <input type="hidden" value="${requestScope.quizTime}" name="quizTime"/>
                        <input type="hidden" value="${requestScope.numberOfQuestion}" name="numberOfQuestion"/>
                    </form>
                </c:if>

            </div>
            <c:if test="${requestScope.StudentQuizDetail.status!=null&&requestScope.StudentQuizDetail.status eq 'Completed'}">

            </c:if>
        </c:if>

    </body>
</html>
</c:if>
<c:if test="${!testRole}">
    <c:set var="ERROR" value="You do not have permission to access this" scope="request"/>
    <%@include file="error.jsp"%>
</c:if>