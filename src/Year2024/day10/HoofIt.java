package Year2024.day10;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import tools.FileController;
import tools.PuzzleMap;
import tools.RunPuzzle;
import tools.TestCase;

public class HoofIt extends tools.RunPuzzle {
    private ArrayList<ArrayList<Integer>> elevationMap;

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
        FileController file = new FileController(fileName);
        elevationMap = new ArrayList<>();

        try {
            file.openInput();
            String line = file.readLine();
            while (line != null) {
                line = line.trim();
                ArrayList<Integer> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
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
                            row.add(Integer.valueOf("" + c));
                            break;
                        case '.':
                            row.add(-1);
                            break;
                        default:
                            break;
                    }
                }
                elevationMap.add(row);
                line = file.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            try {
                file.closeFile();
            } catch (IOException ex) {}
        }
        logDebug("Map " + elevationMap.get(0).size() + " by " + elevationMap.size());

        HashMap<Point, ArrayList<Point>> paths = new HashMap<>();
        for (int y = 0; y < elevationMap.size(); y++) {
            ArrayList<Integer> row = elevationMap.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (row.get(x) == 0) {
                    ArrayList<Point> currentPoints = new ArrayList<>();
                    currentPoints.add(new Point(x, y));
                    paths.put(new Point(x, y), currentPoints);
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
                    Point east = new Point(c.x + 1, c.y);
                    logDebug("East " + getElevation(east));
                    if (getElevation(east) == targetElevation) nextPoints.add(east);
                    Point north = new Point(c.x, c.y - 1);
                    logDebug("North " + getElevation(north));
                    if (getElevation(north) == targetElevation) nextPoints.add(north);
                    Point west = new Point(c.x - 1, c.y);
                    logDebug ("West " + getElevation(west));
                    if (getElevation(west) == targetElevation) nextPoints.add(west);
                    Point south = new Point(c.x, c.y + 1);
                    logDebug("South " + getElevation(south));
                    if (getElevation(south) == targetElevation) nextPoints.add(south);
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

    private int getElevation(Point p) {
        if (p.y >= 0 && p.y < elevationMap.size() && p.x >= 0 && p.x < elevationMap.get(p.y).size()) {
            return elevationMap.get(p.y).get(p.x);
        }
        else {
            return -1;
        }
    }

    public class ElevationMap extends PuzzleMap<Integer> {

        @Override
        public Integer parse(char c, int x, int y) {
            return Integer.valueOf("" + c);
        }
        
    }
}
