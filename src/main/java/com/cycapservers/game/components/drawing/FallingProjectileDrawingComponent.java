package com.cycapservers.game.components.drawing;

import com.cycapservers.game.Team;
import com.cycapservers.game.Utils;
import com.cycapservers.game.components.positioning.PositionComponent;

/**
 * A bullet which is launched into the air and falls to the ground.
 * It scales up its draw height and width as it gains altitude and then shrinks as it falls.
 * @author Bryan Friestad
 *
 */
public class FallingProjectileDrawingComponent extends DrawingComponent 
{	
	//internal
	private double initial_v;
	private double scale_per_height;
	private double initial_draw_height;
	private double initial_draw_width;
	private long time_of_creation;
	
	public FallingProjectileDrawingComponent(Image image, int spriteIndex, double drawHeight, double drawWidth, double rot, double a, PositionComponent source, 
							 PositionComponent destination, int damage, double wall_damage_mult, String ownerId, Team team, 
							 long lifeSpan, double max_height_scale) 
	{
		super(image, spriteIndex, drawHeight, drawWidth, rot, a);
		initial_v = -Utils.GRAVITY / 2.0 * lifeSpan; //initial velocity to get height = 0 at time = lifeSpan
		double max_height = ((-Utils.GRAVITY / 2.0) * (lifeSpan / 2.0) * (lifeSpan / 2.0)) + (initial_v * (lifeSpan / 2.0));
		scale_per_height = (max_height_scale - this.getDrawWidth()) / (max_height);
		initial_draw_height = this.getDrawHeight();
		initial_draw_width = this.getDrawWidth();
		this.time_of_creation = System.currentTimeMillis();
	}
	
	/**
	 * Calls Projectile.update() and then performs scaling calculations based on current "height"
	 */
	@Override
	public boolean Update(long delta_t) 
	{	
		long elapsed_life_time = System.currentTimeMillis() - time_of_creation;
		double current_scale = scale_per_height * (((-Utils.GRAVITY / 2.0) * (elapsed_life_time) * (elapsed_life_time)) + (initial_v * (elapsed_life_time)));
		setDrawHeight(initial_draw_height * current_scale);
		setDrawWidth(initial_draw_width * current_scale);
		
		return true;
	}
	
}
