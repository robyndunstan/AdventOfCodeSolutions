package Year2015.day03;

import java.awt.Point;
import java.util.ArrayList;

public class PerfectlySphericalHousesInAVacuum {
	public static int getSantaHouseCount(String instructions) throws Exception {
		Santa santa = new Santa();
		ArrayList<Point> history = new ArrayList<Point>();
		history.add(santa.getPosition());
		
		for (char c : instructions.toCharArray()) {
			santa.doMove(c);
			
			Point p = santa.getPosition();
			boolean matchFound = false;
			for (Point h : history) {
				if (h.x == p.x && h.y == p.y) {
					matchFound = true;
					break;
				}
			}
			if (!matchFound) {history.add(p);}
		}
		
		return history.size();
	}
	
	public static int getSantaAndRobotHouseCount(String instructions) throws Exception {
		Santa santa = new Santa();
		Santa robot = new Santa();
		ArrayList<Point> history = new ArrayList<Point>();
		history.add(santa.getPosition());
		
		for (int i = 0; i < instructions.length(); i++) {
			Point p;
			if (i % 2 == 0) {
				santa.doMove(instructions.charAt(i));
				p = santa.getPosition();
			}
			else {
				robot.doMove(instructions.charAt(i));
				p = robot.getPosition();
			}
			
			boolean matchFound = false;
			for (Point h : history) {
				if (h.x == p.x && h.y == p.y) {
					matchFound = true;
					break;
				}
			}
			if (!matchFound) {history.add(p);}
		}
		
		return history.size();
	}

	private static class Santa {
		private Point position;
		
		public Santa() {
			position = new Point(0, 0);
		}
		
		public Point getPosition() {
			return new Point(position.x, position.y);
		}
		
		public void doMove(char instruction) throws Exception {
			switch(instruction) {
			case '^':
				position.y--;
				break;
			case '>':
				position.x++;
				break;
			case 'v':
				position.y++;
				break;
			case '<':
				position.x--;
				break;
			default:
				throw new Exception("Invalid movement instruction " + instruction);
			}
		}
	}
}
