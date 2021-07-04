package com.cycapservers.game.components;

public class SpeedComponent extends Component 
{
	double current_speed;
	//List<SpeedBuffs> active_buffs;
	
	public SpeedComponent(double normal_speed)
	{
		super("speed");
		current_speed = normal_speed;
	}
	
	@Override
	public void Receive(ComponentMessage message) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean Update(long delta_t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object GetJSONValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public double getCurrentSpeed()
	{
		return current_speed;
	}

}
