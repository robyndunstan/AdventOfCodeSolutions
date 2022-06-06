package Year2016.day13;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class AMazeOfTwistyLittleCubicles extends RunPuzzle {

	public AMazeOfTwistyLittleCubicles(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Input, Integer>(1, new Input(10, 7, 4), 11));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		Input i = (Input)input;
		Point start = new Point(1, 1);
		HashMap<Integer, HashMap<Integer, Integer>> minStepsToPoint = new HashMap<Integer, HashMap<Integer, Integer>>();
		minStepsToPoint.put(start.x, new HashMap<Integer, Integer>());
		minStepsToPoint.get(start.x).put(start.y, 0);
		int section2MaxSteps = 50;
		
		ArrayList<Point> nextSteps = new ArrayList<Point>();
		nextSteps.add(start);
		while (nextSteps.size() > 0) {
			Point p = nextSteps.remove(0);
			int currentStep = minStepsToPoint.get(p.x).get(p.y);
			if ((section == 1 
					&& (!minStepsToPoint.containsKey(i.target.x) 
							|| !minStepsToPoint.get(i.target.x).containsKey(i.target.y) 
							|| currentStep <= minStepsToPoint.get(i.target.x).get(i.target.y)))
					|| (section == 2 && currentStep < section2MaxSteps)) {
				if (p.x > 0 
						&& !isWall(new Point(p.x - 1, p.y), i.favoriteNumber) 
						&& (!minStepsToPoint.containsKey(p.x - 1) || !minStepsToPoint.get(p.x - 1).containsKey(p.y) || minStepsToPoint.get(p.x - 1).get(p.y) > currentStep + 1)) {
					if (!minStepsToPoint.containsKey(p.x - 1)) minStepsToPoint.put(p.x - 1, new HashMap<Integer, Integer>());
					minStepsToPoint.get(p.x - 1).put(p.y, currentStep + 1);
					nextSteps.add(new Point(p.x - 1, p.y));
				}
				
				if (p.y > 0 
						&& !isWall(new Point(p.x, p.y - 1), i.favoriteNumber) 
						&& (!minStepsToPoint.containsKey(p.x) || !minStepsToPoint.get(p.x).containsKey(p.y - 1) || minStepsToPoint.get(p.x).get(p.y - 1) > currentStep + 1)) {
					if (!minStepsToPoint.containsKey(p.x)) minStepsToPoint.put(p.x, new HashMap<Integer, Integer>());
					minStepsToPoint.get(p.x).put(p.y - 1, currentStep + 1);
					nextSteps.add(new Point(p.x, p.y - 1));
				}
				
				if (!isWall(new Point(p.x + 1, p.y), i.favoriteNumber) 
						&& (!minStepsToPoint.containsKey(p.x + 1) || !minStepsToPoint.get(p.x + 1).containsKey(p.y) || minStepsToPoint.get(p.x + 1).get(p.y) > currentStep + 1)) {
					if (!minStepsToPoint.containsKey(p.x + 1)) minStepsToPoint.put(p.x + 1, new HashMap<Integer, Integer>());
					minStepsToPoint.get(p.x + 1).put(p.y, currentStep + 1);
					nextSteps.add(new Point(p.x + 1, p.y));
				}
				
				if (!isWall(new Point(p.x, p.y + 1), i.favoriteNumber) 
						&& (!minStepsToPoint.containsKey(p.x) || !minStepsToPoint.get(p.x).containsKey(p.y + 1) || minStepsToPoint.get(p.x).get(p.y + 1) > currentStep + 1)) {
					if (!minStepsToPoint.containsKey(p.x)) minStepsToPoint.put(p.x, new HashMap<Integer, Integer>());
					minStepsToPoint.get(p.x).put(p.y + 1, currentStep + 1);
					nextSteps.add(new Point(p.x, p.y + 1));
				}
			}
		}
		
		if (section == 1) {
			return minStepsToPoint.get(i.target.x).get(i.target.y);
		}
		else {
			int pointCount = 0;
			for (HashMap<Integer, Integer> h : minStepsToPoint.values()) {
				pointCount += h.keySet().size();
			}
			return pointCount;
		}
	}
	
	boolean isWall(Point position, int number) {
		int temp = position.x * position.x + 3 * position.x + 2 * position.x * position.y + position.y + position.y * position.y;
		temp += number;
		String binary = Integer.toBinaryString(temp);
		
		int count = 0;
		for (char c : binary.toCharArray()) {
			if (c == '1') count++;
		}
		
		return count % 2 == 1;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new AMazeOfTwistyLittleCubicles(13, "A Maze of Twisty Little Cubicles", new Input(1358, 31, 39));
		puzzle.run();
	}

	static class Input {
		int favoriteNumber;
		Point target;
		Input(int n, int x, int y) {
			this.favoriteNumber = n;
			this.target = new Point(x, y);
		}
	}
}
