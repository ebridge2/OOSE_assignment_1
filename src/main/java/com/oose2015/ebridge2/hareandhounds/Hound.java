package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * a class to implement the piece interface for a hound
 * @author eric
 *
 */
public class Hound implements Piece {
	/** a point to track the position of the player */
	Point pos;
	/** an internal tracker for the piecetype */
	private PieceType ptype;
	/** track the player id of the piece */
	private final String playerid;
	/** a hashmap to track the history of the points the hare has been*/
	HashMap<Point, Integer> history;
	/** the turn when this piece should go.*/
	private Gamestate nextTurn = Gamestate.TURN_HARE;
	private final List<Point> oneSpace;
	/**
	 * the constructor for a new hound
	 * @param startpos the startin position of the hound
	 * @param pid the player id associated
	 */
	public Hound(Point startpos, String pid) {
		//initialize hare piece
		this.pos=startpos;
		this.playerid = pid;
		this.ptype = PieceType.valueOf("HOUND");
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
	 * getter for the type
	 * @return the type
	 */
	public String getType() {
		return String.valueOf(this.ptype);
	}
	/**
	 * getter for the point.
	 * @return the point
	 */
	public Point getPos() {
		return this.pos;
	}
	/**
	 * getter for the player ID
	 * @return the id
	 */
	public String getPlayer() {
		return this.playerid;
	}
	/**
	 * getter for the position. also returns the gamestate
	 * for the hares turn. end conditions will all be handled by
	 * the board itself.
	 * @param newPos the new position to move to
	 * @return the type of the next turn
	 */
	public Gamestate move(Point newPos) {
		this.pos = newPos;
		return this.nextTurn;
	}
	/**
	 * validates a move and throws error if invalid
	 * does not need the frompos since the point already saves that info
	 * and for the method to be called in the first place the point must be valid
	 * @param topos the position moving to
	 * @throws IllegalMoveException the move was illegal in some way.
	 */
	public void validateMove(Point topos) throws IllegalMoveException {
		Integer xold = Integer.valueOf((int) this.pos.getX());
		Integer yold = Integer.valueOf((int) this.pos.getY());
		Integer xnew = Integer.valueOf((int) topos.getX());
		Integer ynew = Integer.valueOf((int) topos.getY());
		//calculate the magnitude of the move, since it should only be one space difference
		//this would be a matnitude no greater than sqrt(1+1)
		//calculate the magnitude of the move, since it should only be one space difference
		Integer xdist = Math.abs(xnew-xold);
		Integer ydist = Math.abs(ynew-yold);
		//pythagorean's theorem...
		double magmove = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2)); 
		if (xnew < xold) {
			throw new IllegalMoveException("the hound cannot move backwards.");
		} else if (magmove >= 2 || (magmove > 1 && this.oneSpace.contains(this.pos))) {
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