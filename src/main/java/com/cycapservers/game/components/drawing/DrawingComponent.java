package com.cycapservers.game.components.drawing;

import com.cycapservers.JsonToStringObject;
import com.cycapservers.JSON_Stringable;
import com.cycapservers.game.Utils;
import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.Entity;

/**
 * A component which allows an entity to be drawn.
 * @author Bryan Friestad
 *
 */
public class DrawingComponent implements JSON_Stringable, Component 
{
	private Object parent;
	
	private Image image;
	private int spriteIndex;
	
	//TODO: position, width. height, rotation and alpha could be combined into a "DrawTransform", which when combined with a time unit, could become a "DrawKeyframe"
	private PositionComponent drawPosition;
	private double drawHeight;
	private double drawWidth;
	private double rotation; //in radians
	private double alpha;
	
	public DrawingComponent(Image image, int spriteIndex, double drawHeight, double drawWidth, double rot, double a) 
	{
		parent = null;
		
		if (spriteIndex < 0 || spriteIndex >= image.getSpritesLength())
		{
			throw new IllegalArgumentException("spriteIndex(" + spriteIndex + ") is invalid");
		}
		
		if (drawHeight < 0)
		{
			throw new IllegalArgumentException("drawHeight(" + drawHeight + ") must be positive or 0");
		}
		
		if (drawWidth < 0)
		{
			throw new IllegalArgumentException("drawWidth(" + drawWidth + ") must be positive or 0");
		}
		
		if (a < 0.0 || a > 1.0)
		{
			throw new IllegalArgumentException("alpha value(" + a + ") must be between 0.0 and 1.0");
		}
		
		this.image = image;
		this.spriteIndex = spriteIndex;
		this.drawPosition = new PositionComponent();
		this.drawHeight = drawHeight;
		this.drawWidth = drawWidth;
		this.rotation = rot;
		this.alpha = a;
	}

	/**
	 * Creates a new drawable with no rotation and 100% alpha
	 * @param image
	 * @param spriteIndex
	 * @param drawHeight
	 * @param drawWidth
	 */
	public DrawingComponent(Image image, int spriteIndex, double drawHeight, double drawWidth) 
	{
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
		this.drawPosition = new PositionComponent();
		this.drawHeight = drawHeight;
		this.drawWidth = drawWidth;
		this.rotation = 0;
		this.alpha = 1.0;
	}

	/**
	 * Creates a new drawable with no rotation, 100% alpha, and one grid of draw height and width
	 * @param image
	 * @param spriteIndex
	 */
	public DrawingComponent(Image image, int spriteIndex) 
	{
		if (spriteIndex < 0 || spriteIndex >= image.getSpritesLength()){
			throw new IllegalArgumentException("spriteIndex(" + spriteIndex + ") is invalid");
		}
		this.image = image;
		this.spriteIndex = spriteIndex;
		this.drawPosition = new PositionComponent();
		this.drawHeight = Utils.GRID_LENGTH;
		this.drawWidth = Utils.GRID_LENGTH;
		this.rotation = 0;
		this.alpha = 1.0;
	}
	
	/**
	 * 
	 * @return Whether or not the drawable should be drawn. True = draw.
	 */
	public boolean update(Entity e)
	{
		drawPosition = e.getPosition();
		// TODO: if the height or width has changed, the collider on this Entity needs to be informed.
		return true;
	}

	@Override
	public String toJSONString() 
	{
		JsonToStringObject obj = new JsonToStringObject();
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

	public Image getImage() 
	{
		return image;
	}

	public void setImage(Image image) 
	{
		this.image = image;
	}

	public int getSpriteIndex() 
	{
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) 
	{
		this.spriteIndex = spriteIndex;
	}

	public PositionComponent getDrawPosition() 
	{
		return drawPosition;
	}

	public double getDrawHeight() 
	{
		return drawHeight;
	}
	
	public void setDrawHeight(double dh)
	{
		drawHeight = dh;
	}

	public double getDrawWidth() 
	{
		return drawWidth;
	}
	
	public void setDrawWidth(double dw)
	{
		drawWidth = dw;
	}

	public double getRotation() 
	{
		return rotation;
	}

	public void setRotation(double rotation) 
	{
		this.rotation = rotation;
	}

	public double getAlpha() 
	{
		return alpha;
	}

	public void setAlpha(double alpha) 
	{
		this.alpha = Utils.clamp(0.0, alpha, 1.0);
	}
	
	public DrawingComponent clone() 
	{
		return new DrawingComponent(image.clone(), spriteIndex, drawHeight, drawWidth, rotation, alpha);
	}

	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public Object GetParent()
	{
		return parent;
	}
	
	@Override
	public void SetParent(Object p)
	{
		parent = p;
	}

}
