package Year2022.day09;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class RopeBridge extends RunPuzzle {

	public RopeBridge(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		this.debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, test1File, 13));
		tests.add(new TestCase<String, Integer>(2, test1File, 1));
		tests.add(new TestCase<String, Integer>(2, test2File, 36));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String filename = (String)input;
		Rope rope;
		if (section == 1) {
			rope = new Rope(2, this.debug);
		}
		else {
			rope = new Rope(10, this.debug);
		}
		
		ArrayList<Point> tailHistory = new ArrayList<Point>();
		tailHistory.add(new Point(rope.getTail().x, rope.getTail().y));
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			do {
				int numSteps = Integer.parseInt(line.substring(2));
				Rope.Direction dir = Rope.Direction.Down;
				switch (line.charAt(0)) {
				case 'R': 
					dir = Rope.Direction.Right;
					break;
				case 'U':
					dir = Rope.Direction.Up;
					break;
				case 'L':
					dir = Rope.Direction.Left;
					break;
				case 'D':
					dir = Rope.Direction.Down;
					break;
				}
				
				for (int i = 0; i < numSteps; i++) {
					rope.moveHead(dir);
					if (!isInHistory(tailHistory, rope.getTail())) {
						tailHistory.add(new Point(rope.getTail().x, rope.getTail().y));
					}
				}
				
				line = br.readLine();
			} while (line != null);
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tailHistory.size();
	}
	
	private boolean isInHistory(ArrayList<Point> history, Point test) {
		for (Point p : history) {
			if (p.x == test.x && p.y == test.y)
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new RopeBridge(9, "Rope Bridge", puzzleFile);
		puzzle.run();
	}
	
	private static class Rope {
		Point[] points;
		private boolean debug;
		
		public Rope(int numPoints, boolean debug) {
			points = new Point[numPoints];
			for (int i = 0; i < numPoints; i++) {
				points[i] = new Point();
			}
			this.debug = debug;
		}
		
		public void moveHead(Direction d) throws Exception {
			switch (d) {
			case Down:
				points[0].y++;
				break;
			case Left:
				points[0].x--;
				break;
			case Right:
				points[0].x++;
				break;
			case Up:
				points[0].y--;
				break;
			}
			for (int i = 1; i < points.length; i++) moveTail(i, debug);
		}
		
		private void moveTail(int i, boolean debug) throws Exception {
			if (debug) System.out.println("Index " + i + ", Head (" + points[i - 1].x + ", " + points[i - 1].y + "), Tail (" + points[i].x + ", " + points[i].y + ")");
			if (points[i - 1].x == points[i].x && Math.abs(points[i - 1].y - points[i].y) == 2) {
				points[i].y += (points[i - 1].y - points[i].y)/2;
			}
			else if (points[i - 1].y == points[i].y && Math.abs(points[i - 1].x - points[i].x) == 2) {
				points[i].x += (points[i - 1].x - points[i].x)/2;
			}
			else if (Math.abs(points[i - 1].x - points[i].x) + Math.abs(points[i - 1].y - points[i].y) >= 3) {
				points[i].x += (points[i - 1].x - points[i].x)/Math.abs(points[i - 1].x - points[i].x);
				points[i].y += (points[i - 1].y - points[i].y)/Math.abs(points[i - 1].y - points[i].y);
			}
			if (debug) System.out.println("\tNew tail (" + points[i].x + ", " + points[i].y + ")");
			if (debug) {
				if (Math.abs(points[i - 1].x - points[i].x) > 1 || Math.abs(points[i - 1].y - points[i].y) > 1) {
					throw new Exception();
				}
			}
		}
		
		public Point getTail() {
			return points[points.length - 1];
		}
		
		public static enum Direction {
			Right, Up, Left, Down
		}
	}

	static String test1File = "src\\Year2022\\day09\\data\\test1File";
	static String test2File = "src\\Year2022\\day09\\data\\test2File";
	static String puzzleFile = "src\\Year2022\\day09\\data\\puzzleFile";

}
