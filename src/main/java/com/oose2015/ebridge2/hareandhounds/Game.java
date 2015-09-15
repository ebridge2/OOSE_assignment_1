//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.awt.Point;
import com.google.gson.annotations.Expose;

public class Game {
	/**the string id of the game*/
	private Integer gameId;
	/**the hash map representing the game board*/
	private Board gameBoard;
	/** the first player*/
	private Player player1;
	/** the second player*/
	private Player player2;
	/** an enum that tracks the game state */
	private Gamestate state;
	
	/**
	 * initialize an empty game board, with the pieces in their default
	 * locations
	 * @param gameid
	 */
	public Game(int gameid, Player p1) {
		this.gameId = gameid;
		this.player1 = p1;
		this.gameBoard = new Board();
		this.state = Gamestate.valueOf("TURN_HOUND");
	}
	public void joinGame(Player player2) {
		this.player2 = player2;
	}
	public String getId() {
		return String.valueOf(this.gameId);
	}
	public Player getPlayer1() {
		return this.player1;
	}
	public Player getPlayer2() {
		return this.player2;
	}
	public Gamestate getState() {
		return this.state;
	}
	public List<HashMap<String, String>> getDescribeBoard() {
		return this.gameBoard.getPieces();
	}
}
