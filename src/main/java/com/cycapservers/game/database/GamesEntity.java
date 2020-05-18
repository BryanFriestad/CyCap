package com.cycapservers.game.database;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "games")
public class GamesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int game_id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private GameType game_type;
	
	@NotNull
	@JsonFormat(pattern = "yyy-MM-dd")
	private Date start_date;
	
	@NotNull
	private Time start_time;
	
	private int winning_team;
	
	public GamesEntity(){
		
	}
	
	public GamesEntity(GameType type){
		game_type = type;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		dtf.format(localDate);
		java.sql.Date sql_date = java.sql.Date.valueOf(localDate);
		start_date = sql_date;
		
		LocalTime localTime = LocalTime.now();
		dtf.format(localTime);
		java.sql.Time sql_time = java.sql.Time.valueOf(localTime);
		start_time = sql_time;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public GameType getGame_type() {
		return game_type;
	}

	public void setGame_type(GameType game_type) {
		this.game_type = game_type;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public int getWinning_team() {
		return winning_team;
	}

	public void setWinning_team(int winning_team) {
		this.winning_team = winning_team;
	}
	
	
}
