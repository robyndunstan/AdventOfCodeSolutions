package Year2024.day18;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.MapDirection;
import tools.MapPoint;
import tools.RunPuzzle;
import tools.TestCase;

public class RamRun extends tools.RunPuzzle {
    private int maxCoord;
    private int targetNs;
    private ArrayList<Point> fallingBytes;
    private int fallingIndex;
    private int[][] map;

    public RamRun(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new RamRun(18, "RAM Run", "src\\Year2024\\day18\\data\\test1file");
        RunPuzzle p = new RamRun(18, "RAM Run", "src\\Year2024\\day18\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day18\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day18\\data\\test1File", 22));
        tests.add(new TestCase<>(2, "src\\Year2024\\day18\\data\\test1File", 22)); // actual result is 6,1 the point on line 22 (index + 1) in the file
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String filename = (String)input;
        FileController file = new FileController(filename);
        try {
            file.openInput();
            String line = file.readLine();
            int splitIndex = line.indexOf(";");
            if (splitIndex != -1) {
                maxCoord = Integer.parseInt(line.substring(0, splitIndex).trim());
                targetNs = Integer.parseInt(line.substring(splitIndex + 1).trim());
            }
            line = file.readLine();
            fallingBytes = new ArrayList<>();
            while (line != null) {
                splitIndex = line.indexOf(",");
                if (splitIndex != -1) {
                    int x = Integer.parseInt(line.substring(0, splitIndex).trim());
                    int y = Integer.parseInt(line.substring(splitIndex + 1).trim());
                    fallingBytes.add(new Point(x, y));
                }
                line = file.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                file.closeFile();
            }
            catch (IOException e) {}
        }

        logDebug(targetNs + " time and " + fallingBytes.size() + " bytes");
        map = new int[maxCoord + 1][maxCoord + 1];

        if (section == 1) {
            doDrop(targetNs);
            findPaths();
            return map[maxCoord][maxCoord];
        }
        else {
            doDrop(targetNs);
            findPaths();
            while (map[maxCoord][maxCoord] != 0) {
                logDebug(fallingIndex + " fallen bytes");
                resetPaths();
                doDrop();
                findPaths();
            }
            return fallingIndex + 1; // +1 is for first line with dimensions
        }
    }

    private void resetPaths() {
        for (int x = 0; x <= maxCoord; x++) {
            for (int y = 0; y <= maxCoord; y++) {
                if (map[x][y] != -1) map[x][y] = 0;
            }
        }
    }

    private void doDrop() {
        Point p = fallingBytes.get(fallingIndex);
        map[p.x][p.y] = -1;
        fallingIndex++;
    }
    private void doDrop(int ns) {
        fallingIndex = Math.min(ns, fallingBytes.size());
        for (int i = 0; i < fallingIndex; i++) {
            Point p = fallingBytes.get(i);
            map[p.x][p.y] = -1;
        }
    }

    private void findPaths() {
        if (map[0][1] != -1) map[0][1] = 1;
        if (map[1][0] != -1) map[1][0] = 1;
        int currentSteps = 1;
        boolean changes = true;
        while (map[maxCoord][maxCoord] == 0 && changes) {
            changes = false;
            for (int x = 0; x <= maxCoord; x++) {
                for (int y = 0; y <= maxCoord; y++) {
                    if (map[x][y] == currentSteps) {
                        ArrayList<MapPoint> nextPoints = new ArrayList<>();
                        MapPoint m = new MapPoint(x, y);
                        nextPoints.add(m.getNextPoint(MapDirection.N));
                        nextPoints.add(m.getNextPoint(MapDirection.S));
                        nextPoints.add(m.getNextPoint(MapDirection.E));
                        nextPoints.add(m.getNextPoint(MapDirection.W));
                        for (MapPoint n : nextPoints) {
                            if (getSteps(n) == 0) {
                                map[n.x][n.y] = currentSteps + 1;
                                changes = true;
                            }
                        }
                    }
                }
            }
            currentSteps++;
        }

        if (debug) {
            StringBuilder walls = new StringBuilder();
            StringBuilder steps = new StringBuilder();
            for (int y = 0; y <= maxCoord; y++) {
                StringBuilder w = new StringBuilder();
                StringBuilder s = new StringBuilder();
                for (int x = 0; x <= maxCoord; x++) {
                    if (map[x][y] == -1) {
                        w.append('#');
                        s.append('#');
                    }
                    else  {
                        w.append('.');
                        s.append(map[x][y] % 10);
                    }
                }
                walls.append(w);
                walls.append('\n');
                steps.append(s);
                steps.append('\n');
            }
            logDebug(walls.toString());
            logDebug(steps.toString());
        }
    }

    private int getSteps(Point p) {
        if (p.x < 0 || p.x > maxCoord || p.y < 0 || p.y > maxCoord) {
            return -1;
        }
        else {
            return map[p.x][p.y];
        }
    }
}
