package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;

/**
 * a class for an illegal move
 * @author eric
 *
 */
public class IllegalMoveException extends Exception {
	/** the response to be transformed to json.*/
	private HashMap<String, String> response;
	/**
	 * construct the exception based on the type of error.
	 * @param message the message
	 */
	public IllegalMoveException(String message) {
		super(message);
		this.response = new HashMap<>();
		//because the assignment says it should say this
		this.response.put("reason", "ILLEGAL_MOVE");
	}
	/**
	 * returns the hash map to be formatted to json
	 * @return stated above
	 */
	public HashMap<String, String> getHash() {
		return this.response;
	}
}
