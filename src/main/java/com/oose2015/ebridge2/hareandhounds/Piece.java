package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.HashMap;

public interface Piece {
	
	public Point getPos();
	
	public String getPlayer();
	
	public Gamestate move(Point newpos);

	public void validateMove(Point frompos, Point topos);
	
	public HashMap<String,String> getAttrs();

}
