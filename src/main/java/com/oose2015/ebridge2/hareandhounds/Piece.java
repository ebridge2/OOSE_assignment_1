package com.oose2015.ebridge2.hareandhounds;

public interface Piece {
	
	public Integer getX();
	public Integer getY();
	
	public void move(Integer newx, Integer newy);
	
	public void validateMove(Integer newx, Integer newy);
	
}

}
