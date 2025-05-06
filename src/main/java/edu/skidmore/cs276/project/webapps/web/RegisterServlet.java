package edu.skidmore.cs276.project.webapps.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import edu.skidmore.cs276.project.fileio.OpportunitiesPersistence;

import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 19620501L;
    private static final String VERSION = "01.00.00";
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        LOGGER.warn("Servlet init. Version: " + VERSION);
    }

    public RegisterServlet() {
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller(req, resp);
    }

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

        // Prevent logged-in users from accessing registration
        if (req.getSession().getAttribute(SessionAttribute.USER.getKey()) != null) {
            LOGGER.info("Logged-in user attempted to access registration, redirecting to opportunities");
            resp.sendRedirect(req.getContextPath() + ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath());
            return;
        }

        if ("GET".equalsIgnoreCase(req.getMethod())) {
            LOGGER.info("Rendering registration page");
            forwardingPath = JspInfo.REGISTER.getJspPath();
        } else {
            // Handle POST (registration form submission)
            if (!validateCsrfToken(req)) {
                LOGGER.warn("CSRF validation failed for registration");
                req.setAttribute("errorMessage", "Invalid CSRF token");
                forwardingPath = JspInfo.REGISTER.getJspPath();
            } else {
                String username = StringUtilities.noNull(req.getParameter(RequestParameter.USERNAME.getKey()));
                String password = StringUtilities.noNull(req.getParameter(RequestParameter.LOGIN_PASSWORD.getKey()));
                String role = StringUtilities.noNull(req.getParameter("role"));

                LOGGER.info("Attempting to register user: " + username + " with role: " + role);

                if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                    LOGGER.warn("Registration failed: All fields are required");
                    req.setAttribute("errorMessage", "All fields are required");
                    forwardingPath = JspInfo.REGISTER.getJspPath();
                } else {
                    OpportunitiesPersistence persistence = OpportunitiesPersistence.getInstance();
                    try {
                        // Check if username already exists
                        OpportunitiesPersistence.User existingUser = persistence.getUser(username);
                        if (existingUser != null) {
                            LOGGER.warn("Registration failed: Username already taken: " + username);
                            req.setAttribute("errorMessage", "Username already taken");
                            forwardingPath = JspInfo.REGISTER.getJspPath();
                        } else {
                            // Save new user
                            persistence.saveUser(username, password, role);
                            LOGGER.info("Successfully registered new user: " + username + " with role: " + role);
                            req.setAttribute("successMessage", "Registration successful! Please log in.");
                            forwardingPath = JspInfo.LOGIN.getJspPath();
                        }
                    } catch (IOException e) {
                        String errorMsg = e.getMessage() != null && e.getMessage().equals("Username already taken")
                            ? "Username already taken"
                            : "Registration failed due to a server error: " + e.getMessage();
                        LOGGER.error("Error saving new user: " + username + " - " + errorMsg, e);
                        req.setAttribute("errorMessage", errorMsg);
                        forwardingPath = JspInfo.REGISTER.getJspPath();
                    }
                }
            }
        }

        req.getRequestDispatcher(forwardingPath).forward(req, resp);
    }

    private boolean validateCsrfToken(HttpServletRequest req) {
        String sessionToken = (String) req.getSession().getAttribute("CSRF");
        String requestToken = req.getParameter(RequestParameter.CSRF_TOKEN.getKey());
        LOGGER.info("CSRF validation - Session token: " + sessionToken + ", Request token: " + requestToken);
        return sessionToken != null && requestToken != null && sessionToken.equals(requestToken);
    }
}