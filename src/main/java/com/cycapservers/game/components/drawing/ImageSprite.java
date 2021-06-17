package com.cycapservers.game.components.drawing;

import com.cycapservers.JsonToStringObject;
import com.cycapservers.JSON_Stringable;

public class ImageSprite implements JSON_Stringable {
	
	private int x;
	private int y;
	private int w;
	private int h;

	public ImageSprite() {
		x = 0;
		y = 0;
		w = 0;
		h = 0;
	}

	public ImageSprite(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	@Override
	public String toJSONString() {
		JsonToStringObject object = new JsonToStringObject();
		object.put("class", this.getClass().getSimpleName());
		object.put("x", this.x);
		object.put("y", this.y);
		object.put("w", this.w);
		object.put("h", this.h);
		return object.toString();
	}
	
	@Override
	public ImageSprite clone() {
		return new ImageSprite(x, y, w, h);
	}

}
