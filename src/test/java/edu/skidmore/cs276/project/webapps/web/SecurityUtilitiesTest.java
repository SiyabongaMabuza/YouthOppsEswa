package edu.skidmore.cs276.project.webapps.web;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.skidmore.cs276.project.webapps.web.SecurityUtilities;

import static org.junit.Assert.assertEquals;



public class SecurityUtilitiesTest {
	
	@Test
	public void testZeroLength() {		
		String result = SecurityUtilities.randomString(0);
		assertEquals("Zero length should return empty string", 0, result.length());	
	}

	@Test
	public void testRequestedLength(){
		int requestLength = 30;
		String result = SecurityUtilities.randomString(requestLength);
		assertEquals("Output should match requested length", requestLength, result.length());
	}

	@Test 
	public void testMultipleLengths() {
        int[] lengths = {5, 10, 25, 50};
        for (int length : lengths) {
            String result = SecurityUtilities.randomString(length);
            assertEquals( "Output should match requested length of " + length, length, result.length());
        }
    }
 
}
