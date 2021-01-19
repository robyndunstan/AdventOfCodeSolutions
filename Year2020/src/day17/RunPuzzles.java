package day17;

public class RunPuzzles {

	public static void main(String[] args) {
		ConwayCubes cc = new ConwayCubes(testCase1);
		System.out.println("Part 1 - Test Case 1: " + cc.GetActiveCountAfter6Steps(1));
		cc.ParseInitialState(puzzleInput);
		System.out.println("Part 1 - Puzzle: " + cc.GetActiveCountAfter6Steps(1));
		cc.ParseInitialState(testCase1);
		System.out.println("Part 2 - Test Case 1: " + cc.GetActiveCountAfter6Steps(2));
		cc.ParseInitialState(puzzleInput);
		System.out.println("Part 2 - Puzzle: " + cc.GetActiveCountAfter6Steps(2));
	}

	static String[] testCase1 = {
			".#.",
			"..#",
			"###"
	};
	
	static String[] puzzleInput = {
			"...#...#",
			"#######.",
			"....###.",
			".#..#...",
			"#.#.....",
			".##.....",
			"#.####..",
			"#....##."
	};
}
