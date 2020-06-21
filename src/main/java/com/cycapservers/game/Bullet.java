package com.cycapservers.game;

public class Bullet extends CollidingEntity implements DamageDealer{
	
	private double speed;
	private int damage;
	
	private String owner_id;
	private BulletWeapon shotFrom;
	private int team;
	
	private long life_span;
	private long time_of_creation;
	
	private boolean alive;
	
	public Bullet(String id, Drawable model, Collider c, int collision_priority, double speed, int damage, String ownerId, int team, BulletWeapon firingWeapon, long lifeSpan) {
		super(id, model, c, collision_priority);
		this.speed = speed;
		this.damage = damage;
		this.owner_id = ownerId;
		this.shotFrom = firingWeapon;
		this.team = team;
		this.life_span = lifeSpan;
		this.time_of_creation = System.currentTimeMillis();
	}

	@Override
	public boolean update() {
		if(System.currentTimeMillis() - time_of_creation >= life_span){
			alive = false;
		}
		
		return alive;
	}
	
	@Override
	public void onCollision(Collidable other){
		if(other instanceof Character){
			Character other_character = (Character) other;
			other_character.takeDamage(this);
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

}