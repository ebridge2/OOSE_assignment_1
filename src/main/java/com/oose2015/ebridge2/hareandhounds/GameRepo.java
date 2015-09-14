package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;

public class GameRepo {
	private HashMap<Integer, Game> gamerepo;
	private Integer numgames;
	
	public GameRepo() {
		this.gamerepo = new HashMap<>();
		this.numgames=0;
	}
	
	public Game addGame(Player player1) {
		Game newgame = new Game(this.numgames++, player1);
		this.gamerepo.put(this.numgames,  newgame);
		return newgame;
	}
}
