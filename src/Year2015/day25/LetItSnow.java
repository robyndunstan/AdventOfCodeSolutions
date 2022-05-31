package Year2015.day25;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import tools.RunPuzzle;
import tools.TestCase;

public class LetItSnow extends RunPuzzle {
	boolean debug = false;
	static HashMap<Integer, HashMap<Integer, Long>> codes;

	public LetItSnow(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Point, Long>(1, new Point(1, 1), 20151125l));
		tests.add(new TestCase<Point, Long>(1, new Point(1, 2), 18749137l));
		tests.add(new TestCase<Point, Long>(1, new Point(1, 3), 17289845l));
		tests.add(new TestCase<Point, Long>(1, new Point(1, 4), 30943339l));
		tests.add(new TestCase<Point, Long>(1, new Point(1, 5), 10071777l));
		tests.add(new TestCase<Point, Long>(1, new Point(1, 6), 33511524l));
		tests.add(new TestCase<Point, Long>(1, new Point(2, 1), 31916031l));
		tests.add(new TestCase<Point, Long>(1, new Point(2, 2), 21629792l));
		tests.add(new TestCase<Point, Long>(1, new Point(2, 3), 16929656l));
		tests.add(new TestCase<Point, Long>(1, new Point(2, 4), 7726640l));
		tests.add(new TestCase<Point, Long>(1, new Point(2, 5), 15514188l));
		tests.add(new TestCase<Point, Long>(1, new Point(2, 6), 4041754l));
		tests.add(new TestCase<Point, Long>(1, new Point(3, 1), 16080970l));
		tests.add(new TestCase<Point, Long>(1, new Point(3, 2), 8057251l));
		tests.add(new TestCase<Point, Long>(1, new Point(3, 3), 1601130l));
		tests.add(new TestCase<Point, Long>(1, new Point(3, 4), 7981243l));
		tests.add(new TestCase<Point, Long>(1, new Point(3, 5), 11661866l));
		tests.add(new TestCase<Point, Long>(1, new Point(3, 6), 16474243l));
		tests.add(new TestCase<Point, Long>(1, new Point(4, 1), 24592653l));
		tests.add(new TestCase<Point, Long>(1, new Point(4, 2), 32451966l));
		tests.add(new TestCase<Point, Long>(1, new Point(4, 3), 21345942l));
		tests.add(new TestCase<Point, Long>(1, new Point(4, 4), 9380097l));
		tests.add(new TestCase<Point, Long>(1, new Point(4, 5), 10600672l));
		tests.add(new TestCase<Point, Long>(1, new Point(4, 6), 31527494l));
		tests.add(new TestCase<Point, Long>(1, new Point(5, 1), 77061l));
		tests.add(new TestCase<Point, Long>(1, new Point(5, 2), 17552253l));
		tests.add(new TestCase<Point, Long>(1, new Point(5, 3), 28094349l));
		tests.add(new TestCase<Point, Long>(1, new Point(5, 4), 6899651l));
		tests.add(new TestCase<Point, Long>(1, new Point(5, 5), 9250759l));
		tests.add(new TestCase<Point, Long>(1, new Point(5, 6), 31663883l));
		tests.add(new TestCase<Point, Long>(1, new Point(6, 1), 33071741l));
		tests.add(new TestCase<Point, Long>(1, new Point(6, 2), 6796745l));
		tests.add(new TestCase<Point, Long>(1, new Point(6, 3), 25397450l));
		tests.add(new TestCase<Point, Long>(1, new Point(6, 4), 24659492l));
		tests.add(new TestCase<Point, Long>(1, new Point(6, 5), 1534922l));
		tests.add(new TestCase<Point, Long>(1, new Point(6, 6), 27995004l));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Long)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		if (section == 1) {
			Point p = (Point)input;
			
			if (codes == null) {
				codes = new HashMap<Integer, HashMap<Integer, Long>>();
				codes.put(1, new HashMap<Integer, Long>());
				codes.get(1).put(1, 20151125l);
			}
			if (debug) System.out.println("(" + p.x + ", " + p.y + ") " + isCalculated(p));
			if (isCalculated(p)) {
				return codes.get(p.x).get(p.y);
			}
			else {
				// Find the closest calculated point in the diagonal sequence
				Point testPoint = new Point(p.x, p.y);
				while (!isCalculated(testPoint)) {
					if (testPoint.y > 1) {
						testPoint.y--;
						testPoint.x++;
					}
					else {
						testPoint.y = testPoint.x - 1;
						testPoint.x = 1;
					}
				}
				if (debug) System.out.println("Closest point is (" + testPoint.x + ", " + testPoint.y + ")");
				if (debug) System.out.println("Target point is (" + p.x + ", " + p.y + ") " + isCalculated(p));
				while (testPoint.x != p.x || testPoint.y != p.y) {
					testPoint = calculateForNextPoint(testPoint);
					if (debug) System.out.println("Calculated point (" + testPoint.x + ", " + testPoint.y + ")");
				}
				return codes.get(p.x).get(p.y);
			}
		}
		else {
			return 50l;
		}
	}
	
	Point calculateForNextPoint(Point prev) {
		long prevValue = codes.get(prev.x).get(prev.y);
		long nextValue = prevValue * 252533;
		nextValue %= 33554393;
		
		Point next = new Point();
		if (prev.x > 1) {
			next.x = prev.x - 1;
			next.y = prev.y + 1;
			if (debug) System.out.println("From (" + prev.x + ", " + prev.y + ") to (" + next.x + ", " + next.y + ")");
		}
		else {
			next.x = prev.y + 1;
			next.y = 1;
			if (debug) System.out.println("From (" + prev.x + ", " + prev.y + ") to new line (" + next.x + ", " + next.y + ")");
		}
		
		if (!codes.containsKey(next.x)) {
			codes.put(next.x, new HashMap<Integer, Long>());
		}
		codes.get(next.x).put(next.y, nextValue);
		
		return next;
	}
	
	boolean isCalculated(Point p) {
		return codes.containsKey(p.x) && codes.get(p.x).containsKey(p.y); 
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new LetItSnow(25, "Let It Snow", new Point(2947, 3029));
		puzzle.run();
	}
}
