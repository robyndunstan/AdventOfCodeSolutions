package Year2024.day20;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.MapDirection;
import tools.MapPoint;
import tools.RunPuzzle;
import tools.TestCase;

public class RaceCondition extends tools.RunPuzzle {
    private RaceMap map;

    public RaceCondition(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new RaceCondition(20, "Race Condition", test4);
        RunPuzzle p = new RaceCondition(20, "Race Condition", puzzle);
        //p.setLogFile("src\\Year2024\\day20\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, test1, 1));
        tests.add(new TestCase<>(1, test2, 2)); 
        tests.add(new TestCase<>(1, test3, 3));
        tests.add(new TestCase<>(1, test4, 4));
        tests.add(new TestCase<>(1, test5, 5));
        tests.add(new TestCase<>(1, test6, 8));
        tests.add(new TestCase<>(1, test7, 10));
        tests.add(new TestCase<>(1, test8, 14));
        tests.add(new TestCase<>(1, test9, 16));
        tests.add(new TestCase<>(1, test10, 30));
        tests.add(new TestCase<>(1, test11, 44));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        map = new RaceMap();
        PuzzleInput pi = (PuzzleInput)input;
        try {
            map.parseMap(pi.filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        int defaultTime = map.getDefaultTime();
        int cheatCount = 0;
        for (int x = 0; x < map.getSizeX(); x++) {
            for (int y = 0; y < map.getSizeY(); y++) {
                MapPoint hereP = new MapPoint(x, y);
                Integer hereV = map.getValue(hereP);
                if (hereV != null && hereV != -1) {
                    MapDirection[] ds = new MapDirection[4];
                    ds[0] = MapDirection.E;
                    ds[1] = MapDirection.N;
                    ds[2] = MapDirection.S;
                    ds[3] = MapDirection.W;
                    for (MapDirection d : ds) {
                        if (map.getValue(hereP.getNextPoint(d)) == -1) {
                            Integer cheatV = map.getValue(hereP.getNextPoint(d, 2));
                            if (cheatV != null && cheatV - hereV - 2 >= pi.minPsSaved) {
                                logDebug("\tSaves " + (cheatV - hereV - 2) + " ps by cheating between (" + hereP.x + ", " + hereP.y + ") going " + d);
                                cheatCount++;
                            }
                        }
                    }
                }
            }
        }

        if (section == 1) {
            return cheatCount;
        }
        else {
            return null;
        }
    }

    private static PuzzleInput test1 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 64);
    private static PuzzleInput test2 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 40);
    private static PuzzleInput test3 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 38);
    private static PuzzleInput test4 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 36);
    private static PuzzleInput test5 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 20);
    private static PuzzleInput test6 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 12);
    private static PuzzleInput test7 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 10);
    private static PuzzleInput test8 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 8);
    private static PuzzleInput test9 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 6);
    private static PuzzleInput test10 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 4);
    private static PuzzleInput test11 = new PuzzleInput("src\\Year2024\\day20\\data\\test1file", 2);
    private static PuzzleInput puzzle = new PuzzleInput("src\\Year2024\\day20\\data\\puzzleFile", 100);

    private static class PuzzleInput {
        public String filename;
        public int minPsSaved;
        public PuzzleInput(String f, int m) {
            filename = f;
            minPsSaved = m;
        }
    }

    private class RaceMap extends tools.PuzzleMap<Integer> {
        public MapPoint start, end;

        @Override
        public Integer parse(char c, int x, int y) {
            switch(c) {
                case '#': return -1;
                case '.': return 0;
                case 'S':
                    start = new MapPoint(x, y);
                    return 0;
                case 'E':
                    end = new MapPoint(x, y);
                    return 0;
                default: return -1;
            }
        }

        public int getDefaultTime() {
            markCourse();
            if (debug) {
                StringBuilder s = new StringBuilder();
                s.append("\t_");
                for (int x = 0; x < this.getSizeX(); x++) {
                    s.append((x % 10));
                }
                s.append('\n');
                for (int y = 0; y < this.getSizeY(); y++) {
                    StringBuilder row = new StringBuilder("\t");
                    row.append((y % 10));
                    for (int x = 0; x < this.getSizeX(); x++) {
                        Integer v = this.getValue(new Point(x, y));
                        if (v == null || v == -1) row.append('#');
                        else row.append((v % 10));
                    }
                    s.append(row);
                    s.append("\n");
                }
                logDebug(s.toString());
            }
            return this.getValue(end);
        }

        private void markCourse() {
            for (MapPoint m : start.getCardinalNeighbors()) {
                Integer v = this.getValue(m);
                if (v != null && v != -1) {
                    this.setValue(m, 1);
                }
            }
            int currentSteps = 1;
            boolean changes = true;
            while (this.getValue(end) == 0 && changes) {
                changes = false;
                for (int x = 0; x <= this.getSizeX(); x++) {
                    for (int y = 0; y <= this.getSizeY(); y++) {
                        MapPoint m = new MapPoint(x, y);
                        Integer mV = this.getValue(m);
                        if (mV != null && mV == currentSteps) {
                            for (MapPoint n : m.getCardinalNeighbors()) {
                                Integer nV = this.getValue(n);
                                if (nV != null && nV == 0) {
                                    this.setValue(n, currentSteps + 1);
                                    changes = true;
                                }
                            }
                        }
                    }
                }
                currentSteps++;
            }
        }
    }
}
