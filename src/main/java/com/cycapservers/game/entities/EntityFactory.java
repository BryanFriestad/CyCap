package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.Entity;
import com.cycapservers.game.components.HealthComponent;
import com.cycapservers.game.components.TeamComponent;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.collision.RectangleCollider;
import com.cycapservers.game.components.collision.WallCollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.drawing.DrawingComponentFactory;
import com.cycapservers.game.components.positioning.GridLockedPositionComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

public class EntityFactory
{
	private static EntityFactory instance = new EntityFactory();
	
	private EntityFactory(){}
	
	public static EntityFactory getInstance()
	{
		return instance;
	}
	
	public Entity ManufactureDrawableEntity(String entity_id, PositionComponent p, DrawingComponent d)
	{
		Entity component = new Entity(entity_id);
		component.AddComponent(p);
		component.AddComponent(d);
		return component;
	}
	
	public Entity ManufactureCollidingEntity(String entity_id, PositionComponent p, CollisionComponent c)
	{
		Entity component = new Entity(entity_id);
		component.AddComponent(p);
		component.AddComponent(c);
		return component;
	}

	public Entity ManufactureWall(String entity_id, short x, short y)
	{
		Entity component = ManufactureDrawableEntity(entity_id, new GridLockedPositionComponent(x, y), DrawingComponentFactory.getInstance().BuildGreyWallDrawingComponent());
		component.AddComponent(new WallCollisionComponent(new RectangleCollider(), 1, new GridLockedPositionComponent(x, y)));
		component.AddComponent(new HealthComponent(-1, -1, true));
		return component;
	}
	
	public Entity ManufactureSpawn(String entity_id, short x, short y, Team t)
	{
		Entity component = new Entity(entity_id);
		component.AddComponent(new GridLockedPositionComponent(x, y));
		component.AddComponent(new TeamComponent(t));
		return component;
	}

}
