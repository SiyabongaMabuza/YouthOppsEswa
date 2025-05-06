<!-- badrequest -->

<%@ page import="edu.skidmore.cs276.project.webapps.web.OpportunitiesServlet"%>

<%
response.setStatus(400);
%>

<html>

<head>
<title>Bad Request</title>
</head>

<body bgcolor="#f0a0a0">

	<h1>Unknown JSP Code Requested</h1>

	<p>
		The jsp id supplied was not legal:
		<%=request.getParameter(OpportunitiesServlet.REQUEST_PARAM_NAME_MODE_FLAG)%>
	</p>

	<h2>Method: <%=request.getMethod()%></h2>
	
	<p><a href="<%=application.getContextPath()%>">Back to application</a></p>

</body>

</html>