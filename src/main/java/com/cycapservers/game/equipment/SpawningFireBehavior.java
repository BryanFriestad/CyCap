package com.cycapservers.game.equipment;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.LifespanComponent;
import com.cycapservers.game.components.OwnerComponent;
import com.cycapservers.game.components.SpeedComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.drawing.DrawingComponentFactory;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.Blueprint;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.entities.EntityFactory;

public class SpawningFireBehavior extends FiringBehavior 
{
	private PositionComponent source_pos;
	private PositionComponent destination_pos;
	
	/**
	 * The speed which to fire the bullet in grid spaces per ms. 
	 */
	private double speed;
	
	private int damage;
	private double wall_damage_multiplier;
	private String death_type;
	
	/**
	 * 
	 * @param speed In grid spaces per ms.
	 * @param damage
	 * @param wall_damage_multiplier
	 * @param death_type
	 */
	public SpawningFireBehavior(double speed, int damage, double wall_damage_multiplier, String death_type)
	{
		this.speed = speed;
		this.damage = damage;
		this.wall_damage_multiplier = wall_damage_multiplier;
		this.death_type = death_type;
		this.source_pos = new PositionComponent();
		this.destination_pos = new PositionComponent();
	}

	@Override
	public void UpdateMousePosition(PositionComponent mouse_pos) 
	{
		destination_pos = mouse_pos.clone();
	}

	@Override
	public void UpdateFiringPosition(PositionComponent firing_pos) 
	{
		source_pos = firing_pos.clone();
	}

	@Override
	public void Fire(Entity parent_entity) 
	{
//		System.out.println("FIRE!");
		PositionComponent direction = Utils.MakeUnitVector(Utils.difference(destination_pos, source_pos));
		PositionComponent spawn_point = Utils.add(source_pos, Utils.GetScaleVector(direction, Utils.GRID_LENGTH)); // Spawn the bullet one-half grid along it's path so it doesn't hit shooter.
//		System.out.println("mouse pos: " + destination_pos.GetJSONValue());
//		System.out.println("source position: " + source_pos.GetJSONValue());
//		System.out.println("spawn position: " + spawn_point.GetJSONValue());
		OwnerComponent o = (OwnerComponent) parent_entity.GetComponentOfType(OwnerComponent.class);
		Blueprint b = EntityFactory.getInstance().GiveProjectileBlueprint(o.GetOwner(), 
																		  spawn_point, 
																		  DrawingComponentFactory.getInstance().ManufactureProjectileDrawingComponent(),
																		  1500,
																		  new SpeedComponent(speed, direction),
																		  damage,
																		  wall_damage_multiplier,
																		  death_type);
		parent_entity.Send(new ComponentMessage(ComponentMessageId.EQUIPMENT_FIRE_SPAWN, b));
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

}
