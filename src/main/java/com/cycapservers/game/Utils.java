/**
 * @author bryanf
 */
package com.cycapservers.game;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import com.cycapservers.game.components.drawing.ImageSprite;
import com.cycapservers.game.components.positioning.PositionComponent;

public final class Utils
{
	public final static boolean DEBUG = true;
	public final static float GRAVITY = (float) -9.81;
	public final static int GRID_LENGTH = 32;
	public final static int UP    = 0b1000;
	public final static int DOWN  = 0b0100;
	public final static int LEFT  = 0b0010;
	public final static int RIGHT = 0b0001;
	public final static double SIN_45 = Math.sin(Math.PI/4);
	public final static int AI_NODE_PIXEL_DISTANCE = 16;
	public final static Random RANDOM = new Random();
	
	//////WEAPON TEMPLATES//////
//	public final static Shotgun REMINGTON_870 = new Shotgun("Remington870", 25, 500, 500, 5, 4, 6000, 0.35);
//	public final static Pistol M1911 = new Pistol("Pistol", 10, 175, 400, 8, 4, 200, 0.05);
//	public final static Shotgun SAWED_OFF_SHOTGUN = new Shotgun("Sawed-Off Shotgun", 37, 350, 550, 2, 10, 2500, 0.55);
//	public final static AutomaticGun SMG = new AutomaticGun("SMG", 5, 100, 600, 40, 4, 500, 0.1);
//	public final static AutomaticGun ASSAULT_RIFLE = new AutomaticGun("Assault Rifle", 7, 120, 550, 30, 3, 1200, 0.08);
//	public final static AutomaticGun MACHINE_GUN = new AutomaticGun("Machine Gun", 8, 134, 450, 100, 2, 1750, 0.15);
//	public final static MortarWeapon MORTAR = new MortarWeapon("Mortar Rounds", 40, 0, 1000, 1, 9, 3000, 3.0, 3000, 7);
//	public final static SmokeGrenadeLauncher SMOKE_GRENADE = new SmokeGrenadeLauncher("Smoke Grenade", 0, 2, 1200, 1, 5, 500, 2, 1500, 5, 5000, 1);
//	public final static HealthGun HEAL_GUN = new HealthGun("Heal Gun", 2, 180, 100, 0, 1000, (int) (GRID_LENGTH * 1.5));
//	
//	//////POWER-UP TEMPLATES//////
//	public final static SpeedPotion SPEED_POTION = new SpeedPotion(0, 0, GRID_LENGTH, GRID_LENGTH, 0, 1.0, "speed_pot_template");
//	public final static HealthPack HEALTH_PACK = new HealthPack(0, 0, GRID_LENGTH, GRID_LENGTH, 0, 1.0, "health_pack_template");
//	public final static AmmoPack AMMO_PACK = new AmmoPack(0, 0, GRID_LENGTH, GRID_LENGTH, 0, 1.0, "ammo_pack_template");
	//TODO: shield potion, rage serum, and chill pill
	
	//////BULLET TEMPLATES//////
	
	//////MAP TEMPLATES//////
//	public final static Map BaseMap = new Map();
	
	//////GAMEMODE/TYPE TEMPLATES//////
//	public final static CaptureTheFlag guestCTF = new CaptureTheFlag(-1, BaseMap, false, -1, 5000, true, 2*60*1000, 16);
	
	
	private Utils(){} //prevents the class from being constructed
	
	public static double sumDoubleArray(Double ... values) 
	{
		double sum = 0;
		for(double d : values)
			sum += d;
		return sum;
	}
	
	public static int sumIntArray(Integer ... integer) 
	{
		int sum = 0;
		for(int i : integer)
			sum += i;
		return sum;
	}
	
