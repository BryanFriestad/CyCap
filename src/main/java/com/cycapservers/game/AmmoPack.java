package com.cycapservers.game;

public class AmmoPack extends PowerUp {

	public AmmoPack(Drawable model, Collider c, int collision_priority, Game g, String name, int max_uses, long duration) {
		super(model, c, collision_priority, g, name, max_uses, duration);
	}

	@Override
	public boolean update() {
		return uses_remaining >= 1;
	}

	@Override
	public boolean use() {
		if(grabber == null) {
			throw new IllegalStateException("Item cannot be used when the grabber is null");
		}
		
		if(uses_remaining < 1) {
			return true;
		}
		else {
			for(Equipment e : grabber.getInventory()) {
				if(e instanceof Weapon) {
					Weapon w = (Weapon) e;
					w.refill();
				}
			}
			uses_remaining--;
			last_activate_time = System.currentTimeMillis();
			if(uses_remaining == 0)
				return true;
		}
		
		return false;
	}

	@Override
	public AmmoPack clone() {
		return new AmmoPack(getModel().clone(), getCollider().clone(), getCollisionPriority(), null, name, getMax_uses(), duration);
	}

}
