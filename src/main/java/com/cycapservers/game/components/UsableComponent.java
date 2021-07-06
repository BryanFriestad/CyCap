package com.cycapservers.game.components;

import org.json.JSONObject;

public class UsableComponent extends Component 
{
	private int max_uses;
	private int uses_left;
	/**
	 * In ms.
	 */
	private long duration;
	/**
	 * In ms.
	 */
	private long last_activate_time;
	
	public UsableComponent(int max_uses, long duration)
	{
		super("usable");
		this.max_uses = max_uses;
		this.uses_left = max_uses;
		this.duration = duration;
		this.last_activate_time = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return Returns true if the item is exhausted.
	 */
	public boolean Use()
	{
		return false;
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean Update(long delta_t) 
	{
		return (uses_left > 0) || ((System.currentTimeMillis() - last_activate_time) < duration);
	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject obj = new JSONObject();
		obj.put("uses_left", uses_left);
		obj.put("max_uses", max_uses);
		return obj;
	}

}
