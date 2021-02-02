<%-- 
    Document   : confirmQuestion
    Created on : Jan 25, 2021, 7:53:22 AM
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
            <title>Confirm Submit</title>
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
            <div class="container" style="margin-top: 50px;">
                <div class="center">
                    <h1>Confirm Submit</h1>
                </div>

                <form action="loadQuestionForQuiz" method="POST" style="margin-top: 20px;">
                    <div class="center">
                        <input class="btn btn-primary" type="submit" value="Back to Quiz?"/>
                    </div>
                </form>
                <div class="center">
                    Time Remaining: <p id="time" class="text-muted"></p>
                </div>
                <form action="submitQuiz" method="POST" style="margin-top: 50px;" id="form">
                    <div class="center">
                        <input class="btn btn-primary" type="submit" value="Submit all and finish"/>
                    </div>
                </form>

            </div>

        </body>
    </html>
    <script>
      
// Set the date we're counting down to
        var date = document.getElementById("date");
        var countDownDate = new Date("<fmt:formatDate pattern = "MM dd yyyy HH:mm:ss" value ="${sessionScope.timeEndQuiz}"/>").getTime();
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
                        document.getElementById("form").submit();
                    }
                }, 1000);
    </script>
</c:if>
<c:if test="${!testRole}">
    <c:set var="ERROR" value="You do not have permission to access this" scope="request"/>
    <%@include file="error.jsp"%>
</c:if>