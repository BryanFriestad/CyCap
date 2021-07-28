package com.cycapservers.game.equipment;

public class AmmoReloadBehavior extends ReloadBehavior 
{
	private int max_ammo;
	private int current_ammo;
	private long reload_time;
	private long last_reload_start_time;
	private boolean is_reloading;
	
	/**
	 * 
	 * @param max_ammo
	 * @param reload_time In ms.
	 */
	public AmmoReloadBehavior(int max_ammo, long reload_time)
	{
		this.max_ammo = max_ammo;
		this.current_ammo = this.max_ammo;
		this.reload_time = reload_time;
		this.last_reload_start_time = System.currentTimeMillis();
		this.is_reloading = false;
	}
	
	@Override
	public void OnReloadDown() 
	{
//		System.out.println("Reload down in behavior");
		if (!is_reloading)
		{
			is_reloading = true;
			this.last_reload_start_time = System.currentTimeMillis();
		}
	}

	@Override
	public void OnReloadUp() 
	{
//		System.out.println("Reload up in behavior");
		return;
	}

	@Override
	public boolean CanFire() 
	{
		if (is_reloading) return false;
		
		if (current_ammo > 0)
		{
			current_ammo--;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void Refill() 
	{
		this.current_ammo = this.max_ammo;
	}

	@Override
	public double GetEquipmentBar() 
	{
		return (double) current_ammo / (double) max_ammo;
	}

	@Override
	public boolean Update(long delta_t) 
	{
		if (is_reloading)
		{
			long time_since_reload = System.currentTimeMillis() - this.last_reload_start_time;
			if (time_since_reload > this.reload_time)
			{
				Reload();
				is_reloading = false;
			}
		}
		return true;
	}
	
	private void Reload()
	{
		return;
	}

}
