package com.cycapservers.game.components;

import org.json.JSONObject;

/**
 * A component which deletes its entity after a specified time.
 * @author btrf_
 *
 */
public class LifespanComponent extends Component 
{
	/**
	 * In milliseconds.
	 */
	private long time_of_creation;
	/**
	 * In milliseconds.
	 */
	private long total_lifespan;
	
	/**
	 * 
	 * @param lifespan In ms.
	 */
	public LifespanComponent(long lifespan)
	{
		super("lifespan");
		this.time_of_creation = System.currentTimeMillis();
		this.total_lifespan = lifespan;
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		
	}

	@Override
	public boolean Update(long delta_t) 
	{
		long elapsed_time = System.currentTimeMillis() - time_of_creation;
		return (elapsed_time < total_lifespan);
	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject obj = new JSONObject();
		double lifetime_left = (time_of_creation + total_lifespan) - System.currentTimeMillis();
		obj.put("percent_left", lifetime_left / (double) total_lifespan);
		return obj;
	}

}
