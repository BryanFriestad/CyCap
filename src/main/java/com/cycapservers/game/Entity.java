package com.cycapservers.game;

public class Entity {
	
	private String entity_id;
	
	protected int imageId;
	
	protected int spriteIndex;
	
	protected double x;
	
	protected double y;
	
	private double drawWidth;
	
	private double drawHeight;
	
	protected double collision_radius;
	
	protected double rotation; //in radians
	
	protected double alpha;
	
	public Entity(int id, int sprIdx, double x, double y, double w, double h, double r, double a, String entity_id){
		this.imageId = id;
		this.spriteIndex = sprIdx;
		this.x = x;
		this.y = y;
		this.drawWidth = w;
		this.drawHeight = h;
		this.rotation = r;
		this.alpha = a;
		this.set_entity_id(entity_id);
		updateCollision_radius();
	}
	
	public String toDataString(String client_id){
		String output = "";
		output += get_entity_id() + ",";
		output += imageId + ",";
		output += spriteIndex + ",";
		output += Utils.roundToSpecifiedPlace(x, 2) + ",";
		output += Utils.roundToSpecifiedPlace(y, 2) + ",";
		output += Utils.roundToSpecifiedPlace(drawWidth, 2) + ",";
		output += Utils.roundToSpecifiedPlace(drawHeight, 2) + ",";
		output += Utils.roundToSpecifiedPlace(rotation, 3) + ",";
		output += Utils.roundToSpecifiedPlace(alpha, 3);
		return output;
	}

	public String get_entity_id() {
		return entity_id;
	}

	public void set_entity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getSpriteIndex() {
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDrawWidth() {
		return drawWidth;
	}

	public void setDrawWidth(double drawWidth) {
		this.drawWidth = drawWidth;
		updateCollision_radius();
	}

	public double getDrawHeight() {
		return drawHeight;
	}

	public void setDrawHeight(double drawHeight) {
		this.drawHeight = drawHeight;
		updateCollision_radius();
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getCollision_radius() {
		return collision_radius;
	}
	
	public void updateCollision_radius() {
		collision_radius = Utils.distanceBetween(x, y, x + drawWidth/2, y + drawHeight/2);
	}
}
