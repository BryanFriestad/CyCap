package com.cycapservers.game.components.drawing;

import com.cycapservers.game.entities.Entity;

public class ParticleComponent extends AnimatedDrawingComponent 
{
	//units are in pixels/millisecond
	private double dx;
	private double dy;
	private double dw;
	private double dh;
	private double dr;
	private double da;
	
	public ParticleComponent(Image image, int spriteIndex, double drawHeight, double drawWidth, double rot, double a,
			int animation_frames, long ani_len, boolean do_loop, boolean show_while_paused,
			double dx, double dy, double dw, double dh, double dr, double da) 
	{
		super(image, spriteIndex, drawHeight, drawWidth, rot, a, animation_frames, ani_len, do_loop, show_while_paused);
		this.dx = dx;
		this.dy = dy;
		this.dw = dw;
		this.dh = dh;
		this.dr = dr;
		this.da = da;
	}
	
	public ParticleComponent(Image image, int spriteIndex, double drawHeight, double drawWidth, double rot, double a,
			long lifetime, double dx, double dy, double dw, double dh, double dr, double da) 
	{
		super(image, spriteIndex, drawHeight, drawWidth, rot, a, 1, lifetime, false, false);
		this.dx = dx;
		this.dy = dy;
		this.dw = dw;
		this.dh = dh;
		this.dr = dr;
		this.da = da;
	}

	/**
	 * updates the particle
	 * @return returns false if the particle is dead and should be removed
	 */
	public boolean update(Entity e) 
	{
		if (!super.update(e)) return false;
		
		this.getDrawPosition().setX(this.getDrawPosition().getX() 	+ (dx * this.time_elapsed));
		this.getDrawPosition().setY(this.getDrawPosition().getY() 	+ (dy * this.time_elapsed));
		this.setDrawWidth(this.getDrawWidth() 						+ (dw * this.time_elapsed));
		this.setDrawHeight(this.getDrawHeight() 					+ (dh * this.time_elapsed));
		this.setRotation(this.getRotation() 						+ (dr * this.time_elapsed));
		this.setAlpha(this.getAlpha() 								+ (da * this.time_elapsed));
		
		return true;
	}
	
	@Override
	public ParticleComponent clone() 
	{
		return new ParticleComponent(getImage().clone(), getSpriteIndex(), getDrawHeight(), getDrawWidth(), getRotation(), getAlpha(), 
									 getNumber_of_sprites(), getAnimation_length(), isLooping(), isShow_while_not_running(), 
									 dx, dy, dw, dh, dr, da);
	}

	public double getDx() 
	{
		return dx;
	}

	public void setDx(double dx) 
	{
		this.dx = dx;
	}

	public double getDy() 
	{
		return dy;
	}

	public void setDy(double dy) 
	{
		this.dy = dy;
	}

	public double getDw() 
	{
		return dw;
	}

	public void setDw(double dw) 
	{
		this.dw = dw;
	}

	public double getDh() 
	{
		return dh;
	}

	public void setDh(double dh) 
	{
		this.dh = dh;
	}

	public double getDr() 
	{
		return dr;
	}

	public void setDr(double dr) 
	{
		this.dr = dr;
	}

	public double getDa() 
	{
		return da;
	}

	public void setDa(double da) 
	{
		this.da = da;
	}
	
}