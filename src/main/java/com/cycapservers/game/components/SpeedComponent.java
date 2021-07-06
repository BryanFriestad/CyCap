package com.cycapservers.game.components;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.positioning.PositionComponent;

public class SpeedComponent extends Component 
{
	/**
	 * The speed of this entity in grid spaces per ms.
	 */
	private double current_speed;
	/**
	 * The unit vector direction of this entity.
	 */
	private PositionComponent direction;
	
	//List<SpeedBuffs> active_buffs;
	
	public SpeedComponent(double normal_speed, PositionComponent dir)
	{
		super("speed");
		current_speed = normal_speed;
		direction = Utils.MakeUnitVector(dir);
	}
	
	public SpeedComponent(double normal_speed)
	{
		this(normal_speed, new PositionComponent());
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		switch (message.getMessage_id())
		{
		case INPUT_DIRECTION_CHANGE:
			SetDirection((PositionComponent) message.getData());
			break;
			
		default:
			break;
		}

	}

	@Override
	public boolean Update(long delta_t) 
	{
		parent.Send(new ComponentMessage(ComponentMessageId.SPEED_POSITION_CHANGE_DELTA, Utils.GetScaleVector(direction, current_speed * Utils.GRID_LENGTH * delta_t)));
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private void SetDirection(PositionComponent dir)
	{
		direction = Utils.MakeUnitVector(dir);
	}
}
