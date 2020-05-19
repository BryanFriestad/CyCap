package com.cycapservers.game.database;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GameEventsId implements Serializable{

	private int game_id;
	private int sequence_order;
	
	public GameEventsId(){
		
	}
	
	public GameEventsId(int id, int seq){
		game_id = id;
		sequence_order = seq;
	}

	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		
		if(!(o instanceof GameEventsId))
			return false;
		
		GameEventsId geid = (GameEventsId) o;
		return geid.game_id == this.game_id && geid.sequence_order == this.sequence_order;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(game_id, sequence_order);
	}
}
