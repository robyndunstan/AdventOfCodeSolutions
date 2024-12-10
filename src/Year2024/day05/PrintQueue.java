package Year2024.day05;

import java.io.IOException;
import java.util.ArrayList;
import tools.RunPuzzle;
import tools.TestCase;

public class PrintQueue extends tools.RunPuzzle {
    public PrintQueue(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new PrintQueue(5, "Print Queue", "src\\Year2024\\day05\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day05\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day05\\data\\test1File", 0));
        tests.add(new TestCase<>(2, "src\\Year2024\\day05\\data\\test1File", 0));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
