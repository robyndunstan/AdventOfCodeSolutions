package Year2024.day08;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import tools.PuzzleMap;
import tools.RunPuzzle;
import tools.TestCase;

public class ResonantCollinearity extends tools.RunPuzzle {
    private AntiNodes antennaMap;

    public ResonantCollinearity(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new ResonantCollinearity(8, "Resonant Collinearity", "src\\Year2024\\day08\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day08\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day08\\data\\test1File", 2));
        tests.add(new TestCase<>(1, "src\\Year2024\\day08\\data\\test2File", 4));
        tests.add(new TestCase<>(1, "src\\Year2024\\day08\\data\\test3File", 4));
        tests.add(new TestCase<>(1, "src\\Year2024\\day08\\data\\test4File", 14));
        tests.add(new TestCase<>(2, "src\\Year2024\\day08\\data\\test5File", 9));
        tests.add(new TestCase<>(2, "src\\Year2024\\day08\\data\\test4File", 34));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String fileName = (String)input;
        antennaMap = new AntiNodes();
        try {
            antennaMap.parseMap(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        for (char c : antennaMap.antennas.keySet()) {
            ArrayList<Point> aList = antennaMap.antennas.get(c);
            logDebug(aList.size() + " antennas of type " + c);
            if (aList.size() >= 2) {
                for (int i = 0; i < aList.size() - 1; i++) {
                    for (int j = i + 1; j < aList.size(); j++) {
                        Point a1 = aList.get(i);
                        Point a2 = aList.get(j);
                        Point path = new Point(a2.x - a1.x, a2.y - a1.y);

                        if (section == 1) {
                            Point an1 = new Point(a1.x - path.x, a1.y - path.y);
                            Point an2 = new Point(a2.x + path.x, a2.y + path.y);
                            markAntinode(an1);
                            markAntinode(an2);
                        }
                        else {
                            Point an = a1;
                            while (markAntinode(an)) {
                                an = new Point(an.x - path.x, an.y - path.y);
                            }
                            an = a2;
                            while (markAntinode(an)) {
                                an = new Point(an.x + path.x, an.y + path.y);
                            }
                        }
                    }
                }
            }
        }
        return antennaMap.totalAntinodes();
    }

    private boolean markAntinode(Point p) {
        if (p.y >= 0 && p.y < antennaMap.getSizeY() && p.x >= 0 && p.x < antennaMap.getSizeX()) {
            antennaMap.setValue(p, true);
            logDebug("Marking point (" + p.x + ", " + p.y + ")");
            return true;
        }
        else {
            logDebug("Off map");
            return false;
        }
    }

    public class AntiNodes extends PuzzleMap<Boolean> {
        public HashMap<Character, ArrayList<Point>> antennas;

        public AntiNodes() {
            super();
            antennas = new HashMap<>();
        }

        @Override
        public Boolean parse(char c, int x, int y) {
            if (c != '.' && !Character.isWhitespace(c)) {
                Point a = new Point(x, y);
                if (antennas.containsKey(c)) {
                    antennas.get(c).add(a);
                }
                else {
                    ArrayList<Point> aList = new ArrayList<>();
                    aList.add(a);
                    antennas.put(c, aList);
                }
            }
            return false;
        }

        public int totalAntinodes() {
            int totalAntinodes = 0;
            for (ArrayList<Boolean> row : map) {
                for (boolean b : row) {
                    if (b) totalAntinodes++;
                }
            }
            return totalAntinodes;
        }
    }
}
