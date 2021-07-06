package com.cycapservers.game.components;

import org.json.JSONObject;

import com.cycapservers.game.entities.Item;
import com.cycapservers.game.equipment.Equipment;

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
	
	private void PickupItem(Entity i)
	{
		item_slot = i;
		// set item as grabbed
		// set grabber to this.parent
	}
	
	private void DropItem()
	{
		// set item position to this.position
		// set item as not grabbed
		// set item grabber to null
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
