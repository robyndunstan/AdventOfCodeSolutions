package Year2024.day13;

import java.io.IOException;
import java.util.ArrayList;

import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class ClawContraption extends tools.RunPuzzle {
    public ClawContraption(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        RunPuzzle p = new ClawContraption(13, "Claw Contraption", "src\\Year2024\\day13\\data\\test1file");
        //RunPuzzle p = new ClawContraption(13, "Claw Contraption", "src\\Year2024\\day13\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day13\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day13\\data\\test1File", 480));
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
            file.openInput();
            String line = file.readLine();
            int tokens = 0;
            Point a, b, prize;
            int aTokens = 3;
            int bTokens = 1;

            while (line != null) {
                line = line.trim();
                if (line.startsWith("Button A")) {
                    int i1 = line.indexOf("X+");
                    int i2 = line.indexOf(",");
                    a.x = Integer.valueOf(line.substring(i1 + 2, i2));
                    i1 = line.indexOf("Y+");
                    a.y = Integer.valueOf(line.substring(i1 + 2));
                }
                else if (line.startsWith("Button B")) {
                    int i1 = line.indexOf("X+");
                    int i2 = line.indexOf(",");
                    b.x = Integer.valueOf(line.substring(i1 + 2, i2));
                    i1 = line.indexOf("Y+");
                    b.y = Integer.valueOf(line.substring(i1 + 2));
                } else if (line.startsWith("Prize")) {
                    int i1 = line.indexOf("X=");
                    int i2 = line.indexOf(",");
                    prize.x = Integer.valueOf(line.substring(i1 + 2, i2));
                    i1 = line.indexOf("Y=");
                    prize.y = Integer.valueOf(line.substring(i1 + 2));

                    // calculate the number of tokens
                    int bTop = a.y * prize.x - a.x * prize.y;
                    int bBottom = a.y * b.x - a.x * b.y;
                    if (bTop % bBottom == 0) {
                        int bCount = bTop / bBottom;
                        int aCount = (prize.x - b.x * bCount) / a.x;
                        tokens += aTokens * aCount + bTokens * bCount;
                    }
                }
                else {
                    a = new Point();
                    b = new Point();
                    prize = new Point();
                }
                line = file.readLine();
            }
            return tokens;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
