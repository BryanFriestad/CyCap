package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.drawing.Image;
import com.cycapservers.game.components.positioning.GridLockedPositionComponent;

public class EntityFactory
{
	private static EntityFactory instance = new EntityFactory();
	
	private EntityFactory(){}
	
	public static EntityFactory getInstance()
	{
		return instance;
	}

	public Wall GetWall(short x, short y)
	{
		return new Wall(new GridLockedPositionComponent(x, y), new DrawingComponent(new Image("/res/images/wall.png", -1), 0), -1);
	}
	
	public Spawn GetSpawn(short x, short y, Team t)
	{
		return new Spawn(new GridLockedPositionComponent(x, y), t);
	}

}
