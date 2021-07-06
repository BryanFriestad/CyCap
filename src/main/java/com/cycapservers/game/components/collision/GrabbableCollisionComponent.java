package com.cycapservers.game.components.collision;

import org.json.JSONObject;

import com.cycapservers.game.components.InventoryComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

public class GrabbableCollisionComponent extends CollisionComponent 
{
	private boolean grabbed;
	private InventoryComponent grabber;
	
	public GrabbableCollisionComponent(Collider c, int p, PositionComponent start_pos)
	{
		super(c, p, start_pos);
		grabbed = false;
		grabber = null;
	}
	
	@Override
	public CollisionComponent clone() 
	{
		return new GrabbableCollisionComponent(collider, collision_priority, new PositionComponent());
	}

	@Override
	public void beCollidedBy(CollisionComponent other) 
	{
		other.collideWith(this);
	}

	@Override
	public void collideWith(CharacterCollisionComponent other) 
	{
		GetPickedUp(other);
	}

	@Override
	public void collideWith(DamagingCollisionComponent other) 
	{
		GetPickedUp(other);
	}

	@Override
	public void collideWith(StaticCollisionComponent other) 
	{
		GetPickedUp(other);
	}

	@Override
	public void collideWith(GrabbableCollisionComponent other) 
	{
		GetPickedUp(other);
	}
	
	private void GetPickedUp(CollisionComponent other)
	{
		if (grabbed) return;
		if (other.GetParent().HasComponentOfType(InventoryComponent.class))
		{
			InventoryComponent inventory = (InventoryComponent) other.GetParent().GetComponentOfType(InventoryComponent.class);
			boolean is_picked_up = inventory.PickupItem(this.parent);
			if (is_picked_up)
			{
				grabber = inventory;
				// TODO: disable drawing component
			}
			grabbed = is_picked_up;
		}
	}
	
	public void GetDropped()
	{
		grabber = null;
		grabbed = false;
		// TODO: enable drawing component
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
		obj.put("grabbed", grabbed);
		obj.put("collider", this.collider.getJSONValue());
		return obj;
	}

}
