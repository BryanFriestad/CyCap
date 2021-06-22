package com.cycapservers.game.components.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cycapservers.game.Utils;
import com.cycapservers.game.components.positioning.PositionComponent;

public class ClientInputHandler 
{
	// STATIC functions
	private static final int NUM_CHARS_IN_CODE = 6;
	private static List<String> used_input_codes = new ArrayList<String>();
	
	private static String GenInputCode()
	{
		return Utils.getGoodRandomString(used_input_codes, NUM_CHARS_IN_CODE);
	}
	
	//parameters
	private String input_passcode;
	private HashMap<InputAction, Integer> input_mapping;
	
	//internal use
	private InputSnapshot previous_snapshot;
	private InputSnapshot current_snapshot;
	
	private int highest_snapshot_number;
	
	private List<Integer> keys_down;
	private List<Integer> keys_pnr;
	
	public ClientInputHandler(HashMap<InputAction, Integer> input_mapping) 
	{
		super();
		this.input_passcode = GenInputCode();
		this.input_mapping = input_mapping;
		previous_snapshot = null;
		current_snapshot = null;
		highest_snapshot_number = 0;
		keys_down = new ArrayList<Integer>();
		keys_pnr = new ArrayList<Integer>();
	}

	public ClientInputHandler() 
	{
		super();
		this.input_passcode = GenInputCode();
		input_mapping = new HashMap<InputAction, Integer>();
		input_mapping.put(InputAction.WEAPON_1_SELECT, 49);
		input_mapping.put(InputAction.WEAPON_2_SELECT, 50);
		input_mapping.put(InputAction.WEAPON_3_SELECT, 51);
		input_mapping.put(InputAction.WEAPON_4_SELECT, 52);
		input_mapping.put(InputAction.MOVE_UP, 87);
		input_mapping.put(InputAction.MOVE_DOWN, 83);
		input_mapping.put(InputAction.MOVE_LEFT, 65);
		input_mapping.put(InputAction.MOVE_RIGHT, 68);
		input_mapping.put(InputAction.SHOOT, 1);
		input_mapping.put(InputAction.RELOAD, 82);
		input_mapping.put(InputAction.USE_ITEM, 70);
		input_mapping.put(InputAction.ZOOM, 90);
		previous_snapshot = null;
		current_snapshot = null;
		highest_snapshot_number = 0;
		keys_down = new ArrayList<Integer>();
		keys_pnr = new ArrayList<Integer>();
	}
	
	/**
	 * At this point it is already assumed that the input snapshot has been validated
	 * @param i The new input snapshot
	 */
	public void addNewInputSnapshot(InputSnapshot i)
	{
		if (i.getSnapshotNum() <= highest_snapshot_number)
		{
			throw new IllegalArgumentException("This client has already handled an input snapshot of greater or equal snapshot number");
		}
		this.highest_snapshot_number = i.getSnapshotNum();	
		previous_snapshot = current_snapshot;
		current_snapshot = i;
		keys_down = current_snapshot.getDown();
		keys_pnr = new ArrayList<Integer>();
		for (Integer key : previous_snapshot.getDown())
		{
			if (!keys_down.contains(key))
			{
				keys_pnr.add(key);
			}
		}
	}
	
	public PositionComponent getMouseLocation()
	{
		return current_snapshot.getMouse_position();
	}
	
	public boolean isDown(InputAction action)
	{
		return keys_down.contains(input_mapping.get(action));
	}
	
	public boolean isPressedAndReleased(InputAction action)
	{
		return keys_pnr.contains(input_mapping.get(action));
	}

	public String getInput_passcode() 
	{
		return input_passcode;
	}

}
