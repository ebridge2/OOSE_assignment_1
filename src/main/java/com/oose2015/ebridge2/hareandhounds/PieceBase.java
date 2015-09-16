package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * a parent class for our pieces. Note that the nextturn and ptype 
 * are all initialized to null so we can save some code and hot have to keep 
 * rewriting the getters/setters for these
 * @author eric
 *
 */
public class PieceBase {
	/** a point to track the position of the player */
	protected Point pos;
	/** track the player id of the piece */
	protected final String playerid;
	/** the spaces that can only move vertically or horizontally from */
	protected final List<Point> oneSpace;
	/**the Gamestate updated in the child*/
	protected Gamestate nextTurn;
	/**the piecetype updated in the child*/
	protected PieceType ptype;
	/**
	 * the constructor for a PieceBase
	 * @param startpos the starting position of the hare
	 * @param pid the player id of the hare
	 */
	public PieceBase(Point startpos, String pid) {
		//initialize hare piece
		this.pos=startpos;
		this.playerid = pid;
		this.oneSpace = new ArrayList<>();
		this.populateOneSpace();
		this.nextTurn = null;
		this.ptype = null;
	}
	/**
	 * a function to populate the list of positions can only move
	 * vertically or horizontally from
	 */
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
	 * getter for the id of the player who owns
	 * @return the id of the player who owns
	 */
	public String getPlayer() {
		return this.playerid;
	}
	/**
	 * updates the position. also returns the gamestate
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
	 * a setter for the piecetype and gamestate, for use
	 * in the child
	 * @param piecetype the piecetype
	 * @param gstate the state of the next turn
	 */
	public void updatePiece(PieceType piecetype, Gamestate gstate) {
		this.nextTurn = gstate;
		this.ptype = piecetype;
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
