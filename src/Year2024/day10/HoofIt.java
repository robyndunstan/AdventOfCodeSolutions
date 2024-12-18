package Year2024.day10;

import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class HoofIt extends tools.RunPuzzle {
    private ArrayList<ArrayList<Integer>> elevationMap;

    public HoofIt(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new HoofIt(10, "Hoof It", "src\\Year2024\\day010\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day10\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test1File", 1));
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test2File", 2));
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test3File", 4));
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test4File", 3));
        tests.add(new TestCase<>(1, "src\\Year2024\\day10\\data\\test5File", 36));
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
        elevationMap = new ArrayList<>();

        try {
            file.openInput();
            String line = file.readLine();
            while (line != null) {
                line = line.trim();
                ArrayList<Integer> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            row.add(Integer.valueOf("" + c));
                            break;
                        case '.':
                            row.add(-1);
                            break;
                        default:
                            break;
                    }
                }
                elevationMap.add(row);
                line = file.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            try {
                file.closeFile();
            } catch (IOException ex) {}
        }

        if (section == 1) {
            return null;
        }
        else {
            return null;
        }
    }

}
