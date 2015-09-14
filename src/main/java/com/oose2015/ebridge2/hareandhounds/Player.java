package com.oose2015.ebridge2.hareandhounds;

public class Player {

	public final static String PLAYER_ONE = "player1";
	public final static String PLAYER_TWO = "player2";
	private String playerid;
	private PieceType pieceType;
	
	public Player(String playerId, PieceType ptype) {
		this.playerid = playerId;
		this.pieceType=ptype;
	}
	public String getId() {
		return this.playerid;
	}
	public PieceType getType() {
		return this.pieceType;
	}
}
