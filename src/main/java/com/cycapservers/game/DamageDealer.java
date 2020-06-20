package com.cycapservers.game;

public interface DamageDealer {
	
	/**
	 * Gets the Entity ID of the Character who gets credit for this DamageDealer
	 * @return 
	 */
	public String getOwnerEntityId();
	/**
	 * The amount of damage that this DamageDealer can inflict
	 * @return
	 */
	public int getDamageAmount();
	/**
	 * In the case that this DamageDealer kills the target, this function returns the cause of death
	 * @return
	 */
	public String getDeathType();

}
