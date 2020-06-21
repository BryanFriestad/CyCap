package com.cycapservers.game;

import org.springframework.web.socket.WebSocketSession;

public class Player extends Character {
	
	/**
	 * the password which the client must send for this player to be updated
	 */
	protected String password;
	/**
	 * the websocket session associated with this player during the gameplay
	 */
	protected WebSocketSession session;
	/**
	 * the number of the most recent snapshot handled by the server
	 */
	protected int highestHandledSnapshot;
	/**
	 * the last game state message that has not yet been sent to this client. is null if most recent has been sent
	 */
	protected String lastUnsentGameState;
	
	
	
	public Player(String id, Drawable model, Game game, int team, String class_name, int max_health, double speed,
			int visibility, int inventory_size, String password, WebSocketSession session) {
		super(id, model, game, team, class_name, max_health, speed, visibility, inventory_size);
		this.password = password;
		this.session = session;
		this.highestHandledSnapshot = 0;
		this.lastUnsentGameState = null;
	}
	
	@Override
	public boolean update() {
		if(s.snapshotNum > this.highestHandledSnapshot) {
			this.highestHandledSnapshot = s.snapshotNum;
		}
		
		if(!this.isAlive()){
			if((System.currentTimeMillis() - this.getLast_time_of_death()) > this.getGame().getRespawn_time()) {
				this.respawn();
			}
		}
		else {
			this.movePlayer(getGame(), s); //move the player first
			this.getCurrentEquipment().update(s); //checks to see if the current weapon is to be fired
			
			if(this.getItem_slot() == null) {
				for(Item i : game.current_item_list) {
					if(Utils.isColliding(this, i)) {
						i.pickUp(getGame(), this);
					}
				}
			}
			
			//WEAPON AND ITEM RELATED KEYPRESSES
			if(s.keys_pnr.contains(49)){
				this.switchEquipment(1);
			}
			else if(s.keys_pnr.contains(50)){
				this.switchEquipment(2);
			}
			else if(s.keys_pnr.contains(51)){
				this.switchEquipment(3);
			}
			else if(s.keys_pnr.contains(52)){
				this.switchEquipment(4);
			}
			if(s.keys_pnr.contains(82)){
				this.getCurrentEquipment().reload();
			}
			if(s.keys_pnr.contains(70)){
				this.useItem();
			}
		}
	}
	
	/**
	 * moves the player based on input snapshot and checks for collision
	 * @param game - current game state
	 * @param s - current input snapshot
	 */
	private void movePlayer(GameState game, InputSnapshot s) {
		int movement_code  = 0b0000; //the binary code for which directions the player moving

		//this section will probably end up on the server
		if (s.keys_down.contains(87)) {
			movement_code |= Utils.UP; //trying to move up
		}
		if (s.keys_down.contains(65)) {
			movement_code |= Utils.LEFT; //trying to move left
		}
		if (s.keys_down.contains(68)) {
			movement_code |= Utils.RIGHT; //trying to move right
		}
		if (s.keys_down.contains(83)) {
			movement_code |= Utils.DOWN; //trying to move down
		}

		if((movement_code & (Utils.UP | Utils.DOWN)) == 0b1100){ //if both up and down are pressed
			movement_code &= ~(Utils.UP | Utils.DOWN); //clear the up and down bits
		}
		if((movement_code & (Utils.LEFT | Utils.RIGHT)) == 0b0011){ //if both left and right are pressed
			movement_code &= ~(Utils.LEFT | Utils.RIGHT); //clear the left and right bits
		}

		double delta_x = 0;
		double delta_y = 0;
		if(movement_code == 0b1010){
			delta_y = -1 * this.getSpeed() * Utils.SIN_45 * s.frameTime;
			delta_x = -1 * this.getSpeed() * Utils.SIN_45 * s.frameTime;
		}
		else if(movement_code == 0b1001){
			delta_y = -1 * this.getSpeed() * Utils.SIN_45 * s.frameTime;
			delta_x = this.getSpeed() * Utils.SIN_45 * s.frameTime;
		}
		else if(movement_code == 0b0110){
			delta_y = this.getSpeed() * Utils.SIN_45 * s.frameTime;
			delta_x = -1 * this.getSpeed() * Utils.SIN_45 * s.frameTime;
		}
		else if(movement_code == 0b0101){
			delta_y = this.getSpeed() * Utils.SIN_45 * s.frameTime;
			delta_x = this.getSpeed() * Utils.SIN_45 * s.frameTime;
		}
		else if(movement_code == 0b1000){
			delta_y = -1 * this.getSpeed() * s.frameTime;
		}
		else if(movement_code == 0b0100){
			delta_y = this.getSpeed() * s.frameTime;
		}
		else if(movement_code == 0b0010){
			delta_x = -1 * this.getSpeed() * s.frameTime;
		}
		else if(movement_code == 0b0001){
			delta_x = this.getSpeed() * s.frameTime;
		}

		if(delta_x != 0){
			this.x += delta_x;
			for(Wall w : game.walls){
				if(Utils.isColliding(this, w))
				{
					//if the player hit a wall, reset the player positions and global transforms
					this.x -= delta_x;
					break; //does not check the other walls if at least one was hit
				}
			}
		}
		if(delta_y != 0){
			this.y += delta_y;
			for(Wall w : game.walls){
				if(Utils.isColliding(this, w))
				{
					//if the player hit a wall, reset the player positions
					this.y -= delta_y;
					break; //does not check the other walls if at least one was hit
				}
			}
		}
	}
	
	@Override
	public String toDataString(String client_id){
		if(this.get_entity_id().equals(client_id)) {
			String output = "";
			output += "000,";
			output += this.highestHandledSnapshot + ",";
			output += super.toDataString(client_id) + ",";
			output += this.getRole() + ",";
			output += this.getTeam() + ",";
			output += this.getCurrentEquipment().toString() + ",";
			if(this.getItem_slot() == null) {
				output += "empty" + ",";
			}
			else {
				output += this.getItem_slot().imageId + ",";
			}
			output += this.health + ",";
			output += this.is_invincible + ",";
			output += this.speed_boost + ",";
			output += this.damage_boost + ",";
			output += this.visibility;
			return output;
		}
		else {
			String output = "";
			output += "020,";
			output += super.toDataString(client_id);
			return output;
		}
	}
	
	public String getPassword() {
		return password;
	}

	public String getLastUnsentGameState() {
		return lastUnsentGameState;
	}

	public void setLastUnsentGameState(String lastUnsentGameState) {
		this.lastUnsentGameState = lastUnsentGameState;
	}

	@Override
	/**
	 * respawns the player into a proper respawn node, resets their weapons and health
	 */
	protected void respawn() {
		SpawnNode n = this.getGame().getValidSpawnNode(this.getTeam());
		//respawn player
		this.setX(n.getX());
		this.setY(n.getY());
		
		this.setAlive(true);
		//reset ammo and health
		this.resetClass();
		return;
	}
	
}