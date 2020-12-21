package day08;

import java.util.ArrayList;

public class HandheldHalting {
	ArrayList<Instruction> program;
	int accumulator;
	int index;
	boolean repeatFound;
	
	public HandheldHalting() {
		program = new ArrayList<Instruction>();
	}
	public HandheldHalting(String[] input) {
		ParseInput(input);
	}
	
	public int FindError() {
		for (int i = 0; i < program.size(); i++) {
			Instruction oldInstruction = program.get(i);
			if (oldInstruction.op.contentEquals("nop") || oldInstruction.op.contentEquals("jmp")) {
				Instruction newInstruction = new Instruction();
				newInstruction.arg = oldInstruction.arg;
				newInstruction.op = oldInstruction.op.contentEquals("nop") ? "jmp" : "nop";
				program.set(i, newInstruction);
				RunProgram();
				program.set(i, oldInstruction);
				if (!repeatFound) {
					return accumulator;
				}
			}
		}
		return 0;
	}
	
	public void ParseInput(String[] input) {
		program = new ArrayList<Instruction>();
		for (String s : input) {
			s = s.trim();
			program.add(new Instruction(s));
		}
	}
	
	public void Reset() {
		accumulator = 0;
		index = 0;
		repeatFound = false;
		for (Instruction i : program) {
			i.ranInstruction = false;
		}
	}
	
	public int RunProgram() {
		Reset();
		
		while (!repeatFound && 0 <= index && program.size() > index) {
			Instruction current = program.get(index);
			
			if (current.ranInstruction) {
				repeatFound = true;
			}
			else {
				switch(current.op) {
				case "nop":
					index++;
					break;
				case "acc":
					accumulator += current.arg;
					index++;
					break;
				case "jmp":
					index += current.arg;
					break;
				}
				current.ranInstruction = true;
			}
		}
		
		return accumulator;
	}
	
	class Instruction {
		String op;
		int arg;
		boolean ranInstruction;
		
		public Instruction() {}
		public Instruction(String input) {
			ParseInput(input);
		}
		
		public void ParseInput(String input) {
			input = input.trim();
			op = input.substring(0, input.indexOf(" "));
			arg = Integer.parseInt(input.substring(input.indexOf(" ")).replace('+', ' ').trim());
		}
	}
}
