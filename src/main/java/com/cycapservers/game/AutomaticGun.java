package com.cycapservers.game;

/**
 * A type of projectile weapon that fires as long as you
 * are holding down the fire (mouse) button.
 * @author Bryan Friestad
 *
 */
public class AutomaticGun extends ProjectileWeapon {
	
	public AutomaticGun(String name, long switchCooldown, Drawable icon, long fire_rate, long reload_time,
			int max_reloads, int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
			Sound cannot_reload_sound, Projectile template, int mag_size) {
		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound, cannot_fire_sound,
				reload_sound, cannot_reload_sound, template, mag_size);
	}

	@Override
	public void update(ClientInputHandler input_handler) {
		super.update(input_handler);
		
		//if reloading and current_time - last reload time >= reload time
		
		if(isEquipped()){
			if(input_handler.isDown(InputAction.SHOOT)){
				//check if there is ammo
				//check if fire_time has passed since last shot
				//check if you are reloading
			}
			if(input_handler.isPressedAndReleased(InputAction.RELOAD)){
				
			}
		}
	}

	@Override
	public Equipment clone() {
		throw new UnsupportedOperationException();
	}
}