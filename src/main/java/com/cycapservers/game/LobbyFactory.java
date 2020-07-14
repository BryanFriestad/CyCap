package com.cycapservers.game;

public class LobbyFactory {

	public Lobby getLobby(LobbyType type){
		switch(type){
			case ctf:
				break;
				
			case ffa:
				break;
				
			case mixed:
				break;
				
			case tdm:
				break;
				
			default:
				return null;
		}
	}

}
