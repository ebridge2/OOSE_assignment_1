package com.oose2015.ebridge2.hareandhounds;


import com.oose2015.ebridge2.hareandhounds.Game.Turn;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.oose2015.ebridge2.hareandhounds.Game.Turn;

/**
 * a class that tracks the pieces on the board for a game.
 * the pieces are stored in a hashmap that is points to pieces.
 * in between turns the board will ALWAYS have 4 pieces in it; 
 * during a move after it is determined valid the piece will be temporarily removed
 * and then replaced
 * @author eric
 *
 */
public class Board {
	/**
	 * the max possible number of elements on the board, or 4*3 + 1
	 */
	public static final NUMELS = 13;
	/** the board; will always have 4 pieces in it, except during a move */
	private HashMap<Point, Piece> board;
	/** the history of all the arrangements of the board.
	 * note that the history tracks each arrangement with its integer.
	 * the arrangements are tracked as  hash map of points to lists
	 */
	HashMap<Integer, HashMap<Double, Integer>> gameHistory;
	/**
	 * a constructor for the board, given the type of the first player
	 * note that we keep type as string so that the only object using the 
	 * piecetypes are the players and the pieces themselves
	 * @param p1ype the type of the first player
	 */
	public Board(String p1type) {
		this.board = new HashMap<>();
		this.populateBoard(p1type);
		this.gameHistory = new HashMap<>();
	}
	private Gamestate addHistory(Point harepos, Point h1pos, Point h2pos, Point h3pos) {
		Double hareval = harepos.getX()*3 + harepos.getY();
		Double h1val = (h1pos.getX()*3 + h1pos.getY());
		Double h2val = (h2pos.getX()*3 + h2pos.getY());
		Double h3val = (h3pos.getX()*3 + h3pos.getY());
		// use bitshifting to make every combination of positions 100% 
		//unique relevant excluslively to the position of ANY hound.
		//basically, we bit shift based on what the position shifts to
		//for ex, a point at [1,1] has a value of the 3*1 + 1 = 4 position,
		//so this will shift the value 000001(bin) to 10000(bin)
		//when we then add, this gives us a 1 in every space occupied 
		//by a hound
		Integer h1shift = 1 << h1val.intValue();
		Integer h2shift = 1 << h2val.intValue();
		Integer h3shift = 1 << h3val.intValue();
		Integer houndval = h1shift + h2shift + h3shift;
		checkIfWin(hareval, houndval);
	}
	/**
	 * a function to populate a board given the first player, since
	 * the second palyer's piecetype will be fixed at that point
	 * @param p1
	 */
	private void populateBoard(String p1type) {
		//the starting points for the hare and the 
		//hounds
		Point spha = new Point(4,1);
		Point sp1 = new Point(0,1);
		Point sp2 = new Point(1,0);
		Point sp3 = new Point(1,2);
		this.addHistory(spha, sp1, sp2, sp3);
		//will not create pieces anywhere else... will just remove
		//and place in new locations
		String p1id = Player.PLAYER_ONE;
		String p2id = Player.PLAYER_TWO;
		if (p1type.equals("HARE")) { //make player1 the hare
			this.board.put(spha, new Hare(spha, p1id));
			this.board.put(sp1, new Hound(sp1, p2id));
			this.board.put(sp2, new Hound(sp2, p2id));
			this.board.put(sp3, new Hound(sp3, p2id));
		} else { //make player2 the hare
			this.board.put(spha, new Hare(spha, p2id));
			this.board.put(sp1, new Hound(sp1, p1id));
			this.board.put(sp2, new Hound(sp2, p1id));
			this.board.put(sp3, new Hound(sp3, p1id));
		}
	}
	/**
	 * returns a formatted list of the pieces, where each piece is described
	 * by a hash map and the collection of pieces in a list
	 * @return the formatted list to be transformed to json
	 */
	public List<HashMap<String, String>> getPieces() {
		List<HashMap<String, String>> returnList = new ArrayList<>();
		//iterate over the points, since there is 1 point in the board
		//for each piece on the board
		for (Point point : this.board.keySet() ) {
			Piece activePiece = this.board.get(point);
			returnList.add(activePiece.getAttrs());
		}
		return returnList;
	}
	/**
	 * a function to check whether the position moving to is occupied.
	 * @param toPos the position moving to
	 * @throws IllegalMoveException if the space is occupied
	 */
	private void checkToPos(Point toPos) throws IllegalMoveException {
		if (this.board.get(toPos) != null) {
			throw new IllegalMoveException("A piece occupies that space.");
		}
	}
	private Gamestate checkEndConditions(Gamestate returnState) {
		List<Point> pieceList = new ArrayList<>();
		Point harePoint = null;
		//iteratively add points to the piecelist; only the harepoint is 
		//specific since the other values will not matter in our "cache" history
		//since the hounds can be in any of the same positions
		//to be considered a 3fold repetition
		for (Point point : this.board.keySet() ) {
			if (this.board.get(point).getType().equals("HARE")) {
				harePoint = point;
			} else {
				pieceList.add(point);
			}
		}
		return addHistory(harePoint, pieceList.get(0), pieceList.get(1), pieceList.get(2));
	}
	/**
	 * the turn element as it is concerned for the board.
	 * performs all of the necessary actions in response to the request.
	 * @param turn the turn that is requested
	 * @return the state of the game
	 * @throws IllegalMoveException the move made is invalid
	 */
	public Gamestate move(Turn turn) throws IllegalMoveException {
		String playerid = turn.getPlayer();
		Point frompos = turn.getFromPos();
		Point topos = turn.getNewPos();
		//cross validate the piece requested against the player who
		//owns the piece
		this.checkToPos(topos);
		Piece tobeMoved = this.board.get(frompos);
		Gamestate returnState = null;
		if (tobeMoved == null) { //if the point is not found in the hashmap, tobemoved is null
			throw new IllegalMoveException("You have not selected a piece.");
		}
		//check if the correct player presented turn request
		if (playerid.equals(tobeMoved.getPlayer())) {
			//validate whether the move is legal for the piece
			//if invalid, moveexception will throw
			tobeMoved.validateMove(topos);
			//move the piece
			this.board.remove(frompos);
			returnState = tobeMoved.move(topos);
			this.board.put(topos, tobeMoved);
			returnState = checkEndConditions(returnState); //check if an end condition has been met
															//and update the returnState if so
		} else {
			throw new IllegalMoveException("Wrong piece selected.");
		}
		return returnState;
	}
}
