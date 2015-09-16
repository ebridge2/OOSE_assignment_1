package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;

/**
 * a constructor for an exception when a game has no space
 * and a player tries to join
 * @author eric
 *
 */
public class NoSpaceException extends Exception {
	private HashMap<String, String> response;
	/**
	 * the constructor of a nospaceexception
	 * @param message the context 
	 */
	public NoSpaceException(String message) {
		super(message);
		this.response = new HashMap<>();
		this.response.put("reason", "NO_SPACE");
	}
	/**
	 * a getter for the formatted response to be transformed to json
	 * @return the response
	 */
	public HashMap<String, String> getHash() {
		return this.response;
	}
}
