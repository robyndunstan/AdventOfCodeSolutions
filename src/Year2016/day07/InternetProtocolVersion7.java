package Year2016.day07;

import java.util.ArrayList;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class InternetProtocolVersion7 extends RunPuzzle {

	public InternetProtocolVersion7(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, test1Input, 1));
		tests.add(new TestCase<String[], Integer>(1, test2Input, 0));
		tests.add(new TestCase<String[], Integer>(1, test3Input, 0));
		tests.add(new TestCase<String[], Integer>(1, test4Input, 1));
		tests.add(new TestCase<String[], Integer>(2, test5Input, 1));
		tests.add(new TestCase<String[], Integer>(2, test6Input, 0));
		tests.add(new TestCase<String[], Integer>(2, test7Input, 1));
		tests.add(new TestCase<String[], Integer>(2, test8Input, 1));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String[] ips = (String[])input;
		int count = 0;
		
		for (String i : ips) {
			Ip ip = new Ip(i);
			if ((section == 1 && isTls(ip)) || (section == 2 && isSsl(ip))) count++;
		}
		return count;
	}
	
	private boolean isTls(Ip ip) {
		boolean foundOutsideAbba = false;
		for (String s : ip.outsideBrackets) {
			if (hasAbba(s)) {
				foundOutsideAbba = true;
				break;
			}
		}
		
		if (foundOutsideAbba) {
			for (String s : ip.insideBrackets) {
				if (hasAbba(s)) {
					return false;
				}
			}
		}
		
		return foundOutsideAbba;
	}
	
	private boolean hasAbba(String s) {
		boolean foundAbba = false;
		for (int i = 0; i < s.length() - 3; i++) {
			char c1 = s.charAt(i);
			char c2 = s.charAt(i + 1);
			if (c1 != c2 && c1 == s.charAt(i + 3) && c2 == s.charAt(i + 2)) {
				foundAbba = true;
				break;
			}
		}
		return foundAbba;
	}
	
	private boolean isSsl(Ip ip) {
		char charA, charB;
		for (String s : ip.outsideBrackets) {
			for (int i = 0; i < s.length() - 2; i++) {
				char c1 = s.charAt(i);
				char c2 = s.charAt(i + 1);
				if (c1 != c2 && c1 == s.charAt(i + 2)) {
					String bab = "" + c2 + c1 + c2;
					for (String t : ip.insideBrackets) {
						if (t.indexOf(bab) > -1) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	class Ip {
		ArrayList<String> insideBrackets, outsideBrackets;
		public Ip() {
			insideBrackets = new ArrayList<String>();
			outsideBrackets = new ArrayList<String>();
		}
		public Ip(String ip) {
			this();
			parseIp(ip);
		}
		public void parseIp(String ip) {
			insideBrackets = new ArrayList<String>();
			outsideBrackets = new ArrayList<String>();
			StringBuffer currentSection = new StringBuffer();
			
			for (int i = 0; i < ip.length(); i++) {
				char c = ip.charAt(i);
				switch(c) {
				case '[':
					if (!currentSection.isEmpty()) {
						outsideBrackets.add(currentSection.toString());
						currentSection = new StringBuffer();
					}
					break;
				case ']':
					if (!currentSection.isEmpty()) {
						insideBrackets.add(currentSection.toString());
						currentSection = new StringBuffer();
					}
					break;
				default:
					currentSection.append(c);
					break;
				}
			}
			// Save the last section
			if (!currentSection.isEmpty()) {
				outsideBrackets.add(currentSection.toString());
			}
		}
	}

	public static void main(String[] args) {
		DataFile data = new DataFile(2016, 7, "puzzleInput");
		RunPuzzle puzzle = new InternetProtocolVersion7(7, "Internet Protocol Version 7", data.getData());
		puzzle.run();
	}

	String[] test1Input = { "abba[mnop]qrst" };
	String[] test2Input = { "abcd[bddb]xyyx" };
	String[] test3Input = { "aaaa[qwer]tyui" };
	String[] test4Input = { "ioxxoj[asdfgh]zxcvbn" };
	String[] test5Input = { "aba[bab]xyz" };
	String[] test6Input = { "xyx[xyx]xyx" };
	String[] test7Input = { "aaa[kek]eke" };
	String[] test8Input = { "zazbz[bzb]cdb" };
}
