package Year2024.day05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class PrintQueue extends tools.RunPuzzle {
    private HashMap<Integer, ArrayList<Integer>> pageOrder;
    private ArrayList<ArrayList<Integer>> printUpdates;
    
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
        tests.add(new TestCase<>(1, "src\\Year2024\\day05\\data\\test1File", 143));
        tests.add(new TestCase<>(2, "src\\Year2024\\day05\\data\\test1File", 0));
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
        pageOrder = new HashMap<>();
        printUpdates = new ArrayList<>();

        try {
            file.openInput();
            String line = file.readLine();
            while (line != null) {
                line = line.trim();
                if (line.length() == 0) {
                    line = file.readLine();
                    continue;
                }

                if (line.indexOf('|') > -1) { // X|Y, page X before Y
                    String[] pages = line.split("\\|");
                    int x = Integer.parseInt(pages[0].trim());
                    int y = Integer.parseInt(pages[1].trim());
                    if (pageOrder.containsKey(x)) {
                        ArrayList<Integer> ys = pageOrder.get(x);
                        ys.add(y);
                        pageOrder.put(x, ys);
                    }
                    else {
                        ArrayList<Integer> ys = new ArrayList<>();
                        ys.add(y);
                        pageOrder.put(x, ys);
                    }
                }
                else { // list of pages to print
                    ArrayList<Integer> printRun = new ArrayList<>();
                    String[] pages = line.split(",");
                    for (String p : pages) {
                        printRun.add(Integer.valueOf(p));
                    }
                    printUpdates.add(printRun);
                }
                line = file.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        if (section == 1) {
            int middleSum = 0;

            for (ArrayList<Integer> printList : printUpdates) {
                boolean valid = true;
                for (int i = 0; i < printList.size(); i++) {
                    int currentPage = printList.get(i);
                    if (pageOrder.containsKey(currentPage)) {
                        ArrayList<Integer> mustBeAfter = pageOrder.get(currentPage);
                        for (int page : mustBeAfter) {
                            int testIndex = printList.indexOf(page);
                            if (testIndex > -1 && testIndex < i) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (!valid) break;
                }
                if (valid) {
                    int middleIndex = printList.size() / 2;
                    middleSum += printList.get(middleIndex);
                }
            }

            return middleSum;
        }
        else {
            return null;
        }
    }
}
