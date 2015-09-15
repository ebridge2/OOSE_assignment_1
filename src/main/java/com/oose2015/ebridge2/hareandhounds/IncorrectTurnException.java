package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;

public class IncorrectTurnException extends Exception {
	private HashMap<String, String> response;
	public IncorrectTurnException(String message) {
		super(message);
		this.response = new HashMap<>();
		this.response.put("reason", "INCORRECT_TURN");
	}
	public HashMap<String, String> getHash() {
		return this.response;
	}
}
