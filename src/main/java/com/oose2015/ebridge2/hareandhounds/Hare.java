package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * a class to implement the piece interface for a hare
 * extends the piecebase to keep code more concise, since there
 * are many commonalities between the two pieces (Hare and hound)
 * @author eric
 *
 */
public class Hare extends PieceBase implements Piece {
	/**
	 * the constructor for a hare that extends the piecebase
	 * @param startpos the starting position of the hare
	 * @param pid the player id of the hare
	 */
	public Hare(Point startpos, String pid) {
		//initialize hare piece
		super(startpos, pid);
		this.ptype = PieceType.HARE;
		this.nextTurn = Gamestate.TURN_HOUND;
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

	
}
