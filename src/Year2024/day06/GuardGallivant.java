package Year2024.day06;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class GuardGallivant extends tools.RunPuzzle {
    private Map map;

    public GuardGallivant(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = true;
        map = new Map(this.logFile, debug);
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new GuardGallivant(6, "Guard Gallivant", "src\\Year2024\\day06\\data\\puzzleFile");
        p.setLogFile("src\\Year2024\\day06\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day06\\data\\test1File", 41));
        tests.add(new TestCase<>(2, "src\\Year2024\\day06\\data\\test1File", 0));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) { // < 4688
        String fileName = (String)input;
        FileController file = new FileController(fileName);
        try {
            map.parseMap(file);
            while (map.doStep() && map.getSteps() < Integer.MAX_VALUE) {}
            return map.getTotalCovered();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static enum MapContents {
        Wall,
        Empty,
        Footprints,
        OffMap
    }

    private static class Map {
        ArrayList<ArrayList<MapContents>> walls;
        Point guardPosition, guardDirection;
        int steps;

        FileController log;
        boolean debug;

        public Map(FileController log, boolean debug) {
            walls = new ArrayList<>();
            steps = 0;
            this.log = log;
            this.debug = debug;
        }

        public void parseMap(FileController f) throws Exception {
            logDebug("Parsing file " + f.getFile().getAbsolutePath());
            steps = 0;
            walls = new ArrayList<>();
            f.openInput();
            String line = f.readLine();
            while (line != null) {
                line = line.trim();
                ArrayList<MapContents> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '.' :
                            row.add(MapContents.Empty);
                            break;
                        case '#':
                            row.add(MapContents.Wall);
                            break;
                        case '^':
                            row.add(MapContents.Footprints);
                            guardPosition = new Point(row.size() - 1, walls.size());
                            guardDirection = new Point(0, -1);
                            break;
                        default:
                            log("Invalid character " + c);
                            break;
                    }
                }
                walls.add(row);
                line = f.readLine();
            }
            steps = 1;
            f.closeFile();
            logDebug("Parsing map complete");
        }

        public MapContents getContents(Point p) {
            if (p.y >= 0 && p.y < walls.size() && p.x >= 0 && p.x < walls.get(p.y).size()) {
                return walls.get(p.y).get(p.x);
            }
            else {
                logDebug("Point (" +p.x + ", " + p.y + ") off map");
                return MapContents.OffMap;
            }
        }
        public void setStepped(Point p) {
            if (p.y >= 0 && p.y < walls.size() && p.x >= 0 && p.x < walls.get(p.y).size()) {
                walls.get(p.y).set(p.x, MapContents.Footprints);
            }
        }

        public boolean doStep() {
            Point forward = new Point(guardPosition.x + guardDirection.x, guardPosition.y + guardDirection.y);
            MapContents nextStep = getContents(forward);
            switch (getContents(forward)) {
                case MapContents.Empty:
                case MapContents.Footprints:
                    steps++;
                    logDebug("Step " + steps + ": (" + guardPosition.x + ", " + guardPosition.y + ") to (" + forward.x + ", " + forward.y + ")");
                    guardPosition = forward;
                    setStepped(forward);
                    return true;
                case MapContents.Wall:
                    if (guardDirection.x == 0 && guardDirection.y == -1) {
                        guardDirection = new Point(1, 0);
                        logDebug("Turning E");
                        return true;
                    }
                    else if (guardDirection.x == 1 && guardDirection.y == 0) {
                        guardDirection = new Point(0, 1);
                        logDebug("Turning S");
                        return true;
                    }
                    else if (guardDirection.x == 0 && guardDirection.y == 1) {
                        guardDirection = new Point(-1, 0);
                        logDebug("Turning W");
                        return true;
                    }
                    else if (guardDirection.x == -1 && guardDirection.y == 0) {
                        guardDirection = new Point(0, -1);
                        logDebug("Turning N");
                        return true;
                    }
                    else {
                        log("Invalid direction (" + guardDirection.x + ", " + guardDirection.y + ")");
                        return false;
                    }
                case MapContents.OffMap:
                    logDebug("Reached edge of area");
                    return false;
                default:
                    log("Invalid contents " + getContents(forward));
                    return false;
            }
        }

        public int getSteps() {
            return steps;
        }
        public int getTotalCovered() {
            int total = 0;
            for (ArrayList<MapContents> row : walls) {
                for (MapContents m : row) {
                    if (m == MapContents.Footprints) total++;
                }
            }
            return total;
        }

        public void log(String line) {
            if (this.log.isOpenOutput()) {
                this.log.writeLine(line);
            }
            System.out.println(line);
        }
    
        public void logDebug(String line) {
            if (debug && this.log.isOpenOutput()) {
                this.log.writeLine(line);
            }
            else if (debug) {
                System.out.println(line);
            }
        }
        public void logDebug(Integer value) {
            logDebug(value.toString());
        }
    }
}
