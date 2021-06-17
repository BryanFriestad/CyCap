package com.cycapservers.game.entities;

import com.cycapservers.game.Team;
import com.cycapservers.game.components.collision.CollisionComponent;
import com.cycapservers.game.components.drawing.DrawingComponent;
import com.cycapservers.game.components.positioning.GridLockedPositionComponent;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.database.GameEventType;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.matchmaking.CaptureTheFlag;

public class Flag extends Item 
{
	protected Team team;
	protected GridLockedPositionComponent base;
	
	//internal use
	private boolean at_base;
	
	public Flag(DrawingComponent model, CollisionComponent c, CaptureTheFlag g, Team team, GridLockedPositionComponent base) 
	{
		super(base.clone(), model, c, g, team.name() + "_flag");
		this.team = team;
		this.base = base;
		at_base = true;
	}

	public boolean update() {
		if(isGrabbed()) {
			setPosition(getGrabber().getPosition());
		}
		
		return true;
	}

	@Override
	public boolean use() 
	{
		if(grabber == null)
		{
			throw new IllegalStateException("Item was used but grabber is null");
		}
		return false; //cannot use the flag
	}
	
	@Override
	public boolean pickUp(Character grabber)
	{
		if (!this.isGrabbed()) 
		{
			if (grabber.getTeam() == this.team) 
			{
				if (this.at_base) 
				{
					return false;
				}
				else 
				{
					getGame().addGameEvent(new GameEventsEntity(getGame().getId(), GameEventType.flag_return, grabber.getEntity_id()));
					returnToBase();
					return false;
				}
			}
			else 
			{
				this.at_base = false;
				this.grabber = grabber;
				this.grabbed = true;
				this.grabber.setItem_slot(this);
				
				getGame().addGameEvent(new GameEventsEntity(getGame().getId(), GameEventType.flag_grab, grabber.getEntity_id()));
				return true;
			}
		}
		return false;
	}
	
	public void returnToBase() 
	{
		if(grabber != null) grabber.setItem_slot(null);
		
		grabber = null;
		grabbed = false;
		setPosition(base);
		at_base = true;
	}

}
