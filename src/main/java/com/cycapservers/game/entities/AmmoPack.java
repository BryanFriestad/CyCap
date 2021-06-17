package com.cycapservers.game.entities;

import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.equipment.Equipment;
import com.cycapservers.game.equipment.Weapon;
import com.cycapservers.game.matchmaking.Game;

public class AmmoPack extends PowerUp {

	public AmmoPack(PositionComponent p, DrawingComponent model, CollisionComponent c, Game g, String name, int max_uses, long duration) 
	{
		super(p, model, c, g, name, max_uses, duration);
	}

	@Override
	public boolean update() 
	{
		return uses_remaining >= 1;
	}

	@Override
	public boolean use() 
	{
		if (grabber == null) 
		{
			throw new IllegalStateException("Item cannot be used when the grabber is null");
		}
		
		if (uses_remaining < 1) 
		{
			return true;
		}
		else 
		{
			for (Equipment e : grabber.getInventory()) 
			{
				if (e instanceof Weapon) 
				{
					Weapon w = (Weapon) e;
					w.refill();
				}
			}
			uses_remaining--;
			last_activate_time = System.currentTimeMillis();
			if (uses_remaining == 0) return true;
		}
		
		return false;
	}

	@Override
	public AmmoPack clone() 
	{
		return new AmmoPack(position.clone(), model.clone(), collision_component.clone(), null, name, getMax_uses(), duration);
	}

}
