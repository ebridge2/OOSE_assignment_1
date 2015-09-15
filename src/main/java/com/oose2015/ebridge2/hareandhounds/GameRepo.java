package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;
import java.util.List;
import java.awt.Point;
import com.google.gson.Gson;

/**
 * a class that contains a repository of all the games for a server
 * @author eric
 *
 */
public class GameRepo {
	private HashMap<Integer, Game> gamerepo;
	private Integer numgames;
	/**
	 * the constructor for a new game repo
	 */
	public GameRepo() {
		this.gamerepo = new HashMap<>();
		this.numgames=0;
	}
	/**
	 * a class that to deserialize from the json of the start info to using gson
	 * @author eric
	 *
	 */
	private class GameStartInfo {
		private String pieceType;
		/**
		 * a function to use to return the piece type
		 * @return a string of the piece type
		 */
		public String getpType() {
			return this.pieceType;
		}
	}
	/**
	 * a function that adds a game to the game repo given a request body
	 * @param body the body of the request
	 * @return the formatted hashmap to be turned to json
	 */
	public HashMap<String, String> addGame(String body) {
		HashMap<String, String> returnMap = new HashMap<>();
		GameStartInfo start = new Gson().fromJson(body, GameStartInfo.class);
		Player player1 = new Player(Player.PLAYER_ONE, PieceType.valueOf(start.getpType()));
		Game newgame = new Game(++this.numgames, player1);
		this.gamerepo.put(this.numgames,  newgame);
		returnMap.put("gameId", newgame.getId());
		returnMap.put("playerId", player1.getId());
		returnMap.put("pieceType", player1.getType().toString());
		return returnMap;
	}
	/**
	 * a class for adding a player to the game given a game id
	 * @param gameid
	 * @return a hash map of the spec'd format
	 */
	public HashMap<String, String> addPlayer(String gameid) {
		HashMap<String, String> returnMap = new HashMap<>();
		Game gamejoin = this.getGame(gameid);
		String ptypeToAdd;
		if (gamejoin.needHare()) {
			ptypeToAdd = "HARE";
		} else {
			ptypeToAdd = "HOUND";
		}
		Player player2 = new Player(Player.PLAYER_TWO, PieceType.valueOf(ptypeToAdd));
		gamejoin.joinGame(player2);
    	returnMap.put("gameId",  gamejoin.getId());
    	returnMap.put("playerId",  player2.getId());
    	returnMap.put("pieceType",  player2.getType().toString());		
		return returnMap;
	}
	public HashMap<String, String> fetchState(String gameid) {
    	Game gameget = this.getGame(gameid); //find the game with the ID
    	HashMap<String, String> returnMap = new HashMap<>();
    	returnMap.put("state", String.valueOf(gameget.getState())); //add its state to the return
    	return returnMap;
	}
	public List<HashMap<String, String>> fetchBoard(String gameid) {
		Game gameget = this.getGame(gameid);
		return gameget.getDescribeBoard();
	}
	/**
	 * a function to return a game
	 * @param gameId the id to return a game for
	 * @return the game
	 */
	public Game getGame(String gameId) {
		return this.gamerepo.get(Integer.parseInt(gameId));
	}
	public class Turn {
		private String playerId;
		private String fromX;
		private String fromY;
		private String toX;
		private String toY;
		
		public Point getFromPos() {
			return new Point(Integer.parseInt(this.fromX), Integer.parseInt(this.fromY));
		}
		public Point getNewPos() {
			return new Point(Integer.parseInt(this.toX), Integer.parseInt(this.toY));
		}
		public String getPlayer() {
			return this.playerId;
		}
	}
	public HashMap<String, String> move(String body, String gameid) throws InvalidPlayerException, 
			IllegalMoveException, IncorrectTurnException, InvalidGameException {
		HashMap<String, String> returnMap = new HashMap<>();
		Turn turntake = new Gson( ).fromJson(body, Turn.class);
		Game gamemove = this.getGame(gameid);
		if (gamemove == null) {
			throw new InvalidGameException("The game id does not exist.");
		}
		return gamemove.takeTurn(turntake);
	}
	
}