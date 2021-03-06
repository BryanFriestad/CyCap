package com.cycapservers.game;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.game.database.GamePlayersEntity;

public class FreeForAll extends GameState {
	
	//////PLAYERS//////
	protected volatile boolean playerOnTeam1;
	protected volatile boolean playerOnTeam2;
	protected volatile boolean playerOnTeam3;
	protected volatile boolean playerOnTeam4;
	protected volatile boolean playerOnTeam5;
	protected volatile boolean playerOnTeam6;
	protected volatile boolean playerOnTeam7;
	protected volatile boolean playerOnTeam8;
	///////////////////
	
	public FreeForAll(int id, int map_number) {
		super(id);
		this.max_players = 8;
		playerOnTeam1 = false;
		playerOnTeam2 = false;
		playerOnTeam3 = false;
		playerOnTeam4 = false;
		playerOnTeam5 = false;
		playerOnTeam6 = false;
		playerOnTeam7 = false;
		playerOnTeam8 = false;
		this.team_scores.put(1, 0);
		this.team_scores.put(2, 0);
		this.team_scores.put(3, 0);
		this.team_scores.put(4, 0);
		this.team_scores.put(5, 0);
		this.team_scores.put(6, 0);
		this.team_scores.put(7, 0);
		this.team_scores.put(8, 0);
		
		pu_handler = new PowerUpHandler((short) 12000, (short) 1000);
		
		MapLoader.loadPredefinedMap(map_number, this);//load up the map
		
		// generate the map when player is constructed
		this.ai_map = Utils.generate_node_array(this);

		friendlyFire = false;
		respawnTime = 2500; //2.5 seconds respawn time
		time_limit = 7 * 60 * 1000; //7 minutes to ms
		score_limit = 15; //15 kills
	}

	@Override
	public void updateGameState() {
		//////CHECK TO SEE IF END GAME CONDITIONS ARE MET//////
		if(((System.currentTimeMillis() - this.start_time) >= time_limit) || (team_scores.get(1) >= score_limit) || (team_scores.get(2) >= score_limit) || (team_scores.get(3) >= score_limit) || (team_scores.get(4) >= score_limit) || (team_scores.get(5) >= score_limit) || (team_scores.get(6) >= score_limit) || (team_scores.get(7) >= score_limit) || (team_scores.get(8) >= score_limit)) { //because i'm lazy, thats why
			//TODO: set winning team correctly by comparing flag captures first and then kills
			winner = 1;
			endGame(winner);
		}
		
		this.currentDeltaTime = (System.currentTimeMillis() - this.lastGSMessage)/1000.0;
		this.lastGSMessage = System.currentTimeMillis();
		
		//DEV STUFF
		if(Utils.GAME_DEBUG) {
			int error = (int) (this.currentDeltaTime * 1000 - 100);
			if(error >= GameManager.TOLERABLE_UPDATE_ERROR) {
				System.out.println("Time error in Gamestate sending: " + error);
			}
			if(this.bullets.size() >= GameManager.ADVANCED_BULLET_WARNING_LEVEL) {
				System.out.println("ADVANCED WARNING!! TOO MANY BULLETS");
			}
			else if(this.bullets.size() >= GameManager.BULLET_WARNING_LEVEL) {
				System.out.println("Warning! High number of bullets");
			}
		}
		
		/////////UPDATE GAME OBJECTS///////////
		//move all of the bullets first
		ListIterator<Bullet> bullet_iter = this.bullets.listIterator();
		while(bullet_iter.hasNext()){
			Bullet temp = bullet_iter.next();
		    if(temp.update(this)) {
		    	this.usedEntityIds.remove(temp.get_entity_id());
		    	bullet_iter.remove(); //remove the bullet from the list if it is done (animation done/hit a wall/etc)
		    }
		}
		////UPDATE PARTICLE EFFECTS////
		ListIterator<Particle> part_iter = this.particles.listIterator();
		while(part_iter.hasNext()){
			Particle temp = part_iter.next();
		    if(temp.update(this)) {
		    	this.usedEntityIds.remove(temp.get_entity_id());
		    	part_iter.remove();
		    }
		}
		////UPDATE GROUND MASKS////
		ListIterator<GroundMask> mask_iter = this.ground_masks.listIterator();
		while(mask_iter.hasNext()){
			GroundMask temp = mask_iter.next();
		    if(temp.update()) {
		    	this.usedEntityIds.remove(temp.get_entity_id());
		    	mask_iter.remove();
		    }
		}
		
		
		//////APPLY INPUT SNAPSHOTS//////
		for(int i = 0; i < this.unhandledInputs.size(); i++) {
			try {
				Player p = this.unhandledInputs.get(i).client;
				p.update(this, this.unhandledInputs.get(i));
			}
			catch(ConcurrentModificationException e) {
				System.out.println("unhandled input " + i + ": " + e);
			}
			catch(NullPointerException e) {
				System.out.println("Null pointer Exception when getting index " + i + " of unhandled input list when list size is " + this.unhandledInputs.size() + ".");
			}
		}
		
		//////kill players who are outside the map//////
		for (int i = 0; i < this.players.size(); i++) {
			if ((this.players.get(i).x < 0 || this.players.get(i).x > (Utils.GRID_LENGTH * this.mapGridWidth)) && !this.players.get(i).isDead) {
				this.players.get(i).die();
			} else if ((this.players.get(i).y < 0 || this.players.get(i).y > (Utils.GRID_LENGTH * this.mapGridHeight)) && !this.players.get(i).isDead) {
				this.players.get(i).die();
			}
		}
		
		////// updating AI players ///////
		for (AI_player ai : AI_players) {
			if(!ai_player_delay) {
				ai.update(this, null);
			}
		}
		
		pu_handler.update(this); //update the powerups
	}

