package com.cycapservers.game;

public class ParticleEmitter {
	
	private Particle template;
	private double particles_per_ms;
	private Position spawn_point;
	private long life_span;
	
	private long start_time;
	private long last_update_time;
	
	private int have_spawned; //the number of particles that have been spawned
	
	/**
	 * returns true if it is still going, otherwise it returns false
	 * @return
	 */
	public boolean update() {
		long elapsed_time = System.currentTimeMillis() - start_time;
		if(elapsed_time >= life_span) return false;
		
		int should_be_spawned = (int) (particles_per_ms * elapsed_time);
		for(int i = have_spawned; i <= should_be_spawned; i++) {
			//spawn new particle
			have_spawned++;
		}
		
		last_update_time = System.currentTimeMillis();
		return true;
	}

}
