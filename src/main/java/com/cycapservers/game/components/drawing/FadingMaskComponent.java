package com.cycapservers.game.components.drawing;

public class FadingMaskComponent extends AnimatedDrawingComponent {
	
	//params
	private long fade_time;
	
	//internal
	private long full_alpha_time;

	public FadingMaskComponent(Image image, int startingSpriteIndex, double drawHeight, double drawWidth, double rot, double a,
			int animation_frames, long ani_len, boolean do_loop, boolean show_while_paused, long fade_time) 
	{
		super(image, startingSpriteIndex, drawHeight, drawWidth, rot, a, animation_frames, ani_len, do_loop, show_while_paused);
		this.fade_time = fade_time;
		this.full_alpha_time = getAnimation_length() - fade_time;
	}

	/**
	 * 
	 * @return true if the ground mask is finished fading away
	 */
	@Override
	public boolean Update(long delta_t) 
	{
		if (!super.Update(delta_t)) return false;
		
		if (elapsed_animation_time >= this.full_alpha_time) 
		{
			setAlpha(1.0 - ((elapsed_animation_time - this.full_alpha_time) / (double) this.fade_time));
		}
		else 
		{
			setAlpha(1.0);
		}
		return true;
	}
}
