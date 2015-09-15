package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.HashMap;

public class Hare implements Piece {
	/** a point to track the position of the player */
	private Point pos;
	/** a way to track the piece type internally for the json return*/
	private PieceType ptype;
	/** a hashmap to track the history of the points the hare has been*/
	HashMap<Point, Integer> history;
	public Hare() {
		//does nothing
	}
	public Hare(Point startpos) {
		//initialize hare piece
		this.pos=startpos;
		this.ptype = PieceType.valueOf("HARE");
		this.history = new HashMap<>();
		history.put(startpos,  1);
	}
	public PieceType getType() {
		return this.ptype;
	}
	
	public Point getPos() {
		return this.pos;
	}
	public void move(Point newPos) {
		//not implemented yet
	}
	
	public void validateMove(Point newPos) {
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
