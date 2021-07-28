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
	
	public DrawingComponent BuildRedWallDrawingComponent()
	{
		return new DrawingComponent(Image.GetImageBySrc("res/images/walls.png"), 0);
	}
	
	public DrawingComponent BuildGreyWallDrawingComponent()
	{
		return new DrawingComponent(Image.GetImageBySrc("res/images/walls.png"), 1);
	}

	public DrawingComponent BuildPlayerDrawingComponent(Team team)
	{
		Image i = Image.GetImageBySrc("res/images/player_skins.png");
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
	
	public DrawingComponent ManufactureAmmoPackDrawingComponent()
	{
		return new DrawingComponent(Image.GetImageBySrc("res/images/ammo_box.png"), 0);
	}
	
	public DrawingComponent ManufactureHealthPackDrawingComponent()
	{
		return new DrawingComponent(Image.GetImageBySrc("res/images/health_pack.png"), 0);
	}
	
	public DrawingComponent ManufactureSpeedPotionDrawingComponent()
	{
		return new DrawingComponent(Image.GetImageBySrc("res/images/speed_potion.png"), 0);
	}
	
	public DrawingComponent ManufactureFlagDrawingComponent(Team team)
	{
		Image i = Image.GetImageBySrc("res/images/flags.png");
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
	
	public DrawingComponent ManufactureProjectileDrawingComponent()
	{
		return new DrawingComponent(Image.GetImageBySrc("res/images/bullets.png"), 1);
	}

}
