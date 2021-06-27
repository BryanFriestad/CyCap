package com.cycapservers.game.components;

public class ComponentMessage 
{
	private ComponentMessageId message_id;
	private Object data;
	
	public ComponentMessage(ComponentMessageId message_id, Object data) 
	{
		this.message_id = message_id;
		this.data = data;
	}

	public ComponentMessageId getMessage_id() 
	{
		return message_id;
	}

	public Object getData() 
	{
		return data;
	}
}
