package com.cycapservers.game;

public class ParticleEmitter {
	
	/**
	 * The game which to spawn particles into
	 */
	private Game game;
	
	private Particle template;
	private double particles_per_ms;
	private Position spawn_point;
	private double spawn_range;
	private long life_span;
	
	private long start_time;
	private long last_update_time;
	
	private int have_spawned; //the number of particles that have been spawned
	
	public ParticleEmitter(Game game, Particle template, double particles_per_ms, Position spawn_point, double spawn_range, long life_span) {
		super();
		this.game = game;
		this.template = template;
		this.particles_per_ms = particles_per_ms;
		this.spawn_point = spawn_point;
		this.spawn_range = spawn_range;
		this.life_span = life_span;
		this.start_time = System.currentTimeMillis();
		this.last_update_time = this.start_time;
		this.have_spawned = 0;
	}

	/**
	 * returns true if it is still going, otherwise it returns false
	 * @return
	 */
	public boolean update() {
		long elapsed_time = System.currentTimeMillis() - start_time;
		if(elapsed_time >= life_span) return false;
		
		int should_be_spawned = (int) (particles_per_ms * elapsed_time);
		for(int i = have_spawned; i <= should_be_spawned; i++) {
			Particle p = template.clone();
			//give the particle a random position within the circle
			//give the particle a random height
			//give the particle a random width
			//give the particle a random rotation
			//give the particle a random dx, dy
			//give the particle a random dh
			//give the particle a random dw
			//give the particle a random dr
			game.addEntity(new Entity(p), false);
			have_spawned++;
		}
		
		last_update_time = System.currentTimeMillis();
		return true;
	}

}
