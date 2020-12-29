package day11;

import java.util.ArrayList;

public class SeatingSystem {
	enum SeatState { Floor, Empty, Occupied }
	
	ArrayList<ArrayList<SeatState>> seats;
	
	public SeatingSystem() {
		seats = new ArrayList<ArrayList<SeatState>>();
	}
	public SeatingSystem(String[] input) {
		ParseData(input);
	}
	
	public void PrintSeats() {
		for (ArrayList<SeatState> a : seats) {
			System.out.print("\t\t");
			for (SeatState s : a) {
				switch (s) {
				case Empty:
					System.out.print("L");
					break;
				case Floor:
					System.out.print(".");
					break;
				case Occupied:
					System.out.print("#");
					break;
				}
			}
			System.out.println();
		}
	}
	
	public void ParseData(String[] input) {
		seats = new ArrayList<ArrayList<SeatState>>();
		for (String s : input) {
			ArrayList<SeatState> row = new ArrayList<SeatState>();
			for (char c : s.toCharArray()) {
				switch (c) {
				case '.':
					row.add(SeatState.Floor);
					break;
				case 'L':
					row.add(SeatState.Empty);
					break;
				case '#':
					row.add(SeatState.Occupied);
					break;
				}
			}
			seats.add(row);
		}
	}
	
	public int FinalOccupiedCountNeighbors() {
		boolean changed = false;
		do {
			changed = false;
			ArrayList<ArrayList<SeatState>> newSeats = new ArrayList<ArrayList<SeatState>>();
			for (int y = 0; y < seats.size(); y++) {
				ArrayList<SeatState> newRow = new ArrayList<SeatState>();
				for (int x = 0; x < seats.get(y).size(); x++) {
					int neighbors = GetNeighborCount(x, y);
					switch (seats.get(y).get(x)) {
					case Empty:
						if (neighbors == 0) {
							newRow.add(SeatState.Occupied);
							changed = true;
						}
						else {
							newRow.add(SeatState.Empty);
						}
						break;
					case Occupied:
						if (neighbors >= 4) {
							newRow.add(SeatState.Empty);
							changed = true;
						}
						else {
							newRow.add(SeatState.Occupied);
						}
						break;
					case Floor:
						newRow.add(SeatState.Floor);
						break;
					}
				}
				newSeats.add(newRow);
			}
			seats = newSeats;
		} while (changed);
		return CountSitters();
	}
	
	public int FinalOccupiedCountVisible() {
		boolean changed = false;
		int stepCount = 0;
		do {
			changed = false;
			stepCount++;
			ArrayList<ArrayList<SeatState>> newSeats = new ArrayList<ArrayList<SeatState>>();
			for (int y = 0; y < seats.size(); y++) {
				ArrayList<SeatState> newRow = new ArrayList<SeatState>();
				for (int x = 0; x < seats.get(y).size(); x++) {
					int neighbors = GetVisibleCount(x, y);
					switch (seats.get(y).get(x)) {
					case Empty:
						if (neighbors == 0) {
							newRow.add(SeatState.Occupied);
							changed = true;
						}
						else {
							newRow.add(SeatState.Empty);
						}
						break;
					case Occupied:
						if (neighbors >= 5) {
							newRow.add(SeatState.Empty);
							changed = true;
						}
						else {
							newRow.add(SeatState.Occupied);
						}
						break;
					case Floor:
						newRow.add(SeatState.Floor);
						break;
					}
				}
				newSeats.add(newRow);
			}
			seats = newSeats;
			if (stepCount % 10 == 0) {
				System.out.println("\tStep " + stepCount);
				//PrintSeats();
			}
		} while (changed);
		return CountSitters();
	}
	
