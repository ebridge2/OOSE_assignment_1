//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.awt.Point;
import com.google.gson.annotations.Expose;
import com.oose2015.ebridge2.hareandhounds.GameRepo.Turn;

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
	 * initialize an empty game board, with the pieces in their default
	 * locations
	 * @param gameid
	 */
	public Game(int gameid, Player p1) {
		this.gameId = gameid;
		this.gameBoard = new Board(p1);
		this.state = Gamestate.valueOf("WAITING_FOR_SECOND_PLAYER");
		this.playerOrder = new ArrayList<>();
		this.playerOrder.add(p1);
	}
	public void joinGame(Player player2) {
		this.playerOrder.add(player2);
		this.state = Gamestate.valueOf("TURN_HOUND");
		if (String.valueOf(this.playerOrder.get(0).getType()).equals("HARE")) {
			swapTurns();
		}
	}
	public String getId() {
		return String.valueOf(this.gameId);
	}
	public Gamestate getState() {
		return this.state;
	}
	public List<HashMap<String, String>> getDescribeBoard() {
		return this.gameBoard.getPieces();
	}
	public boolean needHare() {
		//since player1 will be in this slot at this point of the game
		//during initializing
		return (this.playerOrder.get(0).getType() == PieceType.valueOf("HOUND"));
	}
	public void swapTurns() {
		Player tplayer = this.playerOrder.remove(0);
		this.playerOrder.add(tplayer); //remove player from front and add at the end
	}
	public void checkPlayer(String playerid) throws InvalidPlayerException {
		for (Player player : playerOrder) {
			if (player.getId().equals(playerid)) { return;};
		}
		throw new InvalidPlayerException("The player name specified is invalid.");
	}
	public HashMap<String, String> takeTurn(Turn turn) throws InvalidPlayerException, 
			IllegalMoveException, IncorrectTurnException {
		checkPlayer(turn.getPlayer());
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
