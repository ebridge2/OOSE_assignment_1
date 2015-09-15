package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;

public class IllegalMoveException extends Exception {
	private HashMap<String, String> response;
	public IllegalMoveException(String message) {
		super(message);
		this.response = new HashMap<>();
		this.response.put("reason", "ILLEGAL_MOVE");
	}
	public HashMap<String, String> getHash() {
		return this.response;
	}
}
