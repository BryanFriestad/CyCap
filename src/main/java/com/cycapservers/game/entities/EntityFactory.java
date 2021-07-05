package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.Entity;
import com.cycapservers.game.components.HealthComponent;
import com.cycapservers.game.components.LifespanComponent;
import com.cycapservers.game.components.SpeedComponent;
import com.cycapservers.game.components.TeamComponent;
import com.cycapservers.game.components.collision.CharacterCollisionComponent;
import com.cycapservers.game.components.collision.CircleCollider;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.collision.RectangleCollider;
import com.cycapservers.game.components.collision.StaticCollisionComponent;
import com.cycapservers.game.components.collision.WeakDamagingCollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.drawing.DrawingComponentFactory;
import com.cycapservers.game.components.input.ClientInputComponent;
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
		Entity e = new Entity(entity_id);
		e.AddComponent(p);
		e.AddComponent(d);
		return e;
	}
	
	public Entity ManufactureCollidingEntity(String entity_id, PositionComponent p, CollisionComponent c)
	{
		Entity e = new Entity(entity_id);
		e.AddComponent(p);
		e.AddComponent(c);
		return e;
	}

	public Entity ManufactureWall(String entity_id, short x, short y)
	{
		Entity e = ManufactureDrawableEntity(entity_id, new GridLockedPositionComponent(x, y), DrawingComponentFactory.getInstance().BuildGreyWallDrawingComponent());
		e.AddComponent(new StaticCollisionComponent(new RectangleCollider(), 1, new GridLockedPositionComponent(x, y)));
		e.AddComponent(new HealthComponent(-1, -1, true));
		return e;
	}
	
	public Entity ManufactureSpawn(String entity_id, short x, short y, Team t)
	{
		Entity e = new Entity(entity_id);
		e.AddComponent(new GridLockedPositionComponent(x, y));
		e.AddComponent(new TeamComponent(t));
		return e;
	}
	
	public Entity ManufacturePlayerCharacter(String entity_id, Team t)
	{
		Entity e = ManufactureDrawableEntity(entity_id, 
											 new PositionComponent(), 
											 DrawingComponentFactory.getInstance().BuildPlayerDrawingComponent(t));
		e.AddComponent(new ClientInputComponent());
		e.AddComponent(new HealthComponent(-1, 3, false));
		e.AddComponent(new SpeedComponent(5));
		e.AddComponent(new TeamComponent(t));
		// add class, visibility, and inventory components
		e.AddComponent(new CharacterCollisionComponent(new CircleCollider(),
													   1, 
													   new PositionComponent()));
		return e;
	}
	
	public Entity ManufactureProjectile(String entity_id, 
										PositionComponent p, 
										DrawingComponent d, 
										int lifespan, 
										double speed, 
										PositionComponent direction)
	{
		Entity e = new Entity(entity_id);
		e.AddComponent(new LifespanComponent(lifespan));
		e.AddComponent(new SpeedComponent(speed, direction));
		e.AddComponent(p);
		e.AddComponent(d);
		e.AddComponent(new WeakDamagingCollisionComponent(new CircleCollider(), 1, new PositionComponent()));
		return e;
	}

}
