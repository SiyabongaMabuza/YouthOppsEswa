package edu.skidmore.cs276.project.webapps.web;

import java.io.IOException;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

import edu.skidmore.cs276.project.fileio.OpportunitiesPersistence;
import java.util.List;

/**
 * Manages the persistence of opportunities when the session expires
 * 
 */
public class OpportunitiesSessionListener implements HttpSessionListener {
    private static Logger LOGGER = Logger.getLogger(OpportunitiesSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOGGER.info("Session created: " + se.getSession().getId());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String userId = (String) se.getSession().getAttribute(SessionAttribute.USER.getKey());
        if (userId == null) {
            LOGGER.warn("Session destroyed but no user ID found");
            return;
        }

        try {
            OpportunitiesPersistence persistence = OpportunitiesPersistence.getInstance();
            // Save opportunities from ServletContext
            saveCategory(persistence, userId, "scholarships", 
                (List<String[]>) se.getSession().getServletContext().getAttribute(RequestAttribute.SCHOLARSHIPS.getKey()));
            saveCategory(persistence, userId, "grants", 
                (List<String[]>) se.getSession().getServletContext().getAttribute(RequestAttribute.GRANTS.getKey()));
            saveCategory(persistence, userId, "jobs", 
                (List<String[]>) se.getSession().getServletContext().getAttribute(RequestAttribute.JOBS.getKey()));
            
            LOGGER.info("Session destroyed: saved opportunities for user " + userId);
        } catch (Throwable throwable) {
            LOGGER.error("Unable to save opportunities for user " + userId, throwable);
        }
    }

    private void saveCategory(OpportunitiesPersistence persistence, String userId, String category, List<String[]> opportunities) throws IOException {
        if (opportunities != null && !opportunities.isEmpty()) {
            persistence.saveOpportunities(userId, category, opportunities);
            LOGGER.info("Saved " + opportunities.size() + " " + category + " for user " + userId);
        }
    }
}