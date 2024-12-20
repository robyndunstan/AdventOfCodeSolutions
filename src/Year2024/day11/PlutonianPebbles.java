package Year2024.day11;

import java.util.ArrayList;
import tools.RunPuzzle;
import tools.TestCase;

public class PlutonianPebbles extends tools.RunPuzzle {
    public PlutonianPebbles(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = true;
    }

    public static void main(String[] args) {
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
        ArrayList<Stone> stones = new ArrayList<>();
        String[] stoneStrings = stoneInput.initialStones.trim().split(" ");
        for (String s : stoneStrings) {
            if (s.trim().length() > 0) {
                stones.add(new Stone(Long.parseLong(s), targetBlinks));
            }
        }

        long stoneCount = 0l;
        while (!stones.isEmpty()) {
            Stone stone = stones.remove(0);
            if (stone.remainingBlinks == 0) {
                stoneCount++;
                if (stoneCount % 1000000 == 0) logDebug("Fully processed " + stoneCount + " stones with " + stones.size() + " remaining");
            }
            else if (stone.value == 0) {
                stone.value = 1l;
                stone.remainingBlinks--;
                stones.add(0, stone);
            }
            else if (stone.value.toString().length() % 2 == 0) {
                String s = stone.value.toString();
                Stone s1 = new Stone(Long.parseLong(s.substring(0, s.length() / 2)), stone.remainingBlinks - 1);
                Stone s2 = new Stone(Long.parseLong(s.substring(s.length() / 2)), stone.remainingBlinks - 1);
                stones.add(0, s2);
                stones.add(0, s1);
            }
            else {
                stone.value *= 2024;
                stone.remainingBlinks--;
                stones.add(0, stone);
            }
        }
        return stoneCount;
    }

    private static class Stone {
        public Long value;
        public int remainingBlinks;
        public Stone(long value, int blinks) {
            this.value = value;
            this.remainingBlinks = blinks;
        }
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
