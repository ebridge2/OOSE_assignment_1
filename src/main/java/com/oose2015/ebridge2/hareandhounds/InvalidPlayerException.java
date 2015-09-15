package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;

public class InvalidPlayerException extends Exception {
	private HashMap<String, String> response;
	public InvalidPlayerException(String message) {
		super(message);
		this.response = new HashMap<>();
		this.response.put("reason", "INVALID_PLAYER_ID");
	}
	public HashMap<String, String> getHash() {
		return this.response;
	}
}