	/**
	 * returns true if num is between the parameters lower and upper, inclusive
	 * @param num
	 * @param lower
	 * @param upper
	 * @return
	 */
	public static boolean isBetween(double num, double lower, double upper)
	{
		if(num >= lower && num <= upper){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static double clamp(double lowerClamp, double value, double upperClamp)
	{
		return Math.max(lowerClamp, Math.min(value, upperClamp));
	}
	
	/**
	 * Returns the distance between two coordinate pairs
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the distance between the two points
	 */
	public static double distanceBetween(double x1, double y1, double x2, double y2) 
	{
		return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
	}
	
	public static PositionComponent GetScaleVector(PositionComponent p, double scale)
	{
		return new PositionComponent(p.getX() * scale, p.getY() * scale);
	}
	
	public static PositionComponent MakeUnitVector(PositionComponent p)
	{
		double mag = GetMagnitude(p);
		double new_x = p.getX() / mag;
		double new_y = p.getY() / mag;
		p.setX(new_x);
		p.setY(new_y);
		return p;
	}
	
	public static double GetMagnitude(PositionComponent p)
	{
		return distanceBetween(new PositionComponent(), p);
	}
	
	public static double distanceBetween(PositionComponent p1, PositionComponent p2)
	{
		return distanceBetween(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	/**
	 * Returns a unit length 2D vector representing the direction from one position to another
	 * @param to A destination position
	 * @param from A source position
	 * @return A unit-length vector representing the direction to-from
	 */
	public static PositionComponent getDirection(PositionComponent to, PositionComponent from) {
		PositionComponent diff = new PositionComponent(to.getX() - from.getX(), to.getY() - from.getY());
		double len = distanceBetween(new PositionComponent(), diff);
		return new PositionComponent(diff.getX() / len, diff.getY() / len);
	}
	
	/**
	 * Returns the difference between position a and position b
	 * The resulting Position is equal to a new Position instantiated
	 * with Position(a.x - b.x, a.y - b.y)
	 * @param a
	 * @param b
	 * @return
	 */
	public static PositionComponent difference(PositionComponent a, PositionComponent b) {
		return new PositionComponent(a.getX() - b.getX(), a.getY() - b.getY());
	}
	
	public static PositionComponent add(PositionComponent a, PositionComponent b)
	{
		return new PositionComponent(a.getX() + b.getX(), a.getY() + b.getY());
	}
	
	public static PositionComponent getRandomPositionInCircle(PositionComponent center, double radius){
		double angle = RANDOM.nextDouble() *  2 * Math.PI;
		double dist = (RANDOM.nextDouble() + RANDOM.nextDouble()) * radius;
		double r = (dist > radius) ? (2 * radius) - dist : dist;
		
		return new PositionComponent(Math.cos(angle) * r, Math.sin(angle) * r);
	}
	
	/**
	 * returns a random int between 0 and max, not including max
	 * @param max
	 * @return
	 */
	public static int getRandomInt(int max){
		return (int) Math.floor(Math.random() * max);
	}

	/**
	 * returns a random int between lower and upper, inclusive
	 * @param lower
	 * @param upper
	 * @return
	 */
	public static int getRandomInRange(int lower, int upper){
		return getRandomInt(upper - lower + 1) + lower;
	}
	
//	/**
//	 * Sets the role data of the player based off of their "role" field
//	 * @param p The player which we are setting the role/class data
//	 */
//	public static void setRole(Character p) {
//		String role = p.getRole();
//		if(role.equals("recruit")) {
//			p.setSpeed(140);
//			p.max_health = 100;
//			p.health = p.max_health;
//			p.equipment1 = new AutomaticGun(ASSAULT_RIFLE);
//			p.equipment2 = new Shotgun(REMINGTON_870);
//			p.equipment3 = null;
//			p.equipment4 = null;
//			p.setCurrentEquipment(p.equipment1);
//			p.visibility = 6;
//			return;
//		}
//		else if(role.equals("artillery")) {
//			p.setSpeed(120);
//			p.max_health = 85;
//			p.health = p.max_health;
//			p.equipment1 = new AutomaticGun(SMG);
//			p.equipment2 = new MortarWeapon(MORTAR);
//			p.equipment3 = null;
//			p.equipment4 = null;
//			p.setCurrentEquipment(p.equipment1);
//			p.visibility = 6;
//			return;
//		}
//		else if(role.equals("infantry")) {	
//			p.setSpeed(140);
//			p.max_health = 105;
//			p.health = p.max_health;
//			p.equipment1 = new AutomaticGun(MACHINE_GUN);
//			p.equipment2 = new SmokeGrenade(SMOKE_GRENADE);
//			p.equipment3 = new Pistol(M1911); //pistol
//			p.equipment4 = null;
//			p.setCurrentEquipment(p.equipment1);
//			p.visibility = 5;
//			return;
//		}
//		else if(role.equals("scout")) {	
//			p.setSpeed(180);
//			p.max_health = 75;
//			p.health = p.max_health;
//			p.equipment1 = new Shotgun(SAWED_OFF_SHOTGUN);
//			p.equipment2 = new Pistol(M1911); //pistol
//			p.equipment3 = null;
//			p.equipment4 = null;
//			p.setCurrentEquipment(p.equipment1);
//			p.visibility = 7;
//			return;
//		}
//		else {
//			throw new IllegalStateException("Player role is unacceptable!");
//		}
//	}
	
//	public static boolean checkLineOfSight(Entity ent1, Entity ent2, GameState g){
//		double delta_x = ent1.getX() - ent2.getX();
//		double delta_y = ent1.getY() - ent2.getY();
//		
//		double x_coord = ent1.getX(), y_coord = ent1.getY();
//		double divisions = 8.0;
//		for(int i = 0; i < divisions; i++){
//			
//			CollidingEntity e = new CollidingEntity(null, null, i);
//			try{
//				Point temp = get_nearest_map_node(e, g);
//				if(temp == null){
//					return false;
//				}
//			}catch(Exception ex){
//				return false;
//			}
//			
//			x_coord += delta_x / divisions;
//			y_coord += delta_y / divisions;
//		}
//		return true;
//	}
	
//	public static Point get_nearest_map_node(Entity e, GameState g) {
//		int x = (int) (Math.ceil(e.x / AI_NODE_PIXEL_DISTANCE) * AI_NODE_PIXEL_DISTANCE);
//		int y = (int) (Math.ceil(e.y / AI_NODE_PIXEL_DISTANCE) * AI_NODE_PIXEL_DISTANCE);
//		short i = 0, j = 0;
//		while (g.ai_map.get(i).get(j).y != y) {
//			j++;
//		}
//		while (g.ai_map.get(i).get(j).x != x) {
//			i++;
//		}
//		if (g.ai_map.get(i).get(j).node_trav != false) {
//			return new Point(i, j);
//		} else {
//			x = (int) (Math.floor(e.x / AI_NODE_PIXEL_DISTANCE) * AI_NODE_PIXEL_DISTANCE);
//			y = (int) (Math.floor(e.y / AI_NODE_PIXEL_DISTANCE) * AI_NODE_PIXEL_DISTANCE);
//			i = 0;
//			j = 0;
//			while (g.ai_map.get(i).get(j).y != y) {
//				j++;
//			}
//			while (g.ai_map.get(i).get(j).x != x) {
//				i++;
//			}
//			if (g.ai_map.get(i).get(j).node_trav != false) {
//				return new Point(i, j);
//			} else {
//				x = (int) (Math.floor(e.x / AI_NODE_PIXEL_DISTANCE) * AI_NODE_PIXEL_DISTANCE);
//				y = (int) (Math.ceil(e.y / AI_NODE_PIXEL_DISTANCE) * AI_NODE_PIXEL_DISTANCE);
//				i = 0;
//				j = 0;
//				while (g.ai_map.get(i).get(j).y != y) {
//					j++;
//				}
//				while (g.ai_map.get(i).get(j).x != x) {
//					i++;
//				}
//				if (g.ai_map.get(i).get(j).node_trav != false) {
//					return new Point(i, j);
//				} else {
//					x = (int) (Math.ceil(e.x / AI_NODE_PIXEL_DISTANCE) * AI_NODE_PIXEL_DISTANCE);
//					y = (int) (Math.floor(e.y / AI_NODE_PIXEL_DISTANCE) * AI_NODE_PIXEL_DISTANCE);
//					i = 0;
//					j = 0;
//					while (g.ai_map.get(i).get(j).y != y) {
//						j++;
//					}
//					while (g.ai_map.get(i).get(j).x != x) {
//						i++;
//					}
//					if (g.ai_map.get(i).get(j).node_trav != false) {
//						return new Point(i, j);
//					}
//					else {
//						
//					}
//				}
//			}
//		}
//		return null;
//	}
//	
//	public static ArrayList<PathfindingNode> get_neighbors(GameState g, PathfindingNode node, ArrayList<PathfindingNode> closed_list, ArrayList<PathfindingNode> open_list) {
//		ArrayList<PathfindingNode> neighbors = new ArrayList<PathfindingNode>();
//		if (g.ai_map.get(node.gridX - 1).get(node.gridY - 1).node_trav == true) {
//			neighbors.add(g.ai_map.get(node.gridX - 1).get(node.gridY - 1));
//			if (closed_list.contains(neighbors.get(neighbors.size() - 1)) == false
//						&& open_list.contains(neighbors.get(neighbors.size() - 1)) == false) {
//				neighbors.get(neighbors.size() - 1).g = node.g + (1.414 * AI_NODE_PIXEL_DISTANCE);
//			}
//			neighbors.get(neighbors.size() - 1).corner = true;
//		}
//		if (g.ai_map.get(node.gridX - 1).get(node.gridY).node_trav == true) {
//			neighbors.add(g.ai_map.get(node.gridX - 1).get(node.gridY));
//			if (closed_list.contains(neighbors.get(neighbors.size() - 1)) == false
//					&& open_list.contains(neighbors.get(neighbors.size() - 1)) == false) {
//				neighbors.get(neighbors.size() - 1).g = node.g + (1.0 * AI_NODE_PIXEL_DISTANCE);
//			}
//			neighbors.get(neighbors.size() - 1).corner = false;
//		}
//		if (g.ai_map.get(node.gridX - 1).get(node.gridY + 1).node_trav == true) {
//			neighbors.add(g.ai_map.get(node.gridX - 1).get(node.gridY + 1));
//			if (closed_list.contains(neighbors.get(neighbors.size() - 1)) == false
//					&& open_list.contains(neighbors.get(neighbors.size() - 1)) == false) {
//				neighbors.get(neighbors.size() - 1).g = node.g + (1.414 * AI_NODE_PIXEL_DISTANCE);
//			}
//			neighbors.get(neighbors.size() - 1).corner = true;
//		}
//		if (g.ai_map.get(node.gridX).get(node.gridY + 1).node_trav == true) {
//			neighbors.add(g.ai_map.get(node.gridX).get(node.gridY + 1));
//			if (closed_list.contains(neighbors.get(neighbors.size() - 1)) == false
//					&& open_list.contains(neighbors.get(neighbors.size() - 1)) == false) {
//				neighbors.get(neighbors.size() - 1).g = node.g + (1.0 * AI_NODE_PIXEL_DISTANCE);
//			}
//			neighbors.get(neighbors.size() - 1).corner = false;
//		}
//		// neighbor number 5
//		if (g.ai_map.get(node.gridX + 1).get(node.gridY + 1).node_trav == true) {
//			neighbors.add(g.ai_map.get(node.gridX + 1).get(node.gridY + 1));
//			if (closed_list.contains(neighbors.get(neighbors.size() - 1)) == false
//					&& open_list.contains(neighbors.get(neighbors.size() - 1)) == false) {
//				neighbors.get(neighbors.size() - 1).g = node.g + (1.414 * AI_NODE_PIXEL_DISTANCE);
//			}
//			neighbors.get(neighbors.size() - 1).corner = true;
//		}
//		if (g.ai_map.get(node.gridX + 1).get(node.gridY).node_trav == true) {
//			neighbors.add(g.ai_map.get(node.gridX + 1).get(node.gridY));
//			if (closed_list.contains(neighbors.get(neighbors.size() - 1)) == false
//					&& open_list.contains(neighbors.get(neighbors.size() - 1)) == false) {
//				neighbors.get(neighbors.size() - 1).g = node.g + (1.0 * AI_NODE_PIXEL_DISTANCE);
//			}
//			neighbors.get(neighbors.size() - 1).corner = false;
//		}
//		if (g.ai_map.get(node.gridX + 1).get(node.gridY - 1).node_trav == true) {
//			neighbors.add(g.ai_map.get(node.gridX + 1).get(node.gridY - 1));
//			if (closed_list.contains(neighbors.get(neighbors.size() - 1)) == false
//					&& open_list.contains(neighbors.get(neighbors.size() - 1)) == false) {
//				neighbors.get(neighbors.size() - 1).g = node.g + (1.414 * AI_NODE_PIXEL_DISTANCE);
//			}
//			neighbors.get(neighbors.size() - 1).corner = true;
//		}
//		if (g.ai_map.get(node.gridX).get(node.gridY - 1).node_trav == true) {
//			neighbors.add(g.ai_map.get(node.gridX).get(node.gridY - 1));
//			if (closed_list.contains(neighbors.get(neighbors.size() - 1)) == false
//					&& open_list.contains(neighbors.get(neighbors.size() - 1)) == false) {
//				neighbors.get(neighbors.size() - 1).g = node.g + (1.0 * AI_NODE_PIXEL_DISTANCE);
//			}
//			neighbors.get(neighbors.size() - 1).corner = false;
//		}
//		return neighbors;
//	}
	
	/**
	 * returns a randomized string with the specified length that does not exist in currentList
	 * @param currentList
	 * @param length
	 * @return
	 */
	public static String getGoodRandomString(List<String> currentList, int length) {
		String output = createString(length);
		while(currentList.contains(output)) {
			output = createString(length);
		}
		return output;
	}
	
	/**
	 * Generates a random string of a given length
	 * @param length
	 * @return
	 */
	public static String createString(int length){
		String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";
		Random rand = new Random();
		String pass = "";
		for(int i = 0; i < length; i++){
			pass += s.charAt(rand.nextInt(s.length()));	
		}
		return pass;
	}
	
	/**
	 * Takes in the current level and experience in an object, updates them to the new values and then returns them
	 * @param data
	 * @return
	 */
	public static Point calculateLevelAndXP(Point data) {
		int current_level = data.x;
		switch(current_level) {
			case 0:
				if(data.y >= 0) {
					data.x++;
					data.y -= 0;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 1:
				if(data.y >= 250) {
					data.x++;
					data.y -= 250;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 2:
				if(data.y >= 500) {
					data.x++;
					data.y -= 500;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 3:
				if(data.y >= 750) {
					data.x++;
					data.y -= 750;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 4:
				if(data.y >= 1000) {
					data.x++;
					data.y -= 1000;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 5:
				if(data.y >= 1125) {
					data.x++;
					data.y -= 1125;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 6:
				if(data.y >= 1250) {
					data.x++;
					data.y -= 1250;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 7:
				if(data.y >= 1375) {
					data.x++;
					data.y -= 1375;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 8:
				if(data.y >= 1500) {
					data.x++;
					data.y -= 1500;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 9:
				if(data.y >= 1600) {
					data.x++;
					data.y -= 1600;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 10:
				if(data.y >= 1700) {
					data.x++;
					data.y -= 1700;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 11:
				if(data.y >= 1775) {
					data.x++;
					data.y -= 1775;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 12:
				if(data.y >= 1850) {
					data.x++;
					data.y -= 1850;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 13:
				if(data.y >= 1925) {
					data.x++;
					data.y -= 1925;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 14:
				if(data.y >= 2000) {
					data.x++;
					data.y -= 2000;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 15:
				if(data.y >= 2050) {
					data.x++;
					data.y -= 2050;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 16:
				if(data.y >= 2100) {
					data.x++;
					data.y -= 2100;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 17:
				if(data.y >= 2150) {
					data.x++;
					data.y -= 2150;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 18:
				if(data.y >= 2200) {
					data.x++;
					data.y -= 2200;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 19:
				if(data.y >= 2250) {
					data.x++;
					data.y -= 2250;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 20:
				if(data.y >= 2300) {
					data.x++;
					data.y -= 2300;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 21:
				if(data.y >= 2350) {
					data.x++;
					data.y -= 2350;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 22:
				if(data.y >= 2400) {
					data.x++;
					data.y -= 2400;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 23:
				if(data.y >= 2450) {
					data.x++;
					data.y -= 2450;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			case 24:
				if(data.y >= 2500) {
					data.x++;
					data.y -= 2500;
					//intentional fallthrough to check for double level up
				}
				else {
					break;
				}
				
			default:
				break;
		}
		return data;
	}
	
	/**
	 * Takes in a map coordinate pair and returns the position of the grid in which it is placed.
	 * The top-left grid is at position (0,0)
	 * @param mapX - map x coordinate in pixels relative to the top left corner
	 * @param mapY - map y coordinate in pixels relative to the top left corner
	 * @return returns a point
	 */
	public static Point mapCoordinatesToGridCoordinates(double mapX, double mapY) {
		Point p = new Point();
		p.x = (int) Math.floor(mapX/Utils.GRID_LENGTH);
		p.y = (int) Math.floor(mapY/Utils.GRID_LENGTH);
		return p;
	}
	
	public static ImageSprite[] generateSpriteData(int s_width, int s_height, int rows, int cols){
		ImageSprite output[] = new ImageSprite[rows*cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				output[j + i*cols] = new ImageSprite(s_width*j, s_height*i, s_width, s_height);
			}
		}
		return output;
	}
	
	public static double roundToSpecifiedPlace(double num, int decimals) {
		if(decimals < 0) {
			throw new IllegalArgumentException("Error: decimal must be greater than or equal to 0");
		}
		double temp = Math.pow(10, decimals);
		return Math.round(num * temp)/temp;
	}
}