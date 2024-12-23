package Year2024.day06;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.MapDirection;
import tools.MapPoint;
import tools.PuzzleMap;
import tools.RunPuzzle;
import tools.TestCase;

public class GuardGallivant extends tools.RunPuzzle {
    private GuardMap map;

    public GuardGallivant(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        map = new GuardMap();
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
        try {
            map.parseMap(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        if (section == 1) {
            while (map.doStep() == GuardMap.StepResult.Continue) {}
            return map.getTotalCovered();
        }
        else {
            while (map.doStep() == GuardMap.StepResult.Continue) {}
            map.recordUnaltered();
            map.reset();
            int countGoodObstructions = 0;
            int testPoints = 0;
            int maxX = map.getSizeX();
            int maxY = map.getSizeY();
            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    MapBlock m = map.getValue(new Point(x, y));
                    if (!m.unalteredInitialDirections.isEmpty()) {
                        testPoints++;
                        map.reset();
                        m.hasObstruction = true;
                        GuardMap.StepResult r = map.doStep();
                        while (r == GuardMap.StepResult.Continue) {
                            r = map.doStep();
                        }
                        if (r == GuardMap.StepResult.Loop) {
                            countGoodObstructions++;
                        }
                        m.hasObstruction = false;
                        if (testPoints % 1000 == 0) log("Tested " + testPoints + " points");
                    }
                }
            }
            return countGoodObstructions;
        }
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

    private class GuardMap extends PuzzleMap<MapBlock> {
        private MapPoint guardPosition;
        private MapDirection guardDirection;
        private MapPoint initialGuardPosition;
        private MapDirection initialGuardDirection;

        @Override
        public MapBlock parse(char c, int x, int y) {
            switch (c) {
                case '.' :
                    return new MapBlock(false);
                case '#':
                    return new MapBlock(true);
                case '^':
                    MapBlock m = new MapBlock(false);
                    m.addGuardStep(MapDirection.N);
                    guardDirection = MapDirection.N;
                    guardPosition = new MapPoint(x, y);
                    this.initialGuardDirection = this.guardDirection;
                    this.initialGuardPosition = this.guardPosition;
                    return m;
                default:
                    return null;
            }
        }

        public void recordUnaltered() {
            for (ArrayList<MapBlock> row : this.map) {
                for (MapBlock m : row) {
                    m.recordUnaltered();
                }
            }
        }

        public void reset() {
            for (ArrayList<MapBlock> row : map) {
                for (MapBlock m : row) {
                    m.reset();
                }
            }
            guardDirection = initialGuardDirection;
            guardPosition = initialGuardPosition;
        }

        private static enum StepResult {
            Continue, Loop, OffMap
        }

        private StepResult doStep() {
            MapPoint forward = guardPosition.getNextPoint(guardDirection);
            MapBlock nextStep = this.getValue(forward);
            if (nextStep == null) {
                logDebug("Reached edge of area");
                return StepResult.OffMap;
            }
            else if (nextStep.hasObstruction) {
                switch (guardDirection) {
                    case MapDirection.N -> {
                        guardDirection = MapDirection.E;
                        logDebug("Turning E");
                        return StepResult.Continue;
                    }
                    case MapDirection.E -> {
                        guardDirection = MapDirection.S;
                        logDebug("Turning S");
                        return StepResult.Continue;
                    } 
                    case MapDirection.S -> {
                        guardDirection = MapDirection.W;
                        logDebug("Turning W");
                        return StepResult.Continue;
                    }
                    case MapDirection.W -> {
                        guardDirection = MapDirection.N;
                        logDebug("Turning N");
                        return StepResult.Continue;
                    }
                    default -> {
                        log("Invalid direction");
                        return StepResult.OffMap;
                    }
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

        public int getTotalCovered() {
            int total = 0;
            for (ArrayList<MapBlock> row : map) {
                for (MapBlock m : row) {
                    if (!m.guardInitialDirections.isEmpty())
                        total++;
                }
            }
            return total;
        }
    }
}
