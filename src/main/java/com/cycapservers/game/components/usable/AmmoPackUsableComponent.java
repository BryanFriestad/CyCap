package com.cycapservers.game.components.usable;

import com.cycapservers.game.components.InventoryComponent;
import com.cycapservers.game.equipment.Equipment;
import com.cycapservers.game.equipment.Weapon;

public class AmmoPackUsableComponent extends UsableComponent 
{
	private InventoryComponent user;
	
	public AmmoPackUsableComponent(int max_uses)
	{
		super(max_uses, 0);
	}

	@Override
	public boolean Use() 
	{
		/* 
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
		 */
		// TODO Auto-generated method stub
		return false;
	}
}
