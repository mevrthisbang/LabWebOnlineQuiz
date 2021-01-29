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
        <title>List Quiz ${param.subjectID}</title>
    </head>
    <body>
        <h1>List Quiz</h1>
        <c:if test="${requestScope.listQuiz!=null}" var="testNull">
            <c:if test="${not empty requestScope.listQuiz}" var="testEmpty">
                <c:forEach items="${requestScope.listQuiz}" var="quiz">
                    ${quiz.name} - ${quiz.quizTime} minutes - ${quiz.numberOfQuestion} questions - ${quiz.description}
                    <form action="loadQuestionForQuiz" method="POST">
                        <c:if test="${sessionScope.STUDENTQUIZDETAIL==null}" var="testNull">
                            <input type="submit" value="Start Quiz"/>
                        </c:if>
                        <c:if test="${!testNull&&sessionScope.STUDENTQUIZDETAIL.quizID ne quiz.id}">
                            <p>None</p>
                        </c:if>
                        <c:if test="${sessionScope.STUDENTQUIZDETAIL!=null&&sessionScope.STUDENTQUIZDETAIL.quizID eq quiz.id}">
                            <input type="submit" value="Continue Attempt"/>
                        </c:if>
                        <input type="hidden" value="${quiz.id}" name="quizID"/>
                        <input type="hidden" value="${quiz.quizTime}" name="quizTime"/>
                        <input type="hidden" value="${quiz.numberOfQuestion}" name="numberOfQuestion"/>
                        <input type="hidden" value="${param.subjectID}" name="subjectID"/>
                    </form>
                </c:forEach>
            </c:if>
            <c:if test="${!testEmpty}">
                <h2>Not found any quiz for this subject</h2>
            </c:if>
        </c:if>

    </body>
</html>
