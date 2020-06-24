package com.cycapservers.game;

public abstract class Item extends CollidingEntity {
	
	private Game game;
	
	protected String name;
	protected Character grabber;
	protected boolean grabbed;
	
	public Item(String id, Drawable model, Collider c, int collision_priority, Game g, String name) {
		super(id, model, c, collision_priority);
		this.game = g;
		this.name = name;
		this.grabber = null;
		this.grabbed = false;
	}

	/**
	 * 
	 * @param grabber the character that is trying to pick up this item
	 * @return
	 */
	public boolean pickUp(Character grabber) {
		if(!this.grabbed) {
			this.grabber = grabber;
			this.grabbed = true;
			this.grabber.setItem_slot(this);
			return true;
		}
		return false;
	}
	
	/**
	 * Use the item. Throws an IllegalStateException if grabber is null.
	 * @return boolean: returns true if the item is all used up and is to be removed from the grabber's inventory
	 */
	public abstract boolean use();
	
	public void drop() {
		this.setPosition(this.grabber.getPosition());
		this.grabber = null;
		this.grabbed = false;
	}
	
	@Override
	public String toJSONString(){
		return null; //TODO: must hold class, entity_id, drawable, grabbed
	}

	public Game getGame() {
		return game;
	}

	public Character getGrabber() {
		return grabber;
	}

	public boolean isGrabbed() {
		return grabbed;
	}
	
	
}