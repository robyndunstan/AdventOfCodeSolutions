package day05;

import java.util.ArrayList;

public class BinaryBoarding {
	ArrayList<Seat> seats;
	
	public BinaryBoarding() {}
	public BinaryBoarding(String[] input) {
		ParseSeats(input);
	}
	
	public int FindMissingSeat() {
		SortSeats();
		for (int i = 0; i < seats.size() - 1; i++) {
			if (seats.get(i + 1).GetSeatId() - seats.get(i).GetSeatId() > 1) {
				return seats.get(i).GetSeatId() + 1;
			}
		}
		return -1;
	}
	
	public void SortSeats() {
		ArrayList<Seat> orderedSeats = new ArrayList<Seat>();
		
		while (seats.size() > 0) {
			int minId = Integer.MAX_VALUE;
			Seat minSeat = new Seat();;
			for (Seat s : seats) {
				if (s.GetSeatId() < minId) {
					minId = s.GetSeatId();
					minSeat = s;
				}
			}
			orderedSeats.add(minSeat);
			seats.remove(minSeat);
		}
		
		seats = orderedSeats;
	}
	
	public void PrintSeats() {
		for (Seat s : seats) {
			System.out.println("\tRow " + s.row + ", Col " + s.col + ", ID " + s.GetSeatId());
		}
	}
	
	public void ParseSeats(String[] input) {
		seats = new ArrayList<Seat>();
		for (String s : input) {
			seats.add(new Seat(s));
		}
	}
	
	public int GetHighestId() {
		int highest = 0;
		for (Seat s : seats) {
			highest = Integer.max(highest, s.GetSeatId());
		}
		return highest;
	}
	
	private class Seat {
		int row;
		int col;
		
		public Seat() {}
		public Seat(String loc) {
			ParseSeatLocation(loc);
		}
		
		public void ParseSeatLocation(String loc) {
			if (loc.length() == 10) {
				String rowLoc = loc.substring(0, 7);
				StringBuilder binary = new StringBuilder();
				for (char c : rowLoc.toCharArray()) {
					switch (c) {
					case 'F':
						binary.append('0');
						break;
					case 'B':
						binary.append('1');
						break;
					}
				}
				row = Integer.parseInt(binary.toString(), 2);
				
				String colLoc = loc.substring(7);
				binary = new StringBuilder();
				for (char c : colLoc.toCharArray()) {
					switch (c) {
					case 'L':
						binary.append('0');
						break;
					case 'R':
						binary.append('1');
						break;
					}
				}
				col = Integer.parseInt(binary.toString(), 2);
			}
		}
		
		public int GetSeatId() {
			return row * 8 + col;
		}
	}
}
