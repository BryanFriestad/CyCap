package com.cycapservers.game.components.collision;

import java.util.ArrayList;
import java.util.List;

import com.cycapservers.game.DamageDealer;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.positioning.PositionComponent;

/**
 * A collision component which keeps track of entities that it has already hit, but doesn't die on collision.
 * @author Bryan Friestad
 *
 */
public class DamagingCollisionComponent extends CollisionComponent implements DamageDealer
{
	private int damage;
	private double wall_damage_mult;
	
	private String owner_id;
	protected String death_type;
	private Team team;
	
	private List<CollisionComponent> hurt_entities;
	
	public DamagingCollisionComponent(Collider c, int p, PositionComponent start_pos, String owner_id, int damage, double wall_dmg_mult, String deathType, Team team) 
	{
		super(c, p, start_pos);
		this.damage = damage;
		this.wall_damage_mult = wall_dmg_mult;
		this.owner_id = owner_id;
		this.death_type = deathType;
		this.team = team;
		this.hurt_entities = new ArrayList<CollisionComponent>();
	}
	
	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

	@Override
	public CollisionComponent clone() 
	{
		return new DamagingCollisionComponent(collider, collision_priority, new PositionComponent(), owner_id, damage, wall_damage_mult, death_type, team);
	}

	@Override
	public String getOwnerEntityId() 
	{
		return this.owner_id;
	}

	@Override
	public int getDamageAmount() 
	{
		return this.damage;
	}

	@Override
	public String getDeathType() 
	{
		return death_type;
	}

	@Override
	public Team getOwnerTeam() 
	{
		return team;
	}
	
	@Override
	public void beCollidedBy(CollisionComponent other) 
	{
		other.collideWith(this);
	}

	@Override
	public void collideWith(CharacterCollisionComponent other) 
	{
		if (hurt_entities.contains(other)) return;
		other.GetParent().Send(new ComponentMessage(ComponentMessageId.COLLISION_TAKE_DAMAGE, this));
		hurt_entities.add(other);
	}

	@Override
	public void collideWith(DamagingCollisionComponent other) 
	{
		// intentionally blank	
	}

	@Override
	public void collideWith(WeakDamagingCollisionComponent other) 
	{
		// intentionally blank	
	}

	@Override
	public void collideWith(StaticCollisionComponent other) 
	{
		if (hurt_entities.contains(other)) return;
		other.GetParent().Send(new ComponentMessage(ComponentMessageId.COLLISION_TAKE_DAMAGE, this));
		hurt_entities.add(other);
	}

	@Override
	public Object GetJSONValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
