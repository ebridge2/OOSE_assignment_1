package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.HashMap;

public class Board {
	
	private HashMap<Point, Piece> board;
	
	public Board() {
		this.board = new HashMap<>();
		for (int i = 1; i<=3; i++) {
			for (int j = 0; j<=2; j++) {
				this.board.put(new Point(i, j), null);
			}
		}
		this.board.put(new Point(0,1), null);
		this.board.put(new Point(4,1), null);
		this.populateBoard();
	}
	
	private void populateBoard() {
		this.board.put(new Point(4,1), new Hare());
		this.board.put(new Point(0,1), new Hound());
		this.board.put(new Point(1,0), new Hound());
		this.board.put(new Point(1,2), new Hound());
	}
}
