package Year2024.day04;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import tools.FileController;
import tools.RunPuzzle;
import tools.TestCase;

public class CeresSearch extends tools.RunPuzzle {
    private ArrayList<ArrayList<Character>> wordSearch;

    public CeresSearch(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = true;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new CeresSearch(4, "Ceres Search", "src\\Year2024\\day04\\data\\puzzleFile");
        p.setLogFile("src\\Year2024\\day04\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, "src\\Year2024\\day04\\data\\test1File", 18));
        tests.add(new TestCase<>(2, "src\\Year2024\\day04\\data\\test1File", 9));
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

        wordSearch = new ArrayList<>();

        try {
            file.openInput();
            String line = file.readLine();
            while (line != null) {
                line = line.trim();
                ArrayList<Character> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    row.add(c);
                }
                wordSearch.add(row);

                line = file.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        if (section == 1) {
            String targetWord = "XMAS";
            int foundWords = 0;
            for (int i = 0; i < wordSearch.size(); i++) {
                logDebug("Searching row " + i);
                int targetSearchIndex = wordSearch.get(i).indexOf(targetWord.charAt(0));
                while (targetSearchIndex > -1) {
                    logDebug("Found first character at " + targetSearchIndex);
                    Point targetSearchPoint = new Point(i, targetSearchIndex);
                    if (CheckForWord(targetWord, targetSearchPoint, Direction.N)) foundWords++;
                    if (CheckForWord(targetWord, targetSearchPoint, Direction.NE)) foundWords++;
                    if (CheckForWord(targetWord, targetSearchPoint, Direction.E)) foundWords++;
                    if (CheckForWord(targetWord, targetSearchPoint, Direction.SE)) foundWords++;
                    if (CheckForWord(targetWord, targetSearchPoint, Direction.S)) foundWords++;
                    if (CheckForWord(targetWord, targetSearchPoint, Direction.SW)) foundWords++;
                    if (CheckForWord(targetWord, targetSearchPoint, Direction.W)) foundWords++;
                    if (CheckForWord(targetWord, targetSearchPoint, Direction.NW)) foundWords++;
                    if (targetSearchIndex < wordSearch.get(i).size() - 1) {
                        int previousTargetSearchIndex = targetSearchIndex;
                        targetSearchIndex = wordSearch.get(i).subList(previousTargetSearchIndex + 1, wordSearch.get(i).size()).indexOf(targetWord.charAt(0));
                        if (targetSearchIndex > -1) targetSearchIndex += previousTargetSearchIndex + 1;
                    }
                    else 
                        targetSearchIndex = -1;
                    logDebug(foundWords + " total words found after searching point (" + targetSearchPoint.x + ", " + targetSearchPoint.y + ")");
                }
            }
            return foundWords;
        }
        else { // < 1861
            int foundWords = 0;
            for (int i = 0; i < wordSearch.size(); i++) {
                logDebug("Searching row " + i);
                int targetSearchIndex = wordSearch.get(i).indexOf('A');
                while (targetSearchIndex > -1) {
                    logDebug("Found middle character at (" + i + ", " + targetSearchIndex + ")");
                    Point targetSearchPoint = new Point(i, targetSearchIndex);

                    char cNW = GetCharacter(GetNextPoint(targetSearchPoint, Direction.NW));
                    char cN = GetCharacter(GetNextPoint(targetSearchPoint, Direction.N));
                    char cNE = GetCharacter(GetNextPoint(targetSearchPoint, Direction.NE));
                    char cW = GetCharacter(GetNextPoint(targetSearchPoint, Direction.W));
                    char cX = GetCharacter(targetSearchPoint);
                    char cE = GetCharacter(GetNextPoint(targetSearchPoint, Direction.E));
                    char cSW = GetCharacter(GetNextPoint(targetSearchPoint, Direction.SW));
                    char cS = GetCharacter(GetNextPoint(targetSearchPoint, Direction.S));
                    char cSE = GetCharacter(GetNextPoint(targetSearchPoint, Direction.SE));

                    logDebug("\t" + cNW + cN + cNE);
                    logDebug("\t" + cW + cX + cE);
                    logDebug("\t" + cSW + cS + cSE);

                    if (((cNW == 'M' && cSE == 'S') || (cNW == 'S' && cSE == 'M')) && ((cNE == 'M' && cSW == 'S') || (cNE == 'S' && cSW == 'M'))) {
                        foundWords++;
                        logDebug("Found diagonal");
                    }

                    if (targetSearchIndex < wordSearch.get(i).size() - 1) {
                        int previousTargetSearchIndex = targetSearchIndex;
                        targetSearchIndex = wordSearch.get(i).subList(previousTargetSearchIndex + 1, wordSearch.get(i).size()).indexOf('A');
                        if (targetSearchIndex > -1) targetSearchIndex += previousTargetSearchIndex + 1;
                    }
                    else 
                        targetSearchIndex = -1;

                    logDebug(foundWords + " total words found after searching point (" + targetSearchPoint.x + ", " + targetSearchPoint.y + ")");
                }
            }
            return foundWords;
        }
    }

    private boolean CheckForWord(String targetWord, Point targetSearchPoint, Direction d) {
        int targetCharIndex = 0;
        boolean searchError = false;
        while (targetCharIndex < targetWord.length()) {
            char targetChar = targetWord.charAt(targetCharIndex);
            char searchChar = GetCharacter(targetSearchPoint);
            if (targetChar != searchChar) {
                searchError = true;
                break;
            }
            else {
                targetSearchPoint = GetNextPoint(targetSearchPoint, d);
                targetCharIndex++;
            }
        }
        return !searchError;
    }

    private char GetCharacter(int x, int y) {
        if (x < 0 || x >= wordSearch.size() || y < 0 || y >= wordSearch.get(x).size()) {
            return ' ';
        }
        else {
            return wordSearch.get(x).get(y);
        }
    }
    private char GetCharacter(Point p) {
        return GetCharacter(p.x, p.y);
    }

    private static enum Direction {
        N, NE, E, SE, S, SW, W, NW;
    }

    private Point GetNextPoint(int x, int y, Direction d) {
        return switch (d) {
            case Direction.N -> new Point(x, y + 1);
            case Direction.NE -> new Point(x + 1, y + 1);
            case Direction.E -> new Point(x + 1, y);
            case Direction.SE -> new Point(x + 1, y - 1);
            case Direction.S -> new Point(x, y - 1);
            case Direction.SW -> new Point(x - 1, y - 1);
            case Direction.W -> new Point(x - 1, y);
            case Direction.NW -> new Point(x - 1, y + 1);
            default -> new Point(-1, -1);
        };
    }
    private Point GetNextPoint(Point p, Direction d) {
        return GetNextPoint(p.x, p.y, d);
    }
}
