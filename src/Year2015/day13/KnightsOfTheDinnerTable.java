package Year2015.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import tools.RunPuzzle;
import tools.TestCase;

public class KnightsOfTheDinnerTable extends RunPuzzle {
	static boolean debug = false;

	public KnightsOfTheDinnerTable(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, "src\\Year2015\\day13\\data\\test1File", 330));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		log("\t\t\t\t" + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		HashSet<String> guests = new HashSet<String>();
		HashMap<String, HashMap<String, Integer>> happiness = new HashMap<String, HashMap<String, Integer>>();
		try {
			parseFile((String)input, guests, happiness);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
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
							logDebug("Switched " + i + " and " + j + " - new happiness is " + newHappy);
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

	private static String puzzleFile = "src\\Year2015\\day13\\data\\puzzleFile";
	
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
			StringBuilder sb = new StringBuilder();
			sb.append("Test arrangement: ");
			for (int i = 0; i < arr.size(); i++) {
				if (i > 0) sb.append(", ");
				sb.append(arr.get(i));
			}
			logDebug(sb.toString() + " with happiness " + sum);
		}
		
		return sum;
	}
	
	private void parseFile(String file, HashSet<String> guests, HashMap<String, HashMap<String, Integer>> happiness) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line = br.readLine();
		while (line != null) {
			parseLine(line.trim(), guests, happiness);
			line = br.readLine();
		}
		
		br.close();
		logDebug("Number of guests: " + guests.size());
	}
	
	private void parseLine(String line, HashSet<String> guests, HashMap<String, HashMap<String, Integer>> happiness) {
		int i1 = line.indexOf(" ");
		String currGuest = line.substring(0, i1);
		logDebug("Guest " + currGuest);
		guests.add(currGuest);
		
		if (!happiness.containsKey(currGuest)) {
			happiness.put(currGuest, new HashMap<String, Integer>());
		}
		
		i1 = line.lastIndexOf(" ");
		String nextTo = line.substring(i1 + 1, line.length() - 1);
		logDebug("Next to " + nextTo);
		
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
		logDebug("Happiness " + positive + " " + happy);
		
		happiness.get(currGuest).put(nextTo, happy);
	}
}
