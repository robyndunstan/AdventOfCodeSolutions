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
        return getNextPoint(d, 1);
    }
    public MapPoint getNextPoint(MapDirection d, int steps) {
        return switch (d) {
            case MapDirection.N -> new MapPoint(this.x, this.y - steps);
            case MapDirection.NE -> new MapPoint(this.x + steps, this.y - steps);
            case MapDirection.E -> new MapPoint(this.x + steps, this.y);
            case MapDirection.SE -> new MapPoint(this.x + steps, this.y + steps);
            case MapDirection.S -> new MapPoint(this.x, this.y + steps);
            case MapDirection.SW -> new MapPoint(this.x - steps, this.y + steps);
            case MapDirection.W -> new MapPoint(this.x - steps, this.y);
            case MapDirection.NW -> new MapPoint(this.x - steps, this.y - steps);
            default -> null;
        };
    }

    public MapPoint[] getCardinalNeighbors() {
        MapPoint[] ns = new MapPoint[4];
        ns[0] = this.getNextPoint(MapDirection.E);
        ns[1] = this.getNextPoint(MapDirection.N);
        ns[2] = this.getNextPoint(MapDirection.W);
        ns[3] = this.getNextPoint(MapDirection.S);
        return ns;
    }

    public Point toPoint() {
        return new Point(this.x, this.y);
    }
}
