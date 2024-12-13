package Year2024.day06;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class GuardGallivant extends tools.RunPuzzle {
    private ArrayList<ArrayList<MapBlock>> map;
    private Point guardPosition;
    private MapDirection guardDirection;

    public GuardGallivant(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = true;
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
        tests.add(new TestCase<>(2, "src\\Year2024\\day06\\data\\test1File", 6));
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
            ParseMap(file);
            if (section == 1) {
                while (doStep()) {}
                return getTotalCovered();
            }
            else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void ParseMap(FileController f) throws IOException {
        logDebug("Parsing file " + f.getFile().getAbsolutePath());
        map = new ArrayList<>();
        f.openInput();
        String line = f.readLine();
        while (line != null) {
            line = line.trim();
            ArrayList<MapBlock> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                switch (c) {
                    case '.' :
                        row.add(new MapBlock(false));
                        break;
                    case '#':
                        row.add(new MapBlock(true));
                        break;
                    case '^':
                        MapBlock m = new MapBlock(false);
                        m.addGuardStep(MapDirection.N);
                        guardPosition = new Point(row.size(), map.size());
                        guardDirection = MapDirection.N;
                        row.add(m);
                        break;
                    default:
                        log("Invalid character " + c);
                        break;
                }
            }
            map.add(row);
            line = f.readLine();
        }
        try {
            f.closeFile();
        } catch (IOException ex) {}
        logDebug("Parsing map complete");
    }

    private MapBlock getBlock(Point p) {
        if (p.y >= 0 && p.y < map.size() && p.x >= 0 && p.x < map.get(p.y).size())
            return map.get(p.y).get(p.x);
        else 
            return null;
    }

    private Point getForward() {
        return switch (guardDirection) {
            case MapDirection.N -> new Point(guardPosition.x, guardPosition.y - 1);
            case MapDirection.E -> new Point(guardPosition.x + 1, guardPosition.y);
            case MapDirection.S -> new Point(guardPosition.x, guardPosition.y + 1);
            case MapDirection.W -> new Point(guardPosition.x - 1, guardPosition.y);
            default -> new Point(-1, -1);
        };
    }

    private boolean doStep() {
        Point forward = getForward();
        MapBlock nextStep = getBlock(forward);
        if (nextStep == null) {
            logDebug("Reached edge of area");
            return false;
        }
        else if (nextStep.hasObstruction) {
            switch (guardDirection) {
                case MapDirection.N:
                    guardDirection = MapDirection.E;
                    logDebug("Turning E");
                    return true;
                case MapDirection.E:
                    guardDirection = MapDirection.S;
                    logDebug("Turning S");
                    return true;
                case MapDirection.S: 
                    guardDirection = MapDirection.W;
                    logDebug("Turning W");
                    return true;
                case MapDirection.W:
                    guardDirection = MapDirection.N;
                    logDebug("Turning N");
                    return true;
                default:
                    log("Invalid direction");
                    return false;
            }
        }
        else if (nextStep.isRepeat(guardDirection)) {
            logDebug("Completed loop");
            return false;
        }
        else {
            logDebug("(" + guardPosition.x + ", " + guardPosition.y + ") to (" + forward.x + ", " + forward.y + ")");
            guardPosition = forward;
            nextStep.addGuardStep(guardDirection);
            return true;
        }
    }

    private int getTotalCovered() {
        int total = 0;
        for (ArrayList<MapBlock> row : map) {
            for (MapBlock m : row) {
                if (m.guardInitialDirections.size() > 0)
                    total++;
            }
        }
        return total;
    }

    private static enum MapDirection {
        N, E, S, W;
    }

    private static class MapBlock {
        boolean hasObstruction;
        ArrayList<MapDirection> guardInitialDirections;
        
        public MapBlock(boolean hasObstruction) {
            this.hasObstruction = hasObstruction;
            guardInitialDirections = new ArrayList<>();
        }

        public void addGuardStep(MapDirection d) {
            if (!guardInitialDirections.contains(d)) 
                guardInitialDirections.add(d);
        }

        public boolean isRepeat(MapDirection d) {
            return guardInitialDirections.contains(d);
        }
    }
}
