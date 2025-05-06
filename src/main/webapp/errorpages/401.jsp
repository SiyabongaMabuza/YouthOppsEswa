<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>401 Unauthorized</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <h1>401 Unauthorized</h1>
    <p class="error">You are not authorized to access this page.</p>
    <p><a href="<%=request.getContextPath()%>/login">Return to Login</a></p>
</body>
</html>