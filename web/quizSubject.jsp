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
    </head>
    <body>
        <h1>List Quiz</h1>
        <c:if test="${requestScope.quizSubject!=null}">
            ${requestScope.quizSubject.name} - ${requestScope.quizSubject.quizTime} minutes - ${requestScope.quizSubject.numberOfQuestion} questions - ${requestScope.quizSubject.quizDescription}
            <form action="loadQuestionForQuiz" method="POST">
                <c:if test="${sessionScope.STUDENTQUIZDETAIL==null}" var="testNull">
                    <input type="submit" value="Start Quiz"/>
                </c:if>
                <c:if test="${!testNull&&sessionScope.STUDENTQUIZDETAIL.subjectID ne subject.id}">
                    <p>Done the quiz you selected first</p>
                </c:if>
                <c:if test="${sessionScope.STUDENTQUIZDETAIL!=null&&sessionScope.STUDENTQUIZDETAIL.quizID eq quiz.id}">
                    <input type="submit" value="Continue Attempt"/>
                </c:if>
                <input type="hidden" value="${quiz.id}" name="subjectID"/>
                <input type="hidden" value="${quiz.quizTime}" name="quizTime"/>
                <input type="hidden" value="${quiz.numberOfQuestion}" name="numberOfQuestion"/>
            </form>
        </c:if>

    </body>
</html>
