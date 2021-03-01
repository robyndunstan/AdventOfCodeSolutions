package day19;

public class RunPuzzles {

	public static void main(String[] args) {
		MonsterMessages mm = new MonsterMessages();
		mm.ParseRules(testCase1Rules);
		System.out.println("Part 1 - Test Case 1: " + mm.CountValidMessages(testCase1Messages));
	}

	static String[] testCase1Rules = {
			"0: 4 1 5       ",
			"1: 2 3 | 3 2   ",
			"2: 4 4 | 5 5   ",
			"3: 4 5 | 5 4   ",
			"4: \"a\"         ",
			"5: \"b\"         "
	};
	static String[] testCase1Messages = {
			"ababbb ",
			"bababa ",
			"abbbab ",
			"aaabbb ",
			"aaaabbb"
	};
}
