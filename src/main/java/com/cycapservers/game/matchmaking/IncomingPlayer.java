package com.cycapservers.game.matchmaking;

import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.Team;
import com.cycapservers.game.components.collision.CharacterCollisionComponent;
import com.cycapservers.game.components.collision.CircleCollider;
import com.cycapservers.game.components.drawing.DrawingComponentFactory;
import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.Player;

public class IncomingPlayer 
{	
	private String client_id;
	private CharacterClass role;
//	private WebSocketSession session;
	private Team team;
	
	public IncomingPlayer(String c, CharacterClass r, Team t) 
	{
		this.client_id = c;
		this.role = r;
//		this.session = session;
		this.team = t;
	}
	
	public Player BuildPlayer(Game g, int inventory_size, int max_lives)
	{
		return new Player(new CharacterCollisionComponent(new CircleCollider(), 10, new PositionComponent()), 
						  DrawingComponentFactory.getInstance().BuildPlayerDrawingComponent(team), 
						  g, 
						  team, 
						  role,
						  inventory_size,
						  max_lives);
	}
	
	public String GetClientId()
	{
		return client_id;
	}

	public Team GetTeam() 
	{
		return team;
	}
	
	public void SetTeam(Team t)
	{
		team = t;
	}
	
	public CharacterClass GetCharacterClass()
	{
		return role;
	}
}