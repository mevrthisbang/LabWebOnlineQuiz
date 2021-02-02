<%-- 
    Document   : studentQuiz
    Created on : Jan 23, 2021, 10:23:38 PM
    Author     : mevrthisbang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${sessionScope.USER!=null&& sessionScope.USER.role eq 'student'}" var="testRole">

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Take Quiz Page</title>
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
            <form action="dispatchQuestion" method="POST" id="form">
                <div class="float-sm-left" style="margin: 20px; max-width: 350px; padding-bottom: 10px; margin-left: 200px;">
                    <c:forEach begin="1" end="${sessionScope.listQuestionQuiz.size()}" var="i">
                        <input type="submit" class="btn btn-primary" style="margin-top: 10px; margin-right: 10px; max-width: 40px; max-height: 40px;<c:if test="${requestScope.currentQuestion eq i}">background-color: #707070;</c:if>" value="${i}" name="dispatchRandom"/>
                    </c:forEach>
                    <div class="center">
                        <input type="submit" value="Finish Attempt" name="action" style="width: 150px; margin-top: 10px;"/>
                    </div>
                </div><br>
                <div class="wrapper bg-white rounded">
                    <p id="time" class="text-muted"></p>
                    <div class="content">
                        <p class="text-justify h5 pb-2 font-weight-bold">${requestScope.Question.questionContent}</p>
                        <input type="hidden" value="${requestScope.Question.id}" name="questionID"/>
                        <div class="options py-3"> 
                            <c:set var="keyString">${requestScope.Question.id}</c:set>
                            <c:forEach items="${requestScope.listAnswer}" var="answer">
                                <label class="rounded p-2 option"> ${answer.answerContent} <input type="radio" name="answerChoice" value="${answer.id}" <c:if test="${sessionScope.STUDENTANSWER.studentAnswer[keyString].id eq answer.id}">checked="true"</c:if>><span class="crossmark"></span></label>
                                </c:forEach>
                        </div>
                        <input type="hidden" name="currentQuestion" value="${requestScope.currentQuestion}"/>
                        <c:if test="${requestScope.currentQuestion>1}">
                            <input type="submit" value="Previous" name="action" id="previous"/>
                        </c:if>
                        <c:if test="${requestScope.currentQuestion<requestScope.numberOfQuestion}">
                            <input type="submit" value="Next" name="action" id="next"/>
                        </c:if>
                        <c:if test="${requestScope.currentQuestion==requestScope.numberOfQuestion}">
                            <input type="submit" value="Finish Attempt" name="action" id="next"/>
                        </c:if>
                    </div> 
                </div>
            </form>

        </body>
    </html>


    <script>
    // Set the date we're counting down to
        var date = document.getElementById("date");
        var countDownDate = new Date("<fmt:formatDate pattern = "MM dd yyyy HH:mm:ss" value = "${sessionScope.timeEndQuiz}" />").getTime();

    // Update the count down every 1 second
        var x = setInterval(function () {

            // Get today's date and time
            var now = new Date().getTime();

            // Find the distance between now and the count down date
            var distance = countDownDate - now;

            // Time calculations for days, hours, minutes and seconds
            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            // Display the result in the element with id="demo"
            document.getElementById("time").innerHTML = hours + ":"
                    + minutes + ":" + seconds + "";

            // If the count down is finished, write some text
            if (distance < 0) {
                clearInterval(x);
                const hiddenField = document.createElement('input');
                hiddenField.type = 'hidden';
                hiddenField.name = 'action';
                hiddenField.value = 'submit';
                document.getElementById("form").appendChild(hiddenField);
                document.getElementById("form").submit();
            }
        }, 1000);
    </script>
    <script type="text/javascript">
        window.addEventListener('beforeunload', function (e) {
            e.preventDefault();
        });
    </script> 
</c:if>
<c:if test="${!testRole}">
    <c:set var="ERROR" value="You do not have permission to access this" scope="request"/>
    <%@include file="error.jsp"%>
</c:if>