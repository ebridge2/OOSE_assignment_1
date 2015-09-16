package com.oose2015.ebridge2.hareandhounds;


import com.oose2015.ebridge2.hareandhounds.Game.Turn;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * a class that tracks the pieces on the board for a game.
 * the pieces are stored in a hashmap that is points to pieces.
 * in between turns the board will ALWAYS have 4 pieces in it; 
 * during a move after it is determined valid the piece will be temporarily removed
 * and then replaced
 * I am adding a small flowchart since we do not require any diagrams or UMLs, but I
 * think this gives a better sense of how the overall method breakdown works
 * 
 * the layout of a created board is as follows:
 * 		initialize board -> populate board -> addPieces -> add history
 * and every turn will proceed as follows, checking and updating the history,
 * to verify that an end condition was not met at some point:
 * 		move -> checkEndConditions -> CheckIfWin -> addHistory
 * @author eric
 *
 */
public class Board {
	/**the number of rows of the board.*/
	private static final double NUMROWS = 3;
	/** the number of columns of the board.*/
	private static final double NUMCOLS = 5;
	/** the board; will always have 4 pieces in it, except during a move */
	private HashMap<Point, Piece> board;
	/** the history of all the arrangements of the board.
	 * note that the history tracks each arrangement with its integer.
	 * the arrangements are tracked as  hash map of points to lists
	 */
	HashMap<Integer, HashMap<Integer, Integer>> gameHistory;
	/**
	 * a constructor for the board, given the type of the first player
	 * note that we keep type as string so that the only object using the 
	 * piecetypes are the players and the pieces themselves
	 * @param p1ype the type of the first player
	 */
	public Board(String p1type) {
		this.board = new HashMap<>();
		this.gameHistory = new HashMap<>();
		HashMap<Integer, Integer> internalMap = null;
		for (int i = 0; i < NUMROWS*NUMCOLS; i++) {
			internalMap = new HashMap<Integer, Integer>();
			this.gameHistory.put(i, internalMap);
		}
		this.populateBoard(p1type);
	}
	/**
	 * a method to help the add history to determine more specific 
	 * end game conditions that the assignment specifies. note that since we used
	 * bitshifts, every value for the houndval shown below is 100% unique out of the possible board
	 * layout.
	 * @param hareval the position of the hare
	 * @param houndval the sum of the positions of the hound, adjusted using bitshifts for independence
	 * @return a gamestate if there is any change, or null otherwise
	 */
	private Gamestate addHistory(Integer hareval, Integer houndval) {
		if ((hareval == 13 && houndval == 3584) || (hareval == 8 && houndval == 2208) ||
				(hareval == 6 && houndval == 648)) {
			return Gamestate.WIN_HOUND; //since these are the only combinations by which a 
										//hound can win
		}
		HashMap<Integer, Integer> checkMap = this.gameHistory.get(hareval);
		Integer prevVal = checkMap.get(houndval);
		if (prevVal == null) {
			prevVal = 0;
		} else if (prevVal == 2) {
			return Gamestate.WIN_HARE_BY_STALLING; //no need to put the map back since game ends
		}
		prevVal++; //increment previous number of occurences by 1
		checkMap.put(houndval, prevVal); //add back the incremented previous value
		this.gameHistory.put(hareval, checkMap); //update the history
		return null; //no change of the state from the default, which is the next player
					//who is up
	}
	/**
	 * checks overall if the game is a win, if it is not, updates the history and does some more checking 
	 * there
	 * @param harepos the point of the hare
	 * @param h1pos the point of the hound1
	 * @param h2pos the point of the hound2
	 * @param h3pos the point of the houd3
	 * @return the gamestate, which can be from here or from addHistory if an end condition
	 * 	not met
	 */
	private Gamestate checkIfWin(Point harepos, Point h1pos, Point h2pos, Point h3pos) {
		//if the column of the hare is less than that of any of the hounds, game is over and computation 
		//can end
		if ((harepos.getX() <= h1pos.getX()) && (harepos.getX() <= h2pos.getX()) && 
				(harepos.getX() <= h3pos.getX())) {
			return Gamestate.WIN_HARE_BY_ESCAPE;
		}
		//get the position in a simple mapping of numrows * xpos + ypos
		Double hareval = harepos.getX()*NUMROWS + harepos.getY();
		Double h1val = (h1pos.getX()*NUMROWS + h1pos.getY());
		Double h2val = (h2pos.getX()*NUMROWS + h2pos.getY());
		Double h3val = (h3pos.getX()*NUMROWS + h3pos.getY());
		// use bitshifting to make every combination of positions 100% 
		//unique relevant excluslively to the position of ANY hound.
		//basically, we bit shift based on what the position shifts to
		//for ex, a point at [1,1] has a value of the 3*1 + 1 = 4 position,
		//so this will shift the value 000001(bin) to 10000(bin)
		//when we then add, this gives us a 1 in every space occupied 
		//by a hound. 
		Integer h1shift = 1 << h1val.intValue();
		Integer h2shift = 1 << h2val.intValue();
		Integer h3shift = 1 << h3val.intValue();
		Integer houndval = h1shift + h2shift + h3shift;	
		return addHistory(hareval.intValue(), houndval);
	}
	/**
	 * a helper method for populating the board, so as to not
	 * need to write the same thing twice in the if-else loop
	 * @param hareplayer the hare player
	 * @param houndplayer the hound player
	 */
	private void addPieces(String hareplayer, String houndplayer) {
		//the starting points for the hare and the 
		//hounds
		Point spha = new Point(4,1);
		Point sp1 = new Point(0,1);
		Point sp2 = new Point(1,0);
		Point sp3 = new Point(1,2);
		this.board.put(spha, new Hare(spha, hareplayer));
		this.board.put(sp1, new Hound(sp1, houndplayer));
		this.board.put(sp2, new Hound(sp2, houndplayer));
		this.board.put(sp3, new Hound(sp3, houndplayer));
		this.addHistory(13, 42); //the unique starting position
								 // of the board
	}
	/**
	 * a function to populate a board given the first player, since
	 * the second palyer's piecetype will be fixed at that point
	 * @param p1
	 */
	private void populateBoard(String p1type) {
		//will not create pieces anywhere else... will just remove
		//and place in new locations
		String p1id = Player.PLAYER_ONE;
		String p2id = Player.PLAYER_TWO;
		if (p1type.equals("HARE")) { //make player1 the hare
			addPieces(p1id, p2id);

		} else { //make player2 the hare
			addPieces(p2id, p1id);
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
		Gamestate returnAlt = checkIfWin(harePoint, pieceList.get(0), 
				pieceList.get(1), pieceList.get(2));
		if (returnAlt != null) { //addHistory returns null if there is no end condition met, so
								//if it is not null, it can simply return the next player's turn (the input)
			return returnAlt;
		}
		return returnState; //return initial if nothing changes
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