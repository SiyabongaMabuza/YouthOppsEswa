package edu.skidmore.cs276.project.webapps.web;

import java.io.IOException;
import java.util.List;

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
public class GrantsServlet extends HttpServlet {
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
	private static Logger LOGGER = Logger.getLogger(ViewOpportunitiesServlet.class);	

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
	public GrantsServlet() {
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
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		controller(req, resp);
	}


	/**
	 * The controlling method for the servlet. Manages the processing of the
	 * request.
	 * 
	 * @param req  The request
	 * @param resp The response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void controller(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String forwardingPath;
		LOGGER.info("Request method: " + req.getMethod());

		// Generate or reuse CSRF token
        String csrfToken = (String) req.getSession().getAttribute("CSRF");
        if (csrfToken == null) {
            csrfToken = SecurityUtilities.randomString(30);
            req.getSession().setAttribute("CSRF", csrfToken);
        }
        req.setAttribute("csrfToken", csrfToken);
        LOGGER.info("Set CSRF token: " + csrfToken);

		
		String userId = (String) req.getSession().getAttribute(SessionAttribute.USER.getKey());
		List<String[]> grants = OpportunitiesPersistence.getInstance()
			.loadPriorOpportunities(userId, "grants");
		LOGGER.info("Fetched grants for user " + userId + ": " + grants.size() + " items");
		req.setAttribute("grants", grants); // Pass to JSP
		forwardingPath = "/WEB-INF/hiddenjsp/grants.jsp";
	
		
        req.getRequestDispatcher(forwardingPath).forward(req, resp);
		
	}
	

}
