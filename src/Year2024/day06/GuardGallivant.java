package Year2024.day06;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class GuardGallivant extends tools.RunPuzzle {
    private Map map;

    public GuardGallivant(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
        map = new Map();
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new GuardGallivant(6, "Guard Gallivant", "src\\Year2024\\day06\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day06\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day06\\data\\test1File", 0));
        tests.add(new TestCase<>(2, "src\\Year2024\\day06\\data\\test1File", 0));
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
            map.parseMap(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return null;
    }

    private static class Map {
        ArrayList<ArrayList<Boolean>> walls;
        Point guardPosition, guardDirection;

        public Map() {
            walls = new ArrayList<>();
        }

        public void parseMap(FileController f) throws Exception {
            f.openInput();
            String line = f.readLine();
            while (line != null) {
                line = line.trim();
                ArrayList<Boolean> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '.' :
                            row.add(false);
                            break;
                        case '#':
                            row.add(true);
                            break;
                        case '^':
                            row.add(false);
                            guardPosition = new Point(row.size(), walls.size() + 1);
                            guardDirection = new Point(0, -1);
                            break;
                        default:
                            throw new Exception("Invalid character " + c);
                    }
                }
                walls.add(row);
            }
        }

        public boolean hasWall(Point p) throws Exception {
            if (p.y >= 0 && p.y < walls.size() && p.x >= 0 && p.x < walls.get(p.y).size()) {
                return walls.get(p.y).get(p.x);
            }
            else {
                throw new Exception("Off Map");
            }
        }

        public boolean doStep() {
            Point forward = new Point(guardPosition.x + guardDirection.x, guardPosition.y + guardDirection.y);
            try {
                if (hasWall(forward)) {
                    if (guardDirection.x == 0 && guardDirection.y == -1) {
                        guardDirection = new Point(1, 0);
                        return true;
                    }
                    else if (guardDirection.x == 1 && guardDirection.y == 0) {
                        guardDirection = new Point(0, 1);
                        return true;
                    }
                    else if (guardDirection.x == 0 && guardDirection.y == 1) {
                        guardDirection = new Point(-1, 0);
                        return true;
                    }
                    else if (guardDirection.x == -1 && guardDirection.y == 0) {
                        guardDirection = new Point(0, -1);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    guardPosition = new Point(guardPosition.x + guardDirection.x, guardPosition.y + guardDirection.y);
                }
            } catch (Exception e) {
                return false;
            }
        }
    }
}
