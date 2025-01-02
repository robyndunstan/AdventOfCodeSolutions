package Year2024.day15;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.MapDirection;
import tools.MapPoint;
import tools.RunPuzzle;
import tools.TestCase;

public class WarehouseWoes extends tools.RunPuzzle {
    private ArrayList<ArrayList<BlockState>> map;
    private MapPoint robot;
    private ArrayList<MapDirection> moves;

    public WarehouseWoes(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new WarehouseWoes(15, "Warehouse Woes", "src\\Year2024\\day15\\data\\test1file");
        RunPuzzle p = new WarehouseWoes(15, "Warehouse Woes", "src\\Year2024\\day15\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day15\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day15\\data\\test1File", 2028));
        tests.add(new TestCase<>(1, "src\\Year2024\\day15\\data\\test2File", 10092));
        tests.add(new TestCase<>(2, "src\\Year2024\\day15\\data\\test2File", 9021));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String filename = (String)input;
        try {
            parseFile(section, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        for (MapDirection d : moves) {
            MapPoint next = robot.getNextPoint(d);
            switch(getValue(next)) {
                case BlockState.Open:
                    robot = next;
                    break;
                case BlockState.Wall:
                    break;
                case BlockState.Box:
                    if (movedBox(next, d)) {
                        robot = next;
                    }
                    break;
                case BlockState.BoxLeft:
                case BlockState.BoxRight:
                    if (movedWideBox(next, d)) {
                        robot = next;
                    }
                    break;
                default:
                    break;
            }
        }
        
        int total = 0;
        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                if (getValue(new Point(x, y)) == BlockState.Box || getValue(new Point(x, y)) == BlockState.BoxLeft) {
                    total += 100*y + x;
                }
            }
        }
        return total;
    }

    private boolean movedWideBox(MapPoint p, MapDirection d) {
        MapPoint bLeft, bRight;
        if (getValue(p) == BlockState.BoxLeft) {
            bLeft = p;
            bRight = p.getNextPoint(MapDirection.E);
        }
        else {
            bRight = p;
            bLeft = p.getNextPoint(MapDirection.W);
        }
        switch (d) {
            case MapDirection.E:
                MapPoint nextE = bRight.getNextPoint(MapDirection.E);
                switch(getValue(nextE)) {
                    case BlockState.Open:
                        setValue(nextE, BlockState.BoxRight);
                        setValue(bRight, BlockState.BoxLeft);
                        setValue(p, BlockState.Open);
                        return true;
                    case BlockState.Wall:
                        return false;
                    case BlockState.BoxLeft:
                        if (movedWideBox(nextE, MapDirection.E)) {
                            setValue(nextE, BlockState.BoxRight);
                            setValue(bRight, BlockState.BoxLeft);
                            setValue(p, BlockState.Open);
                            return true;
                        }
                        else {
                            return false;
                        }
                    default:
                        return false;
                }
            case MapDirection.W:
                MapPoint nextW = bLeft.getNextPoint(MapDirection.W);
                switch(getValue(nextW)) {
                    case BlockState.Open:
                        setValue(nextW, BlockState.BoxLeft);
                        setValue(bLeft, BlockState.BoxRight);
                        setValue(p, BlockState.Open);
                        return true;
                    case BlockState.Wall:
                        return false;
                    case BlockState.BoxRight:
                        if (movedWideBox(nextW, MapDirection.W)) {
                            setValue(nextW, BlockState.BoxLeft);
                            setValue(bLeft, BlockState.BoxRight);
                            setValue(p, BlockState.Open);
                            return true;
                        }
                        else {
                            return false;
                        }
                    default:
                        return false;
                }
            case MapDirection.N:
            case MapDirection.S:
                MapPoint nLeft = bLeft.getNextPoint(d);
                MapPoint nRight = bRight.getNextPoint(d);
                BlockState nValLeft = getValue(nLeft);
                BlockState nValRight = getValue(nRight);
                if (nValLeft == BlockState.Open && nValRight == BlockState.Open) {
                    setValue(nLeft, BlockState.BoxLeft);
                    setValue(nRight, BlockState.BoxRight);
                    setValue(bLeft, BlockState.Open);
                    setValue(bRight, BlockState.Open);
                    return true;
                }
                else if (nValLeft == BlockState.Wall || nValRight == BlockState.Wall) {
                    return false;
                }
                else if (nValLeft == BlockState.BoxLeft && nValRight == BlockState.BoxRight) {
                    if (movedWideBox(nLeft, d)) {
                        setValue(nLeft, BlockState.BoxLeft);
                        setValue(nRight, BlockState.BoxRight);
                        setValue(bLeft, BlockState.Open);
                        setValue(bRight, BlockState.Open);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else if (nValLeft == BlockState.Open && nValRight == BlockState.BoxLeft) {
                    if (movedWideBox(nRight, d)) {
                        setValue(nLeft, BlockState.BoxLeft);
                        setValue(nRight, BlockState.BoxRight);
                        setValue(bLeft, BlockState.Open);
                        setValue(bRight, BlockState.Open);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else if (nValLeft == BlockState.BoxRight && nValRight == BlockState.Open) {
                    if (movedWideBox(nLeft, d)) {
                        setValue(nLeft, BlockState.BoxLeft);
                        setValue(nRight, BlockState.BoxRight);
                        setValue(bLeft, BlockState.Open);
                        setValue(bRight, BlockState.Open);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else if (nValLeft == BlockState.BoxRight && nValRight == BlockState.BoxLeft) {
                    if (canMoveNorSWideBox(nLeft, d) && canMoveNorSWideBox(nRight, d)) {
                        if (movedWideBox(nLeft, d) && movedWideBox(nRight, d)) {
                            setValue(nLeft, BlockState.BoxLeft);
                            setValue(nRight, BlockState.BoxRight);
                            setValue(bLeft, BlockState.Open);
                            setValue(bRight, BlockState.Open);
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            default:
                return false;
        }
    }

    private boolean canMoveNorSWideBox(MapPoint p, MapDirection d) {
        MapPoint bLeft, bRight;
        if (getValue(p) == BlockState.BoxLeft) {
            bLeft = p;
            bRight = p.getNextPoint(MapDirection.E);
        }
        else {
            bRight = p;
            bLeft = p.getNextPoint(MapDirection.W);
        }
        MapPoint nLeft = bLeft.getNextPoint(d);
        MapPoint nRight = bRight.getNextPoint(d);
        BlockState nValLeft = getValue(nLeft);
        BlockState nValRight = getValue(nRight);
        if (nValLeft == BlockState.Open && nValRight == BlockState.Open)
            return true;
        else if (nValLeft == BlockState.Wall || nValRight == BlockState.Wall)
            return false;
        else if (nValLeft == BlockState.BoxLeft && nValRight == BlockState.BoxRight)
            return canMoveNorSWideBox(nLeft, d);
        else if (nValLeft == BlockState.Open && nValRight == BlockState.BoxLeft)
            return canMoveNorSWideBox(nRight, d);
        else if (nValLeft == BlockState.BoxRight && nValRight == BlockState.Open)
            return canMoveNorSWideBox(nLeft, d);
        else if (nValLeft == BlockState.BoxRight && nValRight == BlockState.BoxLeft) 
            return canMoveNorSWideBox(nLeft, d) && canMoveNorSWideBox(nRight, d);
        else 
            return false;
    }

    private boolean movedBox(MapPoint p, MapDirection d) {
        MapPoint next = p.getNextPoint(d);
        switch (getValue(next)) {
            case BlockState.Open -> {
                setValue(next, BlockState.Box);
                setValue(p, BlockState.Open);
                return true;
            }
            case BlockState.Wall -> {
                return false;
            }
            case BlockState.Box -> {
                if (movedBox(next, d)) {
                    setValue(next, BlockState.Box);
                    setValue(p, BlockState.Open);
                    return true;
                }
                else {
                    return false;
                }
            }
            default -> {
                return false;
            }
        }
    }

    public BlockState getValue(Point p) {
        if (p.y >= 0 && p.y < map.size() && p.x >= 0 && p.x < map.get(p.y).size()) {
            return map.get(p.y).get(p.x);
        }
        else {
            return null;
        }
    }

    public void setValue(Point p, BlockState value) {
        if (p.y >= 0 && p.y < map.size() && p.x >= 0 && p.x < map.get(p.y).size()) {
            map.get(p.y).set(p.x, value);
        }
    }

    public int getSizeX() {
        int maxX = 0;
        for (ArrayList<BlockState> row : map) {
            maxX = Math.max(maxX, row.size());
        }
        return maxX;
    }
    public int getSizeY() {
        return map.size();
    }

    private void parseFile(int section, String filename) throws IOException {
        FileController input = new FileController(filename);
        map = new ArrayList<>();
        robot = new MapPoint();
        moves = new ArrayList<>();
        input.openInput();
        String line = input.readLine();
        int x, y = 0;
        while (line != null) {
            line = line.trim();
            if (line.startsWith("#")) {
                x = 0;
                ArrayList<BlockState> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    if (section == 1) {
                        switch(c) {
                            case '#':
                                row.add(BlockState.Wall);
                                break;
                            case 'O':
                                row.add(BlockState.Box);
                                break;
                            case '@':
                                robot = new MapPoint(x, y);
                            case '.':
                                row.add(BlockState.Open);
                                break;
                        }
                        x++;
                    }
                    else {
                        switch(c) {
                            case '#':
                                row.add(BlockState.Wall);
                                row.add(BlockState.Wall);
                                break;
                            case 'O':
                                row.add(BlockState.BoxLeft);
                                row.add(BlockState.BoxRight);
                                break;
                            case '@':
                                robot = new MapPoint(x, y);
                            case '.':
                                row.add(BlockState.Open);
                                row.add(BlockState.Open);
                                break;
                        }
                        x += 2;
                    }
                }
                map.add(row);
                y++;
            }
            else if (line.length() > 0) {
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '^':
                            moves.add(MapDirection.N);
                            break;
                        case '>':
                            moves.add(MapDirection.E);
                            break;
                        case 'v':
                            moves.add(MapDirection.S);
                            break;
                        case '<':
                            moves.add(MapDirection.W);
                            break;
                    }
                }
            }
            line = input.readLine();
        }
        input.closeFile();
    }

    private static enum BlockState {
        Open,
        Box,
        Wall,
        BoxLeft,
        BoxRight
    }
}
