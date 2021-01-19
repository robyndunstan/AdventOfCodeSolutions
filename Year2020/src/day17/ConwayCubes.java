package day17;

import java.util.ArrayList;
import java.util.HashMap;

public class ConwayCubes {
	HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> activeCubes;
	
	public ConwayCubes() {
		activeCubes = new HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>>();
	}
	public ConwayCubes(String[] z0w0) {
		ParseInitialState(z0w0);
	}
	
	public void ParseInitialState(String[] z0w0) {
		activeCubes = new HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>>();
		int w = 0;
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> volume = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		int z = 0;
		HashMap<Integer, ArrayList<Integer>> plane = new HashMap<Integer, ArrayList<Integer>>();
		for (int y = 0; y < z0w0.length; y++) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			for (int x = 0; x < z0w0[y].length(); x++) {
				if (z0w0[y].charAt(x) == '#') {
					row.add(x);
				}
			}
			plane.put(y, row);
		}
		volume.put(z, plane);
		activeCubes.put(w, volume);
	}
	
	public int GetActiveCountAfter6Steps(int problemPart) {
		for (int i = 0; i < 6; i++) {
			if (problemPart == 1) {
				DoStep3D();
			}
			else {
				DoStep4D();
			}
			//System.out.println("\t\tStep " + i + ": " + CountActiveCubes());
		}
		return CountActiveCubes();
	}
	
	private int CountActiveCubes() {
		int count = 0;
		for (int w : activeCubes.keySet()) {
			for (int z : activeCubes.get(w).keySet()) {
				for (int y : activeCubes.get(w).get(z).keySet()) {
					count += activeCubes.get(w).get(z).get(y).size();
				}
			}
		}
		return count;
	}
	
	private void DoStep3D() {
		Bounds b = GetBounds();
		//System.out.println("\t\tx: [" + b.minX + ", " + b.maxX + "] y: [" + b.minY + ", " + b.maxY + "] z: [" + b.minZ + ", " + b.maxZ + "] w: [" + b.minW + ", " + b.maxW + "]");
		HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> nextState = new HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>>();
		int w = 0;
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> volume = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		for (int z = b.minZ - 1; z <= b.maxZ + 1; z++) {
			HashMap<Integer, ArrayList<Integer>> plane = new HashMap<Integer, ArrayList<Integer>>();
			for (int y = b.minY - 1; y <= b.maxY + 1; y++) {
				ArrayList<Integer> row = new ArrayList<Integer>();
				for (int x = b.minX - 1; x <= b.maxX + 1; x++) {
					if (GetNextCubeState(x, y, z)) {
						row.add(x);
					}
				}
				if (row.size() > 0) {
					plane.put(y, row);
				}
			}
			if (plane.keySet().size() > 0) {
				volume.put(z, plane);
			}
		}
		nextState.put(w, volume);
		activeCubes = nextState;
	}
	private void DoStep4D() {
		Bounds b = GetBounds();
		HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> nextState = new HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>>();
		for (int w = b.minW - 1; w <= b.maxW + 1; w++) {
			HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> volume = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
			for (int z = b.minZ - 1; z <= b.maxZ + 1; z++) {
				HashMap<Integer, ArrayList<Integer>> plane = new HashMap<Integer, ArrayList<Integer>>();
				for (int y = b.minY - 1; y <= b.maxY + 1; y++) {
					ArrayList<Integer> row = new ArrayList<Integer>();
					for (int x = b.minX - 1; x <= b.maxX + 1; x++) {
						if (GetNextCubeState(x, y, z, w)) {
							row.add(x);
						}
					}
					if (row.size() > 0) {
						plane.put(y, row);
					}
				}
				if (plane.keySet().size() > 0) {
					volume.put(z, plane);
				}
			}
			nextState.put(w, volume);
		}
		activeCubes = nextState;
	}

	private Bounds GetBounds() {
		Bounds b = new Bounds();
		for (int w : activeCubes.keySet()) {
			b.minW = Integer.min(b.minW, w);
			b.maxW = Integer.max(b.maxW, w);
			for (int z : activeCubes.get(w).keySet()) {
				b.minZ = Integer.min(b.minZ, z);
				b.maxZ = Integer.max(b.maxZ, z);
				for (int y : activeCubes.get(w).get(z).keySet()) {
					b.minY = Integer.min(b.minY, y);
					b.maxY = Integer.max(b.maxY, y);
					for (int x : activeCubes.get(w).get(z).get(y)) {
						b.minX = Integer.min(b.minX, x);
						b.maxX = Integer.max(b.maxX, x);
					}
				}
			}
		}
		return b;
	}
	
	private boolean GetNextCubeState(int x, int y, int z) {
		return GetNextCubeState(x, y, z, 0, 1, 1, 1, 0, 3, 3, 2, 3);
	}
	private boolean GetNextCubeState(int x, int y, int z, int w) {
		return GetNextCubeState(x, y, z, w, 1, 1, 1, 1, 3, 3, 2, 3);
	}
	private boolean GetNextCubeState(int x, int y, int z, int w, 
			int dx, int dy, int dz, int dw, 
			int inactiveMin, int inactiveMax, int activeMin, int activeMax) {
		int sumActiveNeighbors = 0;
		for (int ww = w - dw; ww <= w + dw; ww++) {
			for (int zz = z - dz; zz <= z + dz; zz++) {
				for (int yy = y - dy; yy <= y + dy; yy++) {
					for (int xx = x - dx; xx <= x + dx; xx++) {
						if (GetCubeState(xx, yy, zz, ww) && (xx != x || yy != y || zz != z || ww != w)) {
							sumActiveNeighbors++;
						}
					}
				}
			}
		}
		//System.out.println("\t\t(" + x + ", " + y + ", " + z + ", " + w + ") " + sumActiveNeighbors + " " + GetCubeState(x, y, z, w));
		if (GetCubeState(x, y, z, w)) {
			if (sumActiveNeighbors >= activeMin && sumActiveNeighbors <= activeMax) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (sumActiveNeighbors >= inactiveMin && sumActiveNeighbors <= inactiveMin) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	private boolean GetCubeState(int x, int y, int z, int w) {
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> volume = activeCubes.get(w);
		if (volume == null) {
			return false;
		}
		else {
			HashMap<Integer, ArrayList<Integer>> plane = volume.get(z);
			if (plane == null) {
				return false;
			}
			else {
				ArrayList<Integer> row = plane.get(y);
				if (row == null) {
					return false;
				}
				else {
					return row.contains(x);
				}
			}
		}
	}
	
	private class Bounds {
		int minX, maxX, minY, maxY, minZ, maxZ, minW, maxW;
		public Bounds() {
			minX = 0;
			maxX = 0;
			minY = 0;
			maxY = 0;
			minZ = 0;
			maxZ = 0;
			minW = 0;
			maxW = 0;
		}
	}
}
