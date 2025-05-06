package edu.skidmore.cs276.project.webapps.web;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import org.apache.log4j.Logger;
import edu.skidmore.cs276.project.fileio.OpportunitiesPersistence;

public class DeleteOpportunityServlet extends HttpServlet {
    private static final long serialVersionUID = 19620501L;
    private static final String VERSION = "01.00.00";
    private static Logger LOGGER = Logger.getLogger(DeleteOpportunityServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        LOGGER.warn("Servlet init. Version: " + VERSION);
    }

    public DeleteOpportunityServlet() {}

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req, resp);
    }

    private void controller(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Request method: " + req.getMethod());

        String role = (String) req.getSession().getAttribute(SessionAttribute.ROLE.getKey());
        if (!"admin".equals(role)) {
            LOGGER.warn("Access denied: User role " + role + " not authorized to delete resources");
            resp.sendRedirect(req.getContextPath() + ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath());
            return;
        }

        String category = req.getParameter(RequestParameter.CATEGORY.getKey());
        String url = req.getParameter(RequestParameter.RESOURCE_URL.getKey());
        String returnPath = req.getParameter("returnPath") != null
            ? req.getParameter("returnPath")
            : req.getContextPath() + ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath();

        LOGGER.info("Received parameters: category=" + category + ", url=" + url + ", returnPath=" + returnPath);

        if (category != null && url != null) {
            try {
                if (validateCsrfToken(req)) {
                    OpportunitiesPersistence.getInstance().deleteOpportunity(category, url);
                    LOGGER.info("Deleted " + category + " with URL: " + url);
                } else {
                    LOGGER.warn("CSRF validation failed on delete request");
                    req.getSession().setAttribute("errorMessage", "Opportunity not deleted. CSRF validation failed.");
                    resp.sendRedirect(returnPath);
                    return;
                }
            } catch (RuntimeException e) {
                LOGGER.error("Failed to delete " + category + " with URL: " + url, e);
                req.getSession().setAttribute("errorMessage", "Failed to delete opportunity: " + e.getMessage());
                resp.sendRedirect(returnPath);
                return;
            }
        } else {
            LOGGER.warn("Missing category or URL for deletion: category=" + category + ", url=" + url);
            req.getSession().setAttribute("errorMessage", "Invalid deletion request: missing category or URL");
        }

        resp.sendRedirect(returnPath);
    }

    private boolean validateCsrfToken(HttpServletRequest req) {
        String sessionToken = (String) req.getSession().getAttribute("CSRF");
        String requestToken = req.getParameter(RequestParameter.CSRF_TOKEN.getKey());

        LOGGER.info("Validate: Session ID: " + req.getSession().getId());
        LOGGER.info("Session token: " + sessionToken);
        LOGGER.info("Request token: " + requestToken);

        return sessionToken != null && requestToken != null && sessionToken.equals(requestToken);
    }
}