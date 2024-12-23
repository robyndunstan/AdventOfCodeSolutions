package tools;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class uses screen directions for the X and Y axes
 *         -y
 *     -x      +x
 *         +y
 */

public abstract class PuzzleMap<T> {
    private FileController input;
    private ArrayList<ArrayList<T>> map;

    public abstract T parse(char c);

    public void parseMap(String filename) throws IOException {
        input = new FileController(filename);
        map = new ArrayList<>();
        input.openInput();
        String line = input.readLine();
        while (line != null) {
            line = line.trim();
            ArrayList<T> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(parse(c));
            }
            map.add(row);
            line = input.readLine();
        }
        input.closeFile();
    }

    public T getValue(Point p) {
        if (p.y >= 0 && p.y < map.size() && p.x >= 0 && p.x < map.get(p.y).size()) {
            return map.get(p.y).get(p.x);
        }
        else {
            return null;
        }
    }

    public int getSizeX() {
        int maxX = 0;
        for (ArrayList<T> row : map) {
            maxX = Math.max(maxX, row.size());
        }
        return maxX;
    }
    public int getSizeY() {
        return map.size();
    }
}
