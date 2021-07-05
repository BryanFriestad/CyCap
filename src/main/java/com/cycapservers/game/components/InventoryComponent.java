package com.cycapservers.game.components;

import com.cycapservers.game.entities.Item;
import com.cycapservers.game.equipment.Equipment;

public class InventoryComponent extends Component 
{
	public static final int DEFAULT_INVENTORY_SIZE = 4;
	private Equipment[] inventory;
	private Equipment current_equipment;
	private Item item_slot;
	
	public InventoryComponent()
	{
		super("inventory");
		item_slot = null;
		current_equipment = null;
		inventory = new Equipment[DEFAULT_INVENTORY_SIZE];
	}
	
	/**
	 * @param equipment_index which inventory slot to equip
	 */
	private boolean switchEquipment(int equipment_index) 
	{
		if (equipment_index >= inventory.length || equipment_index < 0)
		{
			throw new IllegalArgumentException("equipmentIndex(" + equipment_index + ") is out of bounds");
		}
		
		if (inventory[equipment_index] != null)
		{
			Equipment old = current_equipment;
			if(current_equipment.unequip())
			{
				if (inventory[equipment_index].equip())
				{
					current_equipment = inventory[equipment_index];
					return true;
				}
				else //cannot equip new object for some reason
				{ 
					if (old.equip())
					{
						return false; //did not equip new object
					}
					else //cannot re-equip old object
					{ 
						current_equipment = null;
						throw new IllegalStateException("equipment switch failed and could not switch back.");
					}
				}
			}
			else
			{
				return false; //cannot unequip current item
			}
		}
		else
		{
			return false; //new slot is empty
		}
	}
	
	private void SetItem(Item i)
	{
		item_slot = i;
	}
	
	private void SetInventoryList(Equipment[] new_list)
	{
		if (inventory.length != new_list.length) throw new IllegalArgumentException("new list length does not match that of this component");
		for (int slot_index = 0; slot_index < inventory.length; slot_index++)
		{
			inventory[slot_index] = new_list[slot_index];
		}
	}
	
	private void UseItem()
	{
		if (item_slot != null)
		{
			boolean exhausted = item_slot.use();
			if (exhausted)
			{
				item_slot = null;
			}
		}
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean Update(long delta_t) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object GetJSONValue() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
