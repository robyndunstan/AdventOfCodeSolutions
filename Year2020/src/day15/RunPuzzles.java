package day15;

public class RunPuzzles {

	public static void main(String[] args) {
		RambunctiousRecitation rr = new RambunctiousRecitation();
		rr.ParseStartingNumbers(testCase1);
		System.out.println("Part 1 - Test Case 1: " + rr.GetNumber(part1Count));
		System.out.println("Part 2 - Test Case 1: " + rr.GetNumber(part2Count));
		rr.ParseStartingNumbers(testCase2);
		System.out.println("Part 1 - Test Case 2: " + rr.GetNumber(part1Count));
		System.out.println("Part 2 - Test Case 2: " + rr.GetNumber(part2Count));
		rr.ParseStartingNumbers(testCase3);
		System.out.println("Part 1 - Test Case 3: " + rr.GetNumber(part1Count));
		System.out.println("Part 2 - Test Case 3: " + rr.GetNumber(part2Count));
		rr.ParseStartingNumbers(testCase4);
		System.out.println("Part 1 - Test Case 4: " + rr.GetNumber(part1Count));
		System.out.println("Part 2 - Test Case 4: " + rr.GetNumber(part2Count));
		rr.ParseStartingNumbers(testCase5);
		System.out.println("Part 1 - Test Case 5: " + rr.GetNumber(part1Count));
		System.out.println("Part 2 - Test Case 5: " + rr.GetNumber(part2Count));
		rr.ParseStartingNumbers(testCase6);
		System.out.println("Part 1 - Test Case 6: " + rr.GetNumber(part1Count));
		System.out.println("Part 2 - Test Case 6: " + rr.GetNumber(part2Count));
		rr.ParseStartingNumbers(testCase7);
		System.out.println("Part 1 - Test Case 7: " + rr.GetNumber(part1Count));
		System.out.println("Part 2 - Test Case 7: " + rr.GetNumber(part2Count));
		rr.ParseStartingNumbers(puzzleInput);
		System.out.println("Part 1 - Puzzle: " + rr.GetNumber(part1Count));
		System.out.println("Part 2 - Puzzle: " + rr.GetNumber(part2Count));
	}

	static int part1Count = 2020;
	static int part2Count = 30000000;
	
	static String testCase1 = "0,3,6"; // 436, 175594
	static String testCase2 = "1,3,2"; // 1, 2578
	static String testCase3 = "2,1,3"; // 10, 3544142
	static String testCase4 = "1,2,3"; // 27, 261214
	static String testCase5 = "2,3,1"; // 78, 6895259
	static String testCase6 = "3,2,1"; // 438, 18
	static String testCase7 = "3,1,2"; // 1836, 362
	static String puzzleInput = "6,13,1,15,2,0";
}
