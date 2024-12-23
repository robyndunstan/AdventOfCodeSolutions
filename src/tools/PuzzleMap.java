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
    protected ArrayList<ArrayList<T>> map;

    public abstract T parse(char c, int x, int y);

    public void parseMap(String filename) throws IOException {
        input = new FileController(filename);
        map = new ArrayList<>();
        input.openInput();
        String line = input.readLine();
        int x, y = 0;
        while (line != null) {
            x = 0;
            line = line.trim();
            ArrayList<T> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(parse(c, x, y));
                x++;
            }
            map.add(row);
            y++;
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

    public void setValue(Point p, T value) {
        if (p.y >= 0 && p.y < map.size() && p.x >= 0 && p.x < map.get(p.y).size()) {
            map.get(p.y).set(p.x, value);
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
