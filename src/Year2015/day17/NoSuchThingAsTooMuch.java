package Year2015.day17;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class NoSuchThingAsTooMuch extends RunPuzzle {
	boolean debug = false;
	int totalCount, minContainersCount, minContainersUsed;

	public NoSuchThingAsTooMuch(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<PuzzleInput, Integer>(1, testInput, 4));
		tests.add(new TestCase<PuzzleInput, Integer>(2, testInput, 3));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		log("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		PuzzleInput i = (PuzzleInput)input;
		logDebug(i.total + " quantity and " + i.containers.length + " containers");
		
		ArrayList<Integer> availableMaster = new ArrayList<Integer>();
		for (int c : i.containers) {
			availableMaster.add(c);
		}
		
		totalCount = 0;
		minContainersCount = 0;
		minContainersUsed = availableMaster.size();
		fillOneContainer(availableMaster, i.total, 0);
		
		if (section == 1) return totalCount;
		else return minContainersCount;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new NoSuchThingAsTooMuch(17, "No Such Thing as Too Much", puzzleInput);
		puzzle.run();
	}
	
	private void fillOneContainer(ArrayList<Integer> available, int remaining, int containersUsed) {
		if (remaining == 0) {
			logDebug("Used " + containersUsed + " containers");
			totalCount++;
			if (containersUsed == minContainersUsed) {
				minContainersCount++;
			}
			else if (containersUsed < minContainersUsed) {
				minContainersUsed = containersUsed;
				minContainersCount = 1;
			}
			logDebug("Total: " + totalCount + " Min Containers: " + minContainersUsed + " with count: " + minContainersCount);
		}
		else {
			for (int i = 0; i < available.size(); i++) {
				int c = available.get(i);
				if (c <= remaining) {
					ArrayList<Integer> clone = (ArrayList<Integer>) available.clone();
					
					for (int j = i; j >= 0; j--) clone.remove(j);
					
					logDebug("Filling container " + c + " with " + (containersUsed + 1) + " containers used and " + (remaining - c) + " remaining");
					fillOneContainer(clone, remaining - c, containersUsed + 1);
				}
			}
		}
	}

	static class PuzzleInput {
		int total;
		int[] containers;
		
		PuzzleInput(int total, int[] containers) {
			this.total = total;
			this.containers = containers;
		}
	}
	static PuzzleInput testInput = new PuzzleInput(25, new int[] {20, 15, 10, 5, 5});
	static PuzzleInput puzzleInput = new PuzzleInput(150, new int[] {43, 3, 4, 10, 21, 44, 4, 6, 47, 41, 34, 17, 17, 44, 36, 31, 46, 9, 27, 38});
}
