package day01;

public class NotQuiteLisp {
	private static String instructions;
	private static int stepIndex;
	private static int currentFloor;
	
	public static int endingOnFloor(String input) {
		beginSteps(input);
		while (stepIndex < instructions.length()) {
			doStep();
		}
		return currentFloor;
	}
	
	public static int firstEnteringBasement(String input) {
		beginSteps(input);
		while (stepIndex < instructions.length() && currentFloor != -1) {
			doStep();
		}
		return stepIndex;
	}
	
	public static void doStep() {
		char currentStep = instructions.charAt(stepIndex);
		if (currentStep == ')') {
			currentFloor--;
		}
		else if (currentStep == '(') {
			currentFloor++;
		}
		stepIndex++;
	}
	
	public static void beginSteps(String input) {
		instructions = input;
		stepIndex = 0;
		currentFloor = 0;
	}
}
