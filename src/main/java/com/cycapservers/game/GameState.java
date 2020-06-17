package com.cycapservers.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cycapservers.BeanUtil;
import com.cycapservers.account.ProfileDataUpdate;
import com.cycapservers.game.database.GameEventsEntity;
import com.cycapservers.game.database.GameEventsRepository;
import com.cycapservers.game.database.GamePlayersEntity;
import com.cycapservers.game.database.GamePlayersRepository;
import com.cycapservers.game.database.GamesEntity;
import com.cycapservers.game.database.GamesRepository;

public abstract class GameState extends TimerTask
{
	//////NITTY GRITTY STUFF//////
	/**
	 * Players that are planning to join the game.
	 */
	protected ArrayList<IncomingPlayer> incomingPlayers;
	protected int game_id;
	protected List<String> usedEntityIds;
	protected int entity_id_len;
	protected List<String> userPasswords;
	protected List<InputSnapshot> unhandledInputs;
	protected boolean readyToStart;
	protected boolean gameFinished;
	protected long readyTime;
	/////////////////////////////
	
	//////PLAYERS//////
	protected int max_players;
	protected List<AI_player> AI_players;
	protected List<Player> players;
	protected boolean friendlyFire;
	protected long respawnTime; //the amount of time to respawn after death in ms
	///////////////////
	
	//////ITEMS//////
	protected List<Item> current_item_list;
	protected PowerUpHandler pu_handler;
	////////////////
	
	protected List<String> new_sounds;
	
	// stuff for AI
	protected ArrayList<ArrayList<mapNode>> ai_map;
	protected boolean ai_player_delay;
	
	protected List<Bullet> bullets;
	
	//Map Related Stuff
	protected List<Wall> walls;
	protected int mapGridWidth;
	protected int mapGridHeight;
	protected List<SpawnNode> spawns;
	
	//////GRAPHICAL OBJECTS//////
	protected List<Particle> particles;
	protected List<ParticleEffect> effects;
	protected List<GroundMask> ground_masks;
	
	//////SCORES AND TIME//////
	protected HashMap<Integer, Integer> team_scores;
	protected long start_time;
	protected int time_limit;
	protected boolean started;
	protected int winner;
	protected int score_limit;
	
	//////GAMES DATABASE//////
	private List<GamePlayersEntity> game_players;
	private List<GameEventsEntity> game_events;
	
	protected long lastGSMessage;
	protected double currentDeltaTime; //the time since the last game state update in seconds
	
	public GameState(int id) {
		this.game_id = id;
		this.usedEntityIds = new ArrayList<String>();
		entity_id_len = 6;
		this.userPasswords = new ArrayList<String>();

		this.incomingPlayers = new ArrayList<IncomingPlayer>();
		this.players = new ArrayList<Player>();
		this.AI_players = new ArrayList<AI_player>();
		ai_player_delay = false;
		this.team_scores = new HashMap<Integer, Integer>();
		this.bullets = new ArrayList<Bullet>();
		this.walls = new ArrayList<Wall>();
		this.spawns = new ArrayList<SpawnNode>();
		this.new_sounds = new ArrayList<String>();
		this.current_item_list = new ArrayList<Item>();
		this.particles = new ArrayList<Particle>();
		this.effects = new ArrayList<ParticleEffect>();
		this.ground_masks = new ArrayList<GroundMask>();
		
		this.unhandledInputs = new ArrayList<InputSnapshot>();
		this.lastGSMessage = System.currentTimeMillis();
		
		this.started = false;
		this.readyToStart = false;
		this.gameFinished = false;
		
		this.game_players = new ArrayList<GamePlayersEntity>();
		this.game_events = new ArrayList<GameEventsEntity>();
	}

	public List<GamePlayersEntity> getGame_players() {
		return game_players;
	}

	public void setGame_players(List<GamePlayersEntity> game_players) {
		this.game_players = game_players;
	}

	public void addGameEvent(GameEventsEntity event){
		event.setSequence_order(game_events.size());
		game_events.add(event);
	}

