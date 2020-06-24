package com.cycapservers.game;

import java.util.HashMap;

public class CaptureTheFlagMap extends Map {
	
	private HashMap<Integer, GridLockedPosition> team_flag_locations;

	public CaptureTheFlagMap(HashMap<Integer, GridLockedPosition> team_flag_locations) {
		super();
		this.team_flag_locations = team_flag_locations;
	}

	public HashMap<Integer, GridLockedPosition> getTeam_flag_locations() {
		return team_flag_locations;
	}

}
