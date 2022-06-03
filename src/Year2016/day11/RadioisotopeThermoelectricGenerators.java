package Year2016.day11;

import java.util.ArrayList;
import java.util.HashMap;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class RadioisotopeThermoelectricGenerators extends RunPuzzle {
	int minSteps;
	HashMap<Integer, ArrayList<String>> history;
	ArrayList<StringBuffer> stepsToDo;

	public RadioisotopeThermoelectricGenerators(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String, Integer>(1, testInput, 11));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String initialState = (String)input;
		if (section == 2) {
			initialState = initialState.substring(0, 1) + "1111" + initialState.substring(1);
		}
		
		history = new HashMap<Integer, ArrayList<String>>();
		stepsToDo = new ArrayList<StringBuffer>();
		minSteps = Integer.MAX_VALUE;
		stepsToDo.add(orderedStep(new StringBuffer(initialState)));
		int currStep = 0;
		
		while (stepsToDo.size() > 0) {
			StringBuffer s = stepsToDo.get(0);
			
			if (Integer.parseInt(s.substring(s.indexOf(",") + 1)) > currStep) {
				currStep = Integer.parseInt(s.substring(s.indexOf(",") + 1));
				if (debug) System.out.println("Processing step " + currStep + " with " + stepsToDo.size() + " possibilities");
			}
			
			findNextSteps(s);
			if (stepsToDo.size() > 0)
				stepsToDo.remove(0);
		}
		
		return minSteps;
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new RadioisotopeThermoelectricGenerators(11, "Radioisotope Thermoelectric Generators", puzzleInput);
		puzzle.run();
	}

	// first character is the elevator floor
	// every pair after that is a gen/mc pair floor numbers
	// comma before step number
	String testInput = "12131,0";
	static String puzzleInput = "11112123333,0";
	
	// All pairs are equivalent, so order pairs by position to cut down on combinations to test
	private StringBuffer orderedStep(StringBuffer step) {
		StringBuffer ordered = new StringBuffer("" + step.charAt(0)); // floor number
		//System.out.println(step);
		int comma = step.indexOf(",");
		int stepNum = Integer.parseInt(step.substring(comma + 1));
		
		ArrayList<String> pairs = new ArrayList<String>();
		for (int i = 1; i < comma; i = i + 2) {
			pairs.add(step.substring(i, i + 2));
		}
		
		while (pairs.size() > 0) {
			int indOfSmallest = 0;
			for (int i = 1; i < pairs.size(); i++) {
				if (pairs.get(i).compareTo(pairs.get(indOfSmallest)) < 0) {
					indOfSmallest = i;
				}
			}
			ordered.append(pairs.get(indOfSmallest));
			pairs.remove(indOfSmallest);
		}
		
		ordered.append("," + stepNum);
		return ordered;
	}
	
	private void findNextSteps(StringBuffer s) {
		int step = Integer.parseInt(s.substring(s.indexOf(",") + 1));
		if (step >= minSteps) {
			return;
		}
		
		// check if we've been here before
		StringBuffer status = new StringBuffer(s.substring(0, s.indexOf(",")));
		if (checkHistory(status)) {
			return;
		}
		else {
			addToHistory(status);
		}
		
		if (!status.substring(1).contains("1") && !status.substring(1).contains("2") && !status.substring(1).contains("3")) {
			minSteps = Math.min(step, minSteps);
		}
		
		if (areChipsSafe(status)) {
			int newStep = step + 1;
			for (int i = 1; i < status.length(); i++) {
				// item on this floor
				if (status.charAt(i) == status.charAt(0)) {

					// move up
					StringBuffer copy = new StringBuffer(status.toString());
					if (status.charAt(0) < '4') {
						copy.setCharAt(i, (char)(copy.charAt(i) + 1));
						copy.setCharAt(0, (char)(copy.charAt(0) + 1));
						
						if (areChipsSafe(copy) && !checkHistory(copy)) {
							copy.append("," + newStep);
							stepsToDo.add(orderedStep(copy));
						}
						
						// second item
						for (int j = i + 1; j < status.length(); j++) {
							if (status.charAt(j) == status.charAt(0)) {
								copy = new StringBuffer(status.toString());
								copy.setCharAt(i, (char)(copy.charAt(i) + 1));
								copy.setCharAt(j, (char)(copy.charAt(j) + 1));
								copy.setCharAt(0, (char)(copy.charAt(0) + 1));
								
								if (areChipsSafe(copy) && !checkHistory(copy)) {
									copy.append("," + newStep);
									stepsToDo.add(orderedStep(copy));
								}
							}
						}
					}
					
					// move down
					copy = new StringBuffer(status.toString());
					if (status.charAt(0) > '1') {
						copy.setCharAt(i, (char)(copy.charAt(i) - 1));
						copy.setCharAt(0, (char)(copy.charAt(0) - 1));
						
						if (areChipsSafe(copy) && !checkHistory(copy)) {
							copy.append("," + newStep);
							stepsToDo.add(orderedStep(copy));
						}
						
						// second item
						for (int j = i + 1; j < status.length(); j++) {
							if (status.charAt(j) == status.charAt(0)) {
								copy = new StringBuffer(status.toString());
								copy.setCharAt(i, (char)(copy.charAt(i) - 1));
								copy.setCharAt(j, (char)(copy.charAt(j) - 1));
								copy.setCharAt(0, (char)(copy.charAt(0) - 1));
								
								if (areChipsSafe(copy) && !checkHistory(copy)) {
									copy.append("," + newStep);
									stepsToDo.add(orderedStep(copy));
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean checkHistory(StringBuffer status) {
		if (history.containsKey(status.toString().hashCode()))
			for (String s : history.get(status.toString().hashCode())) {
				if (s.equals(status.toString())) return true;
			}
		return false;
	}

	private void addToHistory(StringBuffer status) {
		int hc = status.toString().hashCode();
		if (!history.containsKey(hc))
			history.put(hc, new ArrayList<String>());
		history.get(hc).add(status.toString());
	}
	
	private static boolean areChipsSafe(StringBuffer status) {
		StringBuffer gens = new StringBuffer();
		StringBuffer mcs = new StringBuffer();
		
		for (int i = 1; i < status.length(); i = i + 2) {
			gens.append(status.charAt(i));
			mcs.append(status.charAt(i + 1));
		}
		
		for (int i = 0; i < mcs.length(); i++) {
			if (mcs.charAt(i) != gens.charAt(i) && gens.toString().contains("" + mcs.charAt(i))) 
				return false;
		}
		return true;
	}
}
