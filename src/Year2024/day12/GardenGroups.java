package Year2024.day12;

import java.io.IOException;
import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class GardenGroups extends tools.RunPuzzle {
    public GardenGroups(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new GardenGroups(12, "Garden Groups", "src\\Year2024\\day12\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day12\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test1File", 140));
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test2File", 772));
        tests.add(new TestCase<>(1, "src\\Year2024\\day12\\data\\test3File", 1930));
        return tests;
    }
}
