package Year2024.day12;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
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
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test1File", 140));
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test2File", 772));
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test3File", 1930));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
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

        if (section == 1) {
            long price = 0;
            Point nextArea = findNextArea();
            while (nextArea != null) {
                ArrayList<Point> areaPoints = getAreaPoints(nextArea);
                int area = areaPoints.size();
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
                removeArea(areaPoints);
            }
            return price;
        }
        else {
            return null;
        }
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
        return !found;
    }

    private ArrayList<Point> getAreaPoints(Point p) {
        char plant = getGardenPlot(p);
        ArrayList<Point> allPoints = new ArrayList<>();
        allPoints.add(p);
        ArrayList<Point> pointsToCheck = new ArrayList<>();
        pointsToCheck.add(p);
        while (!pointsToCheck.isEmpty()) {
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
            return false;
        }
        else {
            return !isInArea(p, foundArea);
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
