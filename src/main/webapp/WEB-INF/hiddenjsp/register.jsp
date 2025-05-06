<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestParameter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <h2>Create an Account</h2>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <p><%= request.getAttribute("errorMessage") %></p>
    <% } %>
    <form method="post" action="<%= request.getContextPath() %>/register">
        <input type="hidden" name="<%= RequestParameter.CSRF_TOKEN.getKey() %>" value="<%= request.getAttribute("csrfToken") != null ? request.getAttribute("csrfToken") : "" %>"/>
        <label for="<%= RequestParameter.USERNAME.getKey() %>">Username:</label><br>
        <input type="text" id="<%= RequestParameter.USERNAME.getKey() %>" name="<%= RequestParameter.USERNAME.getKey() %>" required><br><br>

        <label for="<%= RequestParameter.LOGIN_PASSWORD.getKey() %>">Password:</label><br>
        <input type="password" id="<%= RequestParameter.LOGIN_PASSWORD.getKey() %>" name="<%= RequestParameter.LOGIN_PASSWORD.getKey() %>" required><br><br>

        <label for="role">Role:</label><br>
        <select id="role" name="role" required>
            <option value="user">User (View Only)</option>
            <option value="admin">Admin (Add/Delete)</option>
        </select><br><br>

        <input type="submit" value="Register">
    </form>
    <p>Already have an account? <a href="<%= request.getContextPath() %>/login">Login here</a></p>
</body>
</html>