package com.cycapservers.game.equipment;

/**
 * A behvaior which describes when a piece of equipment wants to fire.
 * @author Bryan Friestad
 *
 */
public abstract class ShootBehavior 
{
	public abstract void OnShootDown();
	public abstract void OnShootUp();
	public abstract boolean WantsToShoot();
	public abstract boolean Update(long delta_t);
}
