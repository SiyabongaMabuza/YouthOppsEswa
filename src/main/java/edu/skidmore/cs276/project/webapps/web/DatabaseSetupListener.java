package edu.skidmore.cs276.project.webapps.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import edu.skidmore.cs276.project.rdb.RdbSingletonFacade;

public class DatabaseSetupListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(DatabaseSetupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Initializing database connection for application");
        ServletContext context = sce.getServletContext();
        RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
        try {
            rdb.setRdbDriverClassName(context.getInitParameter("RdbDriverClass"));
            rdb.setRdbUrl(context.getInitParameter("RdbUrl"));
            rdb.setUserId(context.getInitParameter("RdbUserId"));
            rdb.setPassword(context.getInitParameter("RdbPassword"));
            LOGGER.info("Database connection setup complete");
        } catch (Throwable throwable) {
            LOGGER.fatal("Unable to setup the database", throwable);
            throw new IllegalStateException("Unable to setup the database connection", throwable);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Application shutting down; no database cleanup needed");
    }
}
