package com.cycapservers.game.components;

import java.util.ArrayList;
import java.util.List;

import com.cycapservers.game.entities.Blueprint;

public class SpawnerComponent extends Component 
{
	List<Blueprint> blueprints_to_add;
	
	public SpawnerComponent()
	{
		super("spawner");
		this.blueprints_to_add = new ArrayList<Blueprint>();
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		switch (message.getMessage_id())
		{
		
		case EQUIPMENT_FIRE_SPAWN:
//			System.out.println("Spawner got blueprint");
			blueprints_to_add.add((Blueprint) message.getData());
			break;
			
		default:
			break;
		}
	}
	
	public List<Blueprint> GetReadyBlueprints()
	{
		return blueprints_to_add;
	}
	
	public void ClearReadyBlueprints()
	{
		blueprints_to_add.clear();
	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		return null;
	}

}
