//package com.cycapservers.game.equipment;
//
//import com.cycapservers.game.Sound;
//import com.cycapservers.game.components.drawing.DrawingComponent;
//import com.cycapservers.game.components.input.ClientInputHandler;
//import com.cycapservers.game.components.input.InputAction;
//import com.cycapservers.game.entities.Projectile;
//
///**
// * Describes a type of gun which only reloads while you 
// * are holding down the reload button 
// * @author btrf_
// *
// */
//public class ActiveReloadGun extends ProjectileWeapon {
//
//	private double ammo_per_ms;
//
//	public ActiveReloadGun(String name, long switchCooldown, DrawingComponent icon, long fire_rate, long reload_time,
//			int max_reloads, int reloads_remaining, Sound fire_sound, Sound cannot_fire_sound, Sound reload_sound,
//			Sound cannot_reload_sound, Projectile template, int mag_size, double ammo_per_ms) {
//		super(name, switchCooldown, icon, fire_rate, reload_time, max_reloads, reloads_remaining, fire_sound,
//				cannot_fire_sound, reload_sound, cannot_reload_sound, template, mag_size);
//		this.ammo_per_ms = ammo_per_ms;
//	}
//
//	@Override
//	public void update(ClientInputHandler input_handler) {
//		super.update(input_handler);
//		
//		//if reloading and current_time - last reload time >= reload time
//		
//		if(isEquipped()){
//			if(input_handler.isPressedAndReleased(InputAction.SHOOT)){
//				//check if there is ammo
//				//check if fire_time has passed since last shot
//				//check if you are reloading
//			}
//			if(input_handler.isDown(InputAction.RELOAD)){
//				
//			}
//		}
//	}
//	
//	@Override
//	public EquippableComponent clone() {
//		throw new UnsupportedOperationException();
//	}
//
//}
