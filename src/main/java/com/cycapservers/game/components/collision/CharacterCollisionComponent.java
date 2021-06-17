package com.cycapservers.game.components.collision;

import java.util.ArrayList;
import java.util.List;

import com.cycapservers.game.DamageDealer;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.Character;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.entities.Item;
import com.cycapservers.game.entities.Wall;

public class CharacterCollisionComponent extends CollisionComponent 
{
	private List<DamageDealer> damage_to_take;
	
	public CharacterCollisionComponent(Collider c, int p, PositionComponent start_pos) 
	{
		super(c, p, start_pos);
		damage_to_take = new ArrayList<DamageDealer>();
	}

	@Override
	public void onCollision(CollisionComponent other) 
	{
		Object other_parent = other.GetParent();
		
		if (other_parent == null)
		{
			throw new IllegalStateException("other collision component is not attached to an object");
		}
		else if (other_parent instanceof Item)
		{
			Item i = (Item) other_parent;
			Character c = (Character) GetParent();
			
			if (c.getItem_slot() == null)
			{
				i.pickUp(c);
			}
		}
		else if(other_parent instanceof Wall)
		{
			Character c = (Character) GetParent();
			double delta_x = c.getX() - getPreviousPosition().getX();
			double delta_y = c.getY() - getPreviousPosition().getY();
			
			c.setPosition(getPreviousPosition());
			int max_depth = 4;
			for(int i = 1; i <= max_depth; i++) //get as close to the wall as we can by successive approximation
			{ 
				if(!isColliding(other)){
					c.setX(c.getX() + (delta_x / Math.pow(2, i)));
					c.setY(c.getY() + (delta_y / Math.pow(2, i)));
				}
				else{
					c.setX(c.getX() - (delta_x / Math.pow(2, i)));
					c.setY(c.getY() - (delta_y / Math.pow(2, i)));
				}
			}
			
		}
		else if(other instanceof CharacterCollisionComponent)
		{
			return;
		}
		
	}

	@Override
	public CollisionComponent clone() {
		// TODO Auto-generated method stub
		return null;
	}

	public void AddDamageToTake(DamageDealer damage) 
	{
		this.damage_to_take.add(damage);
	}

	@Override
	public boolean update(Entity e) 
	{
		SetPreviousAndCurrentPosition(e);
		
		Character c = (Character) e;
		for (DamageDealer dd : this.damage_to_take)
		{
			c.takeDamage(dd);
		}
		damage_to_take.clear();
		
		return true;
	}

	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub	
	}
}