	@Override
	public List<Item> getItemList() {
		List<Item> list = new ArrayList<Item>();
		list.addAll(this.pu_handler.getPowerUpsList());
		return list;
	}

	@Override
	public String toDataString(Player p) {
		String output = "";
		
		//TODO: need to format this correctly for FFA
		output += "001," + this.team_scores.get(1) + "," + this.team_scores.get(2) + "," + (time_limit - System.currentTimeMillis() + start_time) + ":";
		
		//////ADD NEW SOUNDS TO PLAY//////
		for(int i = 0; i < new_sounds.size(); i++){
			output += "002," + new_sounds.get(i) + ":";
		}
		
		//////ADD PLAYER MESSAGES///////
		for(int i = 0; i < players.size(); i++) {
			if((players.get(i).getTeam() == p.getTeam()) || (Utils.distanceBetween(p, players.get(i)) <= (p.visibility * Utils.GRID_LENGTH))) {
				output += players.get(i).toDataString(p.get_entity_id()) + ":";
			}
		}
		
		//////ADD AI PLAYER MESSAGES///////
		for (int i = 0; i < AI_players.size(); i++) {
			if((AI_players.get(i).getTeam() == p.getTeam()) || (Utils.distanceBetween(p, AI_players.get(i)) <= (p.visibility * Utils.GRID_LENGTH))) {
				output += AI_players.get(i).toDataString(p.get_entity_id()) + ":";
			}
		}
		
		//////ADD ITEM MESSAGES//////
		for (Item i : this.current_item_list) {
			output += i.toDataString(p.get_entity_id()) + ":";
		}
		
		//////ADD PARTICLES//////
		for(Particle parts : particles) {
			output += parts.toDataString(p.get_entity_id()) + ":";
		}
		
		//////ADD GROUND MASKS//////
		for(GroundMask gm : ground_masks) {
			output += gm.toDataString(p.get_entity_id()) + ":";
		}
		
		//////ADD BULLET MESSAGES//////
		for(int i = 0; i < bullets.size(); i++) {
			output += bullets.get(i).toDataString(p.get_entity_id());
			if(i != bullets.size() - 1) output += ":";
		}
		
		return output; //RETURN THE MESSAGE
	}

	@Override
	public void playerJoin(String client_id, WebSocketSession session, String role, int team) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add_AI_player(String role) {
		int team = 8;
		String s = Utils.getGoodRandomString(this.usedEntityIds, this.entity_id_len);
		SpawnNode n = Utils.getRandomSpawn(this.spawns);
		AI_players.add(new AI_player(n.getX(), n.getY(), Utils.GRID_LENGTH, Utils.GRID_LENGTH, 0, 1.0, team, role, s));
		this.usedEntityIds.add(s);
		AI_players.get(AI_players.size() - 1).get_path(this);
		
		String bot_id = "bot" + Integer.toString(AI_players.size());
		getGame_players().add(new GamePlayersEntity(this.game_id, bot_id, team, role, this.started));
	}

	@Override
	public void removePlayer(WebSocketSession session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUpGame() {
		for(Player p : players) {
			p.stats.setLevelAndXP();
		}
		//for(int i = 0; i < (max_players - players.size()); i++){
		//	addAI_player();
		//}
		this.start_time = System.currentTimeMillis();
		this.started = true;
	}

}
