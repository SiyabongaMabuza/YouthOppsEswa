<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.ControllerInfo"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestParameter"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.SessionAttribute"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestAttribute"%>

<html>
<head>
    <title>Opportunities Display Form</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <h1>Welcome to Eswatini Youth Resources</h1>
    <p>Explore opportunities to grow, learn, and succeed!</p>

    <div class="category">
        <h2><a href="<%=request.getContextPath()%>/scholarships">Scholarships</a></h2>
        <p>${requestScope[RequestAttribute.SCHOLARSHIPS_COUNT.getKey()]} available</p>
    </div>

    <div class="category">
        <h2><a href="<%=request.getContextPath()%>/grants">Grants</a></h2>
        <p>${requestScope[RequestAttribute.GRANTS_COUNT.getKey()]} available</p>
    </div>

    <div class="category">
        <h2><a href="<%=request.getContextPath()%>/jobs">Jobs & Internships</a></h2>
        <p>${requestScope[RequestAttribute.JOBS_COUNT.getKey()]} available</p>
    </div>

    <hr />

    <% 
        String username = (String) session.getAttribute(SessionAttribute.USER.getKey());
        String role = (String) session.getAttribute(SessionAttribute.ROLE.getKey());

        if (username == null) {
    %>
        <p>Please <a href="<%= request.getContextPath() %>/login">login</a> to continue.</p>
    <% } else { %>
        <% if ("admin".equals(role)) { %>
            <h2>Add Resource</h2>
            <form method="GET" action="<%=request.getContextPath()%>/addresource">
                <input type="submit" value="Add Resource" />
            </form>
            <hr />
        <% } %>

        <h2>Session Control</h2>
        <p>Session id: <%= session.getId() %></p>
        <form method="POST" action="<%=request.getContextPath()%>/logout">
            <input type="submit" value="Logout" />
        </form>
        <p>Last updated: <%= new java.util.Date() %></p>
    <% } %>
</body>
</html>