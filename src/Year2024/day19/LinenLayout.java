package Year2024.day19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class LinenLayout extends tools.RunPuzzle {
    String[] towels;
    ArrayList<String> patterns;
    HashMap<String, Long> patternCount;

    public LinenLayout(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new LinenLayout(19, "Linen Layout", "src\\Year2024\\day19\\data\\test1file");
        RunPuzzle p = new LinenLayout(19, "Linen Layout", "src\\Year2024\\day19\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day19\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day19\\data\\test1File", 6l));
        tests.add(new TestCase<>(2, "src\\Year2024\\day19\\data\\test1File", 16l));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Long)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String filename = (String)input;
        FileController file = new FileController(filename);
        try {
            file.openInput();
            String line = file.readLine();
            if (line != null && line.length() > 0) {
                towels = line.split(", ");
            }
            line = file.readLine(); // blank line
            line = file.readLine();
            patterns = new ArrayList<>();
            while (line != null) {
                patterns.add(line.trim());
                line = file.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                file.closeFile();
            }
            catch (IOException e) {}
        }

        if (section == 1) {
            long validCount = 0l;
            long totalCount = 0l;
            for (String p : patterns) {
                logDebug(p);
                boolean v = isValidPattern(p);
                if(v) validCount++;
                totalCount++;
                logDebug("" + v);
                logDebug(validCount + " out of " + totalCount + " patterns valid");
            }
            return validCount;
        } 
        else {
            long count = 0;
            patternCount = new HashMap<>();
            for (String p : patterns) {
                long subCount = countValidArrangements(p);
                count += subCount;
                log(subCount + " valid arrangements for " + p);
            }
            return count;
        }
    }

    private long countValidArrangements(String p) {
        if (!isValidPattern(p)) return 0;
        if (patternCount.containsKey(p)) {
            return patternCount.get(p);
        }
        long count = 0;
        for (String t : towels) {
            if (p.equals(t)) count++;
            else if (p.startsWith(t)) {
                String q = p.substring(t.length());
                for (String u : towels) {
                    if (q.equals(u)) count++;
                    else if (q.endsWith(u)) {
                        String r = q.substring(0, q.length() - u.length());
                        count += countValidArrangements(r);
                    }
                }
            }
        }
        if (p.length() > 15) logDebug("\t" + count + " valid arrangements for " + p);
        patternCount.put(p, count);
        return count;
    }

    private boolean isValidPattern(String p) {
        for (String t : towels) {
            if (p.equals(t)) return true;
            else if (p.startsWith(t)) {
                String q = p.substring(t.length());
                for (String u : towels) {
                    if (q.equals(u)) return true;
                    else if (q.endsWith(u)) {
                        String r = q.substring(0, q.length() - u.length());
                        logDebug("\tMatch on " + t + " and " + u + ", testing " + r);
                        if (isValidPattern(r)) return true;
                    }
                }
            }
        }
        return false;
    }
}
