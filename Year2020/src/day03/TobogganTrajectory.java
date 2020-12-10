package day03;

import java.awt.Point;
import java.util.ArrayList;

public class TobogganTrajectory {
	ArrayList<ArrayList<Boolean>> trees;
	
	public TobogganTrajectory() {
		trees = new ArrayList<ArrayList<Boolean>>();
	}
	public TobogganTrajectory(String[] input) {
		this();
		ParseMap(input);
	}
	
	public void ParseMap(String[] input) {
		trees = new ArrayList<ArrayList<Boolean>>();
		for (String s : input) {
			ArrayList<Boolean> row = new ArrayList<Boolean>();
			for (char c : s.toCharArray()) {
				if (c == '.') {
					row.add(false);
				}
				else if (c == '#') {
					row.add(true);
				}
			}
			trees.add(row);
		}
	}
	
	public boolean CheckForTree(int x, int y) {
		if (y > trees.size()) {
			return false;
		}
		else if (trees.size() > 0) {
			return trees.get(y).get(x % trees.get(0).size());
		}
		else {
			return false;
		}
	}
	
	public int CountTreesOnPath(int dx, int dy) {
		int treeCount = 0;
		Point xy = new Point(0, 0);
		while (xy.y < trees.size()) {
			if (CheckForTree(xy.x, xy.y)) {
				treeCount++;
			}
			xy.x += dx;
			xy.y += dy;
		}
		return treeCount;
	}
}
