package edu.skidmore.cs276.project.webapps.web;

import java.util.Enumeration;
import java.util.List;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

import org.apache.log4j.Logger;

import edu.skidmore.cs276.project.fileio.OpportunitiesPersistence;
import edu.skidmore.cs276.project.rdb.RdbSingletonFacade;


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
public class ViewOpportunitiesServlet extends HttpServlet {
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
   * Path content to signal invalidating the session
   */
	private final static String PATH_TEXT_TO_INVALIDATE_SESSION = "/logout";


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
	public ViewOpportunitiesServlet() {
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
		String forwardingPath = null;
		LOGGER.info("Request method: " + req.getMethod() + "  Path Info: " + req.getPathInfo());

		if (req.getSession().getAttribute(SessionAttribute.USER.getKey()) == null) {
			LOGGER.info("Not logged in");
			forwardingPath = ControllerInfo.LOGIN.getMappedPath();
		} else {
			String csrfToken = (String) req.getSession().getAttribute("csrfToken");
			csrfToken = SecurityUtilities.randomString(30);
			req.getSession().setAttribute("CSRF", csrfToken);
			req.setAttribute("csrfToken", csrfToken);
			LOGGER.info("Generated new CSRF Token: " + csrfToken);

			OpportunitiesPersistence persistence = OpportunitiesPersistence.getInstance();
            try {
                int scholarshipsCount = persistence.countOpportunities("scholarships");
                int grantsCount = persistence.countOpportunities("grants");
                int jobsCount = persistence.countOpportunities("jobs");

                req.setAttribute(RequestAttribute.SCHOLARSHIPS_COUNT.getKey(), scholarshipsCount);
                req.setAttribute(RequestAttribute.GRANTS_COUNT.getKey(), grantsCount);
                req.setAttribute(RequestAttribute.JOBS_COUNT.getKey(), jobsCount);

                LOGGER.info("Set counts: Scholarships=" + scholarshipsCount + ", Grants=" + grantsCount + ", Jobs=" + jobsCount);
            } catch (IOException e) {
                LOGGER.error("Failed to fetch opportunity counts", e);
            }

            forwardingPath = JspInfo.OPPORTUNITIES_VIEW.getJspPath();
		}
	  
		LOGGER.info("Forward to JSP: " + forwardingPath);
		
		//user is logged in at this point, so we view opportunities now
		req.getRequestDispatcher(forwardingPath).forward(req, resp);

	}


}
