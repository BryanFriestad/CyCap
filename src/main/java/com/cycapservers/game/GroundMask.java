package com.cycapservers.game;

public class GroundMask extends Entity {
	
	//params
	protected int total_time;
	protected int full_alpha_time;
	protected int fade_time;
	
	//internal
	protected long start_time;

	public GroundMask(String id, Drawable model, int total_time, int full_alpha_time, int fade_time, long start_time) {
		super(id, model);
		this.total_time = total_time;
		this.full_alpha_time = full_alpha_time;
		this.fade_time = fade_time;
		this.start_time = start_time;
	}

	/**
	 * 
	 * @return true if the ground mask is finished fading away
	 */
	public boolean update() {
		long runtime = (System.currentTimeMillis() - this.start_time);
		if(runtime >= this.total_time) {
			return true;
		}
		else if(runtime >= this.full_alpha_time) {
			this.alpha = 1.0 - ((runtime - this.full_alpha_time) / (double) this.fade_time);
		}
		else {
			this.alpha = 1.0;
		}
		return false;
	}
	
	
}
