package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;

/**
 * a class to describe an incorrectly formatted json request packet
 * issues could include forgetting a field, misspelling a field,
 * and other things of that nature
 * @author eric
 *
 */
public class IncorrectJsonException extends Exception {
	/** the formatted response hash map.*/
	private HashMap<String, String> response;
	/**
	 * the constructor for the exception
	 * @param message the message with some context
	 */
	public IncorrectJsonException(String message) {
		super(message);
		this.response = new HashMap<>();
		this.response.put("reason", "BAD_REQUEST");
	}
	/**
	 * a getter for the hashmap
	 * @return the hash map to be transformed to json
	 */
	public HashMap<String, String> getHash() {
		return this.response;
	}
}