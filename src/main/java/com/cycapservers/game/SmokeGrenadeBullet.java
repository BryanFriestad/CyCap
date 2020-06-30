package com.cycapservers.game;

public class SmokeGrenade extends FallingBullet {
	//lifespan = 1sec
	//range = 1.5 grid spaces
	
	/**
	 * the amount of time after the grenade hits the ground to emit smoke particles
	 */
	protected int smoke_time;
	/**
	 * the amount of new smoke particles to make every update
	 */
	protected int smoke_intensity;
	
	/**
	 * the range, in pixels, in which a player will be damaged if the conditions are correct
	 */
	protected int damage_range;
	
	@Override
	public boolean update() {
		if(!super.update()) {
			//create particle emitter
			return false;
		}
		
		return true;
	}
	
	
//	public boolean update(GameState game) {
//		double total_time = (double) (System.currentTimeMillis() - this.time_of_creation) / 1000.0;
//		if((total_time*1000) > (this.life_span + this.smoke_time)) {
//			return true;
//		}
//		else if((total_time*1000) > this.life_span){
//			this.alpha = 0.0; //make sure the bullet is invisible after it hits the ground
//			for(int i = 0; i < this.smoke_intensity; i++) {
//				double tempX = this.endX + ((Utils.RANDOM.nextDouble() * Utils.GRID_LENGTH * 5) - Utils.GRID_LENGTH * 2.5);
//				double tempY = this.endY + ((Utils.RANDOM.nextDouble() * Utils.GRID_LENGTH * 5) - Utils.GRID_LENGTH * 2.5);
//				String id = Utils.getGoodRandomString(game.usedEntityIds, game.entity_id_len);
//				game.particles.add(new Particle(this.fx_code, 0, tempX, tempY, Utils.GRID_LENGTH, Utils.GRID_LENGTH, Utils.RANDOM.nextDouble() * Math.PI * 2.0, 1.0, id, 1, 3500, false, Utils.RANDOM.nextInt(7), Utils.RANDOM.nextInt(7), 13, 13, Utils.RANDOM.nextDouble() * 0.5 - 0.25, -0.1));
//				game.usedEntityIds.add(id);
//			}
//			//TODO: add sound
//			return false;
//		}
//		this.x += (this.xRatio * game.currentDeltaTime);
//		this.y += (this.yRatio * game.currentDeltaTime);
//
//		double temp_multiplier = this.formula_m * ((-Utils.GRAVITY * total_time * total_time) + (this.v_initial * total_time)) + 1;
//		this.setDrawWidth(this.start_width * temp_multiplier);
//		this.setDrawHeight(this.start_height * temp_multiplier);
//		return false;
//	}
}