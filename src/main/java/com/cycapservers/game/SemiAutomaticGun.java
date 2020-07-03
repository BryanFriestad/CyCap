package com.cycapservers.game;

/**
 * A type of projectile weapon that fires upon press
 * and release of the fire (mouse) button.
 * @author Bryan Friestad
 *
 */
public class SemiAutomaticGun extends ProjectileWeapon{

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
	public void update(ClientInputHandler input_handler) {
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

	@Override
	public Equipment clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
