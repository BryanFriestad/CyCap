package com.cycapservers.game;

import java.util.ArrayList;
import java.util.Arrays;

public class CollisionEngine {
	
	private ArrayList<Collidable> collidables;
	private ArrayList<Collision> collisions;

	public CollisionEngine() {
		collidables = new ArrayList<Collidable>();
		collisions = new ArrayList<Collision>();
	}
	
	public void update(){
		collisions.clear();
		
		Collidable[] arr = collidables.toArray(new Collidable[0]);
		for(int i = 0; i < arr.length; i++){
			for(int j = i+1; j < arr.length; j++){
				if(arr[i].isColliding(arr[j]))
					collisions.add(new Collision(arr[i], arr[j]));
			}
		}
		
		Collision[] collision_arr = collisions.toArray(new Collision[0]);
		Arrays.sort(collision_arr);
		for(Collision c : collision_arr)
			c.respond();
	}
	
	public void registerCollidable(Collidable c){
		collidables.add(c);
	}
	
	private class Collision implements Comparable<Collision>{
		
		Collidable c1;
		Collidable c2;
		
		public Collision(Collidable c1, Collidable c2) {
			this.c1 = c1;
			this.c2 = c2;
		}
		
		private void respond(){
			if(c1.compareTo(c2) > 0){
				c1.onCollision(c2);
				c2.onCollision(c1);
			}
			else{
				c2.onCollision(c1);
				c1.onCollision(c2);
			}
		}

		@Override
		public int compareTo(Collision o) {
			return this.c1.compareTo(o.c1) + this.c1.compareTo(o.c2) + this.c2.compareTo(o.c1) + this.c2.compareTo(o.c2);
		}
		
	}

}
