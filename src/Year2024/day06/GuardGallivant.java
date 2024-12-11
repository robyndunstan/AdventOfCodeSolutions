package Year2024.day06;

import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class GuardGallivant extends tools.RunPuzzle {
    public GuardGallivant(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new GuardGallivant(6, "Guard Gallivant", "src\\Year2024\\day06\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day06\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day06\\data\\test1File", 0));
        tests.add(new TestCase<>(2, "src\\Year2024\\day06\\data\\test1File", 0));
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

        return null;
    }
}
