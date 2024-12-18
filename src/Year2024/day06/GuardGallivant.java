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
    private Point initialGuardPosition;
    private MapDirection initialGuardDirection;

    public GuardGallivant(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new GuardGallivant(6, "Guard Gallivant", "src\\Year2024\\day06\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day06\\data\\log.txt");
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
    public Object doProcessing(int section, Object input) {
        String fileName = (String)input;
        FileController file = new FileController(fileName);
        try {
            ParseMap(file);
            if (section == 1) {
                while (doStep() == StepResult.Continue) {}
                return getTotalCovered();
            }
            else {
                while (doStep() == StepResult.Continue) {}
                recordUnaltered();
                reset();
                int countGoodObstructions = 0;
                int testPoints = 0;
                for (ArrayList<MapBlock> row : map) {
                    for (MapBlock m : row) {
                        if (m.unalteredInitialDirections.size() > 0) {
                            testPoints++;
                            reset();
                            m.hasObstruction = true;
                            StepResult r = doStep();
                            while (r == StepResult.Continue) {
                                r = doStep();
                            }
                            if (r == StepResult.Loop) {
                                countGoodObstructions++;
                            }
                            m.hasObstruction = false;
                            if (testPoints % 1000 == 0) log("Tested " + testPoints + " points");
                        }
                    }
                }
                return countGoodObstructions;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            try {
                file.closeFile();
            } catch (IOException ex) {}
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
        initialGuardDirection = guardDirection;
        initialGuardPosition = guardPosition;
        try {
            f.closeFile();
        } catch (IOException ex) {}
        logDebug("Parsing map complete");
    }

    private void recordUnaltered() {
        for (ArrayList<MapBlock> row : map) {
            for (MapBlock m : row) {
                m.recordUnaltered();
            }
        }
    }

    private void reset() {
        for (ArrayList<MapBlock> row : map) {
            for (MapBlock m : row) {
                m.reset();
            }
        }
        guardDirection = initialGuardDirection;
        guardPosition = initialGuardPosition;
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

    private static enum StepResult {
        Continue, Loop, OffMap
    }

    private StepResult doStep() {
        Point forward = getForward();
        MapBlock nextStep = getBlock(forward);
        if (nextStep == null) {
            logDebug("Reached edge of area");
            return StepResult.OffMap;
        }
        else if (nextStep.hasObstruction) {
            switch (guardDirection) {
                case MapDirection.N:
                    guardDirection = MapDirection.E;
                    logDebug("Turning E");
                    return StepResult.Continue;
                case MapDirection.E:
                    guardDirection = MapDirection.S;
                    logDebug("Turning S");
                    return StepResult.Continue;
                case MapDirection.S: 
                    guardDirection = MapDirection.W;
                    logDebug("Turning W");
                    return StepResult.Continue;
                case MapDirection.W:
                    guardDirection = MapDirection.N;
                    logDebug("Turning N");
                    return StepResult.Continue;
                default:
                    log("Invalid direction");
                    return StepResult.OffMap;
            }
        }
        else if (nextStep.isRepeat(guardDirection)) {
            logDebug("Completed loop");
            return StepResult.Loop;
        }
        else {
            logDebug("(" + guardPosition.x + ", " + guardPosition.y + ") to (" + forward.x + ", " + forward.y + ")");
            guardPosition = forward;
            nextStep.addGuardStep(guardDirection);
            return StepResult.Continue;
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
        ArrayList<MapDirection> unalteredInitialDirections;
        
        public MapBlock(boolean hasObstruction) {
            this.hasObstruction = hasObstruction;
            guardInitialDirections = new ArrayList<>();
            unalteredInitialDirections = new ArrayList<>();
        }

        public void addGuardStep(MapDirection d) {
            if (!guardInitialDirections.contains(d)) 
                guardInitialDirections.add(d);
        }

        public boolean isRepeat(MapDirection d) {
            return guardInitialDirections.contains(d);
        }

        public void recordUnaltered() {
            unalteredInitialDirections = guardInitialDirections;
        }

        public void reset() {
            guardInitialDirections = new ArrayList<>();
        }
    }
}
