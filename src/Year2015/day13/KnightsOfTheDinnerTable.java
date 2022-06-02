package Year2015.day13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import tools.Constants;
import tools.DataFile;
import tools.RunPuzzle;
import tools.TestCase;

public class KnightsOfTheDinnerTable extends RunPuzzle {
	public KnightsOfTheDinnerTable(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, "test1File", 330));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		HashSet<String> guests = new HashSet<String>();
		HashMap<String, HashMap<String, Integer>> happiness = new HashMap<String, HashMap<String, Integer>>();
		parseFile((String)input, guests, happiness);
		if (section == 2) {
			addMe(guests, happiness);
		}
		
		ArrayList<String> arrange = new ArrayList<String>();
		for (String s : guests) {
			arrange.add(s);
		}
		int sumHappy = sumHappiness(arrange, happiness);
		
		boolean changed;
		do {
			changed = false;
			for (int i = 0; i < guests.size(); i++) {
				for (int j = i; j < guests.size(); j++) {
					ArrayList<String> newArr = copyArray(arrange);
					if (i != j) {
						newArr.set(i, arrange.get(j));
						newArr.set(j, arrange.get(i));
						int newHappy = sumHappiness(newArr, happiness);
						if (newHappy > sumHappy) {
							if (debug) System.out.println("Switched " + i + " and " + j + " - new happiness is " + newHappy);
							sumHappy = newHappy;
							arrange = newArr;
							changed = true;
						}
					}
				}
			}
		} while (changed);
		return sumHappy;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new KnightsOfTheDinnerTable(13, "Knights of the Dinner Table", puzzleFile);
		puzzle.run();
	}

	private static String puzzleFile = "puzzleFile";
	
	private void addMe(HashSet<String> guests, HashMap<String, HashMap<String, Integer>> happiness) {
		HashMap<String, Integer> meHappy = new HashMap<String, Integer>();
		for (String s : guests) {
			happiness.get(s).put("Me", 0);
			meHappy.put(s, 0);
		}
		guests.add("Me");
		happiness.put("Me", meHappy);
	}
	
	private ArrayList<String> copyArray(ArrayList<String> arr) {
		ArrayList<String> newArr = new ArrayList<String>();
		for (String s : arr) {
			newArr.add(s);
		}
		return newArr;
	}
	
	private int sumHappiness(ArrayList<String> arr, HashMap<String, HashMap<String, Integer>> happiness) {
		int sum = 0;
		
		for (int i = 0; i < arr.size(); i++) {
			HashMap<String, Integer> subHappy = happiness.get(arr.get(i));
			sum += subHappy.get(arr.get((i + 1) % arr.size()));
			sum += subHappy.get(arr.get((i + arr.size() - 1) % arr.size()));
		}
		
		if (debug) {
			System.out.print("Test arrangement: ");
			for (int i = 0; i < arr.size(); i++) {
				if (i > 0) System.out.print(", ");
				System.out.print(arr.get(i));
			}
			System.out.println(" with happiness " + sum);
		}
		
		return sum;
	}
	
	private void parseFile(String file, HashSet<String> guests, HashMap<String, HashMap<String, Integer>> happiness) {
		DataFile d = new DataFile(2015, 13, file);
		String[] data = d.getData();
		for (String s : data) {
			parseLine(s.trim(), guests, happiness);
		}
		if (debug) {
			System.out.println("Number of guests: " + guests.size());
		}
	}
	
	private void parseLine(String line, HashSet<String> guests, HashMap<String, HashMap<String, Integer>> happiness) {
		int i1 = line.indexOf(" ");
		String currGuest = line.substring(0, i1);
		if (debug) System.out.println("Guest " + currGuest);
		guests.add(currGuest);
		
		if (!happiness.containsKey(currGuest)) {
			happiness.put(currGuest, new HashMap<String, Integer>());
		}
		
		i1 = line.lastIndexOf(" ");
		String nextTo = line.substring(i1 + 1, line.length() - 1);
		if (debug) System.out.println("Next to " + nextTo);
		
		boolean positive;
		if (line.contains("gain")) {
			positive = true;
			i1 = line.indexOf("gain");
		}
		else {
			positive = false;
			i1 = line.indexOf("lose");
		}
		
		int i2 = line.indexOf("happiness");
		int happy = Integer.parseInt(line.substring(i1 + 4, i2).trim());
		if (!positive) happy *= -1;
		if (debug) System.out.println("Happiness " + positive + " " + happy);
		
		happiness.get(currGuest).put(nextTo, happy);
	}
}
