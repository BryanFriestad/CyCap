package com.cycapservers.game.components.drawing;

public class AnimatedDrawingComponent extends DrawingComponent 
{	
	//Class parameters
	private int number_of_sprites;
	private long animation_length; //the total length of the animation in milliseconds
	private boolean looping;
	private boolean show_while_not_running;
	private int start_sprite_index;
	
	//internal use
	protected boolean completed;
	protected boolean running;
	private double time_per_sprite;
	protected long elapsed_animation_time; //the amount of time in ms that has been animated.
	protected long last_update_time; //the timestamp at the end of the last update
	protected long time_elapsed; //the time elapsed since the update call

	/**
	 * 
	 * @param image
	 * @param startingSpriteIndex the sprite index on which to start the animation
	 * @param drawHeight
	 * @param drawWidth
	 * @param rot
	 * @param a
	 * @param animation_frames must be at least 1
	 * @param ani_len if this is 0, the animation will stay on the first sprite in the image. If do_loop is true, it will be displayed, otherwise it will not
	 * @param do_loop
	 * @param show_while_paused
	 */
	public AnimatedDrawingComponent(Image image, int startingSpriteIndex, double drawHeight, double drawWidth, double rot, double a, int animation_frames, long ani_len, boolean do_loop, boolean show_while_paused) {
		super(image, startingSpriteIndex, drawHeight, drawWidth, rot, a);
		if (animation_frames < 1) throw new IllegalArgumentException("animation_frames(" + animation_frames + ") must be greater than 0");
		if (startingSpriteIndex + animation_frames > image.GetNumberOfSprites()) throw new IllegalArgumentException("startingSpriteIndex plus animation_frames(" + startingSpriteIndex + animation_frames + ") is out of bounds of image.getSpritesLength()");
		if (ani_len < 0) throw new IllegalArgumentException("ani_len(" + ani_len + ") must be positive or 0");
		
		this.start_sprite_index = startingSpriteIndex;
		this.number_of_sprites = animation_frames;
		this.animation_length = ani_len;
		this.looping = do_loop;
		this.show_while_not_running = show_while_paused;
		this.completed = true;
		this.running = false;
		this.time_per_sprite = (double) ani_len / (double) animation_frames;
		this.elapsed_animation_time = 0;
		this.time_elapsed = 0;
	}

	/**
	 * Makes a new animated drawable which does not loop and does not display while paused.
	 * By default, this drawable has no rotation and 100% alpha
	 * @param image
	 * @param drawHeight
	 * @param drawWidth
	 * @param animation_frames must be <= image.getSpritesLength()
	 * @param ani_len
	 */
	public AnimatedDrawingComponent(Image image, double drawHeight, double drawWidth, int animation_frames, long ani_len) {
		super(image, 0, drawHeight, drawWidth);
		if (animation_frames < 1) throw new IllegalArgumentException("animation_frames(" + animation_frames + ") must be greater than 0");
		if (animation_frames > image.GetNumberOfSprites()) throw new IllegalArgumentException("animation_frames(" + animation_frames + ") must be <= image.getSpritesLength()");
		if (ani_len < 0) throw new IllegalArgumentException("ani_len(" + ani_len + ") must be positive or 0");
		
		this.start_sprite_index = 0;
		this.number_of_sprites = animation_frames;
		this.animation_length = ani_len;
		this.looping = false;
		this.show_while_not_running = false;
		this.completed = true;
		this.running = false;
		this.time_per_sprite = (double) ani_len / (double) animation_frames;
		this.elapsed_animation_time = 0;
		this.time_elapsed = 0;
	}

	/**
	 * Makes a new animated drawable which does not loop and does not display while paused. It uses all sprites of the given image to animate through.
	 * By default, this drawable has no rotation, 100% alpha, and one grid of draw height and width
	 * @param image
	 * @param drawPosition
	 * @param ani_len
	 */
	public AnimatedDrawingComponent(Image image, long ani_len) 
	{
		super(image, 0);
		if (ani_len < 0) throw new IllegalArgumentException("ani_len(" + ani_len + ") must be positive or 0");
		this.start_sprite_index = 0;
		this.number_of_sprites = image.GetNumberOfSprites();
		this.animation_length = ani_len;
		this.looping = false;
		this.show_while_not_running = false;
		this.completed = true;
		this.running = false;
		this.time_per_sprite = (double) ani_len / (double) this.number_of_sprites;
		this.elapsed_animation_time = 0;
		this.time_elapsed = 0;
	}
	
	/**
	 * resets the animation back to the beginning
	 */
	public void startAnimation()
	{
		this.setSpriteIndex(this.start_sprite_index);
		completed = false;
		running = true;
		elapsed_animation_time = 0;
		last_update_time = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return returns false if the animation is already running or if it is completed
	 */
	public boolean resumeAnimation()
	{
		if(running || completed) return false;
		
		last_update_time = System.currentTimeMillis();
		running = true;
		return true;
	}
	
	/**
	 * 
	 * @return returns false if the animation is already not running or if it is completed
	 */
	public boolean pauseAnimation()
	{
		if(!running || completed) return false;
		
		running = false;
		return true;
	}
	
	public void stopAnimation()
	{
		running = false;
		completed = true;
	}
	
	@Override
	public boolean Update(long delta_t)
	{
		if (!super.Update(delta_t)) return false;
		
		if (completed) return false;
		if (!running) return show_while_not_running;
		
		time_elapsed = System.currentTimeMillis() - last_update_time; //time elapsed since last update call
		elapsed_animation_time += time_elapsed;
		
		if (this.getAnimation_length() != 0)
		{
			int next_sprite_index = (int) Math.floor(elapsed_animation_time / time_per_sprite) + this.start_sprite_index;
			if (next_sprite_index >= this.getImage().GetNumberOfSprites())
			{
				if (looping) 
				{
					elapsed_animation_time -= animation_length;
					next_sprite_index = this.start_sprite_index;
				}
				else
				{
					completed = true;
					return false;
				}
			}
			this.setSpriteIndex(next_sprite_index);
		}
		else
		{ //if animation length is 0 ms
			if (!looping)
			{
				completed = true;
				return false;
			}
		}
		
		last_update_time = System.currentTimeMillis();
		return true;
	}

	public long getAnimation_length() 
	{
		return animation_length;
	}

	public int getNumber_of_sprites() 
	{
		return number_of_sprites;
	}

	public boolean isLooping() 
	{
		return looping;
	}

	public boolean isShow_while_not_running() 
	{
		return show_while_not_running;
	}

	public int getStart_sprite_index() 
	{
		return start_sprite_index;
	}

}
