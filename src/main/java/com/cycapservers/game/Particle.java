package com.cycapservers.game;

public class Particle extends AnimatedDrawable {

	//units are in pixels/millisecond
	private double dx;
	private double dy;
	private double dw;
	private double dh;
	private double dr;
	private double da;
	
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
	
	@Override
	public Particle clone() {
		return new Particle(getImage().clone(), getSpriteIndex(), getDrawPosition(), getDrawHeight(), getDrawWidth(), getRotation(), getAlpha(),
	getNumber_of_sprites(), getAnimation_length(), isLooping(), isShow_while_not_running(), dx, dy, dw, dh, dr, da);
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getDw() {
		return dw;
	}

	public void setDw(double dw) {
		this.dw = dw;
	}

	public double getDh() {
		return dh;
	}

	public void setDh(double dh) {
		this.dh = dh;
	}

	public double getDr() {
		return dr;
	}

	public void setDr(double dr) {
		this.dr = dr;
	}

	public double getDa() {
		return da;
	}

	public void setDa(double da) {
		this.da = da;
	}
	
}