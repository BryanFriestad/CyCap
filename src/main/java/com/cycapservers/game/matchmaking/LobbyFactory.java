package com.cycapservers.game.matchmaking;

import java.util.ArrayList;

import com.cycapservers.game.database.GameType;
import com.cycapservers.game.maps.Map;
import com.cycapservers.game.maps.MapLoader;

public class LobbyFactory {
	
	private static LobbyFactory instance = new LobbyFactory();
	
	private LobbyFactory(){}
	
	public static LobbyFactory getInstance(){
		return instance;
	}

	/**
	 * 
	 * @param type
	 * @return returns null if unsupported lobby type is given
	 */
	public Lobby getLobby(LobbyType type, String join_code){
		switch(type){
			case ctf:
				return new Lobby(join_code, LobbyType.ctf, getGameTypeList(type), 8, false, true, true, getMapList(type));
				
			case ffa:
				throw new UnsupportedOperationException("ffa lobbies not ready");
				
			case mixed:
				throw new UnsupportedOperationException("mixed lobbies not ready");
				
			case tdm:
				return new Lobby(join_code, LobbyType.tdm, getGameTypeList(type), 8, false, true, true, getMapList(type));
				
			default:
				return null;
		}
	}
	
	private static final ArrayList<GameType> getGameTypeList(LobbyType t){
		ArrayList<GameType> arr = new ArrayList<GameType>();
		switch(t){
			case ctf:
				arr.add(GameType.ctf);
				return arr;
				
			case ffa:
				arr.add(GameType.ffa);
				return arr;
				
			case mixed:
				arr.add(GameType.tdm);
				arr.add(GameType.ctf);
				arr.add(GameType.ffa);
				return arr;
				
			case tdm:
				arr.add(GameType.tdm);
				return arr;
				
			default:
				return null;
		}
	}
	
	private static final ArrayList<Map> getMapList(LobbyType t){
		ArrayList<Map> arr = new ArrayList<Map>();
		switch(t){
			case ctf:
				
				return arr;
				
			case ffa:
				
				return arr;
				
			case mixed:
				
				return arr;
				
			case tdm:
				arr.add(MapLoader.LoadMap("empty_map"));
				return arr;
				
			default:
				return null;
		}
	}

}
