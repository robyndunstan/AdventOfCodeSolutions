package Year2015.day14;

import java.util.ArrayList;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class ReindeerOlympics extends RunPuzzle {
	static int puzzleRaceTime = 2503;
	static int testRaceTime = 1000;

	public ReindeerOlympics(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new ReindeerOlympics(14, "Reindeer Olympics", puzzleInput);
		puzzle.run();
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, testInput, 1120));
		tests.add(new TestCase<String[], Integer>(2, testInput, 689));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		ArrayList<Reindeer> reindeer = new ArrayList<Reindeer>();
		for (String s : (String[])input) {
			reindeer.add(new Reindeer(s));
		}
		
		int raceTime = reindeer.size() == 2 ? testRaceTime : puzzleRaceTime;
		
		for (int i = 0; i < raceTime; i++) {
			for (Reindeer r : reindeer) {
				r.nextSecond();
			}
			getLeadReindeer(reindeer).score++;
			
			if (debug) {
				if (i % 100 == 0) {
					System.out.println("Race second " + i);
					for (Reindeer r : reindeer) {
						System.out.println("\t" + r.name + " " + r.distance + " " + r.score);
					}
				}
			}
		}
		
		if (debug) System.out.println("Winning reindeer is " + getLeadReindeer(reindeer).name);
		if (section == 1) {
			return getLeadReindeer(reindeer).distance;
		}
		else {
			int maxScore = 0;
			for (Reindeer r : reindeer) {
				maxScore = Math.max(maxScore, r.score);
			}
			return maxScore;
		}
	}
	
	private Reindeer getLeadReindeer(ArrayList<Reindeer> herd) {
		Reindeer leader = new Reindeer();
		for (Reindeer r : herd) {
			if (r.distance > leader.distance) {
				leader = r;
			}
		}
		return leader;
	}
	
	private class Reindeer {
		public int speed, runTime, restTime;
		public String name;
		public int score, distance, raceTime;
		
		public Reindeer(String input) {
			this();
			parseInput(input);
		}
		public Reindeer() {
			speed = 0;
			runTime = 0;
			restTime = 0;
			score = 0;
			distance = 0;
			raceTime = 0;
			name = "Reindeer";
		}
		
		private void parseInput(String input) {
			if (debug) System.out.println(input);
			int endIndex = input.indexOf("km/s");
			int startIndex = input.substring(0, endIndex - 1).lastIndexOf(" ");
			this.speed = Integer.parseInt(input.substring(startIndex, endIndex).trim());
			
			startIndex = input.substring(endIndex).indexOf("for") + endIndex;
			endIndex = input.substring(startIndex).indexOf("seconds") + startIndex;
			this.runTime = Integer.parseInt(input.substring(startIndex + 3, endIndex).trim());
			
			startIndex = input.lastIndexOf("for");
			endIndex = input.lastIndexOf("seconds");
			this.restTime = Integer.parseInt(input.substring(startIndex + 3, endIndex).trim());
			
			name = input.substring(0, input.indexOf(" "));
			if (debug) System.out.println(name + ", speed = " + speed + ", run time = " + runTime + ", rest time = " + restTime);
		}
		
		private boolean isRunning(int time) {
			time %= (runTime + restTime);
			if (time < runTime) {
				return true;
			}
			else {
				return false;
			}
		}
		
		public void nextSecond() {
			if (isRunning(raceTime)) {
				distance += speed;
			}
			raceTime++;
		}
	}
	
	static String[] testInput = {
			"Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
			"Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."
	};
	
	static String[] puzzleInput = {
			"Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds.  ",
			"Cupid can fly 8 km/s for 17 seconds, but then must rest for 114 seconds.    ",
			"Prancer can fly 18 km/s for 6 seconds, but then must rest for 103 seconds.  ",
			"Donner can fly 25 km/s for 6 seconds, but then must rest for 145 seconds.   ",
			"Dasher can fly 11 km/s for 12 seconds, but then must rest for 125 seconds.  ",
			"Comet can fly 21 km/s for 6 seconds, but then must rest for 121 seconds.    ",
			"Blitzen can fly 18 km/s for 3 seconds, but then must rest for 50 seconds.   ",
			"Vixen can fly 20 km/s for 4 seconds, but then must rest for 75 seconds.     ",
			"Dancer can fly 7 km/s for 20 seconds, but then must rest for 119 seconds.   "
	};
}
