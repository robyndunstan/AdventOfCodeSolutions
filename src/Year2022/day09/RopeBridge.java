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
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, test1File, 13));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String filename = (String)input;
		Rope rope = new Rope();
		
		ArrayList<Point> tailHistory = new ArrayList<Point>();
		tailHistory.add(new Point(rope.tail.x, rope.tail.y));
		
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
					if (!isInHistory(tailHistory, rope.tail)) {
						tailHistory.add(new Point(rope.tail.x, rope.tail.y));
					}
				}
				
				line = br.readLine();
			} while (line != null);
			br.close();
		} catch (IOException e) {
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
		Point head, tail;
		
		public Rope() {
			head = new Point();
			tail = new Point();
		}
		
		public void moveHead(Direction d) {
			switch (d) {
			case Down:
				head.y++;
				break;
			case Left:
				head.x++;
				break;
			case Right:
				head.x--;
				break;
			case Up:
				head.y--;
				break;
			}
			moveTail();
		}
		
		private void moveTail() {
			if (head.x == tail.x && Math.abs(head.y - tail.y) == 2) {
				tail.y += (head.y - tail.y)/2;
			}
			else if (head.y == tail.y && Math.abs(head.x - tail.x) == 2) {
				tail.x += (head.x - tail.x)/2;
			}
			else if (Math.abs(head.x - tail.x) + Math.abs(head.y - tail.y) == 3) {
				tail.x += (head.x - tail.x)/Math.abs(head.x - tail.x);
				tail.y += (head.y - tail.y)/Math.abs(head.y - tail.y);
			}
		}
		
		public static enum Direction {
			Right, Up, Left, Down
		}
	}

	static String test1File = "src\\Year2022\\day09\\data\\test1File";
	static String puzzleFile = "src\\Year2022\\day09\\data\\puzzleFile";

}
