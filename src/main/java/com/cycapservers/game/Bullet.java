package com.cycapservers.game;

public class Bullet extends CollidingEntity implements DamageDealer{
	
	//params
	private double speed;
	private Position direction;
	private int damage;
	private double wall_damage_mult;
	
	private String owner_id;
	private BulletWeapon shotFrom;
	private int team;
	
	private long life_span;
	
	//internal
	private long time_of_creation;
	private boolean alive;
	
	public Bullet(Drawable model, Collider c, int collision_priority, double speed, Position direction, int damage, double wall_damage_mult, String ownerId, int team, BulletWeapon firingWeapon, long lifeSpan) {
		super(model, c, collision_priority);
		this.speed = speed;
		this.direction = direction;
		this.damage = damage;
		this.wall_damage_mult = wall_damage_mult;
		this.owner_id = ownerId;
		this.shotFrom = firingWeapon;
		this.team = team;
		this.life_span = lifeSpan;
		this.time_of_creation = System.currentTimeMillis();
		alive = true;
	}

	@Override
	/**
	 * Calls Entity.update() and then checks to see if this
	 * bullet is past its life span. If it is alive still,
	 * it will update its position according to its direction
	 * and speed
	 */
	public boolean update() {
		if(!super.update()) return false;
		if(System.currentTimeMillis() - time_of_creation >= life_span){
			alive = false;
			return alive;
		}
		
		Position delta = new Position(direction.getX() * speed * delta_update_time, direction.getY() * speed * delta_update_time);
		setX(getX() + delta.getX());
		setY(getY() + delta.getY());
		
		return alive;
	}
	
	@Override
	public void onCollision(Collidable other){
		if(other instanceof Character){
			Character other_character = (Character) other;
			other_character.takeDamage(this);
			alive = false;
		}
		else if(other instanceof Wall){
			Wall w = (Wall) other;
			w.takeDamage((int) (damage * wall_damage_mult));
			alive = false;
		}
	}
	
	@Override
	public String toJSONString(){
		return null; //TODO
	}

	@Override
	public String getOwnerEntityId() {
		return this.owner_id;
	}

	@Override
	public int getDamageAmount() {
		return this.damage;
	}

	@Override
	public String getDeathType() {
		return shotFrom.getName();
	}

	@Override
	public int getOwnerTeam() {
		return team;
	}
	
	public long getTime_of_creation() {
		return time_of_creation;
	}

	@Override
	public Bullet clone() {
		return new Bullet(getModel().clone(), getCollider().clone(), getCollisionPriority(), speed, null, damage, wall_damage_mult, null, -1, null, life_span);
	}

}