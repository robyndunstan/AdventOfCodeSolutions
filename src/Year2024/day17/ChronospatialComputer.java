package Year2024.day17;

import java.util.ArrayList;
import tools.RunPuzzle;
import tools.TestCase;

public class ChronospatialComputer extends tools.RunPuzzle {
    private int a, b, c;
    private String program;
    private int pointer;
    private StringBuilder output;

    public ChronospatialComputer(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) {
        //RunPuzzle p = new ChronospatialComputer(17, "Chronospatial Computer", test4input);
        RunPuzzle p = new ChronospatialComputer(17, "Chronospatial Computer", puzzleInput);
        //p.setLogFile("src\\Year2024\\day17\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, test1input, test1expected));
        tests.add(new TestCase<>(1, test2input, test2expected));
        tests.add(new TestCase<>(1, test3input, test3expected));
        tests.add(new TestCase<>(2, test4input, test4expected));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (String)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        ComputerInput initialState = (ComputerInput)input;
        if (section == 1) {
            return runProgram(section, initialState);
        }
        else {
            initialState.a = 0;
            String programOutput = runProgram(section, initialState);
            while (!programOutput.equals(this.program) && initialState.a < Integer.MAX_VALUE) {
                initialState.a++;
                programOutput = runProgram(section, initialState);
            }
            return "" + initialState.a;
        }
    }

    private String runProgram(int section, ComputerInput initialState) {
        this.a = initialState.a;
        this.b = initialState.b;
        this.c = initialState.c;
        this.program = initialState.program.trim();
        this.pointer = 0;
        this.output = new StringBuilder();
        while (pointer >= 0 && pointer < program.length() && (section == 1 || (section == 2 && output.length() < this.program.length() && output.toString().equals(program.substring(0, output.length()))))) {
            char opCode = program.charAt(pointer);
            char operand = program.charAt(pointer + 2);
            int literalOperandValue = Integer.parseInt("" + operand);
            int comboOperandValue = 0;
            switch (operand) {
                case '0':
                case '1':
                case '2':
                case '3':
                    comboOperandValue = Integer.parseInt("" + operand);
                    break;
                case '4': 
                    comboOperandValue = this.a;
                    break;
                case '5':
                    comboOperandValue = this.b;
                    break;
                case '6':
                    comboOperandValue = this.c;
                    break;
                default:
                    comboOperandValue = -1;
                    break;
            }
            switch(opCode) {
                case '0': // adv
                    a = a / (int)Math.pow(2, comboOperandValue);
                    pointer += 4;
                    break;
                case '1': // bxl
                    b = b ^ literalOperandValue;
                    pointer += 4;
                    break;
                case '2': // bst
                    b = comboOperandValue % 8;
                    pointer += 4;
                    break;
                case '3': // jnz
                    if (a != 0) {
                        pointer = literalOperandValue * 2;
                    }
                    else {
                        pointer += 4;
                    }
                    break;
                case '4': // bxc
                    b = b ^ c;
                    pointer += 4;
                    break;
                case '5': // out
                    int outResult = comboOperandValue % 8;
                    if (output.length() > 0) {
                        output.append(",");
                    }
                    output.append(outResult);
                    pointer += 4;
                    break;
                case '6': // bdv
                    b = a / (int)Math.pow(2, comboOperandValue);
                    pointer += 4;
                    break;
                case '7': // cdv
                    c = a / (int)Math.pow(2, comboOperandValue);
                    pointer += 4;
                    break;
                default:
                    log("Invalid opcode " + opCode);
                    pointer += 4;
                    break;
            }
        }
        return output.toString();
    }

    private static class ComputerInput {
        int a, b, c;
        String program;
        public ComputerInput(int a, int b, int c, String program) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.program = program;
        }
    }

    private static ComputerInput test1input = new ComputerInput(10, 0, 0, "5,0,5,1,5,4");
    private static String test1expected = "0,1,2";

    private static ComputerInput test2input = new ComputerInput(2024, 0, 0, "0,1,5,4,3,0");
    private static String test2expected = "4,2,5,6,7,7,7,7,3,1,0";

    private static ComputerInput test3input = new ComputerInput(729, 0, 0, "0,1,5,4,3,0");
    private static String test3expected = "4,6,3,5,6,3,5,2,1,0";

    private static ComputerInput test4input = new ComputerInput(2024, 0, 0, "0,3,5,4,3,0");
    private static String test4expected = "117440";

    private static ComputerInput puzzleInput = new ComputerInput(46337277, 0, 0, "2,4,1,1,7,5,4,4,1,4,0,3,5,5,3,0");
}
