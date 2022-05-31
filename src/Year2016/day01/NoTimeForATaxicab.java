package Year2016.day01;

import java.awt.Point;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class NoTimeForATaxicab extends RunPuzzle {

	public NoTimeForATaxicab(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, test1Input, 5));
		tests.add(new TestCase<String, Integer>(1, test2Input, 2));
		tests.add(new TestCase<String, Integer>(1, test3Input, 12));
		tests.add(new TestCase<String, Integer>(2, test4Input, 4));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String[] instructions = ((String)input).split(",");
		Point position = new Point(0, 0);
		double direction = Math.PI / 2; // North, 90 deg.
		ArrayList<Point> history = new ArrayList<Point>();
		
		for (String s : instructions) {
			s = s.trim();
			if (s.charAt(0) == 'R') {
				direction -= Math.PI / 2;
			}
			else {
				direction += Math.PI / 2;
			}
			int distance = Integer.parseInt(s.substring(1));
			
			if (section == 1) {
				position.x += distance * Math.round(Math.cos(direction));
				position.y += distance * Math.round(Math.sin(direction));
			}
			else {
				for (int i = 0; i < distance; i++) {
					position.x += Math.round(Math.cos(direction));
					position.y += Math.round(Math.sin(direction));
					
					for (Point p : history) {
						if (p.x == position.x && p.y == position.y) {
							return getTaxicabDistance(new Point(0, 0), position);
						}
					}
					history.add(new Point(position.x, position.y));
				}
			}
		}
		return getTaxicabDistance(new Point(0, 0), position);
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new NoTimeForATaxicab(1, "No Time for a Taxicab", puzzleInput);
		puzzle.run();
	}
	
	int getTaxicabDistance(Point start, Point end) {
		return Math.abs(end.x - start.x) + Math.abs(end.y - start.y); 
	}

	String test1Input = "R2, L3";
	String test2Input = "R2, R2, R2";
	String test3Input = "R5, L5, R5, R3";
	String test4Input = "R8, R4, R4, R8";
	static String puzzleInput = "L5, R1, L5, L1, R5, R1, R1, L4, L1, L3, R2, R4, L4, L1, L1, R2, R4, R3, L1, R4, L4, L5, L4, R4, L5, R1, R5, L2, R1, R3, L2, L4, L4, R1, L192, R5, R1, R4, L5, L4, R5, L1, L1, R48, R5, R5, L2, R4, R4, R1, R3, L1, L4, L5, R1, L4, L2, L5, R5, L2, R74, R4, L1, R188, R5, L4, L2, R5, R2, L4, R4, R3, R3, R2, R1, L3, L2, L5, L5, L2, L1, R1, R5, R4, L3, R5, L1, L3, R4, L1, L3, L2, R1, R3, R2, R5, L3, L1, L1, R5, L4, L5, R5, R2, L5, R2, L1, L5, L3, L5, L5, L1, R1, L4, L3, L1, R2, R5, L1, L3, R4, R5, L4, L1, R5, L1, R5, R5, R5, R2, R1, R2, L5, L5, L5, R4, L5, L4, L4, R5, L2, R1, R5, L1, L5, R4, L3, R4, L2, R3, R3, R3, L2, L2, L2, L1, L4, R3, L4, L2, R2, R5, L1, R2";
}
