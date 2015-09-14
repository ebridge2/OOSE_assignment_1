//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import java.util.Date;
import java.util.HashMap;
import java.awt.Point;


public class Game {

	/** the number of pieces that the game has at once
	 * 
	 */
	private static final int NUMPIECES=4;
	
	private String gameId;
	private HashMap<Point, Piece> gameBoard;
	/** the hound*/
	private String player1;
	/** the hare*/
	private String player2;
	
	/**
	 * initialize an empty game board, with the pieces in their default
	 * locations
	 * @param gameid
	 */
	public Game(String gameid) {
		this.gameId = gameid;
		this.gameBoard = this.initializeBoard();
		this.populateBoard();
	}
	/**
	 * a function to initialize the empty board
	 * @return a blank game board
	 */
	private HashMap<Point, Piece> initializeBoard() {
		HashMap<Point, Piece> newgame = new HashMap<>();
		for (int i = 1; i<=3; i++) {
			for (int j = 0; j<=2; j++) {
				newgame.put(new Point(i, j), null);
			}
		}
		newgame.put(new Point(0,1), null);
		newgame.put(new Point(4,1), null);
		return newgame;
	}
	
	private void populateBoard() {
		this.gameBoard.put(new Point(4,1), new Hare(player2,4,1));
		this.gameBoard.put(new Point(0,1), new Hound(player1,0,1));
		this.gameBoard.put(new Point(1,0), new Hound(player1,1,0));
		this.gameBoard.put(new Point(1,2), new Hound(player1,1,2));
	}
	
    @Override
    public String toString() {
        return "Hareandhound{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", done=" + done +
                ", createdOn=" + createdOn +
                '}';
    }
}
