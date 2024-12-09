package Year2015.day24;

import java.util.ArrayList;
import java.util.List;

import tools.RunPuzzle;
import tools.TestCase;

public class ItHangsInTheBalance extends RunPuzzle {
	private boolean debug = false;
	int minQty;
	long minEnt;

	public ItHangsInTheBalance(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Integer[], Long>(1, testInput, 99l));
		tests.add(new TestCase<Integer[], Long>(2, testInput, 44l));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		log("\t\t\t\t" + (Long)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		int totalSum = 0;
		ArrayList<Integer> packages = new ArrayList<Integer>();
		for (int p : (Integer[])input) {
			totalSum += p;
			packages.add(p);
		}
		int targetSum = totalSum / (section == 1 ? 3 : 4);
		logDebug("Target sum: " + targetSum);
		
		minQty = Integer.MAX_VALUE;
		minEnt = Long.MAX_VALUE;
		addPackage(0, 1l, 0, packages, targetSum);
		return minEnt;
	}
	
	void addPackage(int groupQty, long groupEnt, int groupSum, List<Integer> packages, int targetSum) {
		for (int i = 0; i < packages.size(); i++) {
			int p = packages.get(i);
			if (targetSum == groupSum + p) {
				logDebug("Matched sum on " + (groupQty + 1) + " packages with entanglement " + (groupEnt * p));
				if (minQty == groupQty + 1) {
					minEnt = Math.min(minEnt, groupEnt * p);
				}
				else if (minQty > groupQty + 1) {
					minQty = groupQty + 1;
					minEnt = groupEnt * p;
				}
			}
			else if (targetSum > groupSum + p && minQty >= groupQty + 1) {
				logDebug("Add package " + p + " to group of " + groupQty + " packages with sum " + groupSum + " and entanglement " + groupEnt);
				addPackage(groupQty + 1, groupEnt * p, groupSum + p, packages.subList(i + 1, packages.size()), targetSum);
			}
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new ItHangsInTheBalance(24, "It Hangs in the Balance", puzzleInput);
		puzzle.run();
	}

	Integer[] testInput = {1, 2, 3, 4, 5, 7, 8, 9, 10, 11};
	static Integer[] puzzleInput = {
			1  ,
			2  ,
			3  ,
			5  ,
			7  ,
			13 ,
			17 ,
			19 ,
			23 ,
			29 ,
			31 ,
			37 ,
			41 ,
			43 ,
			53 ,
			59 ,
			61 ,
			67 ,
			71 ,
			73 ,
			79 ,
			83 ,
			89 ,
			97 ,
			101,
			103,
			107,
			109,
			113
	};
}
