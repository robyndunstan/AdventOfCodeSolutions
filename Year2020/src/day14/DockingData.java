package day14;

import java.util.ArrayList;
import java.util.HashMap;

public class DockingData {
	private HashMap<BitInteger, BitInteger> memory;
	String mask;
	
	public long DoInstructions(String[] input, int part) {
		memory = new HashMap<BitInteger, BitInteger>();
		for (String s : input) {
			ParseInstruction(s, part);
		}
		return SumMemory();
	}
	
	private long SumMemory() {
		long sum = 0;
		for (BitInteger b : memory.values()) {
			//System.out.println("\tAddend " + b.GetValue());
			sum += b.GetValue();
		}
		return sum;
	}
	
	private void ParseInstruction(String s, int part) {
		s = s.trim();
		if (s.contains("mask")) {
			String value = s.substring(s.indexOf("=") + 1).trim();
			//System.out.println("\tSet mask to " + value);
			mask = value;
		}
		else if (s.contains("mem")) {
			int index = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
			long value = Long.parseLong(s.substring(s.indexOf("=") + 1).trim());
			switch (part) {
			case 1:
				SetMemoryWithValueMask(index, value);
				break;
			case 2:
				SetMemoryWithLocationMask(index, value);
				break;
			}
			
		}
	}
	
	private void SetMemoryWithValueMask(int index, long value) {
		BitInteger i = null, v = null;
		boolean foundMatch = false;
		for (BitInteger b : memory.keySet()) {
			if (b.Equals((long)index)) {
				foundMatch = true;
				i = b;
				v = memory.get(b);
			}
		}
		if (!foundMatch) {
			i = new BitInteger();
			i.SetValue(index);
			v = new BitInteger();
		}
		v.SetValue(value);
		v.ApplyMask();
		memory.put(i, v);
	}
	
	private void SetMemoryWithLocationMask(int index, long value) {
		BitInteger j = new BitInteger();
		j.SetValue((long)index);
		ArrayList<BitInteger> locations = j.ApplyLocationMask();
		System.out.println("\t\t\tApply value to " + locations.size() + " locations");
		for (BitInteger b : locations) {
			BitInteger i = null, v = null;
			boolean foundMatch = false;
			for (BitInteger m : memory.keySet()) {
				if (m.Equals(b)) {
					foundMatch = true;
					i = m;
					v = memory.get(m);
				}
			}
			if (!foundMatch) {
				i = b;
				v = new BitInteger();
			}
			v.SetValue(value);
			//System.out.println("\t\tIndex " + i.GetValue() + " Value " + v.GetValue());
			memory.put(i, v);
		}
	}
	
	private class BitInteger {
		int[] bits;
		
		public BitInteger() {
			bits = new int[36];
		}
		
		public long GetValue() {
			long value = 0;
			long power = 1;
			for (int i = 0; i < 36; i++) {
				if (bits[i] == 1) {
					value += power;
				}
				power *= 2;
			}
			return value;
		}
		
		public void SetValue(long value) {
			bits = new int[36];
			long basePower = 2;
			for (int i = 0; i < 36; i++) {
				if (value % basePower == 1) {
					bits[i] = 1;
				}
				value /= basePower;
			}
		}

		public void ApplyMask() {
			for (int i = 0; i < Integer.min(36, mask.length()); i++) {
				char c = mask.charAt(i);
				switch(c) {
				case '0':
					bits[35 - i] = 0;
					break;
				case '1':
					bits[35 - i] = 1;
					break;
				}
			}
		}
		
		public ArrayList<BitInteger> ApplyLocationMask() {
			ArrayList<BitInteger> validLocs = new ArrayList<BitInteger>();
			validLocs.add(this);
			for (int i = 0; i < Integer.min(36, mask.length()); i++) {
				ArrayList<BitInteger> nextLocs = new ArrayList<BitInteger>();
				char c = mask.charAt(i);
				for (BitInteger b : validLocs) {
					switch (c) {
					case '0':
						nextLocs.add(b);
						break;
					case '1':
						b.bits[35 - i] = 1;
						nextLocs.add(b);
						break;
					case 'X':
						b.bits[35 - i] = 1;
						nextLocs.add(b);
						BitInteger bClone = b.Clone();
						bClone.bits[35 - i] = 0;
						nextLocs.add(bClone);
						break;
					}
				}
				validLocs = nextLocs;
			}
			return validLocs;
		}
		
		public String PrintBits() {
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < 36; i++) {
				if (bits[i] == 1) {
					b.insert(0, '1');
				}
				else if (bits[i] == 0) {
					b.insert(0,  '0');
				}
				else {
					b.insert(0, 'X');
				}
			}
			return b.toString();
		}
		
		public boolean Equals(BitInteger b) {
			return this.GetValue() == b.GetValue();
		}
		public boolean Equals(Long l) {
			return this.GetValue() == l;
		}
		
		public BitInteger Clone() {
			BitInteger b = new BitInteger();
			b.SetValue(this.GetValue());
			return b;
		}
	}
}
