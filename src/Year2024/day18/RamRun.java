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
    private int ns;
    private ArrayList<Point> fallingBytes;
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
                ns = Integer.parseInt(line.substring(splitIndex + 1).trim());
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

        logDebug(ns + " time and " + fallingBytes.size() + " bytes");
        map = new int[maxCoord + 1][maxCoord + 1];
        for (int i = 0; i < Math.min(ns, fallingBytes.size()); i++) {
            Point p = fallingBytes.get(i);
            map[p.x][p.y] = -1;
        }

        if (map[0][1] != -1) map[0][1] = 1;
        if (map[1][0] != -1) map[1][0] = 1;
        int currentSteps = 1;
        while (map[maxCoord][maxCoord] == 0) {
            for (int x = 0; x <= maxCoord; x++) {
                for (int y = 0; y <= maxCoord; y++) {
                    if (map[x][y] == currentSteps) {
                        MapPoint m = new MapPoint(x, y);
                        MapPoint mN = m.getNextPoint(MapDirection.N);
                        if (getSteps(mN) == 0) map[mN.x][mN.y] = currentSteps + 1;
                        MapPoint mE = m.getNextPoint(MapDirection.E);
                        if (getSteps(mE) == 0) map[mE.x][mE.y] = currentSteps + 1;
                        MapPoint mS = m.getNextPoint(MapDirection.S);
                        if (getSteps(mS) == 0) map[mS.x][mS.y] = currentSteps + 1;
                        MapPoint mW = m.getNextPoint(MapDirection.W);
                        if (getSteps(mW) == 0) map[mW.x][mW.y] = currentSteps + 1;
                    }
                }
            }
            currentSteps++;
        }

        return map[maxCoord][maxCoord];
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
