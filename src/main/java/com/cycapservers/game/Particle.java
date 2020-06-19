package com.cycapservers.game;

public class Particle extends AnimatedDrawable {

	//units are in pixels/millisecond
	protected double dx;
	protected double dy;
	protected double dw;
	protected double dh;
	protected double dr;
	protected double da;
	
	/**
	 * Creates a drawable which animates through its sprites and has dynamic position, draw dimensions, rotation and alpha
	 * @param image
	 * @param spriteIndex
	 * @param drawPosition
	 * @param drawHeight
	 * @param drawWidth
	 * @param rot
	 * @param a
	 * @param animation_frames
	 * @param ani_len
	 * @param do_loop
	 * @param show_while_paused
	 * @param dx
	 * @param dy
	 * @param dw
	 * @param dh
	 * @param dr
	 * @param da
	 */
	public Particle(Image image, int spriteIndex, Position drawPosition, double drawHeight, double drawWidth, double rot, double a,
			int animation_frames, long ani_len, boolean do_loop, boolean show_while_paused,
			double dx, double dy, double dw, double dh, double dr, double da) {
		super(image, spriteIndex, drawPosition, drawHeight, drawWidth, rot, a, animation_frames, ani_len, do_loop, show_while_paused);
		this.dx = dx;
		this.dy = dy;
		this.dw = dw;
		this.dh = dh;
		this.dr = dr;
		this.da = da;
	}
	
	public Particle(Image image, int spriteIndex, Position drawPosition, double drawHeight, double drawWidth, double rot, double a,
			long lifetime, double dx, double dy, double dw, double dh, double dr, double da) {
		super(image, spriteIndex, drawPosition, drawHeight, drawWidth, rot, a, 1, lifetime, false, false);
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
	public boolean update() {
		if(!super.update()) return false;
		
		this.getDrawPosition().setX(this.getDrawPosition().getX() 	+ (dx * this.time_elapsed));
		this.getDrawPosition().setY(this.getDrawPosition().getY() 	+ (dy * this.time_elapsed));
		this.setDrawWidth(this.getDrawWidth() 						+ (dw * this.time_elapsed));
		this.setDrawHeight(this.getDrawHeight() 					+ (dh * this.time_elapsed));
		this.setRotation(this.getRotation() 						+ (dr * this.time_elapsed));
		this.setAlpha(this.getAlpha() 								+ (da * this.time_elapsed));
		
		return true;
	}
}