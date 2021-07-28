package com.cycapservers.game.equipment;

/**
 * A behavior which describes when equipment is allowed to fire.
 * @author Bryan Friestad
 *
 */
public abstract class ReloadBehavior
{	
	public abstract void OnReloadDown();
	public abstract void OnReloadUp();
	public abstract boolean CanFire();
	public abstract void Refill();
	public abstract double GetEquipmentBar();
	public abstract boolean Update(long delta_t);
}
