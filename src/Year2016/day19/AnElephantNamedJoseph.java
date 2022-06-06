package Year2016.day19;

import java.util.ArrayList;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class AnElephantNamedJoseph extends RunPuzzle {

	public AnElephantNamedJoseph(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Integer, Integer>(1, 5, 3));
		tests.add(new TestCase<Integer, Integer>(2, 5, 2));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		ArrayList<Integer> elves = new ArrayList<Integer>();
		for (int i = 0; i < (Integer)input; i++) {
			elves.add(i + 1);
		}
		if (debug) System.out.println("Start with " + elves.size() + " elves");
		
		int cursor = 0;
		while (elves.size() > 1) {
			if (debug) System.out.println("Current elf is " + elves.get(cursor));
			int removed;
			if (section == 1) {
				if (cursor < elves.size() - 1) {
					removed = elves.remove(cursor + 1);
					cursor++;
				}
				else {
					removed = elves.remove(0);
				}
			}
			else {
				int removeIndex = (cursor + elves.size() / 2) % elves.size();
				removed = elves.remove(removeIndex);
				if (removeIndex > cursor) cursor++;
			}
			if (debug) System.out.println("Removed elf " + removed);
			if (cursor == elves.size()) cursor = 0;
			if (elves.size() % 1000 == 0) System.out.println(Constants.resultIndent + "\t" + elves.size() + " elves still in the game");
		}
		
		return elves.get(0);
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new AnElephantNamedJoseph(19, "An Elephent Named Joseph", 3017957);
		puzzle.run();
	}

}
