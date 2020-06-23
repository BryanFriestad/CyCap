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
		if(!super.update()) return false;
		
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
			if(input_handler.isPressedAndReleased(InputAction.USE_ITEM)){
				this.useItem();
			}
		}
		
		return true;
	}
	
	/**
	 * moves the player based on input snapshot and checks for collision
	 * @param input_handler - current input snapshot
	 */
	private void movePlayer(ClientInputHandler input_handler) {
		int movement_code  = 0b0000; //the binary code for which directions the player moving

		//this section will probably end up on the server
		if (input_handler.isPressedAndReleased(InputAction.MOVE_UP)) {
			movement_code |= Utils.UP; //trying to move up
		}
		if (input_handler.isPressedAndReleased(InputAction.MOVE_LEFT)) {
			movement_code |= Utils.LEFT; //trying to move left
		}
		if (input_handler.isPressedAndReleased(InputAction.MOVE_RIGHT)) {
			movement_code |= Utils.RIGHT; //trying to move right
		}
		if (input_handler.isPressedAndReleased(InputAction.MOVE_DOWN)) {
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
			delta_y = -1 * this.getSpeed() * Utils.SIN_45 * delta_update_time;
			delta_x = -1 * this.getSpeed() * Utils.SIN_45 * delta_update_time;
		}
		else if(movement_code == 0b1001){
			delta_y = -1 * this.getSpeed() * Utils.SIN_45 * delta_update_time;
			delta_x = this.getSpeed() * Utils.SIN_45 * delta_update_time;
		}
		else if(movement_code == 0b0110){
			delta_y = this.getSpeed() * Utils.SIN_45 * delta_update_time;
			delta_x = -1 * this.getSpeed() * Utils.SIN_45 * delta_update_time;
		}
		else if(movement_code == 0b0101){
			delta_y = this.getSpeed() * Utils.SIN_45 * delta_update_time;
			delta_x = this.getSpeed() * Utils.SIN_45 * delta_update_time;
		}
		else if(movement_code == 0b1000){
			delta_y = -1 * this.getSpeed() * delta_update_time;
		}
		else if(movement_code == 0b0100){
			delta_y = this.getSpeed() * delta_update_time;
		}
		else if(movement_code == 0b0010){
			delta_x = -1 * this.getSpeed() * delta_update_time;
		}
		else if(movement_code == 0b0001){
			delta_x = this.getSpeed() * delta_update_time;
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

	@Override
	/**
	 * respawns the player into a proper respawn node, resets their weapons and health
	 */ 
	public void respawn() {
		Spawn n = this.getGame().getValidSpawnNode(this.getTeam());
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
	
	/**
	 * This function will check if the snapshot's passcode is valid and then add
	 * the snapshot to the ClientInputHandler.
	 * @param i The input snapshot to add to this player
	 * @return Whether or not the addition of this new snapshot was successful
	 */
	public boolean addNewInputSnapshot(InputSnapshot i){
		if(!i.getPassword().equals(input_handler.getInput_passcode())){
			return false;
		}
		input_handler.addNewInputSnapshot(i);
		return true;
	}
	
}