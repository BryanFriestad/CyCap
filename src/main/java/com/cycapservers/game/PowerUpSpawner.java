package com.cycapservers.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import com.cycapservers.game.entities.PowerUp;

public class PowerUpSpawner {
	
	protected List<PowerUpSpawn> spawns;
	
	/**
	 * The percent chance of each type of powerup spawning. The sum of the values should be equal to 1.0
	 */
	private HashMap<PowerUp, Double> power_up_chances;
	
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
	public PowerUpSpawner(ArrayList<PowerUpSpawn> spawns, HashMap<PowerUp, Double> power_up_chances, short s, short r) {
		if(r > s) {
			throw new IllegalArgumentException("Error: randomness of PowerUpHandler is greater than it's rate");
		}
		if(!(Math.abs(Double.compare(Utils.sumDoubleArray((Double[]) power_up_chances.values().toArray()), 1.0)) <= 0.0001)){
			throw new IllegalArgumentException("Error: the power_up_chances hashmap values do not sum to 1.0 (" + power_up_chances + ")");
		}
		this.spawns = spawns;
		this.power_up_chances = power_up_chances;
		this.rate = s;
		this.randomness = r;
		setNextSpawnTime();
	}
	
	/**
	 * 
	 * @return Returns the spawn power up or null if none was created
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
		double val = Utils.RANDOM.nextDouble();
		double sum = 0;
		PowerUp[] arr = (PowerUp[]) this.power_up_chances.keySet().toArray();
		for(int i = 0; i < arr.length; i++){
			Double d = this.power_up_chances.get(arr[i]);
			sum += d;
			if(sum > val){
				PowerUp selected = arr[i];
				spawn.setSlot(selected.clone());
				return selected;
			}
		}
		throw new IllegalStateException("Error with spawning new power up. Sum of spawn chances are invalid.");
	}
}
