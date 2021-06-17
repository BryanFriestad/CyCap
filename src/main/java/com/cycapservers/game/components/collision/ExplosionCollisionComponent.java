package com.cycapservers.game.components.collision;

import java.util.ArrayList;
import java.util.List;

import com.cycapservers.game.DamageDealer;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.entities.Wall;

/**
 * A collision component which keeps track of entities that it has already hit, but doesn't die on collision.
 * @author Bryan Friestad
 *
 */
public class ExplosionCollisionComponent extends CollisionComponent implements DamageDealer
{
	private int damage;
	private double wall_damage_mult;
	
	private String owner_id;
	protected String death_type;
	private Team team;
	
	private List<CollisionComponent> hurt_entities;
	
	public ExplosionCollisionComponent(Collider c, int p, PositionComponent start_pos, String owner_id, int damage, double wall_dmg_mult, String deathType, Team team) 
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
	public boolean update(Entity e) 
	{
		SetPreviousAndCurrentPosition(e);
		return true;
	}

	@Override
	public void onCollision(CollisionComponent other)
	{
		if (hurt_entities.contains(other)) return;
		
		if(other.GetParent() instanceof Character)
		{
			CharacterCollisionComponent c = (CharacterCollisionComponent) other;
			c.AddDamageToTake((DamageDealer) this.clone());
			hurt_entities.add(other);
		}
		else if(other.GetParent() instanceof Wall)
		{
			WallCollisionComponent w = (WallCollisionComponent) other;
			w.AddDamageToTake(((int) (damage * wall_damage_mult)));
			hurt_entities.add(other);
		}
	}

	@Override
	public CollisionComponent clone() 
	{
		return new ExplosionCollisionComponent(collider, collision_priority, new PositionComponent(), owner_id, damage, wall_damage_mult, death_type, team);
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
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub
	}
}
