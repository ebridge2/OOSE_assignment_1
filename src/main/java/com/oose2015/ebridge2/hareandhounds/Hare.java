package com.oose2015.ebridge2.hareandhounds;

public class Hare implements Piece{
	
	Integer xpos;
	Integer ypos;
	String playerid;
	public Hare(String player, Integer x, Integer y) {
		//initialize hare piece
		this.xpos = x;
		this.ypos = y;
		this.playerid = player;
	}
	
	public Integer getX() {
		return this.xpos;
	}
	public Integer getY() {
		return this.ypos;
	}
	
	public void move(Integer newx, Integer newy) {
		//not implemented yet
	}
	
	public void validateMove(Integer newx, Integer newy) {
		//not implemented yet
	}
	
}
