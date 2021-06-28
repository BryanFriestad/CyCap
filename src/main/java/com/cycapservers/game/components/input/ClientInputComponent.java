package com.cycapservers.game.components.input;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.entities.Character;

/**
 * A component which allows a character to be controller by a client across the internet.
 * @author Bryan Friestad
 *
 */
public class ClientInputComponent extends InputComponent 
{
	private ClientInputHandler input_handler;
	
	public ClientInputComponent()
	{
//		session = s;
		input_handler = new ClientInputHandler();
	}
	
	@Override
	public boolean update(Character c) 
	{
		movePlayer(c, input_handler); //move the player first
		if (c.getCurrentEquipment() != null) c.getCurrentEquipment().update(input_handler); //checks to see if the current weapon is to be fired
		
		//WEAPON AND ITEM RELATED KEYPRESSES
		if (input_handler.isPressedAndReleased(InputAction.WEAPON_1_SELECT))
		{
			c.switchEquipment(1);
		}
		else if (input_handler.isPressedAndReleased(InputAction.WEAPON_2_SELECT))
		{
			c.switchEquipment(2);
		}
		else if (input_handler.isPressedAndReleased(InputAction.WEAPON_3_SELECT))
		{
			c.switchEquipment(3);
		}
		else if (input_handler.isPressedAndReleased(InputAction.WEAPON_4_SELECT))
		{
			c.switchEquipment(4);
		}
		if (input_handler.isPressedAndReleased(InputAction.USE_ITEM))
		{
			c.useItem();
		}
		return true;
	}
	
	/**
	 * moves the player based on input snapshot and checks for collision
	 * @param input_handler - current input snapshot
	 */
	private void movePlayer(Character c, ClientInputHandler input_handler) 
	{
		int movement_code  = 0b0000; //the binary code for which directions the player moving

		//this section will probably end up on the server
		if (input_handler.isDown(InputAction.MOVE_UP)) 
		{
			movement_code |= Utils.UP; //trying to move up
		}
		if (input_handler.isDown(InputAction.MOVE_LEFT)) 
		{
			movement_code |= Utils.LEFT; //trying to move left
		}
		if (input_handler.isDown(InputAction.MOVE_RIGHT)) 
		{
			movement_code |= Utils.RIGHT; //trying to move right
		}
		if (input_handler.isDown(InputAction.MOVE_DOWN)) 
		{
			movement_code |= Utils.DOWN; //trying to move down
		}

		if ((movement_code & (Utils.UP | Utils.DOWN)) == 0b1100) //if both up and down are pressed
		{
			movement_code &= ~(Utils.UP | Utils.DOWN); //clear the up and down bits
		}
		if ((movement_code & (Utils.LEFT | Utils.RIGHT)) == 0b0011) //if both left and right are pressed
		{
			movement_code &= ~(Utils.LEFT | Utils.RIGHT); //clear the left and right bits
		}

		double delta_x = 0;
		double delta_y = 0;
		double pixels_per_ms = c.getSpeed() * Utils.GRID_LENGTH * (c.getDelta_update_time() / 1000.0);
		if (movement_code == 0b1010)
		{
			delta_y = -1 * pixels_per_ms * Utils.SIN_45;
			delta_x = -1 * pixels_per_ms * Utils.SIN_45;
		}
		else if (movement_code == 0b1001)
		{
			delta_y = -1 * pixels_per_ms * Utils.SIN_45;
			delta_x = 	   pixels_per_ms * Utils.SIN_45;
		}
		else if (movement_code == 0b0110)
		{
			delta_y = 	   pixels_per_ms * Utils.SIN_45;
			delta_x = -1 * pixels_per_ms * Utils.SIN_45;
		}
		else if (movement_code == 0b0101)
		{
			delta_y = pixels_per_ms * Utils.SIN_45;
			delta_x = pixels_per_ms * Utils.SIN_45;
		}
		else if (movement_code == 0b1000)
		{
			delta_y = -1 * pixels_per_ms;
		}
		else if (movement_code == 0b0100)
		{
			delta_y = pixels_per_ms;
		}
		else if (movement_code == 0b0010)
		{
			delta_x = -1 * pixels_per_ms;
		}
		else if (movement_code == 0b0001)
		{
			delta_x = pixels_per_ms;
		}
		
		c.setX(c.getX() + delta_x);
		c.setY(c.getY() + delta_y);
	}

	@Override
	public void Receive(ComponentMessage message) 
	{
		switch (message.getMessage_id())
		{
		case EXTERNAL_INPUT_SNAPSHOT:
			addNewInputSnapshot((InputSnapshot) message.getData());
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * This function will check if the snapshot's passcode is valid and then add
	 * the snapshot to the ClientInputHandler.
	 * @param i The input snapshot to add to this player
	 * @return Whether or not the addition of this new snapshot was successful
	 */
	private boolean addNewInputSnapshot(InputSnapshot i)
	{
		if (!i.getPassword().equals(input_handler.getInput_passcode()))
		{
			return false;
		}
		input_handler.addNewInputSnapshot(i);
		return true;
	}
	
	public String GetInputPasscode()
	{
		return input_handler.getInput_passcode();
	}

}
