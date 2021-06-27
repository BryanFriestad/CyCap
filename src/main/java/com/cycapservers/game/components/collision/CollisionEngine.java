package com.cycapservers.game.components.collision;

import java.util.ArrayList;
import java.util.Arrays;

import com.cycapservers.game.entities.CollidingEntity;

public class CollisionEngine {
	
	private ArrayList<CollisionComponent> collidables;
	private ArrayList<Collision> collisions;

	public CollisionEngine() {
		collidables = new ArrayList<CollisionComponent>();
		collisions = new ArrayList<Collision>();
	}
	
	public void update(){
		collisions.clear();
		
		CollisionComponent[] arr = collidables.toArray(new CollisionComponent[0]);
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
	
	public void registerCollidable(CollisionComponent cc){
		collidables.add(cc);
	}
	
	private class Collision implements Comparable<Collision>{
		
		CollisionComponent c1;
		CollisionComponent c2;
		
		public Collision(CollisionComponent c1, CollisionComponent c2) {
			this.c1 = c1;
			this.c2 = c2;
		}
		
		private void respond()
		{
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
