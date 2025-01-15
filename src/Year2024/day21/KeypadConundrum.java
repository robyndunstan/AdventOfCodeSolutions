package Year2024.day21;

import java.awt.Point;
import java.util.ArrayList;
import tools.RunPuzzle;
import tools.TestCase;

public class KeypadConundrum extends tools.RunPuzzle {
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
    public Object doProcessing(int section, Object input) {
        return null;
    }

    public abstract class Keypad {
        public Point position;
        public Point start;
        public abstract char getButton(Point p) throws Exception;
        public abstract Point getPosition(char c) throws Exception;
        
        public Character doMove(char instruction) throws Exception {
            switch(instruction) {
                case '^' -> position.y--;
                case 'A' -> {
                    return getButton(position);
                }
                case '<' -> position.x--;
                case 'v' -> position.y++;
                case '>' -> position.x++;
                default -> throw new Exception("Robot panics");
            }
            getButton(position); // throw Exception if over blank
            return null;
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
                        default -> throw new Exception("Robot panics");
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
                        default -> throw new Exception("Robot panics");
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
                        default -> throw new Exception("Robot panics");
                    }
                }

                default -> throw new Exception("Robot panics");
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
                default -> throw new Exception("Robot panics");
            };
        }
    }

    public static String[] test1 = {"029A", "980A", "179A", "456A", "379A"};
    public static String[] puzzle = {"836A", "540A", "965A", "480A", "789A"};
}
