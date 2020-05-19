package com.cycapservers.game.database;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "game_events")
@IdClass(GameEventsId.class)
public class GameEventsEntity {
	
	@Id
	private int game_id;
	
	@Id
	private int sequence_order;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private GameEventType event_type;
	
	@NotNull
	private String src_player;
	
	private String target_player;
	
	private String weapon_used;
	
	public GameEventsEntity(){
		
	}
	
	public GameEventsEntity(int game_id, int sequence_order, GameEventType event_type, String src_player, String target_player, String weapon_used) {
		this.game_id = game_id;
		this.sequence_order = sequence_order;
		this.event_type = event_type;
		this.src_player = src_player;
		this.target_player = target_player;
		this.weapon_used = weapon_used;
	}
	
	public GameEventsEntity(int game_id, int sequence_order, GameEventType event_type, String src_player) {
		this.game_id = game_id;
		this.sequence_order = sequence_order;
		this.event_type = event_type;
		this.src_player = src_player;
		this.target_player = null;
		this.weapon_used = null;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getSequence_order() {
		return sequence_order;
	}

	public void setSequence_order(int sequence_order) {
		this.sequence_order = sequence_order;
	}

	public GameEventType getEvent_type() {
		return event_type;
	}

	public void setEvent_type(GameEventType event_type) {
		this.event_type = event_type;
	}

	public String getSrc_player() {
		return src_player;
	}

	public void setSrc_player(String src_player) {
		this.src_player = src_player;
	}

	public String getTarget_player() {
		return target_player;
	}

	public void setTarget_player(String target_player) {
		this.target_player = target_player;
	}

	public String getWeapon_used() {
		return weapon_used;
	}

	public void setWeapon_used(String weapon_used) {
		this.weapon_used = weapon_used;
	}

	
}
