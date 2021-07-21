package com.cycapservers.game.components.positioning;

import org.json.JSONObject;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;

public class PositionComponent extends Component
{
	private double prevX;
	private double prevY;
	private double x;
	private double y;

	public PositionComponent() 
	{
		super("position");
		x = 0;
		y = 0;
		prevX = x;
		prevY = y;
	}

	public PositionComponent(double x, double y) 
	{
		super("position");
		this.x = x;
		this.y = y;
		prevX = x;
		prevY = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) 
	{
		this.prevX = this.x;
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) 
	{
		this.prevY = this.y;
		this.y = y;
	}

	public short getClosestGridX(){
		return (short) Math.round((this.x - (Utils.GRID_LENGTH / 2.0)) / Utils.GRID_LENGTH);
	}
	
	public short getClosestGridY(){
		return (short) Math.round((this.y - (Utils.GRID_LENGTH / 2.0)) / Utils.GRID_LENGTH);
	}
	
	public void SetToNearestPixelPosition()
	{
		x = Math.round(x);
		y = Math.round(y);
	}
	
	public PositionComponent GetPreviousPosition()
	{
		return new PositionComponent(prevX, prevY);
	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject object = new JSONObject();
		object.put("x", this.x);
		object.put("y", this.y);
		return object;
	}
	
	@Override
	public PositionComponent clone() 
	{
		return new PositionComponent(x, y);
	}

	@Override
	public void Receive(ComponentMessage message) 
	{
		switch(message.getMessage_id())
		{
		
		case DRAWING_POSITION_CHANGE_DELTA:
			PositionComponent drawing_delta = (PositionComponent) message.getData();
			setX(x + drawing_delta.getX());
			setY(y + drawing_delta.getY());
			parent.Send(new ComponentMessage(ComponentMessageId.POSITIONING_UPDATE, this));
			break;
			
		case SPEED_POSITION_CHANGE_DELTA:
			PositionComponent speed_delta = (PositionComponent) message.getData();
			setX(x + speed_delta.getX());
			setY(y + speed_delta.getY());
			parent.Send(new ComponentMessage(ComponentMessageId.POSITIONING_UPDATE, this));
			break;
			
		case COLLISION_CORRECT_POSITION:
			PositionComponent p = (PositionComponent) message.getData();
			System.out.println("Position changed from: " + x + ", " + y + " to " + p.getX() + ", " + p.getY());
			x = p.getX();
			y = p.getY();
			// Set previous X & Y to corrected position to ensure they are valid (ie non-colliding).
			prevX = x;
			prevY = y;
			parent.Send(new ComponentMessage(ComponentMessageId.POSITIONING_UPDATE, this));
			break;
			
		default:
			break;
		
		}
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}
	
	@Override
	public boolean equals(Object o)
	{
        if (o == this) 
        {
            return true;
        }

        if (!(o instanceof PositionComponent)) 
        {
            return false;
        }

        PositionComponent p = (PositionComponent) o;

        return Double.compare(x, p.x) == 0 && Double.compare(y, p.y) == 0;
	}
}
