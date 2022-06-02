package Year2015.day09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class AllInASingleNight extends tools.RunPuzzle {
	private HashSet<String> locationList;
	static HashMap<Integer, String[]> paths;
	private ArrayList<TravelLeg> distanceList;

	public AllInASingleNight(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new AllInASingleNight(9, "All in a Single Night", legs);
		puzzle.run();
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, testLegs, 605));
		tests.add(new TestCase<String[], Integer>(2, testLegs, 982));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String[] in = (String[])input;
		locationList = new HashSet<String>();
		paths = new HashMap<Integer, String[]>();
		distanceList = new ArrayList<TravelLeg>();
		
		for (String s : in) {
			parseLeg(s);
		}
		
		int pathCount = factorial(locationList.size()) / 2;
		int loopMax = locationList.size() * ((int) Math.pow(locationList.size(), locationList.size()));
		int i = 0;
		while (paths.size() < pathCount) {
			doRandomPath();
			i++;
			if (i == loopMax) {
				break;
			}
		}
		
		if (section == 1) {
			int minD = Integer.MAX_VALUE;
			for (int d : paths.keySet()) {
				if (d < minD) {
					minD = d;
				}
			}
			return minD;
		}
		else {
			int maxD = 0;
			for (int d : paths.keySet()) {
				if (d > maxD) {
					maxD = d;
				}
			}
			return maxD;
		}
	}
	
	private int factorial(int i) {
		int result = 1;
		for (int j = 2; j <= i; j++) {
			result *= j;
		}
		return result;
	}
	
	private void doRandomPath() {
		String[] locs = new String[locationList.size()];
		Random rand = new Random(System.currentTimeMillis());
		
		ArrayList<String> yetToVisit = new ArrayList<String>();
		for (String l : locationList) yetToVisit.add(l);
		
		int dist = 0;
		for (int i = 0; i < locationList.size(); i++) {
			String nextLoc = yetToVisit.remove(rand.nextInt(yetToVisit.size()));
			locs[i] = nextLoc;
			if (i > 0) {
				dist += getDistance(locs[i - 1], locs[i]);
			}
		}
		paths.put(dist, locs);
	}
	
	private int getDistance(String l1, String l2) {
		for (TravelLeg t : distanceList) {
			if ((t.loc1.equals(l1) && t.loc2.equals(l2)) || (t.loc1.equals(l2) && t.loc2.equals(l1))) {
				return t.d;
			}
		}
		return 0;
	}
	
	private void parseLeg(String l) {
		int ind1 = l.indexOf(" ");
		String loc1 = l.substring(0, ind1).trim();
		int ind2 = l.indexOf("=");
		String loc2 = l.substring(ind1 + 3, ind2).trim();
		int d = Integer.parseInt(l.substring(ind2 + 1).trim());
		
		locationList.add(loc1);
		locationList.add(loc2);
		distanceList.add(new TravelLeg(loc1, loc2, d));
	}
	
	static class TravelLeg {
		String loc1, loc2;
		int d;
		
		public TravelLeg(String loc1, String loc2, int d) {
			this.loc1 = loc1;
			this.loc2 = loc2;
			this.d = d;
		}
	}

	static String[] testLegs = {
			"London to Dublin = 464                ",
			"London to Belfast = 518               ",
			"Dublin to Belfast = 141               "
		};
		
		static String[] legs = {
			"Tristram to AlphaCentauri = 34        ",
			"Tristram to Snowdin = 100             ",
			"Tristram to Tambi = 63                ",
			"Tristram to Faerun = 108              ",
			"Tristram to Norrath = 111             ",
			"Tristram to Straylight = 89           ",
			"Tristram to Arbre = 132               ",
			"AlphaCentauri to Snowdin = 4          ",
			"AlphaCentauri to Tambi = 79           ",
			"AlphaCentauri to Faerun = 44          ",
			"AlphaCentauri to Norrath = 147        ",
			"AlphaCentauri to Straylight = 133     ",
			"AlphaCentauri to Arbre = 74           ",
			"Snowdin to Tambi = 105                ",
			"Snowdin to Faerun = 95                ",
			"Snowdin to Norrath = 48               ",
			"Snowdin to Straylight = 88            ",
			"Snowdin to Arbre = 7                  ",
			"Tambi to Faerun = 68                  ",
			"Tambi to Norrath = 134                ",
			"Tambi to Straylight = 107             ",
			"Tambi to Arbre = 40                   ",
			"Faerun to Norrath = 11                ",
			"Faerun to Straylight = 66             ",
			"Faerun to Arbre = 144                 ",
			"Norrath to Straylight = 115           ",
			"Norrath to Arbre = 135                ",
			"Straylight to Arbre = 127             "
		};
}
