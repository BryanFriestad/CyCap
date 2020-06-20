package com.cycapservers.game;

public class AmmoPack extends PowerUp {
	
	public AmmoPack(int x, int y, int w, int h, int r, double a, String entity_id) {
		super(5, 0, x, y, w, h, r, a, "Ammo Pack", 0, entity_id);
	}
	
	public AmmoPack(AmmoPack ap, int x, int y, String entity_id) {
		super(ap, x, y, entity_id);
	}

	@Override
	public boolean update() {
		if(this.started && ((System.currentTimeMillis() - this.startTime) > this.duration)){
			return true;
		}
		return false;
	}

	@Override
	public boolean use() {
		if(this.grabber == null) {
			throw new IllegalStateException("Error, this powerup has no grabber and cannot be used");
		}
		if(!this.started){
			if(this.grabber.equipment1 != null) {
				this.grabber.equipment1.extra_ammo = this.grabber.equipment1.max_ammo_refill;
			}
			if(this.grabber.equipment2 != null) {
				this.grabber.equipment2.extra_ammo = this.grabber.equipment2.max_ammo_refill;
			}
			if(this.grabber.equipment3 != null) {
				this.grabber.equipment3.extra_ammo = this.grabber.equipment3.max_ammo_refill;
			}
			if(this.grabber.equipment4 != null) {
				this.grabber.equipment4.extra_ammo = this.grabber.equipment4.max_ammo_refill;
			}
			this.startTime = System.currentTimeMillis();
			this.started = true;
			return true;
		}
		return false;
	}

}
