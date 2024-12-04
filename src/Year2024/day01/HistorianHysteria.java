package Year2024.day01;

import java.io.IOException;
import java.util.ArrayList;

import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class HistorianHysteria extends tools.RunPuzzle {

    public HistorianHysteria(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        RunPuzzle p = new HistorianHysteria(1, "Historian Hysteria", "src\\Year2024\\day01\\data\\puzzleFile");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<TestCase>();
        tests.add(new TestCase<String, Integer>(1, "src\\Year2024\\day01\\data\\test1File", 11));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        System.out.println(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String fileName = (String)input;
        FileController file = new FileController(fileName);

        if (section == 1) {
            try {
                file.openInput();
                String line = file.readLine().trim();
                while (line != null) {
                    // do things

                    line = file.readLine();
                }
                file.closeFile();
                // do more things
                return null;
            }
            catch (IOException e) {
                e.printStackTrace();
			    return null;
            }
        }
        else {
            return null;
        }
    }
    
}
