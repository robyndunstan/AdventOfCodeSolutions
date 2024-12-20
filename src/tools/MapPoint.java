package tools;

import java.awt.Point;

/*
 * This class uses screen directions for the X and Y axes
 *         -y
 *     -x      +x
 *         +y
 */

public class MapPoint extends Point {
    public Point getNextPoint(MapDirection d) {
        return switch (d) {
            case MapDirection.N -> new Point(this.x, this.y - 1);
            case MapDirection.NE -> new Point(this.x + 1, this.y - 1);
            case MapDirection.E -> new Point(this.x + 1, this.y);
            case MapDirection.SE -> new Point(this.x + 1, this.y + 1);
            case MapDirection.S -> new Point(this.x, this.y + 1);
            case MapDirection.SW -> new Point(this.x - 1, this.y + 1);
            case MapDirection.W -> new Point(this.x - 1, this.y);
            case MapDirection.NW -> new Point(this.x - 1, this.y - 1);
            default -> null;
        };
    }
}
