package Year2015.day20;

import java.util.ArrayList;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class InfiniteElvesAndInfiniteHouses extends RunPuzzle {
	public InfiniteElvesAndInfiniteHouses(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		return new ArrayList<TestCase>();
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		int minPresents = (Integer)input;
		int presents = 0;
		int house = 0;
		while (presents < minPresents) {
			house++;
			presents = 0;
			int elfMax = (int) Math.ceil(Math.sqrt(house));
			for (int i = 1; i <= elfMax; i++) {
				if (house % i == 0) {
					if (section == 1) {
						presents += i + house / i;
					}
					else {
						if (house / i <= 50) {
							presents += i;
						}
						if (i <= 50) {
							presents += house / i;
						}
					}
				}
			}
			presents *= (section == 1 ? 10 : 11);
			if (debug && house % 10000 == 0) System.out.println("House " + house + " gets " + presents + " presents");
		}
		return house;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new InfiniteElvesAndInfiniteHouses(20, "Infinite Elves and Infinite Houses", 29000000);
		puzzle.run();
	}

}
