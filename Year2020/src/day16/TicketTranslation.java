package day16;

import java.util.ArrayList;

public class TicketTranslation {
	ArrayList<Field> fields;
	ArrayList<Ticket> nearbyTickets;
	Ticket myTicket;
	
	public TicketTranslation() {
		fields = new ArrayList<Field>();
		nearbyTickets = new ArrayList<Ticket>();
	}
	public TicketTranslation(String[] fields, String myTicket, String[] nearbyTickets) {
		ParseInput(fields, myTicket, nearbyTickets);
	}
	
	public void ParseInput(String[] fields, String myTicket, String[] nearbyTickets) {
		this.fields = new ArrayList<Field>();
		for (String s : fields) {
			this.fields.add(new Field(s));
		}
		this.nearbyTickets = new ArrayList<Ticket>();
		for (String s : nearbyTickets) {
			this.nearbyTickets.add(new Ticket(s));
		}
		this.myTicket = new Ticket(myTicket);
	}
	
	public int SumInvalidValues() {
		int sum = 0;
		for (Ticket t : nearbyTickets) {
			for (int i : t.fields) {
				boolean validValue = false;
				for (Field f : fields) {
					if (f.IsValidValue(i)) {
						validValue = true;
						break;
					}
				}
				if (!validValue) {
					sum += i;
				}
			}
		}
		return sum;
	}
	
	public void PrintAllFields() {
		for (Field f : fields) {
			PrintPossibleIndicesForField(f);
		}
	}
	
	public void PrintMyTicket(String[] fieldList) {
		myTicket.PrintTicket(fieldList);
	}
	
	private void PrintPossibleIndicesForField(Field f) {
		DiscardInvalidTickets();
		boolean[] possible = new boolean[nearbyTickets.get(0).fields.size()];
		for (int i = 0; i < possible.length; i++) possible[i] = true;
		for (Ticket t : nearbyTickets) {
			for (int index = 0; index < t.fields.size(); index++) {
				if (!f.IsValidValue(t.fields.get(index))) {
					possible[index] = false;
				}
			}
		}
		System.out.print("\t\t" + f.name + ": ");
		for (int index = 0; index < possible.length; index++) {
			if (possible[index]) {
				System.out.print(index + " ");
			}
		}
		System.out.println();
	}
	
	private void DiscardInvalidTickets() {
		ArrayList<Ticket> validTickets = new ArrayList<Ticket>();
		for (Ticket t : nearbyTickets) {
			boolean validTicket = true;
			for (int i : t.fields) {
				boolean validValue = false;
				for (Field f : fields) {
					if (f.IsValidValue(i)) {
						validValue = true;
						break;
					}
				}
				if (!validValue) {
					validTicket = false;
					break;
				}
			}
			if (validTicket) {
				validTickets.add(t);
			}
		}
		nearbyTickets = validTickets;
	}

	private class Ticket {
		ArrayList<Integer> fields;
		
		public Ticket() {}
		public Ticket(String input) {
			ParseTicket(input);
		}
		
		public void ParseTicket(String input) {
			fields = new ArrayList<Integer>();
			String[] splitInput = input.trim().split(",");
			for (String s : splitInput) {
				fields.add(Integer.parseInt(s));
			}
		}
		
		public void PrintTicket(String[] fieldList) {
			for (int i = 0; i < fields.size(); i++) {
				if (i < fieldList.length) {
					System.out.print("\t\t" + fieldList[i] + ": ");
				}
				else {
					System.out.print("\t\t" + i + ": ");
				}
				System.out.println(fields.get(i));
			}
		}
	}
	
	private class Field {
		String name;
		Range range1, range2;
		
		public Field() {}
		public Field(String input) {
			ParseField(input);
		}
		
		public void ParseField(String input) {
			input = input.trim();
			int index1 = input.indexOf(":");
			name = input.substring(0, index1);
			int index2 = input.indexOf("-", index1);
			range1 = new Range();
			range1.start = Integer.parseInt(input.substring(index1 + 1, index2).trim());
			index1 = input.indexOf("or", index2);
			range1.end = Integer.parseInt(input.substring(index2 + 1, index1).trim());
			index2 = input.indexOf("-", index1);
			range2 = new Range();
			range2.start = Integer.parseInt(input.substring(index1 + 2, index2).trim());
			range2.end = Integer.parseInt(input.substring(index2 + 1).trim());
		}
		
		public boolean IsValidValue(int input) {
			return (range1.start <= input && range1.end >= input) || (range2.start <= input && range2.end >= input);
		}
	}
	
	private class Range {
		int start, end;
	}
}
