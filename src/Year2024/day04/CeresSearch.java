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
        debug = false;
    }

    public static void main(String[] args) {
        RunPuzzle p = new CeresSearch(4, "Ceres Search", "src\\Year2024\\day04\\data\\puzzleFile");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<TestCase>();
        tests.add(new TestCase<String, Integer>(1, "src\\Year2024\\day04\\data\\test1File", 18));
        tests.add(new TestCase<String, Integer>(2, "src\\Year2024\\day04\\data\\test1File", 9));
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

        wordSearch = new ArrayList<ArrayList<Character>>();

        try {
            file.openInput();
            String line = file.readLine();
            while (line != null) {
                line = line.trim();
                ArrayList<Character> row = new ArrayList<Character>();
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
                if (debug) System.out.println("Searching row " + i);
                int targetSearchIndex = wordSearch.get(i).indexOf(targetWord.charAt(0));
                while (targetSearchIndex > -1) {
                    if (debug) System.out.println("Found first character at " + targetSearchIndex);
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
                    if (debug) System.out.println(foundWords + " total words found after searching point (" + targetSearchPoint.x + ", " + targetSearchPoint.y + ")");
                }
            }
            return foundWords;
        }
        else { // < 1862
            int foundWords = 0;
            String targetWord = "MAS";
            for (int i = 1; i < wordSearch.size() - 1; i++) {
                if (debug) System.out.println("Searching row " + i);
                int targetSearchIndex = wordSearch.get(i).indexOf(targetWord.charAt(1));
                if (debug) System.out.println("Found middle character at " + targetSearchIndex);
                while (targetSearchIndex > -1) {
                    Point targetSearchPoint = new Point(i, targetSearchIndex);

                    if ((CheckForWord(targetWord, GetNextPoint(targetSearchPoint, Direction.N), Direction.S)
                            || CheckForWord(targetWord, GetNextPoint(targetSearchPoint, Direction.S), Direction.N))
                        && (CheckForWord(targetWord, GetNextPoint(targetSearchPoint, Direction.E), Direction.W)
                            || CheckForWord(targetWord, GetNextPoint(targetSearchPoint, Direction.W), Direction.E))) {
                                foundWords++;
                                if (debug) System.out.println("Found orthogonal");
                    }

                    if ((CheckForWord(targetWord, GetNextPoint(targetSearchPoint, Direction.NW), Direction.SE)
                            || CheckForWord(targetWord, GetNextPoint(targetSearchPoint, Direction.SE), Direction.NW))
                        && (CheckForWord(targetWord, GetNextPoint(targetSearchPoint, Direction.NE), Direction.SW)
                            || CheckForWord(targetWord, GetNextPoint(targetSearchPoint, Direction.SW), Direction.NE))) {
                                foundWords++;
                                if (debug) System.out.println("Found diagonal");
                    }
                    if (targetSearchIndex < wordSearch.get(i).size() - 1) {
                        int previousTargetSearchIndex = targetSearchIndex;
                        targetSearchIndex = wordSearch.get(i).subList(previousTargetSearchIndex + 1, wordSearch.get(i).size()).indexOf(targetWord.charAt(1));
                        if (targetSearchIndex > -1) targetSearchIndex += previousTargetSearchIndex + 1;
                        if (debug) System.out.println("Next search at " + targetSearchIndex);
                    }
                    else 
                        targetSearchIndex = -1;
                    if (debug) System.out.println(foundWords + " total words found after searching point (" + targetSearchPoint.x + ", " + targetSearchPoint.y + ")");
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
