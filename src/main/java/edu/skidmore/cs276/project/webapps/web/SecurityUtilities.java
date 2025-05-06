package edu.skidmore.cs276.project.webapps.web;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import nl.captcha.Captcha;

/**
 * A library of static functions used to support the demonstration of web
 * security concepts
 * 
 * This library is for class use only and is NOT designed or tested for
 * production use.
 * 
 * @author David Read
 * @version 01.00.00
 *
 */
public class SecurityUtilities {

	/**
	 * A secure random instance used for generating secure random values
	 */
	private final static SecureRandom random = new SecureRandom();

	/**
	 * This is a library of static functions, no instance should be created
	 */
	private SecurityUtilities() {

	}

	/**
	 * Validate the CAPTCHA value, comparing the generated value with the one
	 * entered by the user.
	 * 
	 * @param req The request containing the user's session and entered CAPTCHA
	 *            value
	 * 
	 * @return True if the CAPTCHA value entered matches the one in the session
	 */
	public final static boolean isCaptchaValueValid(HttpServletRequest req) {
		boolean isValid = false;
		Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
		String userEnteredCaptchaValue = req.getParameter(RequestParameter.CAPTCHA_VALUE.getKey());

		if (captcha != null && userEnteredCaptchaValue != null) {
			isValid = captcha.isCorrect(userEnteredCaptchaValue);
		}

		return isValid;
	}

	/**
	 * Create a random string of characters using Java's SecureRandom class. The
	 * processes creates an array of random bytes and then Base64 encodes the byte
	 * array. Finally it returns a String of the encoded characters matching the
	 * requested length.
	 * 
	 * @param requestLength The length of the String to create
	 * 
	 * @return A String of random bytes, encoded with Base64
	 */
	public final static String randomString(int requestLength) {
		Random random = new SecureRandom();
		byte[] randomValue;
		byte[] base64Value;
		
		randomValue = new byte[requestLength];
		random.nextBytes(randomValue);
		base64Value = Base64.encodeBase64(randomValue);
		String finalValue = new String(base64Value, 0, requestLength);
		
		return finalValue;
	}

	/**
	 * Collect some data to determine frequencies of characters in the random
	 * strings being generated
	 * 
	 * @param numIterations How many random values to be generated and collected
	 */
	public final static void collectRandomData(long numIterations) {
		char min = Character.MAX_VALUE;
		char max = 0;
		int[] freq = new int[255];
		PrintWriter out = null;

		for (int x = 0; x < freq.length; ++x) {
			freq[x] = 0;
		}

		for (int x = 0; x < numIterations; ++x) {
			String value = randomString(30);
			for (char oneChar : value.toCharArray()) {
				min = (char) Math.min(min, oneChar);
				max = (char) Math.max(max, oneChar);
				freq[oneChar]++;
			}
			System.out.print(value + " : ");
			System.out.println("min:" + min + " max:" + max);
		}

		try {
			out = new PrintWriter(new FileWriter("char.freq.tsv"));
			for (char index = '+'; index <= 'z'; ++index) {
				out.println(index + "\t" + freq[index]);
			}
		} catch (Throwable throwable) {
			System.err.println("Unable to write frequencies to file");
			throwable.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Throwable throwable) {
					System.err.println("Unable to close frequencies file");
					throwable.printStackTrace();
				}
			}
		}
	}

	/**
	 * Show the effect of using the same and different seeds with Random and
	 * SecureRandom classes
	 */
	private final static void randomSamples() {
		Random random1 = new Random();
		Random random2 = new Random();
		Random secureRandom1 = new SecureRandom();
		Random secureRandom2 = new SecureRandom();

		random1.setSeed(10);
		random2.setSeed(10);
		secureRandom1.setSeed(10);
		secureRandom2.setSeed(10);

		showRandom(5, random1);
		showRandom(5, random2);
		showRandom(5, secureRandom1);
		showRandom(5, secureRandom2);
	}

	/**
	 * Send a set of random values to standard out.
	 * 
	 * @param numValues       The number of values to generate
	 * @param randomGenerator The random number generator to use
	 */
	private final static void showRandom(int numValues, Random randomGenerator) {
		for (int count = 0; count < numValues; ++count) {
			if (count > 0) {
				System.out.print(", ");
			}
			System.out.print(randomGenerator.nextInt());
		}

		System.out.println();
	}

	public final static void main(String[] args) {
		randomSamples();
		System.out.println(randomString(30));
		System.out.println("Google " + Base64.encodeBase64String("Google".getBytes()));
		System.out.println("Goodie " + Base64.encodeBase64String("Goodie".getBytes()));
	}
}
