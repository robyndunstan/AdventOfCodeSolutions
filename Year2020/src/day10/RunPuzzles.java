package day10;

public class RunPuzzles {

	public static void main(String[] args) {
		AdapterArray aa = new AdapterArray(testCase1);
		int diff1 = aa.CountJoltDifferences(1);
		int diff3 = aa.CountJoltDifferences(3);
		System.out.println("Part 1 - Test Case 1: " + diff1 + " x " + diff3 + " = " + (diff1 * diff3));
		aa.ParseData(testCase2);
		diff1 = aa.CountJoltDifferences(1);
		diff3 = aa.CountJoltDifferences(3);
		System.out.println("Part 1 - Test Case 2: " + diff1 + " x " + diff3 + " = " + (diff1 * diff3));
		aa.ParseData(puzzleInput);
		diff1 = aa.CountJoltDifferences(1);
		diff3 = aa.CountJoltDifferences(3);
		System.out.println("Part 1 - Puzzle: " + diff1 + " x " + diff3 + " = " + (diff1 * diff3));
		aa.ParseData(testCase1);
		System.out.println("Part 2 - Test Case 1: " + aa.CountValidArrangements() + " valid arrangements");
		aa.ParseData(testCase2);
		System.out.println("Part 2 - Test Case 2: " + aa.CountValidArrangements() + " valid arrangements");
		aa.ParseData(puzzleInput);
		System.out.println("Part 2 - Puzzle: " + aa.CountValidArrangements() + " valid arrangements");
	}
	
	static String[] testCase1 = {
			"16  ",
			"10  ",
			"15  ",
			"5   ",
			"1   ",
			"11  ",
			"7   ",
			"19  ",
			"6   ",
			"12  ",
			"4   "
	};
	
	static String[] testCase2 = {
			"28 ",
			"33 ",
			"18 ",
			"42 ",
			"31 ",
			"14 ",
			"46 ",
			"20 ",
			"48 ",
			"47 ",
			"24 ",
			"23 ",
			"49 ",
			"45 ",
			"19 ",
			"38 ",
			"39 ",
			"11 ",
			"1  ",
			"32 ",
			"25 ",
			"35 ",
			"8  ",
			"17 ",
			"7  ",
			"9  ",
			"4  ",
			"2  ",
			"34 ",
			"10 ",
			"3  "
	};
	
	static String[] puzzleInput = {
			"118    ",
			"14     ",
			"98     ",
			"154    ",
			"71     ",
			"127    ",
			"38     ",
			"50     ",
			"36     ",
			"132    ",
			"66     ",
			"121    ",
			"65     ",
			"26     ",
			"119    ",
			"46     ",
			"2      ",
			"140    ",
			"95     ",
			"133    ",
			"15     ",
			"40     ",
			"32     ",
			"137    ",
			"45     ",
			"155    ",
			"156    ",
			"97     ",
			"145    ",
			"44     ",
			"153    ",
			"96     ",
			"104    ",
			"58     ",
			"149    ",
			"75     ",
			"72     ",
			"57     ",
			"76     ",
			"56     ",
			"143    ",
			"11     ",
			"138    ",
			"37     ",
			"9      ",
			"82     ",
			"62     ",
			"17     ",
			"88     ",
			"33     ",
			"5      ",
			"10     ",
			"134    ",
			"114    ",
			"23     ",
			"111    ",
			"81     ",
			"21     ",
			"103    ",
			"126    ",
			"18     ",
			"8      ",
			"43     ",
			"108    ",
			"120    ",
			"16     ",
			"146    ",
			"110    ",
			"144    ",
			"124    ",
			"67     ",
			"79     ",
			"59     ",
			"89     ",
			"87     ",
			"131    ",
			"80     ",
			"139    ",
			"31     ",
			"115    ",
			"107    ",
			"53     ",
			"68     ",
			"130    ",
			"101    ",
			"22     ",
			"125    ",
			"83     ",
			"92     ",
			"30     ",
			"39     ",
			"102    ",
			"47     ",
			"109    ",
			"152    ",
			"1      ",
			"29     ",
			"86     "
	};
}
