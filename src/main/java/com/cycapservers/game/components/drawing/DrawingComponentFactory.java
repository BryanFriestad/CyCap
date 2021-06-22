package com.cycapservers.game.components.drawing;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.drawing.Image;

public class DrawingComponentFactory
{
	private static DrawingComponentFactory instance = new DrawingComponentFactory();
	
	private DrawingComponentFactory(){}
	
	public static DrawingComponentFactory getInstance()
	{
		return instance;
	}

	public DrawingComponent BuildPlayerDrawingComponent(Team team)
	{
		Image i = new Image("static/res/player_skins.png", -1,  128, 128, 8, 8);
		int index;
		switch (team)
		{
		case Blue:
			index = 0;
			break;
			
		case Green:
			index = 1;
			break;
			
		case LightSkyBlue:
			index = 7;
			break;
			
		case None:
			throw new IllegalArgumentException();
			
		case Orange:
			index = 4;
			break;
			
		case Pink:
			index = 3;
			break;
			
		case Purple:
			index = 2;
			break;
			
		case Red:
			index = 5;
			break;
			
		case Yellow:
			index = 6;
			break;
			
		default:
			throw new IllegalArgumentException();
		}
		return new DrawingComponent(i, index);
	}

}
