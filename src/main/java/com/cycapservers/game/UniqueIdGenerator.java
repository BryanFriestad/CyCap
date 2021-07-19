package com.cycapservers.game;

import java.util.ArrayList;
import java.util.List;

public class UniqueIdGenerator 
{
	private int id_length;
	private List<String> used_ids;
	
	public UniqueIdGenerator(int id_length)
	{
		this.id_length = id_length;
		this.used_ids = new ArrayList<String>();
	}
	
	/**
	 * Generates a unique entity id and then adds it to the list of used ones.
	 * @return A unique entity id.
	 */
	public String GenerateUniqueEntityId()
	{
		String s = GetGoodRandomString() + "(" + used_ids.size() + ")";
		used_ids.add(s);
		return s;
	}
	
	/**
	 * returns a randomized string with the specified length that does not exist in currentList
	 * @return
	 */
	public String GetGoodRandomString() 
	{
		String output = Utils.createString(id_length);
		while(used_ids.contains(output)) 
		{
			output = Utils.createString(id_length);
		}
		return output;
	}
	
	/**
	 * Adds an externally generated ID to the list of used IDs. Prevents further random generation of the specified ID.
	 * @param id
	 */
	public void AddUsedId(String id)
	{
		if (used_ids.contains(id)) throw new IllegalStateException("This id generator already used that id!");
		used_ids.add(id);
	}
}
