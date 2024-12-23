package Year2024.day12;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.MapDirection;
import tools.MapPoint;
import tools.RunPuzzle;
import tools.TestCase;

public class GardenGroups extends tools.RunPuzzle {
    ArrayList<ArrayList<Character>> gardenMap;

    public GardenGroups(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new GardenGroups(12, "Garden Groups", "src\\Year2024\\day12\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day12\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test1File", 140l));
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test2File", 772l));
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test3File", 1930l));
        tests.add(new TestCase<>(2, "src\\Year2024\\day12\\data\\test1File", 80l));
        tests.add(new TestCase<>(2, "src\\Year2024\\day12\\data\\test2File", 436l));
        tests.add(new TestCase<>(2, "src\\Year2024\\day12\\data\\test4File", 236l));
        tests.add(new TestCase<>(2, "src\\Year2024\\day12\\data\\test5File", 368l));
        tests.add(new TestCase<>(2, "src\\Year2024\\day12\\data\\test3File", 1206l));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Long)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String fileName = (String)input;
        FileController file = new FileController(fileName);
        gardenMap = new ArrayList<>();

        try {
            file.openInput();
            String line = file.readLine();
            while (line != null) {
                line = line.trim();
                ArrayList<Character> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    row.add(c);
                }
                gardenMap.add(row);
                line = file.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        long price = 0;
        Point nextArea = findNextArea();
        while (nextArea != null) {
            logDebug("Checking plot " + getGardenPlot(nextArea) + " at (" + nextArea.x + ", " + nextArea.y + ")");
            ArrayList<Point> areaPoints = getAreaPoints(nextArea);
            int area = areaPoints.size();

            if (section == 1) {
                int perimeter = 0;
                for (Point p : areaPoints) {
                    Point north = new Point(p.x, p.y - 1);
                    if (!isInArea(north, areaPoints)) perimeter++;
                    Point east = new Point(p.x + 1, p.y);
                    if (!isInArea(east, areaPoints)) perimeter++;
                    Point south = new Point(p.x, p.y + 1);
                    if (!isInArea(south, areaPoints)) perimeter++;
                    Point west = new Point(p.x - 1, p.y);
                    if (!isInArea(west, areaPoints)) perimeter++;
                }
                price += area * (long)perimeter;
                logDebug("Plot has area " + area + " and perimeter " + perimeter);
            }
            else {
                int sides = 0;
                ArrayList<Point> hasNWall = new ArrayList<>();
                ArrayList<Point> hasEWall = new ArrayList<>();
                ArrayList<Point> hasSWall = new ArrayList<>();
                ArrayList<Point> hasWWall = new ArrayList<>();
                for (Point p : areaPoints) {
                    Point north = new Point(p.x, p.y - 1);
                    if (!isInArea(north, areaPoints)) hasNWall.add(p);;
                    Point east = new Point(p.x + 1, p.y);
                    if (!isInArea(east, areaPoints)) hasEWall.add(p);
                    Point south = new Point(p.x, p.y + 1);
                    if (!isInArea(south, areaPoints)) hasSWall.add(p);
                    Point west = new Point(p.x - 1, p.y);
                    if (!isInArea(west, areaPoints)) hasWWall.add(p);
                }
                while (!hasNWall.isEmpty()) {
                    sides++;
                    Point start = hasNWall.remove(0);
                    hasNWall = removeNeighbors(start, MapDirection.N, hasNWall);
                }
                while (!hasEWall.isEmpty()) {
                    sides++;
                    Point start = hasEWall.remove(0);
                    hasEWall = removeNeighbors(start, MapDirection.E, hasEWall);
                }
                while (!hasSWall.isEmpty()) {
                    sides++;
                    Point start = hasSWall.remove(0);
                    hasSWall = removeNeighbors(start, MapDirection.S, hasSWall);
                }
                while (!hasWWall.isEmpty()) {
                    sides++;
                    Point start = hasWWall.remove(0);
                    hasWWall = removeNeighbors(start, MapDirection.W, hasWWall);
                }
                price += sides * area;
            }
            removeArea(areaPoints);
            nextArea = findNextArea();
        }
        return price;
    }

    private ArrayList<Point> removeNeighbors(Point p, MapDirection wallDirection, ArrayList<Point> pointsWithWall) {
        ArrayList<Point> newArray = new ArrayList<>();
        Point left, right;
        MapPoint m = new MapPoint(p);
        switch (wallDirection) {
            case MapDirection.N -> {
                left = m.getNextPoint(MapDirection.W).toPoint();
                right = m.getNextPoint(MapDirection.E).toPoint();
            }
            case MapDirection.E -> {
                left = m.getNextPoint(MapDirection.N).toPoint();
                right = m.getNextPoint(MapDirection.S).toPoint();
            }
            case MapDirection.S -> {
                left = m.getNextPoint(MapDirection.E).toPoint();
                right = m.getNextPoint(MapDirection.W).toPoint();
            }
            case MapDirection.W -> {
                left = m.getNextPoint(MapDirection.S).toPoint();
                right = m.getNextPoint(MapDirection.N).toPoint();
            }
            default -> {
                left = new Point(-1, -1);
                right = new Point(-1, -1);
            }
        }
        boolean foundLeft = false;
        boolean foundRight = false;
        for (Point w : pointsWithWall) {
            if (w.x == left.x && w.y == left.y)
                foundLeft = true;
            else if (w.x == right.x && w.y == right.y)
                foundRight = true;
            else
                newArray.add(w);
        }
        if (foundLeft)
            newArray = removeNeighbors(left, wallDirection, newArray);
        if (foundRight)
            newArray = removeNeighbors(right, wallDirection, newArray);
        return newArray;
    }

    private void removeArea(ArrayList<Point> areaPoints) {
        for (Point p : areaPoints) {
            gardenMap.get(p.y).set(p.x, ' ');
        }
    }

    private boolean isInArea(Point p, ArrayList<Point> area) {
        boolean found = false;
        for (Point f : area) {
            if (f.x == p.x && f.y == p.y) {
                found = true;
                break;
            }
        }
        logDebug("\t\tTest Point (" + p.x + ", " + p.y + ")");
        if (debug) {
            StringBuilder s = new StringBuilder("\t\tArray ");
            for (Point a : area) {
                s.append("(").append(a.x).append(", ").append(a.y).append(")");
            }
            logDebug(s.toString());
        }
        return found;
    }

    private ArrayList<Point> getAreaPoints(Point p) {
        char plant = getGardenPlot(p);
        ArrayList<Point> allPoints = new ArrayList<>();
        allPoints.add(p);
        ArrayList<Point> pointsToCheck = new ArrayList<>();
        pointsToCheck.add(p);
        while (!pointsToCheck.isEmpty()) {
            logDebug("\t" + pointsToCheck.size() + " points to check");
            Point here = pointsToCheck.remove(0);
            Point north = new Point(here.x, here.y - 1);
            if (doesPointNeedAddedToArea(plant, north, allPoints)) {
                allPoints.add(north);
                pointsToCheck.add(north);
            }
            Point east = new Point(here.x + 1, here.y);
            if (doesPointNeedAddedToArea(plant, east, allPoints)) {
                allPoints.add(east);
                pointsToCheck.add(east);
            }
            Point south = new Point(here.x, here.y + 1);
            if (doesPointNeedAddedToArea(plant, south, allPoints)) {
                allPoints.add(south);
                pointsToCheck.add(south);
            }
            Point west = new Point(here.x - 1, here.y);
            if (doesPointNeedAddedToArea(plant, west, allPoints)) {
                allPoints.add(west);
                pointsToCheck.add(west);
            }
        }
        return allPoints;
    }

    private boolean doesPointNeedAddedToArea(char c, Point p, ArrayList<Point> foundArea) {
        if (c != getGardenPlot(p)) {
            logDebug("\tPlot does not match");
            return false;
        }
        else {
            boolean isInArea = isInArea(p, foundArea);
            logDebug("\tIs in area = " + isInArea);
            return !isInArea;
        }
    }

    private Point findNextArea() {
        for (int y = 0; y < gardenMap.size(); y++) {
            for (int x = 0; x < gardenMap.get(y).size(); x++) {
                Point here = new Point(x, y);
                if (getGardenPlot(here) != ' ') return here;
            }
        }
        return null;
    }

    private char getGardenPlot(Point p) {
        if (p.y >= 0 && p.y < gardenMap.size() && p.x >= 0 && p.x < gardenMap.get(p.y).size()) {
            return gardenMap.get(p.y).get(p.x);
        }
        else {
            return ' ';
        }
    }
}
