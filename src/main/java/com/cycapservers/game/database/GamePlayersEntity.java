package com.cycapservers.game.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "game_players")
@IdClass(GamePlayersId.class)
public class GamePlayersEntity {
	
	@Id
	private int game_id;
	
	@Id
	private String user_id;
	
	@NotNull
	private int team;
	
	@NotNull
	private String class_used;
	
	@NotNull
	private boolean joined_late;
	
	@NotNull
	private boolean left_early;

	public GamePlayersEntity() {
	
	}

	public GamePlayersEntity(int game_id, String user_id, int team, String class_used, boolean joined_late) {
		this.game_id = game_id;
		this.user_id = user_id;
		this.team = team;
		this.class_used = class_used;
		this.joined_late = joined_late;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public String getClass_used() {
		return class_used;
	}

	public void setClass_used(String class_used) {
		this.class_used = class_used;
	}

	public boolean isJoined_late() {
		return joined_late;
	}

	public void setJoined_late(boolean joined_late) {
		this.joined_late = joined_late;
	}

	public boolean isLeft_early() {
		return left_early;
	}

	public void setLeft_early(boolean left_early) {
		this.left_early = left_early;
	}

}
