<%-- 
    Document   : updateForm
    Created on : Jan 23, 2021, 10:24:12 PM
    Author     : mevrthisbang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Update Question Form</title>
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
                <h1>Update Question Form</h1>
            </div>
            <form action="updateQuestion" method="POST">
                <div class="row">

                    <div class="form-row">
                        <div class="form-group col-md-4">
                        </div>
                        <div class="center">
                            <div class="form-group col-md-4">
                                <label for="inputContent">Question Content</label>
                                <textarea class="form-control" id="inputContent" name="txtContent">${requestScope.Question.questionContent}</textarea>
                                <font color="red">
                                ${requestScope.INVALID.nameError}
                                </font>
                            </div>
                        </div>
                    </div>
                    <c:forEach items="${requestScope.listAnswers}" var="answer" varStatus="counter">
                        <c:if test="${answer.isCorrectAnswer}" var="testCorrect">
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                </div>
                                <div class="center">
                                    <div class="form-group col-md-4">
                                        <label for="inputCorrectAnswer">Correct Answer</label>
                                        <textarea class="form-control" id="inputCorrectAnswer" name="txtCorrectAnswer">${answer.answerContent}</textarea>
                                        <font color="red">
                                        ${requestScope.INVALID.nameError}
                                        </font>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${!testCorrect}">
                            <div class="form-row">
                            <div class="form-group col-md-4">
                            </div>
                            <div class="center">
                                <div class="form-group col-md-4">
                                    <label for="inputAnswer${counter.count}">Answer ${counter.count}</label>
                                    <textarea class="form-control" id="inputAnswer${counter.count}" name="txtAnswer${counter.count}">${answer.answerContent}</textarea>
                                    <font color="red">
                                    ${requestScope.INVALID.nameError}
                                    </font>
                                </div>
                            </div>
                        </div>
                        </c:if>
                    </c:forEach>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                        </div>
                        <div class="center">
                            <div class="form-group col-md-4">
                                <label for="inputStatus">Status</label><br>
                                <select name="cboStatus" id="inputStatus" class="custom-select">
                                    <option <c:if test="${requestScope.Question.status eq 'Active'}">selected="true"</c:if>>Active</option>
                                    <option <c:if test="${requestScope.Question.status eq 'Deactive'}">selected="true"</c:if>>Deactive</option>
                                </select>
                                <font color="red">
                                </font>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                        </div>
                        <div class="center">
                            <div class="form-group col-md-4">
                                <label for="inputSubject">Subject</label><br>
                                <select name="cboSubject" id="inputSubject" class="custom-select">
                                    <c:if test="${applicationScope.listSubjects!=null}">
                                        <c:forEach items="${applicationScope.listSubjects}" var="subject">
                                            <option value="${subject.id}" <c:if test="${requestScope.Question.id eq subject.id}">selected="true"</c:if>>${subject.id} - ${subject.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                                <font color="red">
                                </font>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col text-center">
                            <input type="submit" class="btn btn-large btn-primary" value="Update" style="margin-top: 30px;"/>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
