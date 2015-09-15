package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.HashMap;

public class Hare implements Piece {
	/** a point to track the position of the player */
	private Point pos;
	/** a way to track the piece type internally for the json return*/
	private PieceType ptype;
	/** the player who owns the piece */
	private final String playerid;
	/** a hashmap to track the history of the points the hare has been*/
	HashMap<Point, Integer> history;

	public Hare(Point startpos, String pid) {
		//initialize hare piece
		this.pos=startpos;
		this.ptype = PieceType.valueOf("HARE");
		this.playerid = pid;
		this.history = new HashMap<>();
		history.put(startpos,  1);
	}
	public PieceType getType() {
		return this.ptype;
	}
	
	public String getPlayer() {
		return this.playerid;
	}
	
	public Point getPos() {
		return this.pos;
	}
	public Gamestate move(Point newPos) {
		this.pos = newPos;
		return Gamestate.valueOf("TURN_HOUND");
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
