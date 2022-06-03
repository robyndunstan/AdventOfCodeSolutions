package Year2016;

import java.util.ArrayList;
import java.util.HashMap;

public class AssembunnyComputer {
	private HashMap<Character, Integer> registers;
	private ArrayList<Instruction> program;
	private int cursor;
	
	public AssembunnyComputer() {
		registers = new HashMap<Character, Integer>();
		registers.put('a', 0);
		registers.put('b', 0);
		registers.put('c', 0);
		registers.put('d', 0);
		
		program = new ArrayList<Instruction>();
		cursor = -1;
	}
	
	public void parseProgram(String[] input) {
		program = new ArrayList<Instruction>();
		for (String s : input) {
			program.add(new Instruction(s));
		}
	}
	
	public void setRegister(char c, int value) {
		registers.put(c, value);
	}
	
	public int getRegister(char c) {
		return registers.get(c);
	}
	
	public void runProgram() {
		cursor = 0;
		while (cursor >= 0 && cursor < program.size()) {
			Instruction i = program.get(cursor);
			switch (i.op) {
			case Copy:
				registers.put(i.arg2.register, i.arg1.getValue());
				cursor++;
				break;
			case Decrease:
				registers.put(i.arg1.register, registers.get(i.arg1.register) - 1);
				cursor++;
				break;
			case Increase:
				registers.put(i.arg1.register, registers.get(i.arg1.register) + 1);
				cursor++;
				break;
			case JumpNotZero:
				if (i.arg1.getValue() != 0)
					cursor += i.arg2.getValue();
				else 
					cursor++;
				break;
			}
		}
	}
	
	enum Operator {
		Copy, Increase, Decrease, JumpNotZero
	}
	
	class Instruction {
		Operator op;
		Operand arg1, arg2;
		
		Instruction() {}
		Instruction(String input) {
			parseInstruction(input);
		}
		
		void parseInstruction(String input) {
			input = input.trim();
			String[] parts = input.split("\s+");
			switch (parts[0]) {
			case "cpy":
				op = Operator.Copy;
				break;
			case "inc":
				op = Operator.Increase;
				break;
			case "dec":
				op = Operator.Decrease;
				break;
			case "jnz":
				op = Operator.JumpNotZero;
				break;
			}
			
			arg1 = new Operand(parts[1]);
			if (parts.length > 2) {
				arg2 = new Operand(parts[2]);
			}
		}
	}
	
	class Operand {
		boolean isRegister;
		int value;
		char register;
		
		Operand() {
			isRegister = false;
			value = 0;
		}
		Operand(String input) {
			this();
			parseOperand(input);
		}
		
		void parseOperand(String input) {
			input = input.trim();
			char c = input.charAt(0);
			if (c == 'a' || c == 'b' || c == 'c' || c == 'd') {
				isRegister = true;
				register = c;
			}
			else {
				isRegister = false;
				value = Integer.parseInt(input);
			}
		}
		
		int getValue() {
			if (isRegister)
				return registers.get(register);
			else
				return value;
		}
	}
}
