<%-- 
    Document   : quizHistory
    Created on : Jan 23, 2021, 10:34:04 PM
    Author     : mevrthisbang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz History</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
        <script src= "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="css/style_1.css" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>

        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
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
                <h1>Your Quiz History</h1>
            </div>
        </div>
        <form action="getQuizHistory" method="POST">
            <div class="center">
                Subject Name: <input type="text" name="txtName" value="${param.txtName}"/>
                <input type="submit" value="Search" class="btn btn-primary"/>
            </div>
        </form>
        <div class="container">
            <div class="center">
                <table id="listQuizHistory" class="display" style="width:100%">
                    <thead>
                        <tr>
                            <th>Subject</th>
                            <th>Quiz Name</th>
                            <th>Number Of Correct</th>
                            <th>Score</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.listQuizHistory}" var="history">
                            <tr>
                                <td>${requestScope[history.quizID].subjectID}</td>
                                <td>${requestScope[history.quizID].name}</td>
                                <td>${history.numberOfCorrect}</td>
                                <td>${history.score}</td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
<script>
    $(document).ready(function () {
        $('#listQuizHistory').DataTable({
            "pagingType": "simple_numbers",
            "bFilter": false,
            "bInfo": false,
            "ordering": false,
            "columnDefs": [
                {"className": "dt-center", "targets": "_all"}
            ],
            "lengthMenu": [[2, 5], [2, 5]]
        });
    });
</script>
