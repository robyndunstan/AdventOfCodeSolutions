package Year2015.day17;

import java.util.ArrayList;
import java.util.HashMap;

import Year2015.day16.AuntSue;
import tools.RunPuzzle;
import tools.TestCase;

public class NoSuchThingAsTooMuch extends RunPuzzle {

	public NoSuchThingAsTooMuch(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<PuzzleInput, Integer>(1, testInput, 4));
		tests.add(new TestCase<PuzzleInput, Integer>(2, testInput, 17));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		PuzzleInput i = (PuzzleInput)input;
		
		ArrayList<Integer> availableMaster = new ArrayList<Integer>();
		for (int c : i.containers) {
			availableMaster.add(c);
		}
		
		ArrayList<Integer> filled = new ArrayList<Integer>();
		HashMap<Integer, Integer> combinations = new HashMap<Integer, Integer>();
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new NoSuchThingAsTooMuch(17, "No Such Thing as Too Much", puzzleInput);
		puzzle.run();
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
