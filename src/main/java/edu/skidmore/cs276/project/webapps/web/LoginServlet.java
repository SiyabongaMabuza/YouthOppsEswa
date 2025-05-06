package edu.skidmore.cs276.project.webapps.web;


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
 * Title: Controller Servlet for loggin in
 * </p>
 *
 * <p>
 * Description: Servlet for logging into the oppourtunities website
 * </p>
 *
 * @author David Read
 * @version 01.00.00
 */
public class LoginServlet extends HttpServlet {
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
	private static Logger LOGGER = Logger.getLogger(LoginServlet.class);


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
	public LoginServlet() {
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

		
		if (req.getParameter(RequestParameter.LOGIN_PASSWORD.getKey()) == null) {
			forwardingPath = setupForLogin(req);
		} else {
			OpportunitiesPersistence.User authenticatedUser = authenticateUser(req);
			if (authenticatedUser != null) {
				setupSession(authenticatedUser, req);
				forwardingPath = ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath();
			} else {
				req.setAttribute("errorMessage", "Invalid username or password");
				forwardingPath = setupForLogin(req);
			}
		}
		

		req.getRequestDispatcher(forwardingPath).forward(req, resp);
	}

	/**
	 * Setup the session with the user's internal id and tasklist
	 * 
	 * @param authenticatedUser The user's internal id
	 * @param req               The request
	 */
	private void setupSession(OpportunitiesPersistence.User authenticatedUser, HttpServletRequest req) {
		String username = authenticatedUser.getId();
		req.getSession().setAttribute(SessionAttribute.USER.getKey(), username);
		req.getSession().setAttribute(SessionAttribute.ROLE.getKey(), authenticatedUser.getRole());
		OpportunitiesPersistence persistence = OpportunitiesPersistence.getInstance();
        req.getSession().setAttribute(SessionAttribute.SCHOLARSHIPS.getKey(), 
            persistence.loadPriorOpportunities(username, "scholarships"));
        req.getSession().setAttribute(SessionAttribute.GRANTS.getKey(), 
            persistence.loadPriorOpportunities(username, "grants"));
        req.getSession().setAttribute(SessionAttribute.JOBS.getKey(), 
            persistence.loadPriorOpportunities(username, "jobs"));
			LOGGER.info("Loaded opportunities for user: " + username + " with role: " + authenticatedUser.getRole());	}

	/**
	 * Check the user's credentials. If correct then return the user's internal id.
	 * 
	 * @param req The request
	 * 
	 * @return The user's internal id or null if unauthenticated
	 */
	private OpportunitiesPersistence.User authenticateUser(HttpServletRequest req) {
		String username = StringUtilities.noNull(req.getParameter(RequestParameter.USERNAME.getKey()));
		String password = StringUtilities.noNull(req.getParameter(RequestParameter.LOGIN_PASSWORD.getKey()));
		LOGGER.info("Attempting login for username: " + username + " with password: " + password);

		if (username.isEmpty() || password.isEmpty()) {
			LOGGER.warn("Login failed: Empty username or password");
            return null;
        }

        OpportunitiesPersistence persistence = OpportunitiesPersistence.getInstance();
		try {
            OpportunitiesPersistence.User user = persistence.getUser(username);
            if (user == null) {
                LOGGER.warn("Login failed: Username not found: " + username);
                return null;
            }
            LOGGER.info("User found: " + user.getUsername() + ", stored password: " + user.getPassword());
            if (password.equals(user.getPassword())) {
                LOGGER.info("Login successful for username: " + username);
                return user;
            } else {
                LOGGER.warn("Login failed: Incorrect password for username: " + username + " (entered: " + password + ", stored: " + user.getPassword() + ")");
                return null;
            }
        } catch (IOException e) {
            LOGGER.error("Error accessing user database for username: " + username, e);
            return null;
        }
	}

	/**
	 * Prepare data for the login screen
	 * 
	 * @param req The request
	 * 
	 * @return The JSP for rendering the login page
	 */
	private String setupForLogin(HttpServletRequest req) {
		String loginId = StringUtilities.noNull(req.getParameter(RequestParameter.LOGIN_ID.getKey()));
		req.setAttribute(RequestParameter.LOGIN_ID.getKey(), loginId);

		return JspInfo.LOGIN.getJspPath();
	}

	
}
