package Year2015.day23;

import java.util.ArrayList;

import tools.Constants;
import tools.RunPuzzle;
import tools.TestCase;

public class OpeningTheTuringLock extends RunPuzzle {
	public OpeningTheTuringLock(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, testInput, 2));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(Constants.resultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		Computer c = new Computer((String[])input);
		return c.runProgram(section);
	}
	
	enum Operator {
		Half, Triple, Increase, Jump, JumpIfEven, JumpIfOne
	}
	
	class Instruction {
		Operator op;
		char r;
		int offset;
		
		Instruction(String input) {
			input = input.trim();
			switch(input.substring(0, 3)) {
			case "hlf":
				op = Operator.Half;
				r = input.charAt(4);
				offset = 0;
				break;
			case "tpl":
				op = Operator.Triple;
				r = input.charAt(4);
				offset = 0;
				break;
			case "inc":
				op = Operator.Increase;
				r = input.charAt(4);
				offset = 0;
				break;
			case "jmp":
				op = Operator.Jump;
				r = ' ';
				offset = Integer.parseInt(input.substring(4).trim());
				break;
			case "jie":
				op = Operator.JumpIfEven;
				r = input.charAt(4);
				offset = Integer.parseInt(input.substring(6).trim());
				break;
			case "jio":
				op = Operator.JumpIfOne;
				r = input.charAt(4);
				offset = Integer.parseInt(input.substring(6).trim());
				break;
			}
		}
	}
	
	class Computer {
		ArrayList<Instruction> program;
		int cursor;
		int registerA, registerB;
		
		Computer(String[] input) {
			this.parseInstructions(input);
		}
		
		void parseInstructions(String[] input) {
			program = new ArrayList<Instruction>();
			for (String s : input) {
				program.add(new Instruction(s));
			}
			if (debug) System.out.println("Parsed " + program.size() + " instructions");
		}
		
		int runProgram(int section) {
			cursor = 0;
			registerB = 0;
			registerA = section == 1 ? 0 : 1;
			
			while (cursor >= 0 && cursor < program.size()) {
				Instruction i = program.get(cursor);
				if (debug) System.out.println("Do instruction " + cursor + ": " + i.op + ", " + i.r + ", " + i.offset + " (values " + registerA + ", " + registerB + ", cursor " + cursor + ")");
				
				switch(i.op) {
				case Half:
					if (i.r == 'a') registerA /= 2;
					else registerB /= 2;
					cursor++;
					break;
				case Increase:
					if (i.r == 'a') registerA++;
					else registerB++;
					cursor++;
					break;
				case Jump:
					cursor += i.offset;
					break;
				case JumpIfEven:
					if ((i.r == 'a' && registerA % 2 == 0) || (i.r == 'b' && registerB % 2 == 0)) cursor += i.offset;
					else cursor++;
					break;
				case JumpIfOne:
					if ((i.r == 'a' && registerA == 1) || (i.r == 'b' && registerB == 1)) cursor += i.offset;
					else cursor++;
					break;
				case Triple:
					if (i.r == 'a') registerA *= 3;
					else registerB *= 3;
					cursor++;
					break;
				}
				if (debug) System.out.println("\tNew values " + registerA + ", " + registerB + ", cursor " + cursor);
			}
			
			return registerB;
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new OpeningTheTuringLock(23, "Opening the Turing Lock", puzzleInput);
		puzzle.run();
	}
	
	String[] testInput = {
			"inc b",
		"	jio b, +2",
			"tpl b",
			"inc b"
	};

	static String[] puzzleInput = {
			"jio a, +19    ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"tpl a         ",
			"tpl a         ",
			"inc a         ",
			"inc a         ",
			"tpl a         ",
			"tpl a         ",
			"inc a         ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"inc a         ",
			"tpl a         ",
			"jmp +23       ",
			"tpl a         ",
			"tpl a         ",
			"inc a         ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"inc a         ",
			"tpl a         ",
			"inc a         ",
			"inc a         ",
			"tpl a         ",
			"tpl a         ",
			"inc a         ",
			"jio a, +8     ",
			"inc b         ",
			"jie a, +4     ",
			"tpl a         ",
			"inc a         ",
			"jmp +2        ",
			"hlf a         ",
			"jmp -7        "
	};
}
