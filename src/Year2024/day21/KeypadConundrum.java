package Year2024.day21;

import java.awt.Point;
import java.util.ArrayList;
import tools.RunPuzzle;
import tools.TestCase;

public class KeypadConundrum extends tools.RunPuzzle {
    ArrayList<Keypad> keypads;

    public KeypadConundrum(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new KeypadConundrum(21, "Keypad Conundrum", test1);
        RunPuzzle p = new KeypadConundrum(21, "Keypad Conundrum", puzzle);
        //p.setLogFile("src\\Year2024\\day21\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, test1, 126384));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Integer)result);
    }

    @Override
    public Object doProcessing(int section, Object input) { // 255888 too high
        String[] codes = (String[])input;
        keypads = new ArrayList<>();
        keypads.add(new NumericKeypad());
        keypads.add(new DirectionalKeypad());
        keypads.add(new DirectionalKeypad());
        int complexityCount = 0;
        for (String s : codes) {
            char[] previousButton = new char[keypads.size()];
            char[] nextButton = new char[keypads.size()];
            StringBuilder[] validInstructions = new StringBuilder[keypads.size()];
            for (int i = 0; i < keypads.size(); i++) {
                previousButton[i] = 'A';
                validInstructions[i] = new StringBuilder();
            }
            for (char c : s.toCharArray()) {
                nextButton[0] = c;
                try {
                    validInstructions[0].append(keypads.get(0).getInstruction(previousButton[0], nextButton[0]));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
                previousButton[0] = nextButton[0];
            }
            for (int i = 1; i < keypads.size(); i++) {
                Keypad k = keypads.get(i);
                for (char c : validInstructions[i - 1].toString().toCharArray()) {
                    nextButton[i] = c;
                    try {
                        validInstructions[i].append(k.getInstruction(previousButton[i], nextButton[i]));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                    previousButton[i] = nextButton[i];
                }
            }
            logDebug("Code " + s + " length " + validInstructions[keypads.size() - 1].length());
            for (int i = 1; i < keypads.size(); i++) logDebug("\t" + validInstructions[i].toString());
            complexityCount += Integer.parseInt(s.substring(0, s.length() - 1)) * validInstructions[keypads.size() - 1].length();
        }
        return complexityCount;
    }

    public abstract class Keypad {
        public Point position;
        public Point start;
        protected Exception e = new Exception("Robot panics");
        protected ArrayList<Character> blankColumn;
        protected ArrayList<Character> blankRow;

        public abstract char getButton(Point p) throws Exception;
        public abstract Point getPosition(char c) throws Exception;

        public String getInstruction(char prev, char next) throws Exception {
            Point pP = this.getPosition(prev);
            Point nP = this.getPosition(next);
            Point delta = new Point(nP.x - pP.x, nP.y - pP.y);
            char horiz = delta.x >= 0 ? '>' : '<';
            char vert = delta.y >= 0 ? 'v' : '^';
            StringBuilder s = new StringBuilder();
            if (blankColumn.contains(prev) && blankRow.contains(next)) {
                s.repeat(horiz, Math.abs(delta.x));
                s.repeat(vert, Math.abs(delta.y));
            }
            else if (blankColumn.contains(next) && blankRow.contains(prev)) {
                s.repeat(vert, Math.abs(delta.y));
                s.repeat(horiz, Math.abs(delta.x));
            }
            else {
                s.repeat(horiz, Math.abs(delta.x));
                s.repeat(vert, Math.abs(delta.y));
            }
            s.append('A');
            logDebug("\t\tFrom " + prev + " to " + next + " is " + s.toString());
            return s.toString();
        }
    }

    public class NumericKeypad extends Keypad {
        /*
         * 789
         * 456
         * 123
         *  0A
         */
        public NumericKeypad() {
            super();
            this.start = new Point(2, 3);
            this.position = new Point(start.x, start.y);
            blankColumn = new ArrayList<>();
            blankColumn.add('1');
            blankColumn.add('4');
            blankColumn.add('7');
            blankRow = new ArrayList<>();
            blankRow.add('0');
            blankRow.add('A');
        }

        @Override
        public char getButton(Point p) throws Exception {
            switch(p.x) {
                case 0 -> {
                    switch(p.y) {
                        case 0 -> {
                            return '7';
                        }
                        case 1 -> {
                            return '4';
                        }
                        case 2 -> {
                            return '1';
                        }
                        default -> throw e;
                    }
                }

                case 1 -> {
                    switch(p.y) {
                        case 0 -> {
                            return '8';
                        }
                        case 1 -> {
                            return '5';
                        }
                        case 2 -> {
                            return '2';
                        }
                        case 3 -> {
                            return '0';
                        }
                        default -> throw e;
                    }
                }

                case 2 -> {
                    switch(p.y) {
                        case 0 -> {
                            return '9';
                        }
                        case 1 -> {
                            return '6';
                        }
                        case 2 -> {
                            return '3';
                        }
                        case 3 -> {
                            return 'A';
                        }
                        default -> throw e;
                    }
                }

                default -> throw e;
            }
        }

        @Override
        public Point getPosition(char button) throws Exception {
            return switch (button) {
                case 'A' -> new Point(2, 3);
                case '0' -> new Point(1, 3);
                case '1' -> new Point(0, 2);
                case '2' -> new Point(1, 2);
                case '3' -> new Point(2, 2);
                case '4' -> new Point(0, 1);
                case '5' -> new Point(1, 1);
                case '6' -> new Point(2, 1);
                case '7' -> new Point(0, 0);
                case '8' -> new Point(1, 0);
                case '9' -> new Point(2, 0);
                default -> throw e;
            };
        }
    }

    private class DirectionalKeypad extends Keypad {
        /*
         *  ^A
         * <v>
         */
        public DirectionalKeypad() {
            super();
            this.start = new Point(2, 0);
            this.position = new Point(start.x, start.y);
            blankColumn = new ArrayList<>();
            blankColumn.add('<');
            blankRow = new ArrayList<>();
            blankRow.add('^');
            blankRow.add('A');
        }

        @Override
        public char getButton(Point p) throws Exception {
            switch(p.x) {
                case 0 -> { 
                    switch(p.y) {
                        case 1 -> {
                            return '<';
                        }
                        default -> throw e;
                    }
                }

                case 1 -> {
                    switch(p.y) {
                        case 0 -> {
                            return '^';
                        }
                        case 1 -> {
                            return 'v';
                        }
                        default -> throw e;
                    }
                }

                case 2 -> {
                    switch(p.y) {
                        case 0 -> {
                            return 'A';
                        }
                        case 1 -> {
                            return '>';
                        }
                        default -> throw e;
                    }
                }

                default -> throw e;
            }
        }

        @Override
        public Point getPosition(char c) throws Exception {
            switch(c) {
                case '^' -> {
                    return new Point(1, 0);
                }
                case 'A' -> {
                    return new Point(2, 0);
                }
                case '<' -> {
                    return new Point(0, 1);
                }
                case 'v' -> {
                    return new Point(1, 1);
                }
                case '>' -> {
                    return new Point(2, 1);
                }
                default -> throw e;
            }
        }
    }

    public static String[] test1 = {"029A", "980A", "179A", "456A", "379A"};
    public static String[] puzzle = {"836A", "540A", "965A", "480A", "789A"};
}
