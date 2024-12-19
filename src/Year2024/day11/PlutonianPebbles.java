package Year2024.day11;

import java.util.ArrayList;
import tools.RunPuzzle;
import tools.TestCase;

public class PlutonianPebbles extends tools.RunPuzzle {
    public PlutonianPebbles(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        RunPuzzle p = new PlutonianPebbles(10, "Plutonian Pebbles", new StoneInput(puzzleStones, puzzleBlinks));
        //p.setLogFile("src\\Year2024\\day11\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase(1, new StoneInput(test1stones, test1blinks), 7));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks1), 7));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks2), 7));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks3), 7));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks4), 7));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks5), 7));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks6), 7));
        tests.add(new TestCase(1, new StoneInput(test2stones, test2blinks7), 7));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        StoneInput stoneInput = (StoneInput)input;

        return null;
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
