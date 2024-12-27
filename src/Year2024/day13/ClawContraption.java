package Year2024.day13;

import java.io.IOException;
import java.math.BigInteger;
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
        //RunPuzzle p = new ClawContraption(13, "Claw Contraption", "src\\Year2024\\day13\\data\\test1file");
        RunPuzzle p = new ClawContraption(13, "Claw Contraption", "src\\Year2024\\day13\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day13\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day13\\data\\test1File", new BigInteger("480")));
        tests.add(new TestCase<>(2, "src\\Year2024\\day13\\data\\test1File", new BigInteger("875318608908")));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (BigInteger)result);
    }

    @Override
    public Object doProcessing(int section, Object input) { // 94196527538398 too high, 31065 too low
        String fileName = (String)input;
        FileController file = new FileController(fileName);

        try {
            file.openInput();
            String line = file.readLine();
            BigInteger tokens = new BigInteger("0");
            BigIntPoint a = new BigIntPoint(), b = new BigIntPoint(), prize = new BigIntPoint();
            BigInteger aTokens = new BigInteger("3");
            BigInteger bTokens = new BigInteger("1");

            while (line != null) {
                line = line.trim();
                if (line.startsWith("Button A")) {
                    int i1 = line.indexOf("X+");
                    int i2 = line.indexOf(",");
                    a.x = new BigInteger(line.substring(i1 + 2, i2));
                    i1 = line.indexOf("Y+");
                    a.y = new BigInteger(line.substring(i1 + 2));
                }
                else if (line.startsWith("Button B")) {
                    int i1 = line.indexOf("X+");
                    int i2 = line.indexOf(",");
                    b.x = new BigInteger(line.substring(i1 + 2, i2));
                    i1 = line.indexOf("Y+");
                    b.y = new BigInteger(line.substring(i1 + 2));
                } else if (line.startsWith("Prize")) {
                    int i1 = line.indexOf("X=");
                    int i2 = line.indexOf(",");
                    prize.x = new BigInteger(line.substring(i1 + 2, i2));
                    i1 = line.indexOf("Y=");
                    prize.y = new BigInteger(line.substring(i1 + 2));

                    if (section == 2) {
                        prize.x = prize.x.add(new BigInteger("10000000000000"));
                        prize.y = prize.y.add(new BigInteger("10000000000000"));
                    }

                    // calculate the number of tokens
                    BigInteger bTop = a.y.multiply(prize.x).subtract(a.x.multiply(prize.y));
                    BigInteger bBottom = a.y.multiply(b.x).subtract(a.x.multiply(b.y));
                    if (bTop.remainder(bBottom).equals(new BigInteger("0"))) {
                        BigInteger bCount = bTop.divide(bBottom);
                        BigInteger aCount = prize.x.subtract(b.x.multiply(bCount)).divide(a.x);
                        tokens = tokens.add(aTokens.multiply(aCount)).add(bTokens.multiply(bCount));
                    }
                }
                else {
                    a = new BigIntPoint();
                    b = new BigIntPoint();
                    prize = new BigIntPoint();
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

    private class BigIntPoint {
        public BigInteger x;
        public BigInteger y;
        public BigIntPoint() {
            x = new BigInteger("0");
            y = new BigInteger("0");
        }
    }
}