	public abstract void updateGameState();
	
	public abstract List<Item> getItemList();
	
	public abstract String toDataString(Player p);
	
	public void addInputSnap(InputSnapshot s) {
		for(Player p : this.players) {
			if(s.password.equals(p.getPassword())) {
				s.setClient(p);
				unhandledInputs.add(s);
				if(p.getLastUnsentGameState() != null) {
					try {
						p.session.sendMessage(new TextMessage(p.getLastUnsentGameState()));
						p.setLastUnsentGameState(null);
					} catch (IOException e) {
						System.out.println("Error when sending game state");
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}
	
	/**
	 * Works with the lobby to show if a player is going to arrive.
	 * @param userId
	 */
	public void addIncomingPlayer(IncomingPlayer p){
		this.incomingPlayers.add(p);
	}
	
	/**
	 * Checks to see if the given userId is in the incoming players list and if they are adds them to the game.
	 * @param userId
	 * The person who wants to joins user id.
	 * @param session
	 * The person who wants to join websocketsession.
	 * @param role
	 * The role that the person wants.
	 * @return
	 * returns true if the person is in the incoming player list and they joined the game. returns false if they arn't.
	 */
	public boolean findIncomingPlayer(String userId, WebSocketSession session){
		for(IncomingPlayer p : incomingPlayers){
			if(p.client_id.equals(userId)){
				this.playerJoin(p.client_id, session, p.role, p.team);
				return true;
			}
		}
		return false;
	}
	
	public abstract void playerJoin(String client_id, WebSocketSession session, String role, int team);
	
	public abstract void add_AI_player(String role);
	
	public abstract void removePlayer(WebSocketSession session);
	
	public abstract void setUpGame();
	
	public void endGame(int winner) {
		
		gameFinished = true;
		List<String> player_ids = new ArrayList<String>();
		for(Player p : this.players) {
			player_ids.add(p.get_entity_id());
			
			p.stats.updateScore(winner);
			ProfileDataUpdate.dbSaveData(p.stats);
			String message = "endgame:";
			try {
				p.session.sendMessage(new TextMessage(message + p.stats.get_endgame_message()));
			} catch (IOException e) {
				System.out.println("Error sending endgame message");
				e.printStackTrace();
			}
		}
		
		GamePlayersRepository gamePlayersRepo = BeanUtil.getBean(GamePlayersRepository.class);
		for(GamePlayersEntity e : game_players){
			if(player_ids.contains(e.getUser_id())){
				e.setLeft_early(false);
			}
			else{
				e.setLeft_early(true);
			}
			
			gamePlayersRepo.save(e);
		}
		
		GameEventsRepository gameEventsRepo = BeanUtil.getBean(GameEventsRepository.class);
		gameEventsRepo.save(game_events);
		
		GamesRepository gamesRepo = BeanUtil.getBean(GamesRepository.class);
		GamesEntity this_game = gamesRepo.findOne(this.game_id);
		
		this_game.setStart_time(start_time);
		this_game.setWinning_team(winner);
		gamesRepo.save(this_game);
	}

	@Override
	public void run() {
		if(started && !gameFinished) {
			////UPDATE GAME STATE////
			updateGameState();
			
			////GET NEW ITEM LIST////
			this.current_item_list = getItemList();
			
			////INFORM PLAYERS OF GS UPDATE////
			for(Player p : players) {
				p.setLastUnsentGameState(this.toDataString(p));
			}
			
			//////CLEAR LISTS/////
			this.new_sounds.clear();
			this.unhandledInputs.clear(); //empty the queue of unhandled inputs
		}
		else if(readyToStart && !gameFinished){
			if(Utils.DEBUG) System.out.println("Game: " + this.game_id + " - Type: " + this.getClass() +  " - Players size: " + players.size() + " - Incoming size: " + incomingPlayers.size() + " - ReadyTime: " + this.readyTime);
			if(players.size() == incomingPlayers.size() || (System.currentTimeMillis() - this.readyTime) >= 15000) {
				setUpGame();
			}
		}
	}
}