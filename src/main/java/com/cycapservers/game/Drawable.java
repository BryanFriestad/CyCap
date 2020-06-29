package com.cycapservers.game;

import com.cycapservers.JSONObject;
import com.cycapservers.JSON_Stringable;

/**
 * A class to represent a drawble image, which includes Image data and a transform specifying where to draw the object.
 * @author Bryan Friestad
 *
 */
public class Drawable implements JSON_Stringable {
	
	private Image image;
	private int spriteIndex;
	
	//TODO: position, width. height, rotation and alpha could be combined into a "DrawTransform", which when combined with a time unit, could become a "DrawKeyframe"
	private Position drawPosition;
	private double drawHeight;
	private double drawWidth;
	private double rotation; //in radians
	private double alpha;
	
	public Drawable(Image image, int spriteIndex, Position drawPosition, double drawHeight, double drawWidth, double rot, double a) {
		if(spriteIndex < 0 || spriteIndex >= image.getSpritesLength()){
			throw new IllegalArgumentException("spriteIndex(" + spriteIndex + ") is invalid");
		}
		if(drawHeight < 0){
			throw new IllegalArgumentException("drawHeight(" + drawHeight + ") must be positive or 0");
		}
		if(drawWidth < 0){
			throw new IllegalArgumentException("drawWidth(" + drawWidth + ") must be positive or 0");
		}
		if(a < 0.0 || a > 1.0){
			throw new IllegalArgumentException("alpha value(" + a + ") must be between 0.0 and 1.0");
		}
		this.image = image;
		this.spriteIndex = spriteIndex;
		this.drawPosition = drawPosition;
		this.drawHeight = drawHeight;
		this.drawWidth = drawWidth;
		this.rotation = rot;
		this.alpha = a;
	}

	/**
	 * Creates a new drawable with no rotation and 100% alpha
	 * @param image
	 * @param spriteIndex
	 * @param drawPosition
	 * @param drawHeight
	 * @param drawWidth
	 */
	public Drawable(Image image, int spriteIndex, Position drawPosition, double drawHeight, double drawWidth) {
		if(spriteIndex < 0 || spriteIndex >= image.getSpritesLength()){
			throw new IllegalArgumentException("spriteIndex(" + spriteIndex + ") is invalid");
		}
		if(drawHeight < 0){
			throw new IllegalArgumentException("drawHeight(" + drawHeight + ") must be positive or 0");
		}
		if(drawWidth < 0){
			throw new IllegalArgumentException("drawWidth(" + drawWidth + ") must be positive or 0");
		}
		this.image = image;
		this.spriteIndex = spriteIndex;
		this.drawPosition = drawPosition;
		this.drawHeight = drawHeight;
		this.drawWidth = drawWidth;
		this.rotation = 0;
		this.alpha = 1.0;
	}

	/**
	 * Creates a new drawable with no rotation, 100% alpha, and one grid of draw height and width
	 * @param image
	 * @param spriteIndex
	 * @param drawPosition
	 */
	public Drawable(Image image, int spriteIndex, Position drawPosition) {
		if(spriteIndex < 0 || spriteIndex >= image.getSpritesLength()){
			throw new IllegalArgumentException("spriteIndex(" + spriteIndex + ") is invalid");
		}
		this.image = image;
		this.spriteIndex = spriteIndex;
		this.drawPosition = drawPosition;
		this.drawHeight = Utils.GRID_LENGTH;
		this.drawWidth = Utils.GRID_LENGTH;
		this.rotation = 0;
		this.alpha = 1.0;
	}
	
	/**
	 * 
	 * @return Whether or not the drawable should be drawn. True = draw.
	 */
	public boolean update(){
		return true;
	}

	@Override
	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("img", this.image);
		obj.put("sprIdx", spriteIndex);
		obj.put("drawPos", this.drawPosition);
		obj.put("drawH", drawHeight);
		obj.put("drawW", drawWidth);
		obj.put("rotation", rotation);
		obj.put("alpha", alpha);
		return obj.toString();
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getSpriteIndex() {
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}

	public Position getDrawPosition() {
		return drawPosition;
	}

	public void setDrawPosition(Position drawPosition) {
		this.drawPosition = drawPosition;
	}

	public double getDrawHeight() {
		return drawHeight;
	}

	public void setDrawHeight(double drawHeight) {
		this.drawHeight = drawHeight;
	}

	public double getDrawWidth() {
		return drawWidth;
	}

	public void setDrawWidth(double drawWidth) {
		this.drawWidth = drawWidth;
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
	
	public Drawable clone() {
		return new Drawable(image.clone(), spriteIndex, drawPosition.clone(), drawHeight, drawWidth, rotation, alpha);
	}

}
