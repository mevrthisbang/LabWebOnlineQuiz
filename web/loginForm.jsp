<%-- 
    Document   : loginForm
    Created on : Jan 22, 2021, 9:29:22 PM
    Author     : mevrthisbang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" >
        <script
            src="https://kit.fontawesome.com/64d58efce2.js"
            crossorigin="anonymous"
        ></script>
        <title>Login</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <div class="login-box">
        <img src="img/avatar.png" class="avatar">
        <h1>LOGIN</h1>
        <div>
            <font color="red">${requestScope.ERROR}</font>
            <form action="login" method="POST">
                <p>Email</p>
                <div class="textbox">
                    <input type="text" placeholder="Enter email" name="txtEmail" value="${param.txtEmail}"/>
                    <i class="fas fa-user fa-lg fa-fw" aria-hidden="true"></i>

                </div>
                <p >Password</p>
                <div class="textbox">
                    <input type="password" placeholder="Enter Password" name="txtPassword" value=""/>
                    <i class="fas fa-lock fa-lg fa-fw" aria-hidden="true"></i>

                </div>
                <input class="btn" value="Login" name="action" type="submit" /><br>
                Don't have an account?<a href="register.jsp"> Register</a><br>
            </form>
        </div>
    </div>
</body>
</html>
