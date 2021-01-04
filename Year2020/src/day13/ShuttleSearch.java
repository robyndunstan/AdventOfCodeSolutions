package day13;

import java.util.ArrayList;

public class ShuttleSearch {
	ArrayList<Shuttle> shuttles;
	
	public ShuttleSearch() {
		shuttles = new ArrayList<Shuttle>();
	}
	public ShuttleSearch(String input) {
		ParseShuttles(input);
	}
	
	public void ParseShuttles(String input) {
		shuttles = new ArrayList<Shuttle>();
		String[] splits = input.trim().split(",");
		int index = 0;
		for (String s : splits) {
			if (!s.equals("x")) {
				shuttles.add(new Shuttle(Integer.parseInt(s), index));
			}
			index++;
		}
	}
	
	public int FindNextShuttle(int time) {
		Shuttle nextShuttle = null;
		int leastWait = Integer.MAX_VALUE;
		for (Shuttle s : shuttles) {
			int wait = s.GetWaitTime(time);
			if (wait < leastWait) {
				leastWait = wait;
				nextShuttle = s;
			}
		}
		return nextShuttle.interval * leastWait;
	}
	
	public long FindValidStartTime() {
		Shuttle maxShuttle = FindMaxShuttle();
		long baseTime = maxShuttle.interval - maxShuttle.offset;
		long maxTime = GetMaxTime();
		System.out.println("\tMax time before repeats is " + maxTime);
		boolean allValid = false;
		int count = 0;
		do {
			allValid = true;
			for (Shuttle s : shuttles) {
				if (!s.IsValidOffset(baseTime)) {
					allValid = false;
					break;
				}
			}
			if (count % 1000000000 == 0) System.out.println("\tBase Time Checked: " + baseTime);
			if (!allValid) {
				baseTime += maxShuttle.interval;
				count++;
			}
		} while (!allValid && baseTime < maxTime);
		return baseTime;
	}
	
	private Shuttle FindMaxShuttle() {
		Shuttle maxShuttle = shuttles.get(0);
		for (Shuttle s : shuttles) {
			if (s.interval > maxShuttle.interval) {
				maxShuttle = s;
			}
		}
		return maxShuttle;
	}
	
	private long GetMaxTime() {
		long time = 1;
		for (Shuttle s : shuttles) {
			time *= s.interval;
		}
		return time;
	}
	
	private class Shuttle {
		int interval;
		int offset;
		
		public Shuttle() {}
		public Shuttle(int interval) {
			this.interval = interval;
		}
		public Shuttle(int interval, int offset) {
			this.interval = interval;
			this.offset = offset;
		}
		
		public int GetWaitTime(int time) {
			return interval - time % interval;
		}
		
		public boolean IsValidOffset(long baseTime) {
			return (baseTime + offset) % interval == 0;
		}
	}
}
