package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * a class to implement the piece interface for a hound
 * extends the Piecebase to keep code more concise, since there
 * are many commonalities btwn Hare and hound
 * @author eric
 *
 */
public class Hound extends PieceBase implements Piece {
	
	/**
	 * the constructor of a hound that extends the piecebase
	 * @param startpos the starting position of the hound
	 * @param pid the player id of the hound
	 */
	public Hound(Point startpos,String pid) {
		super(startpos, pid);
		this.ptype = PieceType.HOUND;
		this.nextTurn = Gamestate.TURN_HARE;
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
}