package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;
import java.util.List;
import java.awt.Point;
import com.google.gson.Gson;
import com.oose2015.ebridge2.hareandhounds.Game.Turn;

/**
 * a class that contains a repository of all the games for a server
 * the class is also responsible for deserializing all json requests
 * and appropriately passing the necessary information to the requested
 * games
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
		public void validateGameStart() throws IncorrectJsonException {
			if (pieceType == null) {
				throw new IncorrectJsonException("Your request for a new game does not contain"
						+ " a properly formatted pieceType");
			}
		}
	}
	/**
	 * a function that adds a game to the game repo given a request body
	 * @param body the body of the request
	 * @return the formatted hashmap to be turned to json
	 * @throws IncorrectJsonException if the request poorly formatted
	 */
	public HashMap<String, String> addGame(String body) throws IncorrectJsonException {
		HashMap<String, String> returnMap = new HashMap<>();
		GameStartInfo start = new Gson().fromJson(body, GameStartInfo.class);
		start.validateGameStart();
		Game newgame = new Game(++this.numgames); //increment the game counter
		Player player1 = newgame.addFirstPlayer(start.getpType());
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
	 * @throws InvalidGameException if the id does not exist
	 * @throws NoSpaceException if the game is full
	 * @throws IncorrectJsonException if the request is poorly formatted
	 */
	public HashMap<String, String> addPlayer(String gameid) throws InvalidGameException,
			NoSpaceException, IncorrectJsonException {
		HashMap<String, String> returnMap = new HashMap<>();
		if (gameid == null) {
			throw new IncorrectJsonException("the id of the game you wish to add a player to"
					+ "is not formatted properly.");
		}
		Game gamejoin = this.getGame(gameid);
		Player playerAdded = gamejoin.joinGame(); //return player so you can format the map
    	returnMap.put("gameId",  gamejoin.getId());
    	returnMap.put("playerId",  playerAdded.getId());
    	returnMap.put("pieceType",  playerAdded.getType());		
		return returnMap;
	}
	/**
	 * a method to return the state of a specified game.
	 * @param gameid the game to fetch
	 * @return the game state
	 * @throws InvalidGameException the game was not found
	 * @throws IncorrectJsonException if the request is formatted wrong
	 */
	public HashMap<String, String> fetchState(String gameid) throws InvalidGameException, 
			IncorrectJsonException {
		if (gameid == null) {
			throw new IncorrectJsonException("the id of the game you wish to fetch the"
					+ "state of is invalid.");
		}
    	Game gameget = this.getGame(gameid); //find the game with the ID
    	HashMap<String, String> returnMap = new HashMap<>();
    	returnMap.put("state", String.valueOf(gameget.getState())); //add its state to the return
    	return returnMap;
	}
	/**
	 * a method to fetch the board state
	 * @param gameid the game id to get
	 * @return the board state, formatted in a list of hash maps (the list of
	 * formatted hash maps to describe each piece)
	 * @throws InvalidGameException the game id was not found
	 * @throws IncorrectJsonException if the request is formatted wrong
	 */
	public List<HashMap<String, String>> fetchBoard(String gameid) throws InvalidGameException, 
			IncorrectJsonException {
		if (gameid == null) {
			throw new IncorrectJsonException("the request to fetch board state"
					+ "is improperly formatted.");
		}
		Game gameget = this.getGame(gameid);
		return gameget.getDescribeBoard();
	}
	/**
	 * a function to return a game
	 * @param gameId the id to return a game for
	 * @return the game
	 * @throws InvalidGameException 
	 */
	public Game getGame(String gameId) throws InvalidGameException {
		Game gameget = this.gamerepo.get(Integer.parseInt(gameId));
		if (gameget == null) {
			throw new InvalidGameException("The game id does not exist.");
		}
		return gameget;
	}
	/**
	 * a method that begins a turn for the required game
	 * @param body the request to be deserialized to a turn
	 * @param gameid the id of the game
	 * @return the response in a hash map that can be transformed to json
	 * @throws InvalidPlayerException wrong player id sent
	 * @throws IllegalMoveException turn was invalid
	 * @throws IncorrectTurnException wrong player attempts a turn
	 * @throws InvalidGameException game id does not exist or game is over
	 * @throws IncorrectJsonException if the json is formatted wrong
	 */
	public HashMap<String, String> move(String body, String gameid) throws InvalidPlayerException, 
			IllegalMoveException, IncorrectTurnException, InvalidGameException, IncorrectJsonException {
		HashMap<String, String> returnMap = new HashMap<>();
		//deserialize here, since it would be absolutely gross to deserialize within
		//the game itself
		Turn turntake = new Gson( ).fromJson(body, Turn.class);
		turntake.validateTurn();
		Game gamemove = this.getGame(gameid);
		return gamemove.takeTurn(turntake);
	}
	
}