package com.cycapservers.game;

import java.util.HashMap;
import java.util.List;

import com.cycapservers.game.database.GameType;

public class CaptureTheFlag extends Game {
	
	private static final HashMap<Team, Integer> getTeamLayout(){
		HashMap<Team, Integer> map = new HashMap<Team, Integer>();
		map.put(Team.Red, 4);
		map.put(Team.Blue, 4);
		return map;
	}
	
	//////CTF STUFF//////
	protected Flag team1_flag = new Flag(null, null, 0, this, Team.Red, null); //get base from map
	protected Flag team2_flag = new Flag(null, null, 0, this, Team.Blue, null);
	/////////////////////
	
	public CaptureTheFlag() {
		super(GameType.ctf, false, -1, 10000, true, 3*60*1000, getTeamLayout());
	}

	@Override
	public boolean addCharacter(Character c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean removeCharacter(Character c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void startGame(List<String> inc_player_ids, HashMap<String, Team> inc_player_teams, HashMap<String, CharacterClass> inc_player_classes) {
		super.startGame(inc_player_ids, inc_player_teams, inc_player_classes);
	}
	
	@Override
	public void receiveInputSnapshot(InputSnapshot s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendGameState() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Spawn getValidSpawnNode(Team team) {
		// TODO Auto-generated method stub
		return null;
	}

//	public void updateGameState() {
//		//////CHECK TO SEE IF END GAME CONDITIONS ARE MET//////
//		if(((System.currentTimeMillis() - this.start_time) >= time_limit)) {
//			int team1Kills = 0;
//			int team2Kills = 0;
//			
//			if((team_scores.get(1) > team_scores.get(2))){
//				winner = 1;
//			}
//			else if((team_scores.get(1) < team_scores.get(2))){
//				winner = 2;
//			}
//			else{
//				for(Player p: players){
//					if(p.getTeam() == 1){
//						team1Kills += p.stats.getKills();
//					}
//					else{
//						team2Kills += p.stats.getKills();
//					}
//				}
//				for(AI_player p: AI_players){
//					if(p.getTeam() == 1){
//						team1Kills += p.stats.getKills();
//					}
//					else{
//						team2Kills += p.stats.getKills();
//					}
//				}
//				if(team1Kills > team2Kills){
//					winner = 1;
//				}
//				else if(team1Kills < team2Kills){
//					winner = 2;
//				}
//				else{
//					winner = Utils.getRandomInRange(1, 2);
//				}
//			}
//			
//			endGame(winner);
//		}
//		else if((team_scores.get(1) >= score_limit)){
//			winner = 1;
//			endGame(winner);
//		}
//		else if((team_scores.get(2) >= score_limit)){
//			winner = 2;
//			endGame(winner);
//		}
//
//		
//		this.currentDeltaTime = (System.currentTimeMillis() - this.lastGSMessage)/1000.0;
//		this.lastGSMessage = System.currentTimeMillis();
//		
//		//DEV STUFF
//		if(Utils.DEBUG) {
//			int error = (int) (this.currentDeltaTime * 1000 - 100);
//			if(error >= GameManager.TOLERABLE_UPDATE_ERROR) {
//				System.out.println("Time error in Gamestate sending: " + error);
//			}
//			if(this.bullets.size() >= GameManager.ADVANCED_BULLET_WARNING_LEVEL) {
//				System.out.println("ADVANCED WARNING!! TOO MANY BULLETS");
//			}
//			else if(this.bullets.size() >= GameManager.BULLET_WARNING_LEVEL) {
//				System.out.println("Warning! High number of bullets");
//			}
//		}
//		
//		/////////UPDATE GAME OBJECTS///////////
//		//move all of the bullets first
//		ListIterator<Bullet> bullet_iter = this.bullets.listIterator();
//		while(bullet_iter.hasNext()){
//			Bullet temp = bullet_iter.next();
//		    if(temp.update(this)) {
//		    	this.usedEntityIds.remove(temp.get_entity_id());
//		    	bullet_iter.remove(); //remove the bullet from the list if it is done (animation done/hit a wall/etc)
//		    }
//		}
//		//UPDATE the flags
//		this.team1_flag.update();
//		this.team2_flag.update();
//		////UPDATE PARTICLE EFFECTS////
//		ListIterator<Particle> part_iter = this.particles.listIterator();
//		while(part_iter.hasNext()){
//			Particle temp = part_iter.next();
//		    if(temp.update(this)) {
//		    	this.usedEntityIds.remove(temp.get_entity_id());
//		    	part_iter.remove();
//		    }
//		}
//		////UPDATE GROUND MASKS////
//		ListIterator<GroundMask> mask_iter = this.ground_masks.listIterator();
//		while(mask_iter.hasNext()){
//			GroundMask temp = mask_iter.next();
//		    if(temp.update()) {
//		    	this.usedEntityIds.remove(temp.get_entity_id());
//		    	mask_iter.remove();
//		    }
//		}
//		
//		
//		//////APPLY INPUT SNAPSHOTS//////
//		for(int i = 0; i < this.unhandledInputs.size(); i++) {
//			try {
//				Player p = this.unhandledInputs.get(i).client;
//				p.update(this, this.unhandledInputs.get(i));
//			}
//			catch(ConcurrentModificationException e) {
//				System.out.println("unhandled input " + i + ": " + e);
//			}
//			catch(NullPointerException e) {
//				System.out.println("Null pointer Exception when getting index " + i + " of unhandled input list when list size is " + this.unhandledInputs.size() + ".");
//			}
//		}
//		
//		//////kill players who are outside the map//////
//		for (int i = 0; i < this.players.size(); i++) {
//			if ((this.players.get(i).x < 0 || this.players.get(i).x > (Utils.GRID_LENGTH * this.mapGridWidth)) && !this.players.get(i).isDead) {
//				this.players.get(i).die();
//			} else if ((this.players.get(i).y < 0 || this.players.get(i).y > (Utils.GRID_LENGTH * this.mapGridHeight)) && !this.players.get(i).isDead) {
//				this.players.get(i).die();
//			}
//			
//			//TODO: find a way to have this counted in the game events table
//		}
//		
//		////// updating AI players ///////
//		for (AI_player ai : AI_players) {
//			if(!ai_player_delay) {
//				ai.update(this, null);
//			}
//		}
//		
//		//////Check For Flag captures//////
//		if(!this.team1_flag.atBase && this.team2_flag.atBase && Utils.isColliding(this.team1_flag, team2_base)) {
//			this.team_scores.put(2, this.team_scores.get(2) + 1); //+1 to team 2
//			this.team1_flag.grabber.stats.addFlagCapture(); //give the proper player a flag capture
//			
//			addGameEvent(new GameEventsEntity(this.game_id, GameEventType.flag_capture, this.team1_flag.grabber.get_entity_id()));
//			
//			if(Utils.DEBUG) System.out.println("FLAG 1 CAPTURED!!");
//			this.team1_flag.returnToBase(); //return the flag to base
//		}
//		else if(!this.team2_flag.atBase && this.team1_flag.atBase && Utils.isColliding(this.team2_flag, team1_base)) {
//			this.team_scores.put(1, this.team_scores.get(1) + 1); //+1 to team 1
//			this.team2_flag.grabber.stats.addFlagCapture(); //give the proper player a flag capture
//			
//			addGameEvent(new GameEventsEntity(this.game_id, GameEventType.flag_capture, this.team2_flag.grabber.get_entity_id()));
//			
//			if(Utils.DEBUG) System.out.println("FLAG 2 CAPTURED!!");
//			this.team2_flag.returnToBase(); //return the flag to base
//		}
//		
//		pu_handler.update(this); //update the powerups
//	}
//	
//	public List<Item> getItemList(){
//		List<Item> list = new ArrayList<Item>();
//		list.addAll(this.pu_handler.getPowerUpsList());
//		list.add(this.team1_flag);
//		list.add(this.team2_flag);
//		return list;
//	}
//	
//	public String toDataString(Player p) {
//		String output = "";
//		
//		//add game score data
//		output += "001," + this.team_scores.get(1) + "," + this.team_scores.get(2) + "," + (time_limit - System.currentTimeMillis() + start_time) + ":";
//		
//		//////ADD NEW SOUNDS TO PLAY//////
//		for(int i = 0; i < new_sounds.size(); i++){
//			output += "002," + new_sounds.get(i) + ":";
//		}
//		
//		//////ADD PLAYER MESSAGES///////
//		for(int i = 0; i < players.size(); i++) {
//			if((players.get(i).getTeam() == p.getTeam()) || (Utils.distanceBetween(p, players.get(i)) <= (p.visibility * Utils.GRID_LENGTH))) {
//				output += players.get(i).toDataString(p.get_entity_id()) + ":";
//			}
//		}
//		
//		//////ADD AI PLAYER MESSAGES///////
//		for (int i = 0; i < AI_players.size(); i++) {
//			if((AI_players.get(i).getTeam() == p.getTeam()) || (Utils.distanceBetween(p, AI_players.get(i)) <= (p.visibility * Utils.GRID_LENGTH))) {
//				output += AI_players.get(i).toDataString(p.get_entity_id()) + ":";
//			}
//		}
//		
//		//////ADD ITEM MESSAGES//////
//		for (Item i : this.current_item_list) {
//			output += i.toDataString(p.get_entity_id()) + ":";
//		}
//		
//		//////ADD PARTICLES//////
//		for(Particle parts : particles) {
//			output += parts.toDataString(p.get_entity_id()) + ":";
//		}
//		
//		//////ADD GROUND MASKS//////
//		for(GroundMask gm : ground_masks) {
//			output += gm.toDataString(p.get_entity_id()) + ":";
//		}
//		
//		//////ADD BULLET MESSAGES//////
//		for(int i = 0; i < bullets.size(); i++) {
//			output += bullets.get(i).toDataString(p.get_entity_id());
//			if(i != bullets.size() - 1) output += ":";
//		}
//		
//		return output; //RETURN THE MESSAGE
//	}
//	
//	public void playerJoin(String client_id, WebSocketSession session, String role, int team) {
//		if(team == 1) {
//			this.playersOnTeam1++;
//		}
//		else if(team == 2) {
//			this.playersOnTeam2++;
//		}
//		else {
//			throw new IllegalStateException("Error in player join. Illegal team for CTF.");
//		}
//		String pass = Utils.getGoodRandomString(this.userPasswords, 6);
//		Spawn n = Utils.getRandomSpawn(this.spawns, team);
//		Player p = new Player(n.getX(), n.getY(), Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0, 1.0, team, role, client_id, pass, session);
//		this.players.add(p);
//		p.stats.setGameType(this.getClass());
//		this.userPasswords.add(pass);
//		
//		getGame_players().add(new GamePlayersEntity(this.game_id, client_id, team, role, this.started));
//		
//		try {
//			
//			String message = "join:" + pass + ":" + this.game_id + ":" + "CTF:" + role + ":" + this.mapGridWidth + ":" + this.mapGridHeight;
//			for(Wall w : this.walls) {
//				message += ":" + w.toDataString(client_id);
//			}
//			session.sendMessage(new TextMessage(message));
//		} catch (IOException e) {
//			System.out.println("could not send password for " + client_id + "! error!");
//			e.printStackTrace();
//		}
//	}
//	
//	public void add_AI_player(String role) {
//		ai_player_delay = true;
//		int team = 0;
//
//		if (this.playersOnTeam1 > this.playersOnTeam2) {
//			team = 2;
//			this.playersOnTeam2++;
//		} else {
//			team = 1;
//			this.playersOnTeam1++;
//		}
//
//		// make AI player and send map reference
//		// mapNode randomNode = getRandomNode();
//		String bot_id = "bot" + Integer.toString(AI_players.size());
//		Spawn n = Utils.getRandomSpawn(this.spawns, team);
//		AI_players.add(new AI_player(n.getX(), n.getY(), Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0, 1.0, team, role, bot_id));
//		this.usedEntityIds.add(bot_id);
//		AI_players.get(AI_players.size() - 1).get_path(this);
//		ai_player_delay = false;
//		
//		getGame_players().add(new GamePlayersEntity(this.game_id, bot_id, team, role, this.started));
//	}
//	
//	public void removePlayer(WebSocketSession session) {
//		ListIterator<Player> iter = this.players.listIterator();
//		while(iter.hasNext()){
//			Player temp = iter.next();
//		    if(temp.session.equals(session)) {
//		    	if(temp.getTeam() == 1) {
//		    		this.playersOnTeam1--;
//		    	}
//		    	else {
//		    		this.playersOnTeam2--;
//		    	}
//		    	temp.leaveGame(); //drops items, et cetera
//		    	this.usedEntityIds.remove(temp.get_entity_id());
//		    	this.userPasswords.remove(temp.password);
//		    	iter.remove();
//		    	return;
//		    }
//		}
//	}
//	
//	@Override
//	public void setUpGame() {
//		if(Utils.DEBUG) System.out.println("Num of Players @ setup: " + players.size());
//		for(Player p : players) {
//			p.stats.setLevelAndXP();
//		}
//		String roles[] = { "scout", "recruit", "infantry" };
//		int number_ai_players = this.max_players - players.size();
//		for (int i = 0; i < number_ai_players; i++) {
//			add_AI_player(roles[Utils.RANDOM.nextInt(roles.length)]);
//		}
//		this.start_time = System.currentTimeMillis();
//		this.started = true;
//	}

}