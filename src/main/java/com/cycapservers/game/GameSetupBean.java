package com.cycapservers.game;

import java.io.IOException;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cycapservers.game.components.drawing.Image;

@Component
public class GameSetupBean 
{
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		LoadGameResources();
	}
	
	public void LoadGameResources()
	{
		try 
		{
			Image.LoadGameImages();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
