<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
    <%
        Cookie[] cookies = request.getCookies();
        Boolean isRemember = false;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("rememberMe")){
                    isRemember = true;
                    break;
                }
            }
        }
        if(isRemember){
            response.sendRedirect(request.getContextPath() + "/products");
        }
    %>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <h3 class="text-center text-secondary mt-5 mb-3">User Login</h3>
            <form class="border rounded w-100 mb-5 mx-auto px-3 pt-3 bg-light" action="<%=request.getContextPath()%>/login" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input id="username" type="text" class="form-control" placeholder="Username" name="username">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" type="password" class="form-control" placeholder="Password" name="password">
                </div>
                <!-- check box remember me -->
                <div>
                    <input type="checkbox" name="rememberMe" value="remember">
                    <label for="remember">Remember me</label>
                </div>
                <div class="form-group">
                    <button class="btn btn-success px-5">Login</button>
                </div>
                <c:if test="${not empty flash_message}">
			        <div class="alert alert-danger">${flash_message}</div>
		        </c:if>
            </form>

        </div>

    </div>
</div>

</body>
</html>
