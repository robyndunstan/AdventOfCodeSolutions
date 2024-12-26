package Year2024.day11;

import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class PlutonianPebbles extends tools.RunPuzzle {
    private long stoneCount;
    
    public PlutonianPebbles(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = true;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new PlutonianPebbles(10, "Plutonian Pebbles", new StoneInput(test2stones, test2blinks5));
        RunPuzzle p = new PlutonianPebbles(10, "Plutonian Pebbles", new StoneInput(puzzleStones, puzzleBlinks));
        //p.setLogFile("src\\Year2024\\day11\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase(1, new StoneInput(test1stones, test1blinks), 7l));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks1), 3l));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks2), 4l));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks3), 5l));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks4), 9l));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks5), 13l));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks6), 22l));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks7), 55312l));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Long)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        StoneInput stoneInput = (StoneInput)input;
        int targetBlinks = stoneInput.blinks;
        if (section == 2) targetBlinks *= 3; 
        String[] stoneStrings = stoneInput.initialStones.trim().split(" ");
        FileController outputFile;
        try {
            outputFile = getOutputFile(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        for (String s : stoneStrings) {
            if (s.trim().length() > 0) {
                outputFile.writeLine(s);
            }
        }
        try {
            outputFile.closeFile();
        } catch (IOException ex) {}

        long stoneCount = 0l;
        for (int currentBlink = 1; currentBlink <= targetBlinks; currentBlink++) {
            FileController inputFile = new FileController("src\\Year2024\\day11\\data\\blink" + String.format("%02d", currentBlink - 1) + ".txt");
            stoneCount = 0l;
            try {
                inputFile.openInput();
                outputFile = getOutputFile(currentBlink);
                outputFile.openOutput();
                String line = inputFile.readLine();
                while (line != null) {
                    line = line.trim();
                    if (line.equals("0")) {
                        outputFile.writeLine("1");
                        stoneCount++;
                    }
                    else if (line.length() % 2 == 0) {
                        outputFile.writeLine(line.substring(0, line.length() / 2));
                        Long stone = Long.valueOf(line.substring(line.length() / 2));
                        outputFile.writeLine(stone.toString());
                        stoneCount += 2;
                    }
                    else {
                        Long stone = Long.parseLong(line) * 2024;
                        outputFile.writeLine(stone.toString());
                        stoneCount++;
                    }
                    line = inputFile.readLine();
                }
                if (currentBlink % 5 == 0) logDebug(stoneCount + " stones after " + currentBlink + " blinks");
                inputFile.closeFile();
                outputFile.closeFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return stoneCount;
    }

    private FileController getOutputFile(int currentBlinks) throws IOException {
        FileController f = new FileController("src\\Year2024\\day11\\data\\blink" + String.format("%02d", currentBlinks) + ".txt");
        f.deleteFile();
        f.openOutput();
        return f;
    }

    private static String test1stones = "0 1 10 99 999"; 
    private static int test1blinks = 1; // 7

    private static String test2stones = "125 17";
    private static int test2blinks1 = 1; // 3
    private static int test2blinks2 = 2; // 4
    private static int test2blinks3 = 3; // 5
    private static int test2blinks4 = 4; // 9
    private static int test2blinks5 = 5; // 13
    private static int test2blinks6 = 6; // 22
    private static int test2blinks7 = 25; // 55312

    private static String puzzleStones = "41078 18 7 0 4785508 535256 8154 447";
    private static int puzzleBlinks = 25;

    private static class StoneInput {
        public String initialStones;
        public int blinks;
        public StoneInput(String initialStones, int blinks) {
            this.initialStones = initialStones;
            this.blinks = blinks;
        }
    }
}
