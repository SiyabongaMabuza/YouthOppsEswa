<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 Internal Server Error</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <h1>500 Internal Server Error</h1>
    <p class="error">An unexpected error occurred: <%= request.getAttribute("javax.servlet.error.message") %></p>
    <p><a href="<%=request.getContextPath()%>/login">Return to Login</a></p>
</body>
</html>
