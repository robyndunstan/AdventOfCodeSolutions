package day13;

public class RunPuzzles {

	public static void main(String[] args) {
		ShuttleSearch ss = new ShuttleSearch(testCase1Shuttles);
		System.out.println("Part 1 - Test Case 1: " + ss.FindNextShuttle(testCase1Time));
		ss.ParseShuttles(puzzleShuttles);
		System.out.println("Part 1 - Puzzle: " + ss.FindNextShuttle(puzzleTime));
		ss.ParseShuttles(testCase1Shuttles);
		System.out.println("Part 2 - Test Case 1: " + ss.FindValidStartTime());
		ss.ParseShuttles(puzzleShuttles);
		System.out.println("Part 2 - Puzzle: " + ss.FindValidStartTime());
	}

	static int testCase1Time = 939;
	static String testCase1Shuttles = "7,13,x,x,59,x,31,19";
	
	static int puzzleTime = 1002392;
	static String puzzleShuttles = "23,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,421,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17,x,19,x,x,x,x,x,x,x,x,x,29,x,487,x,x,x,x,x,x,x,x,x,x,x,x,13";
}
