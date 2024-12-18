package Year2024.day07;

import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class BridgeRepair extends tools.RunPuzzle {
    private ArrayList<CalibrationEquation> calibrations;

    public BridgeRepair(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new BridgeRepair(7, "Bridge Repair", "src\\Year2024\\day07\\data\\puzzleFile");
        //p.setLogFile("src\\Year2024\\day07\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day07\\data\\test1File", 3749l));
        tests.add(new TestCase<>(2, "src\\Year2024\\day07\\data\\test1File", 11387l));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Long)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String fileName = (String)input;
        FileController file = new FileController(fileName);
        calibrations = new ArrayList<>();

        try {
            file.openInput();
            String line = file.readLine();
            while (line != null) {
                CalibrationEquation c = new CalibrationEquation();
                c.parseLine(line);
                calibrations.add(c);

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

        long totalValid = 0;
        for (CalibrationEquation c : calibrations) {
            logDebug("Testing " + c.toString());
            if (section == 1) {
                if (c.isValidAddMultiply()) {
                    totalValid += c.testValue;
                    logDebug("Total: " + totalValid);
                }
            }
            else {
                if (c.isValidAddMultiplyConcat()) {
                    totalValid += c.testValue;
                    logDebug("Total: " + totalValid);
                }
            }
        }
        return totalValid;
    }

    private static class CalibrationEquation {
        long testValue;
        ArrayList<Long> operands;

        public CalibrationEquation() {
            operands = new ArrayList<>();
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(testValue);
            s.append(": ");
            for (long l : operands) {
                s.append(l);
                s.append(" ");
            }
            return s.toString();
        }

        public void parseLine(String line) {
            line = line.trim();
            int index = line.indexOf(":");
            String testValue = line.substring(0, index).trim();
            this.testValue = Long.parseLong(testValue);
            String equation = line.substring(index + 1).trim();
            String[] operands = equation.split(" ");
            this.operands = new ArrayList<>();
            for (String o : operands) {
                if (o.trim().length() > 0)
                    this.operands.add(Long.parseLong(o.trim()));
            }
        }

        public boolean isValidAddMultiplyConcat() {
            switch (operands.size()) {
                case 0:
                    return false;
                case 1:
                    return testValue == operands.get(0);
                default:
                    return doAddForAddMultiplyConcat(0, (ArrayList<Long>)operands.clone()) 
                        || doMultiplyForAddMultiplyConcat(0, (ArrayList<Long>)operands.clone()) 
                        || doConcatForAddMultiplyConcat(0, (ArrayList<Long>)operands.clone());
            }
        }

        public boolean doAddForAddMultiplyConcat(long subtotal, ArrayList<Long> remainingOperands) {
            if (remainingOperands.isEmpty()) return subtotal == testValue;
            else if (remainingOperands.size()== 1) return subtotal + remainingOperands.get(0) == testValue;
            else {
                ArrayList<Long> r = (ArrayList<Long>)remainingOperands.clone();
                subtotal += r.get(0);
                r.remove(0);
                return doAddForAddMultiplyConcat(subtotal, r) 
                    || doMultiplyForAddMultiplyConcat(subtotal, r) 
                    || doConcatForAddMultiplyConcat(subtotal, r);
            }
        }

        public boolean doMultiplyForAddMultiplyConcat(long subtotal, ArrayList<Long> remainingOperands) {
            if (remainingOperands.isEmpty()) return subtotal == testValue;
            else if (remainingOperands.size()== 1) return subtotal * remainingOperands.get(0) == testValue;
            else {
                ArrayList<Long> r = (ArrayList<Long>)remainingOperands.clone();
                subtotal *= r.get(0);
                r.remove(0);
                return doAddForAddMultiplyConcat(subtotal, r) 
                    || doMultiplyForAddMultiplyConcat(subtotal, r) 
                    || doConcatForAddMultiplyConcat(subtotal, r);
            }
        }

        public boolean doConcatForAddMultiplyConcat(long subtotal, ArrayList<Long> remainingOperands) {
            if (remainingOperands.isEmpty()) return subtotal == testValue;
            else if (remainingOperands.size() == 1) return concatNumbers(subtotal, remainingOperands.get(0)) == testValue;
            else {
                ArrayList<Long> r = (ArrayList<Long>)remainingOperands.clone();
                subtotal = concatNumbers(subtotal, remainingOperands.get(0));
                r.remove(0);
                return doAddForAddMultiplyConcat(subtotal, r) 
                    || doMultiplyForAddMultiplyConcat(subtotal, r) 
                    || doConcatForAddMultiplyConcat(subtotal, r);
            }
        }

        public boolean isValidAddMultiply() {
            switch (operands.size()) {
                case 0:
                    return false;
                case 1:
                    return testValue == operands.get(0);
                default:
                    return doAddForAddMultiply(0, (ArrayList<Long>)operands.clone()) 
                        || doMultiplyForAddMultiply(0, (ArrayList<Long>)operands.clone());
            }
        }

        public boolean doAddForAddMultiply(long subtotal, ArrayList<Long> remainingOperands) {
            if (remainingOperands.isEmpty()) return subtotal == testValue;
            else if (remainingOperands.size()== 1) return subtotal + remainingOperands.get(0) == testValue;
            else {
                ArrayList<Long> r = (ArrayList<Long>)remainingOperands.clone();
                subtotal += r.get(0);
                r.remove(0);
                return doAddForAddMultiply(subtotal, r) || doMultiplyForAddMultiply(subtotal, r);
            }
        }

        public boolean doMultiplyForAddMultiply(long subtotal, ArrayList<Long> remainingOperands) {
            if (remainingOperands.isEmpty()) return subtotal == testValue;
            else if (remainingOperands.size()== 1) return subtotal * remainingOperands.get(0) == testValue;
            else {
                ArrayList<Long> r = (ArrayList<Long>)remainingOperands.clone();
                subtotal *= r.get(0);
                r.remove(0);
                return doAddForAddMultiply(subtotal, r) || doMultiplyForAddMultiply(subtotal, r);
            }
        }

        private long concatNumbers(long l1, long l2) {
            StringBuilder s = new StringBuilder();
            s.append(l1);
            s.append(l2);
            return Long.parseLong(s.toString());
        }
    }
}
