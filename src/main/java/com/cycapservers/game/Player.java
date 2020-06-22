package com.cycapservers.game;

import org.springframework.web.socket.WebSocketSession;

public class Player extends Character {
	
	
	/**
	 * the websocket session associated with this player during the gameplay
	 */
	protected WebSocketSession session;
	
	private ClientInputHandler input_handler;
	
	public Player(String id, Drawable model, Game game, int team, String class_name, int max_health, double speed,
			int visibility, int inventory_size, String password, WebSocketSession session) {
		super(id, model, game, team, class_name, max_health, speed, visibility, inventory_size);
		this.input_handler = new ClientInputHandler(id, password);
		this.session = session;
	}
	
	@Override
	public boolean update() {
		
		if(!this.isAlive()){
			if((System.currentTimeMillis() - this.getLast_time_of_death()) > this.getGame().getRespawn_time() && this.getLives_remaining() > 0) {
				this.respawn();
			}
		}
		else {
			this.movePlayer(input_handler); //move the player first
			this.getCurrentEquipment().update(input_handler); //checks to see if the current weapon is to be fired
			
			//WEAPON AND ITEM RELATED KEYPRESSES
			if(input_handler.isPressedAndReleased(InputAction.WEAPON_1_SELECT)){
				this.switchEquipment(1);
			}
			else if(input_handler.isPressedAndReleased(InputAction.WEAPON_2_SELECT)){
				this.switchEquipment(2);
			}
			else if(input_handler.isPressedAndReleased(InputAction.WEAPON_3_SELECT)){
				this.switchEquipment(3);
			}
			else if(input_handler.isPressedAndReleased(InputAction.WEAPON_4_SELECT)){
				this.switchEquipment(4);
			}
			if(input_handler.isPressedAndReleased(InputAction.RELOAD)){
				this.getCurrentEquipment().reload();
			}
			if(s.keys_pnr.contains(70)){
				this.useItem();
			}
		}
	}
	
	/**
	 * moves the player based on input snapshot and checks for collision
	 * @param input_handler - current input snapshot
	 */
	private void movePlayer(ClientInputHandler input_handler) {
		int movement_code  = 0b0000; //the binary code for which directions the player moving

		//this section will probably end up on the server
		if (input_handler.keys_down.contains(87)) {
			movement_code |= Utils.UP; //trying to move up
		}
		if (input_handler.keys_down.contains(65)) {
			movement_code |= Utils.LEFT; //trying to move left
		}
		if (input_handler.keys_down.contains(68)) {
			movement_code |= Utils.RIGHT; //trying to move right
		}
		if (input_handler.keys_down.contains(83)) {
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
			delta_y = -1 * this.getSpeed() * Utils.SIN_45 * input_handler.frameTime;
			delta_x = -1 * this.getSpeed() * Utils.SIN_45 * input_handler.frameTime;
		}
		else if(movement_code == 0b1001){
			delta_y = -1 * this.getSpeed() * Utils.SIN_45 * input_handler.frameTime;
			delta_x = this.getSpeed() * Utils.SIN_45 * input_handler.frameTime;
		}
		else if(movement_code == 0b0110){
			delta_y = this.getSpeed() * Utils.SIN_45 * input_handler.frameTime;
			delta_x = -1 * this.getSpeed() * Utils.SIN_45 * input_handler.frameTime;
		}
		else if(movement_code == 0b0101){
			delta_y = this.getSpeed() * Utils.SIN_45 * input_handler.frameTime;
			delta_x = this.getSpeed() * Utils.SIN_45 * input_handler.frameTime;
		}
		else if(movement_code == 0b1000){
			delta_y = -1 * this.getSpeed() * input_handler.frameTime;
		}
		else if(movement_code == 0b0100){
			delta_y = this.getSpeed() * input_handler.frameTime;
		}
		else if(movement_code == 0b0010){
			delta_x = -1 * this.getSpeed() * input_handler.frameTime;
		}
		else if(movement_code == 0b0001){
			delta_x = this.getSpeed() * input_handler.frameTime;
		}
		
		setX(getX() + delta_x);
		setY(getY() + delta_y);
	}
	
	@Override
	public String toJSONString(){
//		if(this.get_entity_id().equals(client_id)) {
//			String output = "";
//			output += "000,";
//			output += this.highestHandledSnapshot + ",";
//			output += super.toDataString(client_id) + ",";
//			output += this.getRole() + ",";
//			output += this.getTeam() + ",";
//			output += this.getCurrentEquipment().toString() + ",";
//			if(this.getItem_slot() == null) {
//				output += "empty" + ",";
//			}
//			else {
//				output += this.getItem_slot().imageId + ",";
//			}
//			output += this.health + ",";
//			output += this.is_invincible + ",";
//			output += this.speed_boost + ",";
//			output += this.damage_boost + ",";
//			output += this.visibility;
//			return output;
//		}
//		else {
//			String output = "";
//			output += "020,";
//			output += super.toDataString(client_id);
//			return output;
//		}
		return null; //TODO
	}
	
	public String getPassword() {
		return password;
	}

	@Override
	/**
	 * respawns the player into a proper respawn node, resets their weapons and health
	 */ 
	public void respawn() {
		SpawnNode n = this.getGame().getValidSpawnNode(this.getTeam());
		this.setX(n.getX());
		this.setY(n.getY());
		
		this.setAlive(true);
		//reset ammo and health
		this.resetClass();
		return;
	}

	@Override
	public void resetClass() {
		// TODO Auto-generated method stub
		
	}

	public InputSnapshot getCurrent_input_state() {
		return current_input_state;
	}

	public void setCurrent_input_state(InputSnapshot current_input_state) {
		this.current_input_state = current_input_state;
	}
	
}