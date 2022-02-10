package Year2015.day07;

import java.util.ArrayList;
import java.util.HashMap;

public class SomeAssemblyRequired {
	public static HashMap<String, Integer> buildCircuit(Object[] input) {
		HashMap<String, Integer> wires = new HashMap<String, Integer>();
		ArrayList<Instruction> backlog = new ArrayList<Instruction>();
		
		for (Object o : input) {
			String s = (String)o;
			backlog.add(Instruction.parseInstruction(s));
		}
		while (!backlog.isEmpty()) {
			backlog = doInstructions(wires, backlog);
		}
		
		return wires;
	}
	
	static enum BitOp {
		SIGNAL, BIT_AND, BIT_OR, BIT_NOT, BIT_LEFT, BIT_RIGHT;
	}
	
	private static ArrayList<Instruction> doInstructions(HashMap<String, Integer> wires, ArrayList<Instruction> instructions) {
		ArrayList<Instruction> backlog = new ArrayList<Instruction>();
		
		for (Instruction i : instructions) {
			switch (i.op) {
			case SIGNAL:
				if (getInputValue(i.in1, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_AND:
				if (getInputValue(i.in1, wires) != null && getInputValue(i.in2, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires) & getInputValue(i.in2, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_OR:
				if (getInputValue(i.in1, wires) != null && getInputValue(i.in2, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires) | getInputValue(i.in2, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_NOT:
				if (getInputValue(i.in1, wires) != null) {
					wires.put(i.out, ~getInputValue(i.in1, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_LEFT:
				if (getInputValue(i.in1, wires) != null && getInputValue(i.in2, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires) << getInputValue(i.in2, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_RIGHT:
				if (getInputValue(i.in1, wires) != null && getInputValue(i.in2, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires) >> getInputValue(i.in2, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			}
			
			if (wires.containsKey(i.out) && wires.get(i.out)< 0) {
				wires.put(i.out, wires.get(i.out)+ 65536);
			}
		}
		
		return backlog;
	}
	
	private static Integer getInputValue(String input, HashMap<String, Integer> wires) {
		if (isNumber(input)) {
			return Integer.parseInt(input);
		}
		else if (wires.containsKey(input)) {
			return wires.get(input);
		}
		else {
			return null;
		}
	}
	
	private static class Instruction {
		public BitOp op;
		public String in1, in2, out;
		
		public static Instruction parseInstruction(String s) {
			Instruction i = new Instruction();
			
			if (s.contains("AND")) {
				i.op = BitOp.BIT_AND;
				int ind1 = s.indexOf("AND");
				i.in1 = s.substring(0, ind1).trim();
				int ind2 = s.indexOf("->");
				i.in2 = s.substring(ind1 + 3, ind2).trim();
				i.out = s.substring(ind2 + 2).trim();
			}
			else if (s.contains("OR")) {
				i.op = BitOp.BIT_OR;
				int ind1 = s.indexOf("OR");
				i.in1 = s.substring(0, ind1).trim();
				int ind2 = s.indexOf("->");
				i.in2 = s.substring(ind1 + 3, ind2).trim();
				i.out = s.substring(ind2 + 2).trim();
			}
			else if (s.contains("NOT")) {
				i.op = BitOp.BIT_NOT;
				int ind1 = s.indexOf("->");
				i.in1 = s.substring(3, ind1).trim();
				i.in2 = "";
				i.out = s.substring(ind1 + 2).trim();
			}
			else if (s.contains("LSHIFT")) {
				i.op = BitOp.BIT_LEFT;
				int ind1 = s.indexOf("LSHIFT");
				i.in1 = s.substring(0, ind1).trim();
				int ind2 = s.indexOf("->");
				i.in2 = s.substring(ind1 + 6, ind2).trim();
				i.out = s.substring(ind2 + 2).trim();
			}
			else if (s.contains("RSHIFT")) {
				i.op = BitOp.BIT_RIGHT;
				int ind1 = s.indexOf("RSHIFT");
				i.in1 = s.substring(0, ind1).trim();
				int ind2 = s.indexOf("->");
				i.in2 = s.substring(ind1 + 6, ind2).trim();
				i.out = s.substring(ind2 + 2).trim();
			}
			else {
				i.op = BitOp.SIGNAL;
				int ind1 = s.indexOf("->");
				i.in1 = s.substring(0, ind1).trim();
				i.in2 = "";
				i.out = s.substring(ind1 + 2).trim();
			}
			
			return i;
		}
	}
	
	static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
