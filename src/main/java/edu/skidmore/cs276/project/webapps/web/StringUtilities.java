package edu.skidmore.cs276.project.webapps.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Static utility methods for common string manipulations.
 * 
 * @author David Read
 *
 */
public class StringUtilities {

	/**
     * Makes a string safe for inclusion in a SQL query by escaping special characters
     * 
     * @param input The input string
     * @return A sanitized string, empty if null
     */
    public static String dbSafeString(String input) {
        if (input == null) {
            return "";
        }
        // Escape single quotes (for SQL) and backslashes
        String safe = input.replace("\\", "\\\\").replace("'", "\\'");
        return safe;
    }
	/**
	 * Standard date format: MM/dd/yyyy
	 */
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM/dd/yyyy");

	/**
	 * No instances should be created, these are static utility methods
	 */
	private StringUtilities() {
	}

	/**
	 * Format the date as a MM/dd/yyyy String
	 * 
	 * @param date
	 *          The date
	 * 
	 * @return The formatted string
	 */
	public final static String formatDate(Date date) {
		String formatted = "";

		if (date != null) {
			formatted = dateFormat.format(date);
		}

		return formatted;
	}

	/**
	 * Parse a string in MM/dd/yyyy format into a Date object
	 * 
	 * @param date
	 *          The date string to parse
	 * 
	 * @return The parsed date
	 * 
	 * @throws ParseException
	 */
	public final static Date parseDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}

	/**
	 * Assure that a value is not null. If the value is null, an empty string is
	 * returned otherwise the initial value is returned intact.
	 * 
	 * @param data The value
	 * 
	 * @return The value or an empty string if the value was null
	 */
	public final static String noNull(String data) {
		return data == null ? "" : data;
	}
}
