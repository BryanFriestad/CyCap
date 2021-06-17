package com.cycapservers.game.components;

public class ComponentMessage 
{
	private ComponentMessageId message_id;
	private String message;
	
	public ComponentMessage(ComponentMessageId message_id, String message) 
	{
		this.message_id = message_id;
		this.message = message;
	}

	public ComponentMessageId getMessage_id() 
	{
		return message_id;
	}

	public String getMessage() 
	{
		return message;
	}
}
