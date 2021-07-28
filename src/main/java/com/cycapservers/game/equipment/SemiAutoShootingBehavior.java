package com.cycapservers.game.equipment;

public class SemiAutoShootingBehavior extends ShootBehavior 
{
	boolean to_fire = false;
	
	@Override
	public void OnShootDown() 
	{
//		System.out.println("shoot down in behavior");
	}

	@Override
	public void OnShootUp() 
	{
//		System.out.println("shoot up in behavior");
		to_fire = true;
	}

	@Override
	public boolean WantsToShoot() 
	{
		boolean output = to_fire;
		to_fire = false;
		return output;
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

}
