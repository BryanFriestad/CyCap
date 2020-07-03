//package com.cycapservers.game;
//
//import java.awt.Point;
//import java.util.ArrayList;
//
//public class AI_path_generator {
//
//	private AI_player ai;
//	private GameState g;
//	protected ArrayList<PathfindingNode> closed_list = new ArrayList<PathfindingNode>();
//	protected ArrayList<PathfindingNode> open_list = new ArrayList<PathfindingNode>();
//
//	public AI_path_generator(AI_player ai, GameState g) {
//		this.ai = ai;
//		this.g = g;
//	}
//
//	/**
//	 * unifying path finding function
//	 * 
//	 * @param start
//	 *            starting entity (the one that moves)
//	 * @param end
//	 *            ending entity
//	 * @return
//	 */
//	public ArrayList<PathfindingNode> get_a_star_path(Entity start, Entity end) {
//		Point moving_point = Utils.get_nearest_map_node(start, this.g);
//		Point ending_point = Utils.get_nearest_map_node(end, this.g);
//		return A_Star_Path(moving_point, ending_point);
//	}
//
//	/**
//	 * generates a path using A*
//	 * 
//	 * @param a
//	 *            starting point
//	 * @param b
//	 *            ending point
//	 */
//	private ArrayList<PathfindingNode> A_Star_Path(Position a, Position b) {
//		closed_list.clear();
//		open_list.clear();
//		PathfindingNode start_node = new PathfindingNode(a.getX(), a.getY(), false, false);
//		PathfindingNode goal_node = new PathfindingNode(b.getX(), b.getY(), false, false);
//		PathfindingNode current_node;
//
//		start_node.g = 0;
//		open_list.add(start_node);
//		while (open_list.size() != 0) {
//			current_node = get_lowest_f(open_list);
//			if (current_node == goal_node) {
//				return construct_path(current_node);
//			}
//			open_list.remove(get_node_index(open_list, current_node));
//			closed_list.add(current_node);
//			ArrayList<PathfindingNode> neighbors = Utils.get_neighbors(g, current_node, closed_list, open_list);
//			for (int i = 0; i < neighbors.size(); i++) {
//				PathfindingNode neighbor = neighbors.get(i);
//				if (closed_list.contains(neighbor)) {
//					continue;
//				}
//				double temp_g_score = 0.0;
//				if (neighbor.corner == true) {
//					temp_g_score = current_node.g + (1.414 * Utils.AI_NODE_PIXEL_DISTANCE);
//				} else {
//					temp_g_score = current_node.g + (1.0 * Utils.AI_NODE_PIXEL_DISTANCE);
//				}
//				if (open_list.contains(neighbor) == false) {
//					open_list.add(neighbor);
//				} else if (temp_g_score >= neighbor.g) {
//					continue;
//				}
//				neighbor.set_prev(ai.get_entity_id(), current_node);
//				neighbor.g = temp_g_score;
//				neighbor.f = neighbor.g + heuristic(neighbor, goal_node);
//			}
//		}
//		//it couldn't find a path
//		//System.out.println("couldn't find path");
//		return null;
//	}
//
//	/**
//	 * gets node with lowest f value from list
//	 * 
//	 * @param a
//	 *            list of nodes
//	 * @return node with lowest f
//	 */
//	private PathfindingNode get_lowest_f(ArrayList<PathfindingNode> a) {
//		PathfindingNode lowest = a.get(0);
//		for (int i = 0; i < a.size(); i++) {
//			try {
//				if (a.get(i).f < lowest.f) {
//					lowest = a.get(i);
//				}
//			} catch (Exception e) {
//				return a.get(0);
//			}
//		}
//		return lowest;
//	}
//
//	private int get_node_index(ArrayList<PathfindingNode> a, PathfindingNode b) {
//		for (int i = 0; i < a.size(); i++) {
//			if (a.get(i) == b) {
//				return i;
//			}
//		}
//		//System.out.println("get node index issue");
//		return 0;
//	}
//
//	/**
//	 * estimated cost to get to goal from current node
//	 * 
//	 * @param one
//	 *            first node
//	 * @param two
//	 *            second node
//	 * @return calculated cost which in this case is just the distance
//	 */
//	private double heuristic(PathfindingNode one, PathfindingNode two) {
//		return Math.sqrt(Math.pow((one.x - two.x), 2) + Math.pow((one.y - two.y), 2));
//	}
//
//	private ArrayList<PathfindingNode> construct_path(PathfindingNode cur) {
//		ArrayList<PathfindingNode> path = new ArrayList<PathfindingNode>();
//		PathfindingNode temp = cur;
//		path.add(temp);
//		while (temp.get_prev(ai.get_entity_id()) != null) {
//
//			path.add(temp.get_prev(ai.get_entity_id()));
//			// reseting the reference for the next path
//			temp.set_prev(ai.get_entity_id(), null);
//			// get next node in path
//			temp = path.get(path.size() - 1);
//		}
//		return path;
//	}
//
//}
