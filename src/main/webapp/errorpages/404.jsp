<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
    <title>404 - Page Not Found</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <h1>404 - Page Not Found</h1>
    <p>Sorry, the page you are looking for does not exist.</p>
    <p><a href="${pageContext.request.contextPath}/viewopportunities">Return to Home</a></p>
</body>
</html>
