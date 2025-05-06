package edu.skidmore.cs276.project.webapps.web;

import org.junit.Test;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.Date;

public class StringUtilitiesTest {

    @Test
    public void testDbSafeString() {
        assertEquals("Should escape special characters", "O\\'Brien\\\\", StringUtilities.dbSafeString("O'Brien\\"));
        assertEquals("Should return empty for null", "", StringUtilities.dbSafeString(null));
    }

    @Test
    public void testFormatDate() {
        Date date = new Date(2025 - 1900, 3, 26); // April 26, 2025
        assertEquals("Should format date as MM/dd/yyyy", "04/26/2025", StringUtilities.formatDate(date));
        assertEquals("Should return empty for null", "", StringUtilities.formatDate(null));
    }

    @Test
    public void testParseDate() throws ParseException {
        Date date = StringUtilities.parseDate("04/26/2025");
        assertEquals("Should parse date correctly", "04/26/2025", StringUtilities.formatDate(date));
    }

    @Test(expected = ParseException.class)
    public void testParseDateInvalidFormat() throws ParseException {
        StringUtilities.parseDate("2025-04-26"); // Wrong format
    }

    @Test
    public void testNoNull() {
        assertEquals("Should return empty for null", "", StringUtilities.noNull(null));
        assertEquals("Should return original string", "test", StringUtilities.noNull("test"));
    }
}