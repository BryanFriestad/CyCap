package com.cycapservers.game;

public class Buff {
	
	private double mult_factor;
	private BuffType type;
	private long duration;
	
	private long start_time;
	
	/**
	 * Creates a buff
	 * @param percent Must be greater than or equal to -1.0
	 * @param type
	 * @param duration The amount of time in ms for the buff to last before being removed
	 */
	public Buff(double percent, BuffType type, long duration) {
		super();
		if(percent < -1.0) {
			throw new IllegalArgumentException("percent(" + percent + ") is less than -1.0");
		}
		this.mult_factor = 1.0 + percent;
		this.type = type;
		this.duration = duration;
		start_time = System.currentTimeMillis();
	}

	public boolean isCompleted() {
		return (System.currentTimeMillis() - start_time > duration);
	}
	
	public BuffType getType() {
		return type;
	}

	public double getMult_factor() {
		return mult_factor;
	}
	
}
