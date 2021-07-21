package com.cycapservers.game.entities;

import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.ClassComponent;
import com.cycapservers.game.components.HealthComponent;
import com.cycapservers.game.components.InventoryComponent;
import com.cycapservers.game.components.LifespanComponent;
import com.cycapservers.game.components.SpeedComponent;
import com.cycapservers.game.components.TeamComponent;
import com.cycapservers.game.components.VisibilityComponent;
import com.cycapservers.game.components.collision.CharacterCollisionComponent;
import com.cycapservers.game.components.collision.CircleCollider;
import com.cycapservers.game.components.collision.Collider;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.collision.DamagingCollisionComponent;
import com.cycapservers.game.components.collision.GrabbableCollisionComponent;
import com.cycapservers.game.components.collision.RectangleCollider;
import com.cycapservers.game.components.collision.StaticCollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.drawing.DrawingComponentFactory;
import com.cycapservers.game.components.input.ClientInputComponent;
import com.cycapservers.game.components.positioning.GridLockedPositionComponent;
import com.cycapservers.game.components.positioning.HomebasePositionComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.components.usable.AmmoPackUsableComponent;
import com.cycapservers.game.components.usable.HealthPackUsableComponent;
import com.cycapservers.game.components.usable.SpeedPotionUsableComponent;

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
		PositionComponent p = new GridLockedPositionComponent(x, y);
		Entity e = ManufactureDrawableEntity(entity_id, p, DrawingComponentFactory.getInstance().BuildGreyWallDrawingComponent());
		e.AddComponent(new StaticCollisionComponent(new RectangleCollider(p), 1));
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
	
	public Entity ManufacturePlayerCharacter(String entity_id, Team t, CharacterClass c)
	{
		PositionComponent p = new PositionComponent(48, 48);
		Entity e = new Entity(entity_id); 
		e.AddComponent(new ClientInputComponent());
		e.AddComponent(new SpeedComponent(0.005));
		e.AddComponent(p);
		e.AddComponent(DrawingComponentFactory.getInstance().BuildPlayerDrawingComponent(t));
		e.AddComponent(new HealthComponent(-1, 3, false));
		e.AddComponent(new TeamComponent(t));
		e.AddComponent(new InventoryComponent());
		e.AddComponent(new ClassComponent(c));
		e.AddComponent(new VisibilityComponent());
		e.AddComponent(new CharacterCollisionComponent(new CircleCollider(p), 1));
		return e;
	}
	
//	public Entity ManufactureProjectile(String entity_id, 
//										PositionComponent p, 
//										DrawingComponent d, 
//										int lifespan, 
//										double speed, 
//										PositionComponent direction)
//	{
//		Entity e = new Entity(entity_id);
//		e.AddComponent(new LifespanComponent(lifespan));
//		e.AddComponent(new SpeedComponent(speed, direction));
//		e.AddComponent(p);
//		e.AddComponent(d);
//		e.AddComponent(new DamagingCollisionComponent(new CircleCollider(), 1, new PositionComponent(), "owner", damage, wall_dmg_mult, deathType, team, 1));
//		return e;
//	}
	
	public Entity ManufactureFlag(String entity_id, Team t, double base_x, double base_y)
	{
		Entity e = new Entity(entity_id);
		e.AddComponent(new TeamComponent(t));
		HomebasePositionComponent p = new HomebasePositionComponent(base_x, base_y);
		e.AddComponent(p);
		e.AddComponent(new GrabbableCollisionComponent(new RectangleCollider(p), 1));
		e.AddComponent(DrawingComponentFactory.getInstance().ManufactureFlagDrawingComponent(t));
		return e;
	}
	
	////////// POWER UPS //////////
	
	public Entity ManufactureAmmoPack(String entity_id, PositionComponent p)
	{
		Entity e = new Entity(entity_id);
		e.AddComponent(p);
		e.AddComponent(new GrabbableCollisionComponent(new RectangleCollider(p), 1));
		e.AddComponent(new AmmoPackUsableComponent(1));
		e.AddComponent(DrawingComponentFactory.getInstance().ManufactureAmmoPackDrawingComponent());
		return e;
	}

	public Entity ManufactureHealthPack(String entity_id, PositionComponent p)
	{
		Entity e = new Entity(entity_id);
		e.AddComponent(p);
		e.AddComponent(new GrabbableCollisionComponent(new RectangleCollider(p), 1));
		e.AddComponent(new HealthPackUsableComponent(1, 500));
		e.AddComponent(DrawingComponentFactory.getInstance().ManufactureHealthPackDrawingComponent());
		return e;
	}

	public Entity ManufactureSpeedPotion(String entity_id, PositionComponent p)
	{
		Entity e = new Entity(entity_id);
		e.AddComponent(p);
		e.AddComponent(new GrabbableCollisionComponent(new RectangleCollider(p), 1));
		e.AddComponent(new SpeedPotionUsableComponent(1, 10, 1.5, true));
		e.AddComponent(DrawingComponentFactory.getInstance().ManufactureSpeedPotionDrawingComponent());
		return e;
	}

}
