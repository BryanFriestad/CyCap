package com.cycapservers.game.components.drawing;

import org.json.JSONObject;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.positioning.PositionComponent;

/**
 * A component which allows an entity to be drawn.
 * @author Bryan Friestad
 *
 */
public class DrawingComponent extends Component 
{
	private Image image;
	private int spriteIndex;
	private double drawHeight;
	private double drawWidth;
	private double rotation; //in radians
	private double alpha;
	
	public DrawingComponent(Image image, int spriteIndex, double drawHeight, double drawWidth, double rot, double a) 
	{
		super("model");
		
		if (spriteIndex < 0 || spriteIndex >= image.GetNumberOfSprites())
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
		super("model");
		
		if(spriteIndex < 0 || spriteIndex >= image.GetNumberOfSprites()){
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
		super("model");
		
		if (spriteIndex < 0 || spriteIndex >= image.GetNumberOfSprites()){
			throw new IllegalArgumentException("spriteIndex(" + spriteIndex + ") is invalid");
		}
		this.image = image;
		this.spriteIndex = spriteIndex;
		this.drawHeight = Utils.GRID_LENGTH;
		this.drawWidth = Utils.GRID_LENGTH;
		this.rotation = 0;
		this.alpha = 1.0;
	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject obj = new JSONObject();
		obj.put("class", this.getClass().getSimpleName());
		obj.put("img", this.image.toJSONObject());
		obj.put("sprIdx", spriteIndex);
		obj.put("drawH", drawHeight);
		obj.put("drawW", drawWidth);
		obj.put("rotation", rotation);
		obj.put("alpha", alpha);
		return obj;
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
		return new DrawingComponent(image, spriteIndex, drawHeight, drawWidth, rotation, alpha);
	}

	@Override
	public void Receive(ComponentMessage message) 
	{
		
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}


}
