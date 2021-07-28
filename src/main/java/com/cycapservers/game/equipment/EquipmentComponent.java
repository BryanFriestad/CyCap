package com.cycapservers.game.equipment;

import org.json.JSONObject;

import com.cycapservers.game.components.Component;
import com.cycapservers.game.components.ComponentMessage;
import com.cycapservers.game.components.positioning.PositionComponent;

public class EquipmentComponent extends Component 
{
	private String name;
	private ShootBehavior shooter;
	private ReloadBehavior reloader;
	private FiringBehavior firer;
	
	public EquipmentComponent(String name, ShootBehavior shoot_behavior, ReloadBehavior reload_behavior, FiringBehavior fire_behavior) 
	{
		super("equipment");
		this.name = name;
		this.shooter = shoot_behavior;
		this.reloader = reload_behavior;
		this.firer = fire_behavior;
	}

	@Override
	public void Receive(ComponentMessage message)
	{
		switch (message.getMessage_id())
		{
		case INPUT_ON_SHOOT_UP:
			shooter.OnShootUp();
			break;
			
		case INPUT_ON_SHOOT_DOWN:
			shooter.OnShootDown();
			break;
			
		case INPUT_ON_RELOAD_UP:
			reloader.OnReloadUp();
			break;
			
		case INPUT_ON_RELOAD_DOWN:
			reloader.OnReloadDown();
			break;
			
		case INPUT_UPDATE_MOUSE_POSITION:
			firer.UpdateMousePosition((PositionComponent) message.getData());
			break;
			
		case POSITIONING_UPDATE:
			firer.UpdateFiringPosition((PositionComponent) message.getData());
			break;
			
		default:
			break;
		}
	}

	@Override
	public boolean Update(long delta_t) 
	{
		shooter.Update(delta_t);
		reloader.Update(delta_t);
		firer.Update(delta_t);
		
		if (shooter.WantsToShoot())
		{
//			System.out.println("Equipment: Wants to shoot");
			if (reloader.CanFire())
			{
//				System.out.println("Equipment: can fire");
				firer.Fire(this.parent);
			}
			else
			{
//				System.out.println("Equipment: cannot fire");
			}
		}
		else
		{
			
		}
		
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		JSONObject obj = new JSONObject();
		obj.put("bar", reloader.GetEquipmentBar());
		return obj;
	}

}
