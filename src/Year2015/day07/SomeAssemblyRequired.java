package Year2015.day07;

import java.util.ArrayList;
import java.util.HashMap;

public class SomeAssemblyRequired {
	public static HashMap<String, Wire> buildCircuit(String[] input) {
		HashMap<String, Wire> wires = createWires(input);
		printWires(wires);
		
		for (String s : input) {
			// get target wire
			int arrowIndex = s.indexOf("->");
			String targetId = s.substring(arrowIndex + 2).trim();
			Wire targetWire = wires.get(targetId);
			
			// process wire inputs
			String[] inputs = s.substring(0, arrowIndex).split(" ");
			Source source = null;
			if (inputs.length == 1) {
				try {
					int value = Integer.parseInt(inputs[0].trim());
					source = new Signal(value);
				}
				catch (Exception e) {
					source = wires.get(inputs[0].trim());
				}
			}
			else if (inputs.length == 2) {
				if (inputs[0].trim().equals("NOT")) {
					Gate gate = new Gate();
					gate.setType(GateType.NOT);
					try {
						int value = Integer.parseInt(inputs[1].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[1].trim());
					}
					gate.addSource(source);
					source = gate;
				}
				else {
					System.out.println("Invalid source type for instruction '" + s + "'");
				}
			}
			else if (inputs.length == 3) {
				if (inputs[1].trim().equals("AND")) {
					Gate gate = new Gate();
					gate.setType(GateType.AND);
					try {
						int value = Integer.parseInt(inputs[0].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[0].trim());
					}
					gate.addSource(source);
					try {
						int value = Integer.parseInt(inputs[2].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[2].trim());
					}
					gate.addSource(source);
					source = gate;
				}
				else if (inputs[1].trim().equals("OR")) {
					Gate gate = new Gate();
					gate.setType(GateType.OR);
					try {
						int value = Integer.parseInt(inputs[0].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[0].trim());
					}
					gate.addSource(source);
					try {
						int value = Integer.parseInt(inputs[2].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[2].trim());
					}
					gate.addSource(source);
					source = gate;
				}
				else if (inputs[1].trim().equals("LSHIFT")) {
					Gate gate = new Gate();
					gate.setType(GateType.LSHIFT);
					try {
						int value = Integer.parseInt(inputs[0].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[0].trim());
					}
					gate.addSource(source);
					try {
						int value = Integer.parseInt(inputs[2].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[2].trim());
					}
					gate.addSource(source);
					source = gate;
				}
				else if (inputs[1].trim().equals("RSHIFT")) {
					Gate gate = new Gate();
					gate.setType(GateType.RSHIFT);
					try {
						int value = Integer.parseInt(inputs[0].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[0].trim());
					}
					gate.addSource(source);
					try {
						int value = Integer.parseInt(inputs[2].trim());
						source = new Signal(value);
					}
					catch (Exception e) {
						source = wires.get(inputs[2].trim());
					}
					gate.addSource(source);
					source = gate;
				}
				else {
					System.out.println("Invalid source type for instruction '" + s + "'");
				}
			}
			else {
				System.out.println("Invalid number of source strings (" + inputs.length + ") for instruction '" + s + "'");
			}
			targetWire.setSource(source);
		}
		
		printWires(wires);
		return wires;
	}
	
	public static void printWires(HashMap<String, Wire> wires) {
		for (String key : wires.keySet()) {
			System.out.println("\t\t\t\t" + wires.get(key));
		}
	}

	public static HashMap<String, Wire> createWires(String[] input) {
		HashMap<String, Wire> wires = new HashMap<String, Wire>();
		for (String s : input) {
			String[] ss = s.split(" ");
			for (String si : ss) {
				si = si.trim();
				if (si.length() > 0 && !si.equals("->") && !si.equals("AND") && !si.equals("LSHIFT") && !si.equals("NOT")
						&& !si.equals("OR") && !si.equals("RSHIFT")) {
					try {
						Integer.parseInt(si);
						// This is a number, not a wire identifier
					}
					catch (Exception e) {
						if (!wires.containsKey(si)) {
							wires.put(si, new Wire(si));
						}
					}
				}
			}
		}
		return wires;
	}
	
	public static class Wire implements Source {
		String id;
		Source source;
		
		public Wire() {source = new Signal(0);}
		public Wire(String id) {
			this.id = id;
		}
		
		public int getSignal() {
			int s = source.getSignal();
			if (s < 0) s += 65536;
			return s;
		}
		
		public String toString() {
			return id + ": " + getSignal();
		}
		public void setSource(Source source) {
			this.source = source;
		}
	}
	
	private static interface Source {
		public int getSignal();
	}
	
	private static class Signal implements Source {
		int signal;
		public Signal() {signal = -1;}
		public Signal(int signal) {this.signal = signal;}
		public void setSignal(int signal) {this.signal = signal;}
		public int getSignal() {return signal;}
	}
	
	private static enum GateType {
		AND, OR, NOT, LSHIFT, RSHIFT
	}
	
	private static class Gate implements Source {
		GateType type;
		ArrayList<Source> sources;
		
		public Gate() {sources = new ArrayList<Source>();}
		public void setType(GateType type) {this.type = type;}
		public void addSource(Source source) {sources.add(source);}
		public int getSignal() {
			switch (type) {
			case AND:
				return sources.get(0).getSignal() & sources.get(1).getSignal();
			case LSHIFT:
				return sources.get(0).getSignal() << sources.get(1).getSignal();
			case NOT:
				return ~sources.get(0).getSignal();
			case OR:
				return sources.get(0).getSignal() | sources.get(1).getSignal();
			case RSHIFT:
				return sources.get(0).getSignal() >> sources.get(1).getSignal();
			default:
				return -1;
			}
		}
	}
}
