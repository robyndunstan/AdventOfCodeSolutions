package Year2024.day14;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.MapDirection;
import tools.MapPoint;
import tools.RunPuzzle;
import tools.TestCase;

public class RestroomRedoubt extends tools.RunPuzzle {
    private int dx, dy;
    private ArrayList<Robot> robots;

    public RestroomRedoubt(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = true;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new RestroomRedoubt(14, "Restroom Redoubt", "src\\Year2024\\day14\\data\\test1file");
        RunPuzzle p = new RestroomRedoubt(14, "Restroom Redoubt", "src\\Year2024\\day14\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day14\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day14\\data\\test1File", 12));
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
        dx = 0;
        dy = 0;
        robots = new ArrayList<>();
        try {
            file.openInput();
            String line = file.readLine(); // first line is # x #
            if (line != null) {
                int splitIndex = line.indexOf("x");
                if (splitIndex != -1) {
                    dx = Integer.parseInt(line.substring(0, splitIndex).trim());
                    dy = Integer.parseInt(line.substring(splitIndex + 1).trim());
                    logDebug("Area size " + dx + " by " + dy);
                }
            }
            if (dx == 0 || dy == 0) {
                log("Invalid area: " + line);
                return null;
            }
            line = file.readLine();
            while (line != null) {
                robots.add(new Robot(line));
                line = file.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                file.closeFile();
            }
            catch (IOException ex) {}
        }

        logDebug("Parsed " + robots.size() + " robots");
        runRobots(100);

        int midx = dx / 2;
        int midy = dy / 2;
        if (section == 1) {
            logDebug("Center point at " + midx + ", " + midy);
            int neCount = 0, seCount = 0, swCount = 0, nwCount = 0;
            for(Robot r : robots) {
                if (r.position.x < midx && r.position.y < midy) nwCount++;
                else if (r.position.x < midx && r.position.y > midy) swCount++;
                else if (r.position.x > midx && r.position.y < midy) neCount++;
                else if (r.position.x > midx && r.position.y > midy) seCount++;
            }
            logDebug("NE: " + neCount + ", NW: " + nwCount + ", SW: " + swCount + ", SE: " + seCount);
            if (debug) {
                for (Robot r : robots) {
                    logDebug("\tRobot at " + r.position.x + ", " + r.position.y);
                }
            }

            return neCount * nwCount * seCount * swCount;
        }
        else { // 7792 too low
            int startTime = 0; // change this with each run as tree form gets refined
            runRobots(startTime); // get to initial position
            while (!checkForTree() && robots.get(0).time() < Integer.MAX_VALUE) {
                runRobots(1);
                if (robots.get(0).time() % 100_000 == 0) {
                    logRobots(true);
                    logDebug(robots.get(0).time() + " seconds");
                } 
            }
            logRobots(false);
            return robots.get(0).time();
        }
    }

    private void logRobots(boolean debugOnly) {
        boolean[][] image = new boolean[dx][dy];
        for (Robot r : robots) {
            image[r.position.x][r.position.y] = true;
        }
        for (int y = 0; y < dy; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < dx; x++) {
                if (image[x][y]) line.append("X");
                else line.append(".");
            }
            if (debugOnly) {
                logDebug(line.toString());
            }
            else {
                log(line.toString());
            }
        }
    }

    private boolean checkForTree() {
        boolean[][] image = new boolean[dx][dy];
        for (Robot r : robots) {
            image[r.position.x][r.position.y] = true;
        }
        boolean foundCluster = false;
        for (Robot r : robots) {
            MapPoint center = new MapPoint(r.position);
            int countNeighbors = 0;
            MapPoint test = center.getNextPoint(MapDirection.N);
            if (test.x >= 0 && test.x < dx && test.y >= 0 && test.y < dy && image[test.x][test.y]) countNeighbors++;
            test = center.getNextPoint(MapDirection.NE);
            if (test.x >= 0 && test.x < dx && test.y >= 0 && test.y < dy && image[test.x][test.y]) countNeighbors++;
            test = center.getNextPoint(MapDirection.E);
            if (test.x >= 0 && test.x < dx && test.y >= 0 && test.y < dy && image[test.x][test.y]) countNeighbors++;
            test = center.getNextPoint(MapDirection.SE);
            if (test.x >= 0 && test.x < dx && test.y >= 0 && test.y < dy && image[test.x][test.y]) countNeighbors++;
            test = center.getNextPoint(MapDirection.S);
            if (test.x >= 0 && test.x < dx && test.y >= 0 && test.y < dy && image[test.x][test.y]) countNeighbors++;
            test = center.getNextPoint(MapDirection.SW);
            if (test.x >= 0 && test.x < dx && test.y >= 0 && test.y < dy && image[test.x][test.y]) countNeighbors++;
            test = center.getNextPoint(MapDirection.W);
            if (test.x >= 0 && test.x < dx && test.y >= 0 && test.y < dy && image[test.x][test.y]) countNeighbors++;
            test = center.getNextPoint(MapDirection.NW);
            if (test.x >= 0 && test.x < dx && test.y >= 0 && test.y < dy && image[test.x][test.y]) countNeighbors++;
            if (countNeighbors == 8) {
                foundCluster = true;
                break;
            }
        }
        return foundCluster;
    }

    private void runRobots(int time) {
        for (Robot r : robots) {
            r.doSteps(time);
        }
    }

    private class Robot {
        public Point position, velocity;
        private int time; 
        public Robot() {
            position = new Point();
            velocity = new Point();
            time = 0;
        }
        public Robot(String s) {
            this();
            parse(s);
        }
        public int time() {
            return this.time;
        }
        private void parse(String s) { // p=#,# v=#,#
            int i1 = s.indexOf("p=");
            int i2 = s.indexOf(",");
            position.x = Integer.parseInt(s.substring(i1 + 2, i2).trim());
            s = s.substring(i2 + 1).trim();
            i1 = s.indexOf(" ");
            position.y = Integer.parseInt(s.substring(0, i1).trim());
            s = s.substring(i1).trim();
            i1 = s.indexOf("v=");
            i2 = s.indexOf(",");
            velocity.x = Integer.parseInt(s.substring(i1 + 2, i2).trim());
            s = s.substring(i2 + 1).trim();
            velocity.y = Integer.parseInt(s);
        }
        public void doSteps(int steps) {
            position.x = (position.x + velocity.x * steps) % dx;
            if (position.x < 0) position.x += dx;
            position.y = (position.y + velocity.y * steps) % dy;
            if (position.y < 0) position.y += dy;
            time += steps; 
        }
    }
}
