package com.cycapservers.game;

public class GridLockedPosition extends Position {

	public GridLockedPosition() {
		super();
		setX(0);
		setY(0);
	}

	public GridLockedPosition(double x, double y) {
		super();
		setX(x);
		setY(y);
	}
	
	public GridLockedPosition(short x, short y) {
		super((x * Utils.GRID_LENGTH) + (Utils.GRID_LENGTH / 2), (y * Utils.GRID_LENGTH) + (Utils.GRID_LENGTH / 2));
	}
	
	@Override
	public void setX(double x){
		super.setX(x);
		short gridX = getClosestGridX();
		super.setX((gridX * Utils.GRID_LENGTH) + (Utils.GRID_LENGTH / 2));
	}
	
	@Override
	public void setY(double y){
		super.setY(y);
		short gridY = getClosestGridY();
		super.setY((gridY * Utils.GRID_LENGTH) + (Utils.GRID_LENGTH / 2));
	}

}
