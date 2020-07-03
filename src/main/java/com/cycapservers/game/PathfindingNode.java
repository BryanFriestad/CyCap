package com.cycapservers.game;

import java.util.HashMap;

public class PathfindingNode extends GridLockedPosition{

	double f = Double.POSITIVE_INFINITY;
	double g = 0.0;
	HashMap<String, PathfindingNode> previousNodes = new HashMap<String, PathfindingNode>();
	boolean node_trav;
	boolean corner;

	//constructs the node with given characteristics
	public PathfindingNode(double x, double y, boolean trav, boolean corner) {
		super(x, y);
		this.node_trav = trav;
		this.corner = corner;
		
	}

	public void set_prev(String ai_id, PathfindingNode prev){
		previousNodes.put(ai_id, prev);
	}
	
	public PathfindingNode get_prev(String ai_id) {
		return previousNodes.get(ai_id);
	}

}
