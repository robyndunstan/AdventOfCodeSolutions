package day02;

public class IWasToldThereWouldBeNoMath {
	private static String[] input;
	
	public static int getSumPaperNeeded(String[] input) {
		int sum = 0;
		for (String in : input) {
			sum += getPaperNeeded(parseOneInput(in));
		}
		return sum;
	}
	
	public static int getSumRibbonNeeded(String[] input) {
		int sum = 0;
		for (String in : input) {
			sum += getRibbonNeeded(parseOneInput(in));
		}
		return sum;
	}
	
	private static int getPaperNeeded(int[] present) {
		return 2*present[0]*present[1] + 2*present[0]*present[2] + 2*present[1]*present[2] + Math.min(present[0]*present[1], Math.min(present[0]*present[2], present[1]*present[2]));
	}
	
	private static int getRibbonNeeded(int[] present) {
		return Math.min(2*present[0] + 2*present[1], Math.min(2*present[0] + 2*present[2], 2*present[1] + 2*present[2])) + present[0]*present[1]*present[2];
	}
	
	private static int[] parseOneInput(String input) {
		input = input.trim();
		int[] dims = new int[3];
		
		int xInd = input.indexOf("x");
		String ns = input.substring(0, xInd);
		try {
			dims[0] = Integer.parseInt(ns);
		}
		catch (Exception e) {
			dims[0] = 0;
		}
		
		input = input.substring(xInd + 1);
		xInd = input.indexOf("x");
		ns = input.substring(0, xInd);
		try {
			dims[1] = Integer.parseInt(ns);
		}
		catch (Exception e) {
			dims[1] = 0;
		}
		
		input = input.substring(xInd + 1);
		try {
			dims[2] = Integer.parseInt(input);
		}
		catch (Exception e) {
			dims[2] = 0;
		}
		
		return dims;
	}
}
