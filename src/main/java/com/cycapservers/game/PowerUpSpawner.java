package com.cycapservers.game;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PowerUpSpawner {
	
	protected List<PowerUpSpawn> spawns;
	
	/**
	 * the average time in between power up spawns, if feasible
	 */
	private short rate;
	
	/**
	 * the range +/- from rate until the next power up spawn, used when a power up is spawned to determine when the next spawn is
	 */
	private short randomness;
	
	/**
	 * The time of the next power up spawn
	 */
	private long next_spawn_time;
	
	/**
	 * minimum amount of time any particular spawn must wait before spawning another power up
	 */
	private long min_delta_spawn_time;
	
	
	/**
	 * Creates a new PowerUpHandler which spawns new powerups at one of a collection of nodes every so often
	 * @param s time in between each spawn
	 * @param r randomness added to the spawn time 
	 */
	public PowerUpSpawner(ArrayList<PowerUpSpawn> spawns, short s, short r) {
		if(r > s) {
			throw new IllegalArgumentException("Error: randomness of PowerUpHandler is greater than it's rate");
		}
		this.spawns = spawns;
		this.rate = s;
		this.randomness = r;
		setNextSpawnTime();
	}
	
	/**
	 * 
	 * @return Returns the spawn power up or null if non was created
	 */
	public PowerUp update() {
		if(System.currentTimeMillis() >= this.next_spawn_time) {
			List<PowerUpSpawn> freeNodes = new ArrayList<PowerUpSpawn>();
			for(PowerUpSpawn s : spawns) {
				if(s.getSlot() == null) {
					freeNodes.add(s);
				}
			}
			
			if(!freeNodes.isEmpty()) {
				setNextSpawnTime();
				return spawnPowerUp(freeNodes.get(Utils.RANDOM.nextInt(freeNodes.size())));
			}
		}
		
		return null;
	}
	
	private void setNextSpawnTime() {
		this.next_spawn_time = System.currentTimeMillis() + (this.rate + (Utils.RANDOM.nextInt(this.randomness * 2) - this.randomness));
	}
	
	private PowerUp spawnPowerUp(PowerUpSpawn spawn){
		//TODO
		return null;
	}
}
