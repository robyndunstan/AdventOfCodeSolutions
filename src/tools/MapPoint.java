package tools;

import java.awt.Point;

/*
 * This class uses screen directions for the X and Y axes
 *         -y
 *     -x      +x
 *         +y
 */

public class MapPoint extends Point {
    public MapPoint() {
        super();
    }
    public MapPoint(int x, int y) {
        super(x, y);
    }
    public MapPoint(Point p) {
        super(p);
    }
    public MapPoint(MapPoint p) {
        super(p);
    }

    public MapPoint getNextPoint(MapDirection d) {
        return switch (d) {
            case MapDirection.N -> new MapPoint(this.x, this.y - 1);
            case MapDirection.NE -> new MapPoint(this.x + 1, this.y - 1);
            case MapDirection.E -> new MapPoint(this.x + 1, this.y);
            case MapDirection.SE -> new MapPoint(this.x + 1, this.y + 1);
            case MapDirection.S -> new MapPoint(this.x, this.y + 1);
            case MapDirection.SW -> new MapPoint(this.x - 1, this.y + 1);
            case MapDirection.W -> new MapPoint(this.x - 1, this.y);
            case MapDirection.NW -> new MapPoint(this.x - 1, this.y - 1);
            default -> null;
        };
    }
}
