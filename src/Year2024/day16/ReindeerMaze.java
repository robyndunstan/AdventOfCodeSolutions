package Year2024.day16;

import java.io.IOException;
import java.util.ArrayList;
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
        if (section == 1) {
            ArrayList<Reindeer> paths = new ArrayList<>();
            Reindeer rStart = new Reindeer();
            rStart.direction = maze.reindeerStartDirection;
            rStart.position = maze.reindeerStartPosition;
            rStart.score = 0;
            paths.add(rStart);
            int minScore = Integer.MAX_VALUE;
            while (!paths.isEmpty()) {
                ArrayList<Reindeer> nextPaths = new ArrayList<>();
                for (Reindeer r : paths) {
                    if (r.position.x == maze.mazeEnd.x && r.position.y == maze.mazeEnd.y) {
                        minScore = Math.min(minScore, r.score);
                    }
                    else if (!maze.getValue(r.position) && r.score < minScore) {
                        MapPoint forward = r.position.getNextPoint(r.direction);
                        if (!maze.getValue(forward)) {
                            Reindeer rNew = new Reindeer();
                            rNew.direction = r.direction;
                            rNew.position = forward;
                            rNew.score = r.score + 1;
                        }
                        Reindeer rLeft = new Reindeer();
                        rLeft.position = r.position;
                        rLeft.score = r.score + 1000;
                        Reindeer rRight = new Reindeer();
                        rRight.position = r.position;
                        rRight.score = r.score + 1000;
                        switch(r.direction) {
                            case MapDirection.E:
                                rLeft.direction = MapDirection.N;
                                rRight.direction = MapDirection.S;
                                break;
                            case MapDirection.N:
                                rLeft.direction = MapDirection.W;
                                rRight.direction = MapDirection.E;
                                break;
                            case MapDirection.W:
                                rLeft.direction = MapDirection.S;
                                rRight.direction = MapDirection.N;
                                break;
                            case MapDirection.S:
                                rLeft.direction = MapDirection.E;
                                rRight.direction = MapDirection.W;
                                break;
                        }
                        nextPaths.add(rLeft);
                        nextPaths.add(rRight);
                    }
                    // Avoid repeats/circles
                    maze.setValue(r.position, true);
                }
                paths = nextPaths;
            }
            return minScore;
        }
        else {
            return null;
        }
    }

    private class Reindeer {
        public MapPoint position;
        public MapDirection direction;
        public int score;
        public Reindeer() {
            position = new MapPoint();
            score = 0;
            direction = MapDirection.E;
        }
    }

    private class MazeWalls extends tools.PuzzleMap<Boolean> {
        MapPoint reindeerStartPosition, mazeEnd;
        MapDirection reindeerStartDirection;

        public MazeWalls() {
            reindeerStartDirection = MapDirection.E;
            reindeerStartPosition = new MapPoint();
                mazeEnd = new MapPoint();
        }

        @Override
        public Boolean parse(char c, int x, int y) {
            switch(c) {
                case '#': return true;
                case '.': return false;
                case 'E': 
                    mazeEnd = new MapPoint(x, y);
                    return false;
                case 'S':
                    reindeerStartDirection = MapDirection.E;
                    reindeerStartPosition = new MapPoint(x, y);
                    return false;
                default:
                    return false;
            }
        }
    }
}
