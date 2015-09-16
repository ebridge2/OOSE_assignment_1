package com.oose2015.ebridge2.hareandhounds;

/**
 * a class to track a single player.
 * @author eric
 *
 */
public class Player {
	/** A fixed string for the first player. This way we will always
	 * know what to expect player1 and player2 to be called.
	 */
	public final static String PLAYER_ONE = "player1";
	/**Same as above.*/
	public final static String PLAYER_TWO = "player2";
	/**the variable that stores the player id, which can be one of the two above.*/
	private String playerid;
	/**a variable to track which piecetype a player is.*/
	private PieceType pieceType;
	/** a variable to track when a player should go.*/
	private Gamestate iGoWhen;
	/**
	 * a method for setting when the piece goes.
	 */
	private void setWhenGo() {
		if (this.pieceType.equals(PieceType.valueOf("HARE"))) {
			this.iGoWhen = Gamestate.valueOf("TURN_HARE");
		} else {
			this.iGoWhen = Gamestate.valueOf("TURN_HOUND");
		}
	}
	/**
	 * the default constructor for a player. the player will be initialized
	 * to show that they do not yet have a piece. 
	 * @param playerId the id
	 */
	public Player(String playerId) {
		this.playerid = playerId;
		this.pieceType = null;
	}
	/**
	 * the alternate constructor for a player. the player will be initialized 
	 * to based on the requested piece type from the game. 
	 * @param playerId the id
	 * @param ptype the piece type
	 */
	public Player(String playerId, String ptype) {
		this.playerid = playerId;
		this.pieceType=PieceType.valueOf(ptype);
		this.setWhenGo();
	}
	/**
	 * a getter for the playerid.
	 * @return the player id
	 */
	public String getId() {
		return this.playerid;
	}
	/**
	 * a getter for when this player should be going.
	 * @return the gamestate this player is limited to
	 */
	public Gamestate myTurn() {
		return this.iGoWhen;
	}
	/**
	 * a method to assign a player type. this allows the game to 
	 * externally define the types of all the players in the game.
	 * @param ptype the player type that the player will have.
	 * @return the player, so that the game can format the response.
	 */
	public Player giveType(String ptype) {
		this.pieceType = PieceType.valueOf(ptype);
		this.setWhenGo();
		return this;
	}
	/**
	 * a getter for the piecetype.
	 * @return the piecetype string. this keeps the 
	 * piecetype enum outside of the game class, since the game does
	 * not need to ever see a piecetype as it is only concerned with passing
	 * strings at all times.
	 */
	public String getType() {
		return String.valueOf(this.pieceType);
	}
}
