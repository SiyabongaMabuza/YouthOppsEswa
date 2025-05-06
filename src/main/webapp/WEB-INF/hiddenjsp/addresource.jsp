<!-- Add resources -->

<%@ page import="java.util.List"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.ControllerInfo"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.AddResourceServlet"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestParameter"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestAttribute"%>

<%@ page import="edu.skidmore.cs276.project.beans.tasklist.Task"%>

<html>

<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
    <title>Add Resource</title>
</head>

<body bgcolor="#a0f0f0">

    <h3>Share a Resource</h3>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <p><%= request.getAttribute("errorMessage") %></p>
    <% } %>
    <form method="POST" action="<%=request.getContextPath()%>/addresource">
        <input type="hidden" name="csrfToken" value="<%=request.getAttribute(RequestAttribute.CSRF_TOKEN.getKey())%>"/>
        <select name="<%=RequestParameter.CATEGORY%>" required>
            <option value="">Select Category</option>
            <option value="scholarships">Scholarships</option>
            <option value="grants">Grants</option>
            <option value="jobs">Jobs & Internships</option>
        </select>

        <input type="text" name="<%=RequestParameter.RESOURCE_TITLE%>" placeholder="Resource Title" required
       value="<%= request.getAttribute("resourceTitle") != null ? request.getAttribute("resourceTitle") : "" %>">

        <input type="text" name="<%=RequestParameter.RESOURCE_URL%>" placeholder="Resource URL" required>

        <textarea name="<%=RequestParameter.RESOURCE_DESCRIPTION%>" placeholder="Description" rows="4" cols="50"><%= request.getAttribute("description") != null ? request.getAttribute("description") : "" %></textarea>

        <button type="submit">Add Resource</button>

    </form>

    <a href="<%=request.getContextPath()%>/viewopportunities">Back to Home</a>
    
    <hr />

	<h2>Session Control</h2>
	<p>
		Session id:
		<%=session.getId()%></p>
	<form method="POST" action="<%=request.getContextPath()%>/logout">
		 <input type="submit"
			value="Logout" />

    <p>Last updated: <%= new java.util.Date() %></p>

</body>

</html>