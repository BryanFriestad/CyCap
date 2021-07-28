package com.cycapservers.game.equipment;

import com.cycapservers.game.components.positioning.PositionComponent;
import com.cycapservers.game.entities.Entity;

public abstract class FiringBehavior 
{
	public abstract void UpdateMousePosition(PositionComponent mouse_pos);
	public abstract void UpdateFiringPosition(PositionComponent firing_pos);
	public abstract void Fire(Entity e);
	public abstract boolean Update(long delta_t);
}
