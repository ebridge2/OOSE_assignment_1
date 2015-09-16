package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * a class to implement the piece interface for a hare
 * @author eric
 *
 */
public class Hare implements Piece {
	/** a point to track the position of the player */
	private Point pos;
	/** a way to track the piece type internally for the json return*/
	private PieceType ptype;
	/** the player who owns the piece */
	private final String playerid;
	/** a hashmap to track the history of the points the hare has been*/
	HashMap<Point, Integer> history;
	/** the turn when this piece should go.*/
	private Gamestate nextTurn = Gamestate.valueOf("TURN_HOUND");
	/** the Points that can only have vert/horizontal moves */
	private final List<Point> oneSpace;
	/**
	 * the constructor for a hare
	 * @param startpos the starting position of the hare
	 * @param pid the player id of the hare
	 */
	public Hare(Point startpos, String pid) {
		//initialize hare piece
		this.pos=startpos;
		this.ptype = PieceType.valueOf("HARE");
		this.playerid = pid;
		this.history = new HashMap<>();
		history.put(startpos,  1);
		this.oneSpace = new ArrayList<>();
		this.populateOneSpace();
	}
	private void populateOneSpace() {
		this.oneSpace.add(new Point(1,1));
		this.oneSpace.add(new Point(2,2));
		this.oneSpace.add(new Point(2,0));
		this.oneSpace.add(new Point(3,1));
	}
	/**
	 * returns the type of the piece
	 * @return the type of the piece
	 */
	public String getType() {
		return String.valueOf(this.ptype);
	}
	/**
	 * getter for the id of the player who owns
	 * @return the id of the player who owns
	 */
	public String getPlayer() {
		return this.playerid;
	}
	/**
	 * getter for the position of the piece
	 * @return the position of the piece
	 */
	public Point getPos() {
		return this.pos;
	}
	/**
	 * returns the next gamestate after the move. The end conditions
	 * will all be handled externally by the board itself.
	 * @return the gamestate of the next move, the hound.
	 */
	public Gamestate move(Point newPos) {
		this.pos = newPos;
		return this.nextTurn;
	}
	/**
	 * validates a move
	 * does not take the frompos since the piece stores that info
	 * @param topos the new position that the piece will move
	 * @throws IllegalMoveException the illegal move with context given
	 */
	public void validateMove(Point topos) throws IllegalMoveException {
		//not implemented yet
		Integer xold = Integer.valueOf((int) this.pos.getX());
		Integer yold = Integer.valueOf((int) this.pos.getY());
		Integer xnew = Integer.valueOf((int) topos.getX());
		Integer ynew = Integer.valueOf((int) topos.getY());
		//calculate the magnitude of the move, since it should only be one space difference
		Integer xdist = Math.abs(xnew-xold);
		Integer ydist = Math.abs(ynew-yold);
		//pythagorean's theorem...
		double magmove = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2)); 
		if (magmove >= 2 || (magmove > 1 && this.oneSpace.contains(this.pos))) {
			throw new IllegalMoveException("that move is too far.");
		}
	}
	/**
	 * returns the attributes of the piece
	 * @return the attributes formatted to a hahsmap
	 */
	public HashMap<String, String> getAttrs() {
		HashMap<String, String> retHash = new HashMap<>();
		retHash.put("pieceType", String.valueOf(this.ptype));
		retHash.put("x", String.valueOf(Integer.valueOf((int) this.pos.getX())));
		retHash.put("y", String.valueOf(Integer.valueOf((int) this.pos.getY())));
		return retHash;
	}
	
}
