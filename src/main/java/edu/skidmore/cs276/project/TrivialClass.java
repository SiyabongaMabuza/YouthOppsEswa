package edu.skidmore.cs276.project;

/**
 * Trival class with one attribute. Used to test that unit testing is configured
 * correctly.
 */
public class TrivialClass {
	/**
	 * A value.
	 */
	private String value;

	/**
	 * Create the instance with a null value.
	 */
	public TrivialClass() {

	}

	/**
	 * Create and instance and initialize the value.
	 * 
	 * @param value The initial value
	 */
	public TrivialClass(String value) {
		setValue(value);
	}

	/**
	 * Retrieve the value.
	 * 
	 * @return The current value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value.
	 * 
	 * @param value The new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
