package com.cycapservers.game;

public class GridLockedEntity extends Entity {

	public GridLockedEntity(GridLockedDrawable model) {
		super(model);
	}
	
	public GridLockedEntity clone() {
		return new GridLockedEntity((GridLockedDrawable) getModel().clone());
	}

}
