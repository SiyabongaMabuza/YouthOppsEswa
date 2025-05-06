package edu.skidmore.cs276.project.webapps.web;

public enum RequestParameter {
    /**
	 * Request parameter for the LoginId
	 */
	LOGIN_ID("loginId"),

	 /**
	 * Request parameter for the LoginId
	 */
	USERNAME("username"),

	/**
	 * Request parameter for the password
	 */
	LOGIN_PASSWORD("loginPassword"),

	/**
	 * Request parameter for the resource category
	 */
	CATEGORY("category"),

	/**
	 * Request parameter for the resource title
	 */
	RESOURCE_TITLE("resourceTitle"),

	/**
	 * Request parameter for the resource URL
	 */
	RESOURCE_URL("resourceURL"),

	/**
	 * Request parameter for the resource description
	 */
	RESOURCE_DESCRIPTION("resourceDecription"),

	CSRF_TOKEN("csrfToken"),

	/**
	 * User entered CAPTCHA value
	 */
	CAPTCHA_VALUE("captchaValue");

	/**
	 * The key for the parameter
	 */
	private String key;

	/**
	 * Create the request parameter with its key value
	 * 
	 * @param key THe request parameter key value
	 */
	private RequestParameter(String key) {
		this.key = key;
	}

	/**
	 * Get the key value for this request parameter
	 * 
	 * @return The key value
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Returns the request parameter's key value
	 */
	public String toString() {
		return getKey();
	}
}
