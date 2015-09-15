package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.oose2015.ebridge2.hareandhounds.GameRepo.Turn;

public class Board {
	/** the board; will always have 4 pieces in it, except during a move */
	private HashMap<Point, Piece> board;
	
	public Board(Player p1) {
		this.board = new HashMap<>();
		this.populateBoard(p1);
	}
	
	private void populateBoard(Player p1) {
		Point spha = new Point(4,1);
		Point sp1 = new Point(0,1);
		Point sp2 = new Point(1,0);
		Point sp3 = new Point(1,2);
		//will not create pieces anywhere else... will just remove
		//and place in new locations
		String p1type = String.valueOf(p1.getType());
		String p1id = "player1";
		String p2id = "player2";
		if (p1.getType().equals("HARE")) {
			this.board.put(spha, new Hare(spha, p1id));
			this.board.put(sp1, new Hound(sp1, p2id));
			this.board.put(sp2, new Hound(sp2, p2id));
			this.board.put(sp3, new Hound(sp3, p2id));
		} else {
			this.board.put(spha, new Hare(spha, p2id));
			this.board.put(sp1, new Hound(sp1, p1id));
			this.board.put(sp2, new Hound(sp2, p1id));
			this.board.put(sp3, new Hound(sp3, p1id));
		}
	}
	public List<HashMap<String, String>> getPieces() {
		List<HashMap<String, String>> returnList = new ArrayList<>();
		for (Point point : this.board.keySet() ) {
			Piece activePiece = this.board.get(point);
			returnList.add(activePiece.getAttrs());
		}
		return returnList;
	}
	public Gamestate move(Turn turn) throws IllegalMoveException {
		String playerid = turn.getPlayer();
		Point frompos = turn.getFromPos();
		Point topos = turn.getNewPos();
		Piece tobeMoved = this.board.get(frompos);
		Gamestate returnState = null;

		if (playerid.equals(tobeMoved.getPlayer())) {
			this.board.remove(frompos);
			tobeMoved.validateMove(frompos, topos);
			returnState = tobeMoved.move(topos);
			this.board.put(topos, tobeMoved);
		} else {
			throw new IllegalMoveException("Wrong piece selected.");
		}
		return returnState;
	}
}
