//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Point;
/**
 * a game class that contains the game and ordered collection of players
 * baed on their turn. It has a board, a state, an id, and a playerlist.
 * @author eric
 *
 */
public class Game {
	/**the string id of the game*/
	private Integer gameId;
	/**the hash map representing the game board*/
	private Board gameBoard;
	/** an enum that tracks the game state */
	private Gamestate state;
	/** a string that tracks the current player up.*/
	private List<Player> playerOrder;
	
	/**
	 * initialize an empty game
	 * @param gameid the id of the new game to be added
	 */
	public Game(int gameid) {
		this.gameId = gameid;
		this.gameBoard = null;
		this.playerOrder = new ArrayList<>();
	}
	/**
	 * breaks up the constructor and the addplayer method so that
	 * all player manipulation can be handled internally as opposed to
	 * players being made in the gamerepo
	 * also sets up the board pieces, since they will now be ultimately fixed
	 * @param starttype
	 * @return
	 */
	public Player addFirstPlayer(String starttype) {
		Player player1 = new Player(Player.PLAYER_ONE, starttype);
		this.gameBoard = new Board(player1.getType());
		this.playerOrder.add(player1);
		//added first player; so waiting for second player to join
		this.state = Gamestate.valueOf("WAITING_FOR_SECOND_PLAYER");
		return player1;
	}
	/**
	 * a method to add a second player to the game
	 * @return the added player
	 * @throws NoSpaceException tells you there is no space in the game to be 
	 * joined
	 */
	public Player joinGame() throws NoSpaceException {
		if (this.playerOrder.size() == 2) { //another player cannot be added
			throw new NoSpaceException("There is no space left in this game.");
		}
		Player player2 = new Player(Player.PLAYER_TWO);
		if (this.playerOrder.get(0).getType().equals("HOUND")) {
			//the new player is the hare, so they will go second in the playerorder
			this.playerOrder.add(player2.giveType("HARE"));
		} else { //the new player is the hound, so add them to the front of the playerOrder
			this.playerOrder.add(0, player2.giveType("HOUND"));
		} //the game starts as the turn of the hound
		this.state = Gamestate.valueOf("TURN_HOUND");
		return player2;
	}
	/** 
	 * the id of the game
	 * @return the game id
	 */
	public String getId() {
		return String.valueOf(this.gameId);
	}
	/**
	 * the state of the game
	 * @return the gamestate
	 */
	public Gamestate getState() {
		return this.state;
	}
	/**
	 * returns a list of hash maps, where each hash map describes a particular piece
	 * @return the list of hash maps
	 */
	public List<HashMap<String, String>> getDescribeBoard() {
		return this.gameBoard.getPieces();
	}
	/**
	 * a method to swap the piece whose turn it will be next
	 */
	public void swapTurns() {
		Player tplayer = this.playerOrder.remove(0);
		this.playerOrder.add(tplayer); //remove player from front and add at the end
	}
	/**
	 * a method to check whether a player is in the game or not based 
	 * on the id passed in
	 * @param playerid the id to check
	 * @throws InvalidPlayerException the player is not in the game
	 * @throws IncorrectTurnException 
	 */
	public void checkPlayer(String playerid) throws InvalidPlayerException, 
			IncorrectTurnException {
		boolean flagPlayer = false;
		boolean flagTurn = false;
		//iterate over the player and check whether it is 
		//a) the player exists, and b) it is their turn
		for (Player player : playerOrder) {
			if (player.getId().equals(playerid)) { 
				flagPlayer = true;
				if (this.state.equals(player.myTurn())) {
					flagTurn = true;
				}
			}
		}
		if (!flagTurn) {
			throw new IncorrectTurnException("It's not your turn.");
		} else if (!flagPlayer) {
			throw new InvalidPlayerException("The player name specified is invalid.");
		}
	}
	/**
	 * a class to deserialize a turn request to. this is contained
	 * in the game and not the game repo because there is no reason for
	 * the game repo to have information about a turn, as it will only
	 * be used by the game.
	 * @author eric
	 *
	 */
	public class Turn {
		/** the id of the player to be moved.*/
		private String playerId;
		/** the xcoor the player is moving from.*/
		private String fromX;
		/** the ycoor the player is moving from.*/
		private String fromY;
		/**the xcoor the player is moving to.*/
		private String toX;
		/**the ycoor the player is moving to.*/
		private String toY;
		/**
		 * creates and returns a point of the position the player is moving
		 * from
		 * @return the point of the from position
		 */
		public Point getFromPos() {
			return new Point(Integer.parseInt(this.fromX), Integer.parseInt(this.fromY));
		}
		/**
		 * creates and returns a point of the updated position the player is
		 * moving to
		 * @return the point of the to pos
		 */
		public Point getNewPos() {
			return new Point(Integer.parseInt(this.toX), Integer.parseInt(this.toY));
		}
		/**
		 * the player id requesting a turn.
		 * @return the id
		 */
		public String getPlayer() {
			return this.playerId;
		}
		/**
		 * a method to validate that the turn has everything, and the request was
		 * clean
		 */
		public void validateTurn() throws IncorrectJsonException {
			if (this.playerId == null || this.fromX == null || this.fromY == null ||
					this.toX == null || this.toY == null) {
				throw new IncorrectJsonException("your Json request for a turn "
						+ "was formatted improperly.");
			}
		}
	}
	/**
	 * simulates one turn of the game
	 * @param turn the Turn that has all of the information about the turn
	 * @return the formatted hashmap containing all the return type info
	 * spec'd on the sheet that a turn should produce
	 * @throws InvalidPlayerException the player is not in the game
	 * @throws IllegalMoveException the move is invalid
	 * @throws IncorrectTurnException it was not this player's turn
	 */
	public HashMap<String, String> takeTurn(Turn turn) throws InvalidPlayerException, 
			IllegalMoveException, IncorrectTurnException {
		this.checkPlayer(turn.getPlayer());
		if (turn.getPlayer().equals(this.playerOrder.get(0).getId())) {
			this.state = this.gameBoard.move(turn);
			HashMap<String, String> returnMap = new HashMap<>();
			returnMap.put("PlayerId", turn.getPlayer());
			swapTurns();
			return returnMap;
		} else {
			throw new IncorrectTurnException("It's not your turn.");
		}
	}
}
