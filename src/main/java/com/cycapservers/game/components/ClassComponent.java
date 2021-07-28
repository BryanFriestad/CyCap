package com.cycapservers.game.components;

import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.entities.Entity;
import com.cycapservers.game.equipment.AmmoReloadBehavior;
import com.cycapservers.game.equipment.EquipmentComponent;
import com.cycapservers.game.equipment.SemiAutoShootingBehavior;
import com.cycapservers.game.equipment.SpawningFireBehavior;
import com.cycapservers.game.matchmaking.Game;

public class ClassComponent extends Component 
{
	private CharacterClass c;
	
	public ClassComponent(CharacterClass c)
	{
		super("class");
		this.c = c;
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		return c;
	}

	public CharacterClass GetCharacterClass()
	{
		return c;
	}
	
	public static Entity[] GetDefaultEquipmentForClass(Game g, Entity owner, CharacterClass c)
	{
		Entity[] entity_list = new Entity[InventoryComponent.DEFAULT_INVENTORY_SIZE];
		switch (c)
		{
		case Artillery:
			break;
			
		case Builder:
			break;
			
		case Demolitionist:
			break;
			
		case Infantry:
			break;
			
		case Medic:
			break;
			
		case Recruit:
			// PISTOL
			Entity pistol = new Entity(g.GenerateUniqueEntityId());
			String name = "Pistol";
			pistol.AddComponent(new SpawnerComponent());
			pistol.AddComponent(new OwnerComponent(owner));
			pistol.AddComponent(new EquipmentComponent(name, new SemiAutoShootingBehavior(), new AmmoReloadBehavior(32, 2000), new SpawningFireBehavior(0.010, 10, 0.5, name)));
			g.AddUndrawnEntity(pistol);
			entity_list[0] = pistol;
			break;
			
		case Scout:
			break;
			
		case Sniper:
			break;
			
		case Tank:
			break;
			
		default:
			break;
		
		}
		
		return entity_list;
	}
}
