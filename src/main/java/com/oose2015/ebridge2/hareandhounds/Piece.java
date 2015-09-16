package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.HashMap;

/**
 * an interface for a piece in the game.
 * @author eric
 *
 */
public interface Piece {
	/** returns a point of the position.*/
	public Point getPos();
	/** gets the playerid of the owner.*/
	public String getPlayer();
	/** moves the piece to a new position. should always be validated first.*/
	public Gamestate move(Point newpos);
	/**gets the piecetype.*/
	public String getType();
	/** validates the move. this is piece dependent.
	 * @throws IllegalMoveException */
	public void validateMove(Point topos) throws IllegalMoveException;
	/** the attributes of the piece that will be formatted to json.*/
	public HashMap<String,String> getAttrs();

}
