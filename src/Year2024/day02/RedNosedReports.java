package Year2024.day02;

import java.io.IOException;
import java.util.ArrayList;

import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class RedNosedReports extends tools.RunPuzzle {

    public RedNosedReports(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        RunPuzzle p = new RedNosedReports(2, "Red-Nosed Reports", "src\\Year2024\\day02\\data\\puzzleFile");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<TestCase>();
        tests.add(new TestCase<String, Integer>(1, "src\\Year2024\\day02\\data\\test1File", 2));
        tests.add(new TestCase<String, Integer>(2, "src\\Year2024\\day02\\data\\test1File", 4));
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

        ArrayList<ArrayList<Integer>> reports = new ArrayList<ArrayList<Integer>>();

        try {
            file.openInput();
            String line = file.readLine();
            while (line != null) {
                line = line.trim();
                String[] nums = line.split(" ");
                ArrayList<Integer> currentReport = new ArrayList<Integer>();
                for (String n : nums) {
                    if (n.trim().length() > 0) {
                        currentReport.add(Integer.parseInt(n));
                    }
                }
                reports.add(currentReport);

                line = file.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                file.closeFile();
            } catch (IOException ex) {}
        }

        int safeReports = 0;
        for (ArrayList<Integer> r : reports) {
            if (section == 1) {
                if (isSafeReport(r)) safeReports++;
            }
            else {
                if (isSafeReport(r)) safeReports++;
                else {
                    for (int j = 0; j < r.size(); j++) {
                        ArrayList<Integer> r2 = (ArrayList<Integer>)r.clone();
                        r2.remove(j);
                        if (isSafeReport(r2)) {
                            safeReports++;
                            break;
                        }
                    }
                }
            }
        }
        return safeReports;
    }

    private boolean isSafeReport(ArrayList<Integer> report) {
        boolean isAscending;
        if (report.size() < 2) {
            return false;
        }
        else if (report.get(1) - report.get(0) > 0) {
            isAscending = true;
        }
        else if (report.get(1) - report.get(0) < 0) {
            isAscending = false;
        }
        else { // first 2 numbers are equal
            return false;
        }

        for (int i = 1; i < report.size(); i++) {
            if (isAscending & report.get(i) <= report.get(i - 1)) {
                return false;
            }
            else if (!isAscending && report.get(i) >= report.get(i - 1)) {
                return false;
            }
            else if (Math.abs(report.get(i) - report.get(i - 1)) > 3) {
                return false;
            }
        }
        return true;
    }
}
