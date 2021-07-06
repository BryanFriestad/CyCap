package com.cycapservers.game.components;

import org.json.JSONObject;

import com.cycapservers.game.components.collision.GrabbableCollisionComponent;
import com.cycapservers.game.components.usable.UsableComponent;
import com.cycapservers.game.entities.Entity;

public class InventoryComponent extends Component 
{
	public static final int DEFAULT_INVENTORY_SIZE = 4;
	private Entity[] equipment;
	private int active_equipment_index;
	private Entity item_slot;
	
	public InventoryComponent()
	{
		super("inventory");
		item_slot = null;
		active_equipment_index = 0;
		equipment = new Entity[DEFAULT_INVENTORY_SIZE];
	}
	
	/**
	 * @param equipment_index which inventory slot to equip
	 */
	private void SwitchEquipment(int equipment_index) 
	{
		if (equipment_index >= equipment.length || equipment_index < 0)
		{
			throw new IllegalArgumentException("equipmentIndex(" + equipment_index + ") is out of bounds");
		}
		active_equipment_index = equipment_index;
	}
	
	/**
	 * 
	 * @param item The item to pick up by this component.
	 * @return Returns true if the item was successfully picked up.
	 */
	public boolean PickupItem(Entity item)
	{
		if (item_slot != null) return false;
		item_slot = item;
		return true;
	}
	
	private void DropItem()
	{
		// set item position to this.position
		((GrabbableCollisionComponent) item_slot.GetComponentOfType(GrabbableCollisionComponent.class)).GetDropped(); 
		item_slot = null;
	}
	
	private void UseItem()
	{
		if (item_slot != null)
		{
			UsableComponent usable = (UsableComponent) item_slot.GetComponentOfType(UsableComponent.class);
			boolean exhausted = usable.Use();
			if (exhausted)
			{
				item_slot = null;
			}
		}
	}
	
	private void SetInventoryList(Entity[] new_list)
	{
		if (equipment.length != new_list.length) throw new IllegalArgumentException("new list length does not match that of this component");
		for (int slot_index = 0; slot_index < equipment.length; slot_index++)
		{
			equipment[slot_index] = new_list[slot_index];
		}
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		switch (message.getMessage_id())
		{
		case INPUT_SWITCH_EQUIPMENT:
			SwitchEquipment((Integer) message.getData());
			break;
			
		case INPUT_USE_ITEM:
			UseItem();
			break;
			
		default:
			break;
		}
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject obj = new JSONObject();
		obj.put("current_equipment", active_equipment_index);
		for (Entity e : equipment)
		{
			obj.append("inventory", e.toJSONObject());
		}
		obj.put("item_slot", item_slot.toJSONObject());
		return obj;
	}

}
