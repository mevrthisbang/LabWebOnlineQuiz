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
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>

        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
    </head>
    <body>
        <h1>Your Quiz History</h1>
        <form action="getQuizHistory" method="POST">
            <input type="input" name="txtName" value="${param.txtName}"/>
        </form>
        <table id="listQuizHistory" class="display" style="width:100%">
            <thead>
                <tr>
                    <th>SubjectID</th>
                    <th>Number Of Correct</th>
                    <th>Score</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.listQuizHistory}" var="history">
                    <tr>
                        <td>${history.subjectID}</td>
                        <td>${history.numberOfCorrect}</td>
                        <td>${history.score}</td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
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
