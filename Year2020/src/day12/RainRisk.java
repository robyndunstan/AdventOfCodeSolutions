package day12;

import java.awt.Point;

public class RainRisk {
	public static int doInstructions(String[] instructions, int puzzlePart) {
		Ship ship = new Ship();
		for (String i : instructions) {
			if (puzzlePart == 1) {
				ship.doInstruction(i);
			}
			else if (puzzlePart == 2) {
				ship.moveWaypoint(i);
			}
		}
		return ship.getDistance();
	}
	
	private static class Ship {
		Point position;
		int heading; // angle in degrees where the ship is pointed
		Point waypoint;
		
		public Ship() {reset();}
		
		public void reset() {
			position = new Point(0, 0);
			heading = 0;
			waypoint = new Point(10, 1);
		}
		
		public void moveWaypoint(String s) {
			s = s.trim();
			char op = s.charAt(0);
			int arg = Integer.parseInt(s.substring(1));
			double r, theta;
			switch(op) {
			case 'N':
				waypoint.y += arg;
				break;
			case 'S':
				waypoint.y -= arg;
				break;
			case 'E':
				waypoint.x += arg;
				break;
			case 'W':
				waypoint.x -= arg;
				break;
			case 'L':
				r = Math.hypot(waypoint.x, waypoint.y);
				theta = getWaypointAngle(waypoint);
				theta += arg * Math.PI / 180;
				waypoint.x = (int)Math.round(r * Math.cos(theta));
				waypoint.y = (int)Math.round(r * Math.sin(theta));
				break;
			case 'R':
				r = Math.hypot(waypoint.x, waypoint.y);
				theta = getWaypointAngle(waypoint);
				theta -= arg * Math.PI / 180;
				waypoint.x = (int)Math.round(r * Math.cos(theta));
				waypoint.y = (int)Math.round(r * Math.sin(theta));
				break;
			case 'F':
				position.x += arg * waypoint.x;
				position.y += arg * waypoint.y;
				break;
			}
		}
		
		private double getWaypointAngle(Point waypoint) {
			if (waypoint.x == 0) {
				return Math.signum(waypoint.y)* Math.PI / 2; 
			}
			else if (waypoint.x < 0) {
				return Math.atan((double)waypoint.y / waypoint.x) + Math.PI; 
			}
			else { // waypoint.x > 0
				return Math.atan((double)waypoint.y / waypoint.x);
			}
		}
		
		public void doInstruction(String s) {
			s = s.trim();
			char op = s.charAt(0);
			int arg = Integer.parseInt(s.substring(1));
			switch (op) {
			case 'N':
				position.y += arg;
				break;
			case 'S':
				position.y -= arg;
				break;
			case 'E':
				position.x += arg;
				break;
			case 'W':
				position.x -= arg;
				break;
			case 'L':
				heading += arg;
				break;
			case 'R':
				heading -= arg;
				break;
			case 'F':
				position.x += Math.round(arg * Math.cos(Math.PI * heading / 180));
				position.y += Math.round(arg * Math.sin(Math.PI * heading / 180));
				break;
			}
		}
		
		public int getDistance() {
			return Math.abs(position.x) + Math.abs(position.y);
		}
	}
}
