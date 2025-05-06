package edu.skidmore.cs276.project.webapps.web;

public enum JspInfo {
	/**
	 * JSP for the login page
	 */
	REGISTER("/WEB-INF/hiddenjsp/register.jsp"),

    /**
	 * JSP for the login page
	 */
	LOGIN("/WEB-INF/hiddenjsp/login.jsp"),

	/**
	 * JSP for the tasklist (and task add form) page
	 */
	OPPORTUNITIES_VIEW("/WEB-INF/hiddenjsp/opportunities.jsp"),

	
	/**
	 * JSP for adding a resource
	 */
	ADD_RESOURCE("/WEB-INF/hiddenjsp/addresource.jsp"),

    /**
	 * JSP for adding a resource
	 */
	GRANTS("/WEB-INF/hiddenjsp/grants.jsp"),

    /**
	 * JSP for adding a resource
	 */
	SCHOLARSHIPS("/WEB-INF/hiddenjsp/scholarships.jsp"),

    /**
	 * JSP for adding a resource
	 */
	JOBS("/WEB-INF/hiddenjsp/jobs.jsp");
	
	/**
	 * The WAR file's path to the JSP file
	 */
	private String jspPath;

	/**
	 * Define a JSP with its path
	 * 
	 * @param jspPath The path to the JSP in the WAR file
	 */
	private JspInfo(String jspPath) {
		this.jspPath = jspPath;
	}

	/**
	 * Get the path within the WAR file to the JSP
	 * 
	 * @return The path to the JSP file
	 */
	public String getJspPath() {
		return jspPath;
	}
	
	/**
	 * Returns the path to the JSP within the WAR file
	 */
	public String toString() {
		return getJspPath();
	}
}
