package com.cycapservers.game;

public class SmokeGrenade extends FallingProjectile {
	//lifespan = 1sec
	//range = 1.5 grid spaces
	
	/**
	 * the amount of time after the grenade hits the ground to emit smoke particles
	 */
	private int smoke_time;
	/**
	 * the amount of new smoke particles to make every update
	 */
	private int smoke_intensity;
	private Particle smoke_part;
	
	private boolean smoke_started;
	private ParticleEmitter emitter;
	private Position particle_src;
	
	public SmokeGrenade(Drawable model, Collider c, int collision_priority, Position source, Position destination,
			int damage, double wall_damage_mult, String ownerId, int team, ProjectileWeapon firingWeapon, long lifeSpan,
			double max_height_scale, int smoke_time, int smoke_intensity, Particle smoke_part) {
		super(model, c, collision_priority, source, destination, damage, wall_damage_mult, ownerId, team, firingWeapon,
				lifeSpan, max_height_scale);
		this.smoke_time = smoke_time;
		this.smoke_intensity = smoke_intensity;
		this.smoke_started = false;
		this.emitter = null;
		this.particle_src = destination;
		this.smoke_part = smoke_part;
	}

	@Override
	public boolean update() {
		if(!super.update()) {
			smoke_started = true;
		}
		
		if(smoke_started){
			if(emitter == null){
				emitter = new ParticleEmitter(shotFrom.getOwner().getGame(), smoke_part, smoke_intensity, particle_src, 1.5 * Utils.GRID_LENGTH, smoke_time, 
						Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0.007, 0.019, //h0 min, h0 max, dh min, dh max
						Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0.007, 0.019, //w0 min, w0 max, dw min, dw max
						0, 2 * Math.PI, -0.025, 0.025, //r0 min, r0 max, dr min, dr max
						1.0, 1.0, -0.0007, -0.0013, //a0 min, a0 max, da min, da max
						-0.006, 0.006, -0.006, 0.006); //dx min, dx max, dy min, dx max
			}
			else{
				return emitter.update();
			}
		}
		
		return true;
	}
	
}