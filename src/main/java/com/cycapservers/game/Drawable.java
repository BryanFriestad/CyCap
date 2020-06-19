package com.cycapservers.game;

import com.cycapservers.JSON_Stringable;

public class Drawable implements JSON_Stringable {
	
	private Image image;
	private int spriteIndex;
	private Position drawPosition;
	private double drawHeight;
	private double drawWidth;
	private double rotation; //in radians
	private double alpha;

	public Drawable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

}
