package Year2015.day06;

import java.awt.Point;

public class ProbablyAFireHazard {
	public static int getLightCount(String[] input) {
		LightGrid lights = new LightGrid();
		for (String s : input) {
			Instruction inst = new Instruction(s);
			for (int x = inst.start.x; x <= inst.end.x; x++) {
				for (int y = inst.start.y; y <= inst.end.y; y++) {
					switch(inst.command) {
					case TOGGLE:
						lights.toggleLight(x, y);
						break;
					case TURN_OFF:
						lights.setLight(x, y, 0);
						break;
					case TURN_ON:
						lights.setLight(x, y, 1);
						break;
					}
				}
			}
		}
		return lights.getLightCount();
	}
	
	public static int getLightSum(String[] input) {
		LightGrid lights = new LightGrid();
		for (String s : input) {
			Instruction inst = new Instruction(s);
			for (int x = inst.start.x; x <= inst.end.x; x++) {
				for (int y = inst.start.y; y <= inst.end.y; y++) {
					switch(inst.command) {
					case TOGGLE:
						lights.changeLightLevel(x, y, 2);
						break;
					case TURN_OFF:
						lights.changeLightLevel(x, y, -1);
						break;
					case TURN_ON:
						lights.changeLightLevel(x, y, 1);
						break;
					}
				}
			}
		}
		return lights.getLightSum();
	}
	
	private static class LightGrid {
		int[][] grid;

		public LightGrid() {
			grid = new int[1000][1000];
		}
		
		public int getLight(int x, int y) {
			return grid[x][y];
		}
		public int getLight(Point p) {
			return getLight(p.x, p.y);
		}
		
		public void setLight(int x, int y, int v) {
			grid[x][y] = v;
		}
		public void setLight(Point p, int v) {
			setLight(p.x, p.y, v);
		}
		
		public void changeLightLevel(Point p, int v) {
			changeLightLevel(p.x, p.y, v);
		}
		public void changeLightLevel(int x, int y, int v) {
			grid[x][y] += v;
			if (grid[x][y] < 0) grid[x][y] = 0;
		}
		
		public void toggleLight(int x, int y) {
			if (grid[x][y] > 0) {
				grid[x][y] = 0;
			}
			else {
				grid[x][y] = 1;
			}
		}
		
		public int getLightCount() {
			int count = 0;
			for (int x = 0; x < 1000; x++) {
				for (int y = 0; y < 1000; y++) {
					if (grid[x][y] > 0) count++;
				}
			}
			return count;
		}
		
		public int getLightSum() {
			int sum = 0;
			for (int x = 0; x < 1000; x++) {
				for (int y = 0; y < 1000; y++) {
					sum += grid[x][y];
				}
			}
			return sum;
		}
	}
	
	private static class Instruction {
		public Command command;
		public Point start;
		public Point end;
		
		public Instruction() {
			command = Command.TURN_OFF;
			start = new Point();
			end = new Point();
		}
		public Instruction(String input) {
			parseInstruction(input);
		}
		
		public void parseInstruction(String input) {
			input = input.trim();
			
			if (input.startsWith("turn on")) {
				command = Command.TURN_ON;
				input = input.substring(7).trim();
			}
			else if (input.startsWith("turn off")) {
				command = Command.TURN_OFF;
				input = input.substring(8).trim();
			}
			else if (input.startsWith("toggle")) {
				command = Command.TOGGLE;
				input = input.substring(6).trim();
			}
			
			int startComma = input.indexOf(",");
			int startX = Integer.parseInt(input.substring(0, startComma));
			input = input.substring(startComma + 1).trim();
			int startSpace = input.indexOf(" ");
			int startY = Integer.parseInt(input.substring(0, startSpace));
			input = input.substring(startSpace + 1).trim();
			start = new Point(startX, startY);
			
			int endSpace = input.indexOf(" ");
			input = input.substring(endSpace).trim();
			int endComma = input.indexOf(",");
			int endX = Integer.parseInt(input.substring(0, endComma));
			int endY = Integer.parseInt(input.substring(endComma + 1).trim());
			end = new Point(endX, endY);
		}
	}
	
	static enum Command {
		TURN_OFF,
		TURN_ON,
		TOGGLE
	};
}
