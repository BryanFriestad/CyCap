package com.cycapservers.game.components.positioning;

import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.ComponentMessageId;

public class HomebasePositionComponent extends PositionComponent 
{
	private double home_x;
	private double home_y;
	
	public HomebasePositionComponent(double home_x, double home_y)
	{
		super(home_x, home_y);
		this.home_x = home_x;
		this.home_y = home_y;
	}
	
	public boolean IsAtHome()
	{
		return (getX() == home_x) && (getY() == home_y);
	}
	
	private void ReturnToHome()
	{
		setX(home_x);
		setY(home_y);
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		switch(message.getMessage_id())
		{
		
		case DRAWING_POSITION_CHANGE_DELTA:
			PositionComponent drawing_delta = (PositionComponent) message.getData();
			setX(getX() + drawing_delta.getX());
			setY(getY() + drawing_delta.getY());
			parent.Send(new ComponentMessage(ComponentMessageId.POSITIONING_UPDATE, this));
			break;
			
		case SPEED_POSITION_CHANGE_DELTA:
			PositionComponent speed_delta = (PositionComponent) message.getData();
			setX(getX() + speed_delta.getX());
			setY(getY() + speed_delta.getY());
			parent.Send(new ComponentMessage(ComponentMessageId.POSITIONING_UPDATE, this));
			break;
			
		case COLLISION_CORRECT_POSITION:
			PositionComponent p = (PositionComponent) message.getData();
			setX(p.getX());
			setY(p.getY());
			parent.Send(new ComponentMessage(ComponentMessageId.POSITIONING_UPDATE, this));
			break;
			
		default:
			break;
		
		}
	}
}
