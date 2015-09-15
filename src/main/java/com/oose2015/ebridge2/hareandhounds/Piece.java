package com.oose2015.ebridge2.hareandhounds;

import java.awt.Point;
import java.util.HashMap;

public interface Piece {
	
	public Point getPos();
	
	public void move(Point newpos);
	
	public void validateMove(Point newpos);
	
	public HashMap<String,String> getAttrs();
}
