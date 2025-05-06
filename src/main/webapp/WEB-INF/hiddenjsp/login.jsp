<%@ page import="edu.skidmore.cs276.project.webapps.web.ControllerInfo"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestParameter" %>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestAttribute" %>
<%@ page import="edu.skidmore.cs276.project.webapps.web.SessionAttribute" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
    <title>Login Form</title>
</head>
<body>
    <h1>Eswatini Youth Opportunities Login</h1>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <p><%= request.getAttribute("errorMessage") %></p>
    <% } %>
    <% 
        boolean isLoggedIn = session.getAttribute(SessionAttribute.USER.getKey()) != null;
        if (!isLoggedIn) { 
    %>
        <form method="POST" action="<%=request.getContextPath()%><%=ControllerInfo.LOGIN.getMappedPath()%>">
            <table>
                <tr>
                    <td>Username</td>
                    <td><input type="text"
                               name="<%=RequestParameter.USERNAME.getKey()%>"
                               value="<%=request.getAttribute(RequestParameter.USERNAME.getKey()) != null ? request.getAttribute(RequestParameter.USERNAME.getKey()) : "" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password"
                               name="<%=RequestParameter.LOGIN_PASSWORD.getKey()%>"/></td>
                </tr>
            </table>
            <td colspan="2"><input type="submit" value="Login"/></td>
        </form>
        <p>New user? <a href="<%= request.getContextPath() %>/register">Register here</a></p>
    <% } else { %>
        <p>You are already logged in. <a href="<%= request.getContextPath() %><%= ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath() %>">Go to Opportunities</a></p>
    <% } %>
</body>
</html>