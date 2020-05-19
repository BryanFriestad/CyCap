package com.cycapservers.game.database;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GameEventsId implements Serializable{

	private int game_id;
	private int sequence_number;
	
	public GameEventsId(){
		
	}
	
	public GameEventsId(int id, int seq){
		game_id = id;
		sequence_number = seq;
	}

	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		
		if(!(o instanceof GameEventsId))
			return false;
		
		GameEventsId geid = (GameEventsId) o;
		return geid.game_id == this.game_id && geid.sequence_number == this.sequence_number;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(game_id, sequence_number);
	}
}
