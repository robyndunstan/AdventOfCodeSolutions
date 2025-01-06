package Year2024.day16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import tools.MapDirection;
import tools.MapPoint;
import tools.RunPuzzle;
import tools.TestCase;

public class ReindeerMaze extends tools.RunPuzzle {
    private MazeWalls maze;

    public ReindeerMaze(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new ReindeerMaze(16, "Reindeer Maze", "src\\Year2024\\day16\\data\\test1file");
        RunPuzzle p = new ReindeerMaze(16, "Reindeer Maze", "src\\Year2024\\day16\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day16\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day16\\data\\test1File", 7036));
        tests.add(new TestCase<>(1, "src\\Year2024\\day16\\data\\test2File", 11048));
        tests.add(new TestCase<>(2, "src\\Year2024\\day16\\data\\test1File", 45));
        tests.add(new TestCase<>(2, "src\\Year2024\\day16\\data\\test2File", 64));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        maze = new MazeWalls();
        try {
            maze.parseMap((String)input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        ArrayList<Reindeer> paths = new ArrayList<>();
        Reindeer rStart = new Reindeer();
        rStart.direction = maze.reindeerStartDirection;
        rStart.position = maze.reindeerStartPosition;
        rStart.score = 0;
        rStart.path.add(maze.reindeerStartPosition);
        paths.add(rStart);
        int minScore = Integer.MAX_VALUE;
        ArrayList<Reindeer> winners = new ArrayList<>();
        while (!paths.isEmpty()) {
            ArrayList<Reindeer> nextPaths = new ArrayList<>();
            for (Reindeer r : paths) {
                if (r.position.x == maze.mazeEnd.x && r.position.y == maze.mazeEnd.y) {
                    if (minScore > r.score) {
                        minScore = r.score;
                        winners = new ArrayList<>();
                        winners.add(r);
                    }
                    else if (minScore == r.score) {
                        winners.add(r);
                    }
                }
                else if (!maze.getValue(r.position).hasWall && r.score < minScore) {
                    MapPoint forward = r.position.getNextPoint(r.direction);
                    MapBlock fBlock = maze.getValue(forward);
                    Reindeer rForward = new Reindeer(r);
                    rForward.movePosition(forward);
                    if (!fBlock.hasWall && fBlock.isBetterPath(rForward)) {
                        nextPaths.add(rForward);
                    }
                    Reindeer rLeft = new Reindeer(r);
                    Reindeer rRight = new Reindeer(r);
                    switch(r.direction) {
                        case MapDirection.E:
                            rLeft.moveDirection(MapDirection.N);
                            rRight.moveDirection(MapDirection.S);
                            break;
                        case MapDirection.N:
                        rLeft.moveDirection(MapDirection.W);
                        rRight.moveDirection(MapDirection.E);
                            break;
                        case MapDirection.W:
                        rLeft.moveDirection(MapDirection.S);
                        rRight.moveDirection(MapDirection.N);
                            break;
                        case MapDirection.S:
                        rLeft.moveDirection(MapDirection.E);
                        rRight.moveDirection(MapDirection.W);
                            break;
                    }
                    MapBlock hereBlock = maze.getValue(r.position);
                    if (hereBlock.isBetterPath(rLeft)) {
                        nextPaths.add(rLeft);
                    }
                    if (hereBlock.isBetterPath(rRight)) {
                        nextPaths.add(rRight);
                    }
                }
            }
            paths = nextPaths;
        }
        if (section == 1) {
            return minScore;
        }
        else {
            boolean[][] winPaths = new boolean[maze.getSizeX()][maze.getSizeY()];
            for (Reindeer r : winners) {
                for (MapPoint p : r.path) {
                    winPaths[p.x][p.y] = true;
                }
            }
            int count = 0;
            for (int i = 0; i < maze.getSizeX(); i++) {
                for (int j = 0; j < maze.getSizeY(); j++) {
                    if (winPaths[i][j]) count++;
                }
            }
            return count;
        }
    }

    private class Reindeer {
        public MapPoint position;
        public MapDirection direction;
        public int score;
        public ArrayList<MapPoint> path;
        public Reindeer() {
            position = new MapPoint();
            score = 0;
            direction = MapDirection.E;
            path = new ArrayList<>();
        }
        public Reindeer(Reindeer r) {
            this();
            copyReindeer(r);
        }
        public void copyReindeer(Reindeer r) {
            this.position = r.position;
            this.direction = r.direction;
            this.score = r.score;
            for (MapPoint m : r.path) {
                this.path.add(m);
            }
        }
        public void movePosition(MapPoint m) {
            this.position = m;
            this.path.add(m);
            this.score += 1;
        }
        public void moveDirection(MapDirection d) {
            this.direction = d;
            this.score += 1000;
        }
    }

    private class MapBlock {
        public boolean hasWall;
        private HashMap<MapDirection, Integer> history;

        public MapBlock() {
            hasWall = false;
            history = new HashMap<>();
        }
        public MapBlock(boolean hasWall) {
            this();
            this.hasWall = hasWall;
        }

        public boolean isBetterPath(Reindeer r) {
            if (!history.containsKey(r.direction)) {
                history.put(r.direction, r.score);
                return true;
            }
            else {
                int historyScore = history.get(r.direction);
                if (historyScore >= r.score) {
                    history.put(r.direction, r.score);
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }

    private class MazeWalls extends tools.PuzzleMap<MapBlock> {
        MapPoint reindeerStartPosition, mazeEnd;
        MapDirection reindeerStartDirection;

        public MazeWalls() {
            reindeerStartDirection = MapDirection.E;
            reindeerStartPosition = new MapPoint();
                mazeEnd = new MapPoint();
        }

        @Override
        public MapBlock parse(char c, int x, int y) {
            switch(c) {
                case '#': return new MapBlock(true);
                case '.': return new MapBlock(false);
                case 'E': 
                    mazeEnd = new MapPoint(x, y);
                    return new MapBlock(false);
                case 'S':
                    reindeerStartDirection = MapDirection.E;
                    reindeerStartPosition = new MapPoint(x, y);
                    return new MapBlock(false);
                default:
                    return new MapBlock(false);
            }
        }
    }
}
