package Year2024.day01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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
        tests.add(new TestCase<String, Integer>(2, "src\\Year2024\\day01\\data\\test1File", 31));
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

        ArrayList<Integer> left = new ArrayList<Integer>();
        ArrayList<Integer> right = new ArrayList<Integer>();
        try {
            file.openInput();
            String line = file.readLine().trim();
            while (line != null) {
                int splitIndex = line.indexOf(' ');
                String leftString = line.substring(0, splitIndex).trim();
                String rightString = line.substring(splitIndex).trim();

                left.add(Integer.parseInt(leftString));
                right.add(Integer.parseInt(rightString));

                line = file.readLine();
            }
            file.closeFile();
        }
        catch (IOException ex) {
            try {
                file.closeFile();
            } catch (IOException ex2) {}
            ex.printStackTrace();
            return null;
        }

        if (section == 1) {
            IntegerSort sorter = new IntegerSort();
            left.sort(sorter);
            right.sort(sorter);
            
            int totalDistance = 0;
            for (int i = 0; i < Math.min(left.size(), right.size()); i++) {
                totalDistance += Math.abs(left.get(i) - right.get(i));
            }
            return totalDistance;
        }
        else {
            int similarityScore = 0;
            for (int testNum : left) {
                int testCount = 0;
                for (int matchNum: right) {
                    if (testNum == matchNum) testCount++;
                }
                similarityScore += testNum * testCount;
            }
            return similarityScore;
        }
    }
    
    private class IntegerSort implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
        
    }
}
