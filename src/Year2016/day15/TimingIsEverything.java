package Year2016.day15;

import java.util.ArrayList;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class TimingIsEverything extends RunPuzzle {

	public TimingIsEverything(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, testInput, 5));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String[] discDetails = (String[])input;
		ArrayList<Disc> discs = new ArrayList<Disc>();
		for (String s : discDetails) {
			discs.add(new Disc(s));
		}
		if (section == 2) discs.add(new Disc(discs.size() + 1, 0, 11));
		
		int time = 0;
		while (!willPassthroughAllDiscs(time, discs)) {
			int testTime = time;
			for (Disc d : discs) {
				testTime = Math.max(testTime, d.getNextGoodStartTime(time));
			}
			time = testTime;
		}
		
		return time;
	}
	
	boolean willPassthroughAllDiscs(int time, ArrayList<Disc> discs) {
		boolean passthrough = true;
		for (Disc d : discs) {
			passthrough = passthrough && d.willPassthrough(time);
		}
		return passthrough;
	}
	
	class Disc {
		int location, startPosition, totalPositions;
		Disc(int l, int s, int t) {
			this.location = l;
			this.startPosition = s;
			this.totalPositions = t;
		}
		Disc(String input) {
			parseInput(input);
		}
		
		void parseInput(String s) {
			int startIndex = s.indexOf("#");
			int endIndex = s.indexOf(" ", startIndex);
			location = Integer.parseInt(s.substring(startIndex + 1, endIndex));
			
			startIndex = s.indexOf("has") + "has".length();
			endIndex = s.indexOf("positions");
			totalPositions = Integer.parseInt(s.substring(startIndex, endIndex).trim());
			
			startIndex = s.lastIndexOf(" ");
			endIndex = s.indexOf(".");
			startPosition = Integer.parseInt(s.substring(startIndex + 1, endIndex));
		}
		
		int getNextGoodStartTime(int start) {
			boolean passThroughDisc = false;
			do {
				start++;
				int timeAtDisc = start + this.location;
				int discPosition = (timeAtDisc + startPosition) % totalPositions;
				passThroughDisc = discPosition == 0;
			} while (!passThroughDisc);
			return start;
		}
		
		boolean willPassthrough(int time) {
			int timeAtDisc = time + this.location;
			int discPosition = (timeAtDisc + startPosition) % totalPositions;
			return discPosition == 0;
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new TimingIsEverything(15, "Timing is Everything", puzzleInput);
		puzzle.run();
	}

	String[] testInput = {
			"Disc #1 has 5 positions; at time=0, it is at position 4.",
			"Disc #2 has 2 positions; at time=0, it is at position 1."
	};
	
	static String[] puzzleInput = {
			"Disc #1 has 17 positions; at time=0, it is at position 5.",
			"Disc #2 has 19 positions; at time=0, it is at position 8.",
			"Disc #3 has 7 positions; at time=0, it is at position 1.",
			"Disc #4 has 13 positions; at time=0, it is at position 7.",
			"Disc #5 has 5 positions; at time=0, it is at position 1.",
			"Disc #6 has 3 positions; at time=0, it is at position 0."
	};
}
