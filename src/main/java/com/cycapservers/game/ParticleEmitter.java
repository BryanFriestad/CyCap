package com.cycapservers.game;

import com.cycapservers.game.components.drawing.ParticleComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.DrawableEntity;
import com.cycapservers.game.matchmaking.Game;

public class ParticleEmitter {
	
	//params
	/**
	 * The game which to spawn particles into
	 */
	private Game game;
	
	private ParticleComponent template;
	private double particles_per_ms;
	private PositionComponent spawn_point;
	private double spawn_range;
	private long life_span;
	
	//internal
	private long start_time;
	
	private int have_spawned; //the number of particles that have been spawned
	private ParticleEmitterRandomizer randomizer;
	
	public ParticleEmitter(Game game, ParticleComponent template, double particles_per_ms, PositionComponent spawn_point, double spawn_range, long life_span,
			double h0_min, double h0_max, double dh_min, double dh_max,
			double w0_min, double w0_max, double dw_min, double dw_max,
			double r0_min, double r0_max, double dr_min, double dr_max,
			double a0_min, double a0_max, double da_min, double da_max,
			double dx_min, double dx_max, double dy_min, double dy_max) {
		super();
		this.game = game;
		this.template = template;
		this.particles_per_ms = particles_per_ms;
		this.spawn_point = spawn_point;
		this.spawn_range = spawn_range;
		this.life_span = life_span;
		this.start_time = System.currentTimeMillis();
		this.have_spawned = 0;
		this.randomizer = new ParticleEmitterRandomizer(h0_min, h0_max, dh_min, dh_max, 
														w0_min, w0_max, dw_min, dw_max, 
														r0_min, r0_max, dr_min, dr_max, 
														a0_min, a0_max, da_min, da_max, 
														dx_min, dx_max, dy_min, dy_max);
	}

	/**
	 * returns true if it is still going, otherwise it returns false
	 * @return
	 */
	public boolean update() 
	{
		long elapsed_time = System.currentTimeMillis() - start_time;
		if (elapsed_time >= life_span) return false;
		
		int should_be_spawned = (int) (particles_per_ms * elapsed_time);
		for (int i = have_spawned; i <= should_be_spawned; i++) 
		{
			ParticleComponent p = template.clone();
			p.setDrawHeight(randomizer.getStartingHeight()); //give the particle a random height
			p.setDrawWidth(randomizer.getStartingWidth()); //give the particle a random width
			p.setRotation(randomizer.getStartingRotation()); //give the particle a random rotation
			p.setAlpha(randomizer.getStartingAlpha()); //random starting alpha
			p.setDx(randomizer.getDeltaX()); 
			p.setDy(randomizer.getDeltaY()); //give the particle a random dx, dy
			p.setDh(randomizer.getDeltaHeight()); //give the particle a random dh
			p.setDw(randomizer.getDeltaWidth()); //give the particle a random dw
			p.setDr(randomizer.getDeltaRotation()); //give the particle a random dr
			p.setDa(randomizer.getDeltaAlpha()); //give the particle a random da
			game.addEntity(new DrawableEntity(Utils.getRandomPositionInCircle(spawn_point, spawn_range), p), false);
			have_spawned++;
		}
		
		return true;
	}
	
	private class ParticleEmitterRandomizer
	{
		
		private double h0_min,   h0_max,   dh_min,   dh_max, 
		w0_min,   w0_max,   dw_min,   dw_max,
		r0_min,   r0_max,   dr_min,   dr_max,
		a0_min,   a0_max,   da_min,   da_max,
		dx_min,   dx_max,   dy_min,   dy_max;
		
		protected ParticleEmitterRandomizer(double h0_min, double h0_max, double dh_min, double dh_max, double w0_min,
			double w0_max, double dw_min, double dw_max, double r0_min, double r0_max, double dr_min, double dr_max,
			double a0_min, double a0_max, double da_min, double da_max, double dx_min, double dx_max, double dy_min,
			double dy_max) 
		{
			super();
			this.h0_min = h0_min;
			this.h0_max = h0_max;
			this.dh_min = dh_min;
			this.dh_max = dh_max;
			this.w0_min = w0_min;
			this.w0_max = w0_max;
			this.dw_min = dw_min;
			this.dw_max = dw_max;
			this.r0_min = r0_min;
			this.r0_max = r0_max;
			this.dr_min = dr_min;
			this.dr_max = dr_max;
			this.a0_min = a0_min;
			this.a0_max = a0_max;
			this.da_min = da_min;
			this.da_max = da_max;
			this.dx_min = dx_min;
			this.dx_max = dx_max;
			this.dy_min = dy_min;
			this.dy_max = dy_max;
		}
		
		protected double getStartingHeight()
		{
			return (Utils.RANDOM.nextDouble() * (h0_max - h0_min) + h0_min);
		}
		
		protected double getDeltaHeight()
		{
			return (Utils.RANDOM.nextDouble() * (dh_max - dh_min) + dh_min);
		}
		
		protected double getStartingWidth()
		{
			return (Utils.RANDOM.nextDouble() * (w0_max - w0_min) + w0_min);
		}
		
		protected double getDeltaWidth()
		{
			return (Utils.RANDOM.nextDouble() * (dw_max - dw_min) + dw_min);
		}
		
		protected double getStartingRotation()
		{
			return (Utils.RANDOM.nextDouble() * (r0_max - r0_min) + r0_min);
		}
		
		protected double getDeltaRotation()
		{
			return (Utils.RANDOM.nextDouble() * (dr_max - dr_min) + dr_min);
		}
		
		protected double getStartingAlpha()
		{
			return (Utils.RANDOM.nextDouble() * (a0_max - a0_min) + a0_min);
		}
		
		protected double getDeltaAlpha()
		{
			return (Utils.RANDOM.nextDouble() * (da_max - da_min) + da_min);
		}
		
		protected double getDeltaX()
		{
			return (Utils.RANDOM.nextDouble() * (dx_max - dx_min) + dx_min);
		}
		
		protected double getDeltaY()
		{
			return (Utils.RANDOM.nextDouble() * (dy_max - dy_min) + dy_min);
		}
		
	}

}
