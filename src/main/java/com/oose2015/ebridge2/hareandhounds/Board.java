package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
	/** the board; will always have 4 pieces in it, except during a move */
	private HashMap<Point, Piece> board;
	
	public Board() {
		this.board = new HashMap<>();
		this.populateBoard();
	}
	
	private void populateBoard() {
		Point spha = new Point(4,1);
		Point sp1 = new Point(0,1);
		Point sp2 = new Point(1,0);
		Point sp3 = new Point(1,2);
		//will not create pieces anywhere else... will just remove
		//and place in new locations
		this.board.put(spha, new Hare(spha));
		this.board.put(sp1, new Hound(sp1));
		this.board.put(sp2, new Hound(sp2));
		this.board.put(sp3, new Hound(sp3));
	}
	public List<HashMap<String, String>> getPieces() {
		List<HashMap<String, String>> returnList = new ArrayList<>();
		for (Point point : this.board.keySet() ) {
			Piece activePiece = this.board.get(point);
			returnList.add(activePiece.getAttrs());
		}
		return returnList;
	}
}
