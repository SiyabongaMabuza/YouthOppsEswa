package edu.skidmore.cs276.project.webapps.web;
import org.apache.commons.lang3.StringEscapeUtils;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

import org.apache.log4j.Logger;

import edu.skidmore.cs276.project.fileio.OpportunitiesPersistence;

/**
 * <p>
 * Title: Controller Servlet for the tasklist
 * </p>
 *
 * <p>
 * Description: Servlet for displaying and editing the tasklist
 * </p>
 *
 * @author David Read
 * @version 01.00.00
 */
public class AddResourceServlet extends HttpServlet {
	/**
	 * The internal version id of this class
	 */
	private static final long serialVersionUID = 19620501L;

	/**
	 * Servlet version
	 */
	private static final String VERSION = "01.00.00";

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(AddResourceServlet.class);

	/**
	 * Called by container when servlet instance is created. logs the current
	 * version of the servlet.
	 *
	 * @param config The servlet configuration
	 */
	@Override

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        LOGGER.warn("Servlet init. Version: " + VERSION);

    }

	/**
	 * The constructor - no operations carried out here
	 */
	public AddResourceServlet() {
	}

	/**
	 * Uses the controller method to process the request.
	 * 
	 * @see #controller
	 *
	 * @param req  The request
	 * @param resp The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req, resp);
    }

	/**
	 * Uses the controller method to process the request.
	 *
	 * @see #controller
	 *
	 * @param req  The request
	 * @param resp The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req, resp);
    }

	private void controller(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forwardingPath;
        LOGGER.info("Request method: " + req.getMethod());
		String csrfToken = (String) req.getSession().getAttribute("csrfToken");
		csrfToken = SecurityUtilities.randomString(30);  

        
		String role = (String) req.getSession().getAttribute(SessionAttribute.ROLE.getKey());
		if (!"admin".equals(role)) {
			LOGGER.warn("Access denied: User role " + role + " not authorized to add resources");
			resp.sendRedirect(req.getContextPath() + ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath());
			return;
		} else {
			if ("GET".equalsIgnoreCase(req.getMethod())) {
				// Generate CSRF token and store it in the session
					
				req.getSession().setAttribute("CSRF", csrfToken);
				req.setAttribute("csrfToken", csrfToken);
				forwardingPath = JspInfo.ADD_RESOURCE.getJspPath();
			} else {
				// CSRF Token check
				if (!validateCsrfToken(req)) {
					LOGGER.warn("CSRF token validation failed");
					req.setAttribute(RequestAttribute.ERROR_MESSAGE.getKey(), "Invalid csrf token");
					req.getRequestDispatcher(JspInfo.ADD_RESOURCE.getJspPath()).forward(req, resp);
					return;
				}

				String errorMessage = addResource(req);
				if (errorMessage != null) {
					req.setAttribute("errorMessage", errorMessage);
					req.getRequestDispatcher(JspInfo.ADD_RESOURCE.getJspPath()).forward(req, resp);
					return;
				} else {
					resp.sendRedirect(req.getContextPath() + ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath());
					return;
				}
			}
		}
        

        req.getRequestDispatcher(forwardingPath).forward(req, resp);
    }

	private boolean validateCsrfToken(HttpServletRequest req) {
		String sessionToken = req.getSession().getAttribute("CSRF").toString();
		String requestToken = req.getParameter("csrfToken");

		LOGGER.info("Validate: Session ID: " + req.getSession().getId());
		LOGGER.info("Session token: " + sessionToken);
		LOGGER.info("Request token: " + requestToken);
		if (sessionToken == null || requestToken == null) {
			return false;
		}

		return sessionToken.equals(requestToken);
	}


	private String addResource(HttpServletRequest req) {
		String category = StringEscapeUtils.escapeHtml4(req.getParameter(RequestParameter.CATEGORY.getKey()));
        String resourceTitle = StringEscapeUtils.escapeHtml4(req.getParameter(RequestParameter.RESOURCE_TITLE.getKey()));
        String resourceURL = req.getParameter(RequestParameter.RESOURCE_URL.getKey());
        String description = StringEscapeUtils.escapeHtml4(req.getParameter(RequestParameter.RESOURCE_DESCRIPTION.getKey()));

        if (category != null && !category.isEmpty() && resourceTitle != null && !resourceTitle.isEmpty() && resourceURL != null && !resourceURL.isEmpty()) {
            // validate URL
			try {
				new java.net.URL(resourceURL).toURI();
			} catch (Exception e) {
				LOGGER.warn("Invalid URL: " + resourceURL);
				// Preserve user input
				req.setAttribute("category", category);
				req.setAttribute("resourceTitle", resourceTitle);
				req.setAttribute("resourceURL", resourceURL);
				req.setAttribute("description", description);
				return "Please enter a valid URL";
			}

			String username = (String) req.getSession().getAttribute(SessionAttribute.USER.getKey());
			String[] resource = {resourceTitle, resourceURL, description != null ? description : ""};
			List<String[]> resources = new ArrayList<>();
			resources.add(resource);

			//saving resource to database
			try {
				OpportunitiesPersistence.getInstance().saveOpportunities(username, category, resources);
				LOGGER.info("Added resource to " + category + ": " + resourceTitle);
			} catch (IOException e) {
				LOGGER.error("Failed to save " + category + " to database", e);
				return "Error saving resource to database";
			}


            LOGGER.info("Added resource to " + category + ": " + resourceTitle);
        } else {
            LOGGER.warn("Invalid resource data: category=" + category + ", title=" + resourceTitle + ", url=" + resourceURL);
        }
		return null;
	}

}
