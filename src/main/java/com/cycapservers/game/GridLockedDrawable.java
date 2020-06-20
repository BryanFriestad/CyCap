package com.cycapservers.game;

public class GridLockedDrawable extends Drawable{

	public GridLockedDrawable(Image image, int spriteIndex, GridLockedPosition drawPosition) {
		super(image, spriteIndex, drawPosition);
	}

	public GridLockedDrawable(Image image, int spriteIndex, GridLockedPosition drawPosition, double rot, double a) {
		super(image, spriteIndex, drawPosition, Utils.GRID_LENGTH, Utils.GRID_LENGTH, rot, a);
	}

}