	private int GetNeighborCount(int x, int y) {
		int count = 0;
		count += (x > 0 && y > 0 && seats.get(y - 1).get(x - 1) == SeatState.Occupied) ? 1 : 0;
		count += (y > 0 && seats.get(y - 1).get(x) == SeatState.Occupied) ? 1 : 0;
		count += (y > 0 && x < seats.get(y - 1).size() - 1 && seats.get(y - 1).get(x + 1) == SeatState.Occupied) ? 1 : 0;
		count += (x > 0 && seats.get(y).get(x - 1) == SeatState.Occupied) ? 1 : 0;
		count += (x < seats.get(y).size() - 1 && seats.get(y).get(x + 1) == SeatState.Occupied) ? 1 : 0;
		count += (y < seats.size() - 1 && x > 0 && seats.get(y + 1).get(x - 1) == SeatState.Occupied) ? 1 : 0;
		count += (y < seats.size() - 1 && seats.get(y + 1).get(x) == SeatState.Occupied) ? 1 : 0;
		count += (y < seats.size() - 1 && x < seats.get(y + 1).size() - 1 && seats.get(y + 1).get(x + 1) == SeatState.Occupied) ? 1 : 0;
		return count;
	}
	
	private int GetVisibleCount(int x, int y) {
		int count = 0;
		
		// NW
		int i = x - 1;
		int j = y - 1;
		while (i >= 0 && j >= 0) {
			if (seats.get(j).get(i) != SeatState.Floor) {
				count += seats.get(j).get(i) == SeatState.Occupied ? 1 : 0;
				i = -1;
				j = -1;
			}
			else {
				i--;
				j--;
			}
		}
		
		// N
		i = x;
		j = y - 1;
		while (j >= 0) {
			if (seats.get(j).get(i) != SeatState.Floor) {
				count += seats.get(j).get(i) == SeatState.Occupied ? 1 : 0;
				j = -1;
			}
			else {
				j--;
			}
		}
		
		// NE
		i = x + 1;
		j = y - 1;
		while (j >= 0 && i < seats.get(j).size()) {
			if (seats.get(j).get(i) != SeatState.Floor) {
				count += seats.get(j).get(i) == SeatState.Occupied ? 1 : 0;
				i = seats.get(j).size();
				j = -1;
			}
			else {
				i++;
				j--;
			}
		}
		
		// W
		i = x - 1;
		j = y;
		while (i >= 0) {
			if (seats.get(j).get(i) != SeatState.Floor) {
				count += seats.get(j).get(i) == SeatState.Occupied ? 1 : 0;
				i = -1;
			}
			else {
				i--;
			}
		}
		
		// E
		i = x + 1;
		j = y;
		while (i < seats.get(j).size()) {
			if (seats.get(j).get(i) != SeatState.Floor) {
				count += seats.get(j).get(i) == SeatState.Occupied ? 1 : 0;
				i = seats.get(j).size();
			}
			else {
				i++;
			}
		}
		
		// SW
		i = x - 1;
		j = y + 1;
		while (i >= 0 && j < seats.size()) {
			if (seats.get(j).get(i) != SeatState.Floor) {
				count += seats.get(j).get(i) == SeatState.Occupied ? 1 : 0;
				i = -1;
				j = seats.size();
			}
			else {
				i--;
				j++;
			}
		}

		// S
		i = x;
		j = y + 1;
		while (j < seats.size()) {
			if (seats.get(j).get(i) != SeatState.Floor) {
				count += seats.get(j).get(i) == SeatState.Occupied ? 1 : 0;
				j = seats.size();
			}
			else {
				j++;
			}
		}
		
		// SE
		i = x + 1;
		j = y + 1;
		while (j < seats.size() && i < seats.get(j).size()) {
			if (seats.get(j).get(i) != SeatState.Floor) {
				count += seats.get(j).get(i) == SeatState.Occupied ? 1 : 0;
				i = seats.get(j).size();
				j = seats.size();
			}
			else {
				i++;
				j++;
			}
		}

		//if (x == 0 && y == 8) System.out.println("\t\t\t" + count);
		return count;
	}
	
	private int CountSitters() {
		int count = 0;
		for (ArrayList<SeatState> a : seats) {
			for (SeatState s : a) {
				if (s == SeatState.Occupied) count++;
			}
		}
		return count;
	}
}
