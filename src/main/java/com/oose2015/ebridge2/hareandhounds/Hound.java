package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.HashMap;

public class Hound implements Piece {
	/** a point to track the position of the player */
	Point pos;
//	/** the ID of the player */
//	String playerid;
	/** an internal tracker for the piecetype */
	private PieceType ptype;
	/** track the player id of the piece */
	private final String playerid;
	/** a hashmap to track the history of the points the hare has been*/
	HashMap<Point, Integer> history;

	public Hound(Point startpos, String pid) {
		//initialize hare piece
		this.pos=startpos;
		this.playerid = pid;
		this.ptype = PieceType.valueOf("HOUND");
		this.history = new HashMap<>();
		history.put(startpos,  1);
	}
	public PieceType getType() {
		return this.ptype;
	}
	
	public Point getPos() {
		return this.pos;
	}
	public String getPlayer() {
		return this.playerid;
	}
	public Gamestate move(Point newPos) {
		this.pos = newPos;
		return Gamestate.valueOf("TURN_HARE");
	}
	
	public void validateMove(Point frompos, Point topos) {
		//not implemented yet
	}
	
	public HashMap<String, String> getAttrs() {
		HashMap<String, String> retHash = new HashMap<>();
		retHash.put("pieceType", String.valueOf(this.ptype));
		retHash.put("x", String.valueOf(Integer.valueOf((int) this.pos.getX())));
		retHash.put("y", String.valueOf(Integer.valueOf((int) this.pos.getY())));
		return retHash;
	}
	
}