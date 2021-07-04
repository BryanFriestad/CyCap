package com.cycapservers.game.components.collision;

import com.cycapservers.game.DamageDealer;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;
import com.cycapservers.game.components.positioning.PositionComponent;
/**
 * A collision component which deletes itself when it collides with another entity.
 * Will damage the other entity on collision.
 * @author btrf_
 *
 */
public class WeakDamagingCollisionComponent extends CollisionComponent implements DamageDealer
{
	private int damage;
	private double wall_damage_mult;
	
	private String owner_id;
	protected String death_type;
	private Team team;
	
	boolean alive;
	
	public WeakDamagingCollisionComponent(Collider c, int p, PositionComponent start_pos, String owner_id, int damage, double wall_dmg_mult, String deathType, Team team) 
	{
		super(c, p, start_pos);
		this.damage = damage;
		this.wall_damage_mult = wall_dmg_mult;
		this.owner_id = owner_id;
		this.death_type = deathType;
		this.team = team;
		alive = true;
	}
	
	@Override
	public boolean Update(long delta_t) 
	{
		return alive;
	}

	@Override
	public CollisionComponent clone() 
	{
		return new WeakDamagingCollisionComponent(collider, collision_priority, new PositionComponent(), owner_id, damage, wall_damage_mult, death_type, team);
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
	public Object GetJSONValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beCollidedBy(CollisionComponent other) 
	{
		other.collideWith(this);
	}
	
	@Override
	public void collideWith(CharacterCollisionComponent other) 
	{
		if (!alive) return;
		other.GetParent().Send(new ComponentMessage(ComponentMessageId.COLLISION_TAKE_DAMAGE, this));
		alive = false;
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
		if (!alive) return;
		other.GetParent().Send(new ComponentMessage(ComponentMessageId.COLLISION_TAKE_DAMAGE, this));
		alive = false;
	}
}
