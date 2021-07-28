package com.cycapservers.game.components.collision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollisionEngine 
{
	private ArrayList<CollisionComponent> collidables;
	private ArrayList<Collision> collisions;

	public CollisionEngine() 
	{
		collidables = new ArrayList<CollisionComponent>();
		collisions = new ArrayList<Collision>();
	}
	
	public void Update()
	{
		collisions.clear();
		CleanUpDeadColliders();
		
		CollisionComponent[] arr = collidables.toArray(new CollisionComponent[0]);
		for(int i = 0; i < arr.length; i++)
		{
			for(int j = i+1; j < arr.length; j++)
			{
				if(arr[i].isColliding(arr[j]))
					collisions.add(new Collision(arr[i], arr[j]));
			}
		}
		
		Collision[] collision_arr = collisions.toArray(new Collision[0]);
		Arrays.sort(collision_arr);
		for(Collision c : collision_arr)
			c.respond();
	}
	
	private void CleanUpDeadColliders()
	{
		List<CollisionComponent> to_delete = new ArrayList<>();
		for (CollisionComponent cc : collidables)
		{
			if (cc.GetParent().IsMarkedToDelete()) to_delete.add(cc);
		}
		collidables.removeAll(to_delete);
	}
	
	public void registerCollidable(CollisionComponent cc)
	{
		collidables.add(cc);
	}
	
	private class Collision implements Comparable<Collision>
	{	
		CollisionComponent c1;
		CollisionComponent c2;
		
		public Collision(CollisionComponent c1, CollisionComponent c2) 
		{
			this.c1 = c1;
			this.c2 = c2;
		}
		
		private void respond()
		{
			if(c1.compareTo(c2) > 0)
			{
				c2.beCollidedBy(c1);
				c1.beCollidedBy(c2);
			}
			else
			{
				c1.beCollidedBy(c2);
				c2.beCollidedBy(c1);
			}
		}

		@Override
		public int compareTo(Collision o) 
		{
			return this.c1.compareTo(o.c1) + this.c1.compareTo(o.c2) + this.c2.compareTo(o.c1) + this.c2.compareTo(o.c2);
		}
		
	}

}
