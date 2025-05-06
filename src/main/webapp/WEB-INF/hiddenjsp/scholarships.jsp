<%@ page import="java.util.List"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.ScholarshipsServlet"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestAttribute"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.RequestParameter"%>
<%@ page import="edu.skidmore.cs276.project.webapps.web.SessionAttribute"%>

<html lang="en">
<head>
   <meta charset="UTF-8">
   <title>Scholarships - Eswatini Youth Resources</title>
   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
   <h1>Scholarship Opportunities</h1>
   <% if (request.getAttribute("errorMessage") != null) { %>
    <p><%= request.getAttribute("errorMessage") %></p>
    <% } %>
   <p>Scholarships available for undergraduate & post-graduate studies.</p>
   <ul>
    <%
    List<String[]> resources = (List<String[]>) request.getAttribute("scholarships");
    String role = (String) session.getAttribute(SessionAttribute.ROLE.getKey());
    if (resources != null && !resources.isEmpty()) {
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Title</th>");
        out.println("<th>URL</th>");
        out.println("<th>Description</th>");
        if ("admin".equals(role)) { 
            out.println("<th>Action</th>");
        }
        out.println("</tr>");

        for (String[] resource : resources) {
            out.println("<tr>");
            out.println("<td>" + resource[0] + "</td>"); // Title
            out.println("<td><a href=\"" + resource[1] + "\">" + resource[1] + "</a></td>"); // URL
            out.println("<td>" + resource[2] + "</td>"); // Description
            out.println("<td>");
            if ("admin".equals(role)) {
                out.println("<form method=\"POST\" action=\"" + request.getContextPath() + "/deleteOpportunity\" class=\"delete-form\">");
                out.println("<input type=\"hidden\" name=\"" + RequestParameter.CSRF_TOKEN.getKey() + "\" value=\"" + (request.getAttribute("csrfToken") != null ? request.getAttribute("csrfToken") : "") + "\"/>");                
                out.println("<input type=\"hidden\" name=\"" + RequestParameter.CATEGORY.getKey() + "\" value=\"scholarships\">");
                out.println("<input type=\"hidden\" name=\"" + RequestParameter.RESOURCE_URL.getKey() + "\" value=\"" + resource[1] + "\">");
                out.println("<input type=\"hidden\" name=\"returnPath\" value=\"" + request.getContextPath() + "/scholarships\">");
                out.println("<input type=\"submit\" value=\"Delete\">");
                out.println("</form>");
            }
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
    } else {
        out.println("<p>No scholarships added yet.</p>");
    }
    %>
   </ul>
   <a href="<%=request.getContextPath()%>/viewopportunities">Back to Home</a>
</body>
</html>