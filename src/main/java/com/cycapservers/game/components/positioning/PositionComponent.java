package com.cycapservers.game.components.positioning;

import org.json.JSONObject;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;

public class PositionComponent extends Component
{
	private double x;
	private double y;

	public PositionComponent() 
	{
		super("position");
		x = 0;
		y = 0;
	}

	public PositionComponent(double x, double y) 
	{
		super("position");
		this.x = x;
		this.y = y;
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

	public short getClosestGridX(){
		return (short) Math.round((this.x - (Utils.GRID_LENGTH / 2.0)) / Utils.GRID_LENGTH);
	}
	
	public short getClosestGridY(){
		return (short) Math.round((this.y - (Utils.GRID_LENGTH / 2.0)) / Utils.GRID_LENGTH);
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
			x += drawing_delta.getX();
			y += drawing_delta.getY();
			parent.Send(new ComponentMessage(ComponentMessageId.POSITIONING_UPDATE, this));
			break;
			
		case SPEED_POSITION_CHANGE_DELTA:
			PositionComponent speed_delta = (PositionComponent) message.getData();
			x += speed_delta.getX();
			y += speed_delta.getY();
			parent.Send(new ComponentMessage(ComponentMessageId.POSITIONING_UPDATE, this));
			break;
			
		case COLLISION_CORRECT_POSITION:
			PositionComponent p = (PositionComponent) message.getData();
			x = p.getX();
			y = p.getY();
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
}
