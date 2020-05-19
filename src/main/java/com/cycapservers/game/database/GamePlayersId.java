package com.cycapservers.game.database;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GamePlayersId implements Serializable{

	private int game_id;
	private String user_id;
	
	public GamePlayersId(){
		
	}

	public GamePlayersId(int game_id, String user_id) {
		this.game_id = game_id;
		this.user_id = user_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(game_id, user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GamePlayersId other = (GamePlayersId) obj;
		if (game_id != other.game_id)
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
}
