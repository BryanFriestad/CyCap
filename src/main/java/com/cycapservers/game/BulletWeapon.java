package com.cycapservers.game;

public class BulletWeapon extends Weapon {

	public BulletWeapon(String name, long switchCooldown, Drawable icon, long fire_rate, long reload_time,
			int max_reloads, int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
			Sound cannot_reload_sound) {
		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound, cannot_fire_sound,
				reload_sound, cannot_reload_sound);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean fire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refill() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equip() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unequip() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(Object inputs) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getEquipmentBar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addToInventory(Character target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFromInventory() {
		// TODO Auto-generated method stub
		return false;
	}

}
