package Year2024.day10;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import tools.MapDirection;
import tools.MapPoint;
import tools.PuzzleMap;
import tools.RunPuzzle;
import tools.TestCase;

public class HoofIt extends tools.RunPuzzle {
    private ElevationMap elevationMap;

    public HoofIt(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new HoofIt(10, "Hoof It", "src\\Year2024\\day10\\data\\puzzleFile");
        p.setLogFile("src\\Year2024\\day10\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test1File", 1));
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test2File", 2));
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test3File", 4));
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test4File", 3));
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test5File", 36));
        tests.add(new TestCase<>(2, "src\\Year2024\\day10\\data\\test6File", 3));
        tests.add(new TestCase<>(2, "src\\Year2024\\day10\\data\\test7File", 13));
        tests.add(new TestCase<>(2, "src\\Year2024\\day10\\data\\test8File", 227));
        tests.add(new TestCase<>(2, "src\\Year2024\\day10\\data\\test5File", 81));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String fileName = (String)input;
        elevationMap = new ElevationMap();
        try {
            elevationMap.parseMap(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        logDebug("Map " + elevationMap.getSizeX() + " by " + elevationMap.getSizeY());

        HashMap<Point, ArrayList<Point>> paths = new HashMap<>();
        for (int y = 0; y < elevationMap.getSizeY(); y++) {
            for (int x = 0; x < elevationMap.getSizeX(); x++) {
                Point here = new Point(x, y);
                if (elevationMap.getValue(here) == 0) {
                    ArrayList<Point> currentPoints = new ArrayList<>();
                    currentPoints.add(here);
                    paths.put(here, currentPoints);
                }
            }
        }
        logDebug("Found " + paths.keySet().size() + " starting points");

        for (int targetElevation = 1; targetElevation <= 9; targetElevation++) {
            logDebug("Search for elevation " + targetElevation);
            for (Point pathStart : paths.keySet()) {
                ArrayList<Point> nextPoints = new ArrayList<>();
                ArrayList<Point> currentPoints = paths.get(pathStart);
                for (Point c : currentPoints) {
                    logDebug("Testing point (" + c.x + ", " + c.y + ")");
                    MapPoint m = new MapPoint(c);
                    Point east = m.getNextPoint(MapDirection.E).toPoint();
                    Integer eastElevation = elevationMap.getValue(east);
                    logDebug("East " + eastElevation);
                    if (eastElevation != null && eastElevation == targetElevation) nextPoints.add(east);
                    Point north = m.getNextPoint(MapDirection.N).toPoint();
                    Integer northElevation = elevationMap.getValue(north);
                    logDebug("North " + northElevation);
                    if (northElevation != null && northElevation == targetElevation) nextPoints.add(north);
                    Point west = m.getNextPoint(MapDirection.W).toPoint();
                    Integer westElevation = elevationMap.getValue(west);
                    logDebug ("West " + westElevation);
                    if (westElevation != null && westElevation == targetElevation) nextPoints.add(west);
                    Point south = m.getNextPoint(MapDirection.S).toPoint();
                    Integer southElevation = elevationMap.getValue(south);
                    logDebug("South " + southElevation);
                    if (southElevation != null && southElevation == targetElevation) nextPoints.add(south);
                }
                if (section == 1) {
                    logDebug("Before dedup " + nextPoints.size());
                    ArrayList<Point> dedup = new ArrayList<>();
                    for (int i = 0; i < nextPoints.size() - 1; i++) {
                        boolean matchFound = false;
                        Point pi = nextPoints.get(i);
                        for (int j = i + 1; j < nextPoints.size(); j++) {
                            Point pj = nextPoints.get(j);
                            if (pi.x == pj.x && pi.y == pj.y) {
                                matchFound = true;
                                break;
                            }
                        }
                        if (!matchFound) dedup.add(pi);
                    }
                    dedup.add(nextPoints.get(nextPoints.size() - 1));
                    if (debug) {
                        for (Point p : dedup) {
                            logDebug("\t(" + p.x + ", " + p.y + ")");
                        }
                    }
                    paths.put(pathStart, dedup);
                }
                else {
                    paths.put(pathStart, nextPoints);
                }
                logDebug("Start (" + pathStart.x + ", " + pathStart.y + ") has " + paths.get(pathStart).size() + " points at elevation " + targetElevation);
            }
        }

        int sumPaths = 0;
        for (Point pathStart : paths.keySet()) {
            sumPaths += paths.get(pathStart).size();
        }
        return sumPaths;
    }

    public class ElevationMap extends PuzzleMap<Integer> {

        @Override
        public Integer parse(char c, int x, int y) {
            switch (c) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    return Integer.valueOf("" + c);
                case '.':
                default:
                    return -1;
            }
        }
        
    }
}
