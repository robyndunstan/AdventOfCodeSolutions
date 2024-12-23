package Year2024.day04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import tools.MapDirection;
import tools.MapPoint;
import tools.PuzzleMap;
import tools.RunPuzzle;
import tools.TestCase;

public class CeresSearch extends tools.RunPuzzle {
    private WordSearch wordSearch;

    public CeresSearch(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        wordSearch = new WordSearch();
        debug = false;
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
        try {
            wordSearch.parseMap(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        if (section == 1) {
            String targetWord = "XMAS";
            int foundWords = 0;
            int maxX = wordSearch.getSizeX();
            int maxY = wordSearch.getSizeY();
            for (int y = 0; y < maxY; y++) {
                logDebug("Searching row " + y);
                for (int x = 0; x < maxX; x++) {
                    MapPoint here = new MapPoint(x, y);
                    char c = wordSearch.getValue(here);
                    if (c == targetWord.charAt(0)) {
                        logDebug("Found first character at " + x);
                        if (CheckForWord(targetWord, here, MapDirection.N)) foundWords++;
                        if (CheckForWord(targetWord, here, MapDirection.NE)) foundWords++;
                        if (CheckForWord(targetWord, here, MapDirection.E)) foundWords++;
                        if (CheckForWord(targetWord, here, MapDirection.SE)) foundWords++;
                        if (CheckForWord(targetWord, here, MapDirection.S)) foundWords++;
                        if (CheckForWord(targetWord, here, MapDirection.SW)) foundWords++;
                        if (CheckForWord(targetWord, here, MapDirection.W)) foundWords++;
                        if (CheckForWord(targetWord, here, MapDirection.NW)) foundWords++;
                        logDebug(foundWords + " total words found after searching point (" + here.x + ", " + here.y + ")");
                    }
                }
            }
            return foundWords;
        }
        else {
            int foundWords = 0;
            int maxX = wordSearch.getSizeX();
            int maxY = wordSearch.getSizeY();
            for (int y = 0; y < maxY; y++) {
                logDebug("Searching row " + y);
                for (int x = 0; x < maxX; x++) {
                    MapPoint here = new MapPoint(x, y);
                    char c = wordSearch.getValue(here);
                    if (c == 'A') {
                        logDebug("Found middle character at (" + x + ", " + y + ")");
                        Character cNW = wordSearch.getValue(here.getNextPoint(MapDirection.NW));
                        Character cNE = wordSearch.getValue(here.getNextPoint(MapDirection.NE));
                        Character cSW = wordSearch.getValue(here.getNextPoint(MapDirection.SW));
                        Character cSE = wordSearch.getValue(here.getNextPoint(MapDirection.SE));
                        if (cNW != null && cSE != null && cNE != null && cSW != null && 
                            ((cNW == 'M' && cSE == 'S') || (cNW == 'S' && cSE == 'M')) && 
                            ((cNE == 'M' && cSW == 'S') || (cNE == 'S' && cSW == 'M'))) {
                            foundWords++;
                            logDebug("Found diagonal");
                        }
                        logDebug(foundWords + " total words found after searching point (" + here.x + ", " + here.y + ")");
                    }
                }
            }
            return foundWords;
        }
    }

    private boolean CheckForWord(String targetWord, MapPoint targetSearchPoint, MapDirection d) {
        int targetCharIndex = 0;
        boolean searchError = false;
        while (targetCharIndex < targetWord.length()) {
            Character targetChar = targetWord.charAt(targetCharIndex);
            Character searchChar = wordSearch.getValue(targetSearchPoint);
            if (!Objects.equals(targetChar, searchChar)) {
                searchError = true;
                break;
            }
            else {
                targetSearchPoint = targetSearchPoint.getNextPoint(d);
                targetCharIndex++;
            }
        }
        return !searchError;
    }

    private class WordSearch extends PuzzleMap<Character> {

        @Override
        public Character parse(char c, int x, int y) {
            return c;
        }

    }
}
