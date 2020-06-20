package com.cycapservers.game;

import com.cycapservers.JSON_Stringable;

public abstract class Sound implements JSON_Stringable{
	
	private String src;
	private double volume;
	private boolean looping;
	private boolean playing;
	
	public abstract Position getPosition();
	public abstract double getVolume(Position listenPos);

}
