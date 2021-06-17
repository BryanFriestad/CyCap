package com.cycapservers.game.entities;

import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.matchmaking.Game;

/**
 * A special kind of entity which can be collected and used.
 * @author Bryan Friestad
 *
 */
public abstract class Item extends CollidingDrawableEntity 
{
	private Game game;
	
	protected String name;
	protected Character grabber;
	protected boolean grabbed;
	
	//TODO: remove game as a parameter, and have it only be set by the game
	//flag item should override the setGame() func to only allow CTF games)
	public Item(PositionComponent p, DrawingComponent model, CollisionComponent c, Game g, String name)
	{
		super(p, c, model);
		this.game = g;
		this.name = name;
		this.grabber = null;
		this.grabbed = false;
	}

	/**
	 * 
	 * @param grabber the character that is trying to pick up this item
	 * @return
	 */
	public boolean pickUp(Character grabber) 
	{
		if (!this.grabbed) 
		{
			this.grabber = grabber;
			this.grabbed = true;
			this.grabber.setItem_slot(this);
			return true;
		}
		return false;
	}
	
	/**
	 * Use the item. Throws an IllegalStateException if grabber is null.
	 * @return boolean: returns true if the item is all used up and is to be removed from the grabber's inventory
	 */
	public abstract boolean use();
	
	public void drop() 
	{
		this.setPosition(this.grabber.getPosition());
		this.grabber = null;
		this.grabbed = false;
	}

	public Game getGame() 
	{
		return game;
	}

	public Character getGrabber() 
	{
		return grabber;
	}

	public boolean isGrabbed() 
	{
		return grabbed;
	}
	
}