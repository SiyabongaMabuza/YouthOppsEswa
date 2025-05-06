package edu.skidmore.cs276.project.fileio;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.skidmore.cs276.project.rdb.RdbSingletonFacade;
import edu.skidmore.cs276.project.webapps.web.StringUtilities;

public class OpportunitiesPersistence {
    /* Single instance of the class */
    private static OpportunitiesPersistence instance;

    /* Logger instace */
    private static Logger LOGGER = Logger.getLogger(OpportunitiesPersistence.class);

    /* Singleton - private constructor */
    private OpportunitiesPersistence() {
       
    }

    /**
	 * Get the singleton instance
	 * 
	 * @return The instance
	 */
	public static synchronized OpportunitiesPersistence getInstance() {
		if (instance == null) {
			instance = new OpportunitiesPersistence();
		}

		return instance;
	}

    /**
     * Count the total number of opportunities in a given category.
     * 
     * @param category The type of opportunity (scholarships, grants, jobs)
     * @return The count of opportunities
     * @throws IOException If a database error occurs
     */
    public int countOpportunities(String category) throws IOException {
        RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
        String tableName = getTableName(category);
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(*) FROM " + tableName;
            rs = rdb.executQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                LOGGER.error("Failed to retrieve count for " + category + ": No rows returned");
                return 0;
            }
        } catch (SQLException e) {
            LOGGER.error("Database error while counting " + category, e);
            throw new IOException("Error counting opportunities", e);
        } finally {
            if (rs != null) {
                rdb.cleanUp(rs);
            }
        }
    }

    /**
     * Retrieve opportunities from the database for a given user and category
     * 
     * @param userIdentifier The user's identifier
     * @param category The type of opportunity (scholarships, grants, jobs)
     * @return The loaded list of opportunities
     */
    public List<String[]> loadPriorOpportunities(String userIdentifier, String category) {
        RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
        List<String[]> opportunities = new ArrayList<>();
        ResultSet results = null;
        String tableName = getTableName(category);

        try {
            results = rdb.executQuery(
                "SELECT title, url, description FROM " + tableName);

            while (results.next()) {
                String[] opportunity = new String[3];
                opportunity[0] = results.getString("title");
                opportunity[1] = results.getString("url");
                opportunity[2] = results.getString("description");
                opportunities.add(opportunity);
            }
        } catch (Throwable throwable) {
            LOGGER.error("Unable to load prior " + category + " from the database", throwable);
            throw new RuntimeException("Unable to load prior " + category + " from the database", throwable);
        } finally {
            rdb.cleanUp(results);
        }

        return opportunities;
    }

    /**
     * Save opportunities into the database for a given user and category
     * 
     * @param userIdentifier The user's identifier
     * @param category The type of opportunity (scholarships, grants, jobs)
     * @param opportunities The list of opportunities to save
     * @throws IOException If an error occurs during database operations
     */
    public void saveOpportunities(String userIdentifier, String category, List<String[]> opportunities) throws IOException {
        RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
        String tableName = getTableName(category);
    
        for (String[] opportunity : opportunities) {
            ResultSet rs = null;
            try {
                // Check if the resource already exists
                String checkSql = "SELECT COUNT(*) FROM " + tableName + " WHERE url = '" + StringUtilities.dbSafeString(opportunity[1]) + "'";
                rs = rdb.executQuery(checkSql);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    rdb.cleanUp(rs); // Clean up the ResultSet immediately after use
                    rs = null; // Ensure rs is null for safety in finally block
    
                    if (count == 0) { // Only insert if it doesn’t exist
                        String sql = "INSERT INTO " + tableName + " (userId, title, url, description) VALUES (";
                        sql += "'" + userIdentifier + "',";
                        sql += "'" + StringUtilities.dbSafeString(opportunity[0]) + "',"; // title
                        sql += "'" + StringUtilities.dbSafeString(opportunity[1]) + "',"; // url
                        sql += "'" + StringUtilities.dbSafeString(opportunity[2]) + "'";  // description
                        sql += ")";
                        LOGGER.info("Inserting " + category + ": " + sql);
                        rdb.execute(sql);
                    } else {
                        LOGGER.info("Skipping duplicate " + category + ": " + opportunity[1]);
                    }
                } else {
                    LOGGER.error("Failed to retrieve count for URL: " + opportunity[1]);
                    rdb.cleanUp(rs); // Clean up even if rs.next() fails
                    rs = null;
                }
            } catch (SQLException e) {
                LOGGER.error("Database error while checking/saving " + category + " for URL: " + opportunity[1], e);
                throw new IOException("Database error while saving opportunities", e);
            } finally {
                if (rs != null) { // Only clean up if rs hasn’t been cleaned already
                    rdb.cleanUp(rs);
                }
            }
        }
    }

    /**
     * Delete an opportunity from the specified category table based on its URL.
     * 
     * @param category The type of opportunity (scholarships, grants, jobs)
     * @param url The URL of the opportunity to delete
     * @throws IOException If a database error occurs
     * @throws SQLException
     */
    public void deleteOpportunity(String category, String url) {
        RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
        String tableName = getTableName(category);
        String sql = "DELETE FROM " + tableName + " WHERE url = '" + StringUtilities.dbSafeString(url) + "'";
        LOGGER.info("Deleting " + category + " with URL: " + url);
        try {
            rdb.execute(sql);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete " + category + " with URL: " + url, e);
            throw e; // Rethrow to let the servlet handle it
        }
    }

    public void saveUser(String username, String password, String role) throws IOException{
        RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
        String sql = "INSERT INTO users (username, password, role) VALUES (" +
                     "'" + StringUtilities.dbSafeString(username) + "'," +
                     "'" + StringUtilities.dbSafeString(password) + "'," +
                     "'" + StringUtilities.dbSafeString(role) + "')";
        ResultSet rs = null;
        try {
            // Check for duplicate username
            String checkSql = "SELECT COUNT(*) FROM users WHERE username = '" + StringUtilities.dbSafeString(username) + "'";
            LOGGER.info("Checking for existing user: " + checkSql);
            rs = rdb.executQuery(checkSql);
            boolean exists = rs.next() && rs.getInt(1) > 0;
            rdb.cleanUp(rs); // Clean up before INSERT
            rs = null;

            if (!exists) {
                LOGGER.info("Saving user: " + username);
                rdb.execute(sql);
                LOGGER.info("User successfully inserted: " + username);
            } else {
                LOGGER.info("Username " + username + " already exists, skipping save");
                throw new IOException("Username already taken");
            }
        } catch (SQLException e) {
            LOGGER.error("Database error while saving user: " + username, e);
            throw new IOException("Error saving user", e);
        } finally {
            if (rs != null) {
                rdb.cleanUp(rs);
            }
        }
    }

    /**
     * Retrieve a user from the database by username
     * @param username The user's login name
     * @return User object if found, null otherwise
     * @throws IOException If a database error occurs
     */
    public User getUser(String username) throws IOException {
        RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
        ResultSet rs = null;
        try {
            String sql = "SELECT userId, username, password, role FROM users WHERE username = '" + StringUtilities.dbSafeString(username) + "'";
            LOGGER.info("Retrieving user: " + sql);
            rs = rdb.executQuery(sql);
            if (rs.next()) {
                // userId is an int in the database, convert to String for consistency
                String userId = String.valueOf(rs.getInt("userId"));
                return new User(userId, rs.getString("username"), rs.getString("password"), rs.getString("role"));
            }
            return null; // User not found
        } catch (SQLException e) {
            LOGGER.error("Database error retrieving user: " + username, e);
            throw new IOException("Error retrieving user", e);
        } finally {
            if (rs != null) {
                rdb.cleanUp(rs);
            }
        }
    }

    /**
     * Helper method to map category to table name
     * 
     * @param category The opportunity category
     * @return The corresponding table name
     */
    private String getTableName(String category) {
        switch (category.toLowerCase()) {
            case "scholarships":
                return "scholarships";
            case "grants":
                return "grants";
            case "jobs":
                return "jobs";
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

    public static class User {
        private final String id;
        private final String username;
        private final String password;
        private final String role;

        public User(String id, String username, String password, String role) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getId() { return id; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getRole() { return role; }
    }

}

