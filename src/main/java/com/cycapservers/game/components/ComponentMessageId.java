package com.cycapservers.game.components;

/**
 * A enumerated type for messages between two components.
 * @author Bryan Friestad
 *
 */
public enum ComponentMessageId 
{
	// Leave single line comments such as these between groups of messages. Messages should be grouped by who is sending them.
	// Message groups should also have a start and an end enumerated type to avoid cyclical messaging.
	// Start and end enums should look like so: "[GROUP_NAME]_START" "[GROUP_NAME]_END". 
	
	EXTERNAL_START,
	EXTERNAL_INPUT_SNAPSHOT,
	EXTERNAL_END,

}
