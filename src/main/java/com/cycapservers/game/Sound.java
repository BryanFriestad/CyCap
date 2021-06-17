package com.cycapservers.game;

import com.cycapservers.JSON_Stringable;
import com.cycapservers.game.components.positioning.PositionComponent;

public abstract class Sound implements JSON_Stringable
{
	private String src;
	private double volume;
	private boolean looping;
	private boolean playing;
	
	public abstract PositionComponent getPosition();
	public abstract double getVolume(PositionComponent listenPos);
}
