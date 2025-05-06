package edu.skidmore.cs276.project.webapps.web;

public enum RequestAttribute {
    /**
	 * Request attribute for the user's login id
	 */
	LOGIN_ID("requestAttribLoginId"),

	

	/**
	 * Request attribute for the tasklist
	 */
	OPPS("requestAttribOpps"),

    /**
     * Request attribute for the grants list
     */
    GRANTS("requestAttribGrants"),

        /**
     * Request attribute for the scholarships list
     */
    SCHOLARSHIPS("requestAttribScholarships"),

        /**
     * Request attribute for the grants list
     */
    JOBS("requestAttribJobs"),

    /**
     * Request attribute for the grants list
     */
    GRANTS_COUNT("requestAttribGrantsCount"),

        /**
     * Request attribute for the scholarships list
     */
    SCHOLARSHIPS_COUNT("requestAttribScholarshipsCount"),

        /**
     * Request attribute for the grants list
     */
    JOBS_COUNT("requestAttribJobsCount"),

	CSRF_TOKEN("csrfToken"),

	/**
	 * Error message for display to the user
	 */
	ERROR_MESSAGE("requestAttribErrorMessage");

	/**
	 * The request attribute's key
	 */
	private String key;

	/**
	 * Set a request attribute with its key value
	 * 
	 * @param key The request attribute's key value
	 */
	private RequestAttribute(String key) {
		this.key = key;
	}

	/**
	 * Get the request attribute's key value
	 * 
	 * @return The request attributes key value
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Returns the request attribute's key value
	 */
	public String toString() {
		return getKey();
	}
}
