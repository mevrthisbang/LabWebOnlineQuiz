<%-- 
    Document   : admin
    Created on : Jan 22, 2021, 9:35:25 PM
    Author     : mevrthisbang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${sessionScope.USER!=null&& sessionScope.USER.role eq 'admin'}" var="testRole">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Hompage</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous"/>
        <link href="css/style_1.css" rel="stylesheet"/>
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
        <div class="center"><font color="red"><font color="red">Welcome admin, ${sessionScope.USER.fullname}</font></font></div>

        <div class="container" style="padding-top: 50px;">
            <div class="center">
                <form action="loadQuestion" method="POST">
                    Search: <input name="txtContent" type="text" value="${param.txtContent}"/>
                    <select name="cboStatus">
                        <option <c:if test="${empty param.cboStatus}">selected="true"</c:if> value="">-Status-</option>
                        <option <c:if test="${param.cboStatus eq 'Active'}">selected="true"</c:if>>Active</option>
                        <option <c:if test="${param.cboStatus eq 'Inactive'}">selected="true"</c:if>>Inactive</option>
                        </select>
                        <select name="cboSubject">
                            <option value="">-Subject-</option>
                        <c:if test="${applicationScope.listSubjects!=null}">
                            <c:forEach items="${applicationScope.listSubjects}" var="subject">
                                <option value="${subject.id}" <c:if test="${param.cboSubject eq subject.id}">selected="true"</c:if>>${subject.id}-${subject.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <input type="submit" value="Search" name="action" class="btn btn-primary"/>
                </form>
            </div>
        </div>
        <div class="container" style="margin-top: 50px;">
            <c:if test="${requestScope.listQuestion!=null}">
                <c:if test="${not empty requestScope.listQuestion}" var="testEmpty">
                    <div class="center">
                        <table border="1" class="table table-bordered" style="width: 1000px; margin-right: 50px; height: 400px;">
                            <thead>
                                <tr>
                                    <th scope="col">Question Content</th>
                                    <th scope="col">Subject</th>
                                    <th scope="col">Status</th>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.listQuestion}" var="question">

                                    <tr>
                                        <td>
                                            Question: ${question.key.questionContent}<br>
                                            Answer: <br>
                                            <c:forEach items="${question.value}" var="answer">
                                                <c:if test="${answer.isCorrectAnswer}">
                                                    <font color="red">Correct Answer: </font>
                                                </c:if>
                                                ${answer.id}. ${answer.answerContent}<br>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            ${question.key.subject}
                                        </td>
                                        <td>
                                            ${question.key.status}
                                        </td>
                                        <td>
                                            <c:if test="${question.key.status eq 'Deactive'}" var="testInactive">
                                                <div class="center">
                                                    <h1><font color="red">&#10006</font></h1> </br>

                                                </div>
                                                <div class="center">
                                                    <p>Not available action</p>
                                                </div>

                                            </c:if>
                                            <form action="deleteQuestion" method="POST">
                                                <c:if test="${!testInactive}">
                                                    <div class="center">
                                                        <input type="hidden" name="id" value="${question.key.id}"/>
                                                        <input type="hidden" name="page" value="${requestScope.currentPage}"/>
                                                        <input type="hidden" name="txtContent" value="${param.txtContent}"/>
                                                        <input type="hidden" name="cboSubject" value="${param.cboSubject}"/>
                                                        <input type="hidden" name="cboStatus" value="${param.cboStatus}"/>
                                                        <input type="submit" class="btnDelete" onclick="return confirm('Are you sure to delete this question?')" value="Delete"/>
                                                    </div>  
                                                </c:if>
                                            </form>
                                        </td>
                                        <td>
                                            <div class="center">
                                                <c:url var="editLink" value="editQuestion">
                                                    <c:param name="id" value="${question.key.id}"/>
                                                    <c:param name="page" value="${requestScope.currentPage}"/>
                                                    <c:param name="txtContent" value="${param.txtContent}"/>
                                                    <c:param name="cboSubject" value="${param.cboSubject}"/>
                                                    <c:param name="cboStatus" value="${param.cboStatus}"/>
                                                </c:url>
                                                <a href="${editLink}" class="btn">Edit</a>
                                            </div> 

                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <c:if test="${requestScope.noOfPages>1}">
                        <div class="center">
                            <c:if test="${requestScope.currentPage>1}">
                                <c:url var="prevLink" value="loadQuestion">
                                    <c:param name="page" value="${requestScope.currentPage-1}"/>
                                    <c:param name="txtContent" value="${param.txtContent}"/>
                                    <c:param name="cboSubject" value="${param.cboSubject}"/>
                                    <c:param name="cboStatus" value="${param.cboStatus}"/>
                                </c:url>
                                <a href="${prevLink}" class="btn btn-primary" style="min-width: 50px">Previous</a>
                            </c:if>

                            <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                                <c:choose>
                                    <c:when test="${requestScope.currentPage eq i}">
                                        <a class="btn btn-primary" style="background-color: #000; min-width: 50px;">${i}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="pageLink" value="loadQuestion">
                                            <c:param name="page" value="${i}"/>
                                            <c:param name="txtContent" value="${param.txtContent}"/>
                                            <c:param name="cboSubject" value="${param.cboSubject}"/>
                                            <c:param name="cboStatus" value="${param.cboStatus}"/>
                                        </c:url>
                                        <a href="${pageLink}" class="btn btn-primary" style="min-width: 50px">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>


                            <c:if test="${requestScope.currentPage<requestScope.noOfPages}">
                                <c:url var="nextLink" value="loadQuestion">
                                    <c:param name="page" value="${requestScope.currentPage+1}"/>
                                    <c:param name="txtContent" value="${param.txtContent}"/>
                                    <c:param name="cboSubject" value="${param.cboSubject}"/>
                                    <c:param name="cboStatus" value="${param.cboStatus}"/>
                                </c:url>
                                <a href="${nextLink}" class="btn btn-primary" style="min-width: 50px">Next</a>
                            </c:if>
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${!testEmpty}">
                    <div class="container" style="margin-top: 50px;">
                        <div class="center">
                            <h1>Not found any match question!!!</h1>
                        </div>
                    </div>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
</c:if>
<c:if test="${!testRole}">
    <c:set var="ERROR" value="You do not have permission to access this" scope="request"/>
    <%@include file="error.jsp"%>
</c:if>