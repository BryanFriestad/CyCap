package com.cycapservers.game.components.positioning;

import com.cycapservers.game.Utils;

public class GridLockedPositionComponent extends PositionComponent 
{

	public GridLockedPositionComponent() 
	{
		super();
		setX(0);
		setY(0);
	}

	public GridLockedPositionComponent(Double x, Double y) 
	{
		super();
		setX(x);
		setY(y);
	}
	
	public GridLockedPositionComponent(Short x, Short y) 
	{
		super((x * Utils.GRID_LENGTH) + (Utils.GRID_LENGTH / 2), (y * Utils.GRID_LENGTH) + (Utils.GRID_LENGTH / 2));
	}
	
	@Override
	public void setX(double x)
	{
		super.setX(x);
		short gridX = getClosestGridX();
		super.setX((gridX * Utils.GRID_LENGTH) + (Utils.GRID_LENGTH / 2));
	}
	
	@Override
	public void setY(double y)
	{
		super.setY(y);
		short gridY = getClosestGridY();
		super.setY((gridY * Utils.GRID_LENGTH) + (Utils.GRID_LENGTH / 2));
	}
	
	@Override
	public GridLockedPositionComponent clone() 
	{
		return new GridLockedPositionComponent(getX(), getY());
	}

}
