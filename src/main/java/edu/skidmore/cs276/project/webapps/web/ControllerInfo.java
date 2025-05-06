package edu.skidmore.cs276.project.webapps.web;

public enum ControllerInfo {
    LOGIN("/login"), 
    OPPORTUNITIES_VIEW("/viewopportunities"), 
    ADD_RESOURCE("/addresource"), 
    GRANTS("/grants"), 
    SCHOLARSHIPS("/scholarships"),
    JOBS("/jobs");

	/**
	 * The mapped path to the servlet (e.g. mapped within the web.xml)
	 */
	private String mappedPath;

	/**
	 * Define a controller with its mapped path
	 * 
	 * @param mappedPath The mapped path for the controller
	 */
	ControllerInfo(String mappedPath) {
		this.mappedPath = mappedPath;
	}

	/**
	 * Get the mapped path for the controller
	 * 
	 * @return The mapped path for the controller
	 */
	public String getMappedPath() {
		return mappedPath;
	}

	/**
	 * Returns the mapped path to the controller
	 */
	public String toString() {
		return getMappedPath();
	}
}
