package edu.skidmore.cs276.project.webapps.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;

public class LoginFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LoginFilter.class);
    private static final String LOGIN_PATH = ControllerInfo.LOGIN.getMappedPath();
    private static final String REGISTER_PATH = "/register";
    private static final String ERROR_404_PATH = "/errorpages/404.jsp";
    private static final String PROTECTED_PATH = ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("LoginFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        boolean isLoggedIn = session != null && session.getAttribute(SessionAttribute.USER.getKey()) != null;
        String userId = isLoggedIn ? String.valueOf(session.getAttribute(SessionAttribute.USER.getKey())) : "none";

        String loginPath = req.getContextPath() + LOGIN_PATH;
        String registerPath = req.getContextPath() + REGISTER_PATH;
        String requestURI = req.getRequestURI();
        boolean isLoginPage = requestURI.equals(loginPath);
        boolean isRegisterPage = requestURI.equals(registerPath);
        boolean isErrorPage = requestURI.endsWith(ERROR_404_PATH);

        LOGGER.info("Request URI: " + requestURI + ", isLoggedIn: " + isLoggedIn + ", userId: " + userId);

        if (isLoggedIn && (isLoginPage || isRegisterPage)) {
            LOGGER.info("Logged-in user (" + userId + ") attempted to access " + requestURI + ", redirecting to protected page");
            res.sendRedirect(req.getContextPath() + PROTECTED_PATH);
        } else if (isLoginPage || isRegisterPage || isErrorPage) {
            LOGGER.info("Allowing access to public page: " + requestURI);
            chain.doFilter(request, response);
        } else {
            boolean isValidResource = isValidResource(req);

            if (isLoggedIn) {
                if (isValidResource) {
                    LOGGER.info("Allowing access to protected page for user: " + userId);
                    chain.doFilter(request, response);
                } else {
                    LOGGER.info("Invalid URL for logged-in user: " + requestURI + ", letting Tomcat trigger 404");
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND); // optional but good
                    RequestDispatcher dispatcher = req.getRequestDispatcher(ERROR_404_PATH);
                    dispatcher.forward(request, response);
                }
            } else {
                if (isValidResource) {
                    LOGGER.info("Not logged in, resource exists. Redirecting to login page.");
                    res.sendRedirect(loginPath);
                } else {
                    LOGGER.info("Invalid URL for unauthenticated user: " + requestURI + ", letting Tomcat trigger 404");
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND); // optional but good
                    RequestDispatcher dispatcher = req.getRequestDispatcher(ERROR_404_PATH);
                    dispatcher.forward(request, response);
                }
            }
        }
    }

    private boolean isValidResource(HttpServletRequest req) {
        String path = req.getServletPath();
        ServletContext context = req.getServletContext();
        try {
            return context.getResource(path) != null || path.endsWith(".jsp") || path.startsWith("/");
        } catch (MalformedURLException e) {
            LOGGER.error("Malformed URL when checking resource: " + path, e);
            return false;
        }
    }
}