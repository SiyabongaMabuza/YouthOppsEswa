<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>403 Forbidden</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <h1>403 Forbidden</h1>
    <p class="error">Access is forbidden. Please check your permissions.</p>
    <p><a href="<%=request.getContextPath()%>/login">Return to Login</a></p>
</body>
</html>