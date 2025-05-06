package edu.skidmore.cs276.project.webapps.web;

public enum SessionAttribute {
    /**
	 * Session attribute for the opportunities
	 */
	OPPS("sessionAttribOpps"),

	/**
	 * Session attribute for the user's internal id
	 */
	USER("sessionAttribUser"),

	ROLE("sessionAttribRole"),
	
	JOBS("sessionAttribJobs"),
	
	SCHOLARSHIPS("sessionAttribScholarships"),

	GRANTS("sessionAttribGrants"),

	CSRF("csrfToken");

	/**
	 * The session attribute's key value
	 */
	private String key;

	/**
	 * Setup a session attribute with its key value
	 * 
	 * @param key The session attribute's key value
	 */
	private SessionAttribute(String key) {
		this.key = key;
	}

	/**
	 * Get the session attribute's key value
	 * 
	 * @return The session attribute's key value
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Returns the session attribute's key value
	 */
	public String toString() {
		return getKey();
	}
}
