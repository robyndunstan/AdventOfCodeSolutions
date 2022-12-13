package Year2022.day11;

import java.util.ArrayList;
import java.util.function.Function;

import tools.RunPuzzle;
import tools.TestCase;

public class MonkeyInTheMiddle extends RunPuzzle {

	public MonkeyInTheMiddle(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
		this.debug = false;
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<Integer, Long>(1, 1, 10605l));
		tests.add(new TestCase<Integer, Long>(2, 1, 2713310158l));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Long)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		ArrayList<Monkey> monkeys = setInitialMonkeys((Integer)input);
		int maxRounds = section == 1 ? 20 : 10000;
		int[] printRounds = {0, 19, 999, 1999, 2999, 3999, 4999, 5999, 6999, 7999, 8999, 9999};
		
		for (int roundCount = 0; roundCount < maxRounds; roundCount++) {
			for (Monkey m : monkeys) {
				while (m.getItemCount() > 0) {
					Monkey.ThrowItem thrown = m.throwItem(section);
					monkeys.get(thrown.monkeyIndex).catchItem(thrown.item);
				}
			}
			if (this.debug) {
				for (int r : printRounds) {
					if (r == roundCount) {
						System.out.println("Round " + roundCount);
						for (Monkey m : monkeys) {
							System.out.println("\t" + m.getInspections());
						}
					}
				}
			}
		}
		
		return calculateMonkeyBusiness(monkeys);
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new MonkeyInTheMiddle(11, "Monkey in the Middle", 0);
		puzzle.run();
	}
	
	private static long calculateMonkeyBusiness(ArrayList<Monkey> monkeys) {
		long[] max = {Integer.MIN_VALUE, Integer.MIN_VALUE};
		for (Monkey m : monkeys) {
			if (m.getInspections() > max[1]) {
				max[0] = max[1];
				max[1] = m.getInspections();
			}
			else if (m.getInspections() > max[0]) {
				max[0] = m.getInspections();
			}
		}
		return max[0] * max[1];
	}

	private static ArrayList<Monkey> setInitialMonkeys(int scenario) {
		ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
		Monkey m;
		switch (scenario) {
		case 0: // puzzle input
			m = new Monkey(buildMultiplyFunction(7), buildDivisibleTest(2), 7, 1, scenario);
			m.catchItem(62l);
			m.catchItem(92l);
			m.catchItem(50l);
			m.catchItem(63l);
			m.catchItem(62l);
			m.catchItem(93l);
			m.catchItem(73l);
			m.catchItem(50l);
			monkeys.add(m);
			m = new Monkey(buildAdditionFunction(3), buildDivisibleTest(7), 2, 4, scenario);
			m.catchItem(51l);
			m.catchItem(97l);
			m.catchItem(74l);
			m.catchItem(84l);
			m.catchItem(99l);
			monkeys.add(m);
			m = new Monkey(buildAdditionFunction(4), buildDivisibleTest(13), 5, 4, scenario);
			m.catchItem(98l);
			m.catchItem(86l);
			m.catchItem(62l);
			m.catchItem(76l);
			m.catchItem(51l);
			m.catchItem(81l);
			m.catchItem(95l);
			monkeys.add(m);
			m = new Monkey(buildAdditionFunction(5), buildDivisibleTest(19), 6, 0, scenario);
			m.catchItem(53l);
			m.catchItem(95l);
			m.catchItem(50l);
			m.catchItem(85l);
			m.catchItem(83l);
			m.catchItem(72l);
			monkeys.add(m);
			m = new Monkey(buildMultiplyFunction(5), buildDivisibleTest(11), 5, 3, scenario);
			m.catchItem(59l);
			m.catchItem(60l);
			m.catchItem(63l);
			m.catchItem(71l);
			monkeys.add(m);
			m = new Monkey(buildSquareFunction(), buildDivisibleTest(5), 6, 3, scenario);
			m.catchItem(92l);
			m.catchItem(65l);
			monkeys.add(m);
			m = new Monkey(buildAdditionFunction(8), buildDivisibleTest(3), 0, 7, scenario);
			m.catchItem(78l);
			monkeys.add(m);
			m = new Monkey(buildAdditionFunction(1), buildDivisibleTest(17), 2, 1, scenario);
			m.catchItem(84l);
			m.catchItem(93l);
			m.catchItem(54l);
			monkeys.add(m);
			break;
		case 1: // test 1 input
			m = new Monkey(buildMultiplyFunction(19), buildDivisibleTest(23), 2, 3, scenario);
			m.catchItem(79l);
			m.catchItem(98l);
			monkeys.add(m);
			m = new Monkey(buildAdditionFunction(6), buildDivisibleTest(19), 2, 0, scenario);
			m.catchItem(54l);
			m.catchItem(65l);
			m.catchItem(75l);
			m.catchItem(74l);
			monkeys.add(m);
			m = new Monkey(buildSquareFunction(), buildDivisibleTest(13), 1, 3, scenario);
			m.catchItem(79l);
			m.catchItem(60l);
			m.catchItem(97l);
			monkeys.add(m);
			m = new Monkey(buildAdditionFunction(3), buildDivisibleTest(17), 0, 1, scenario);
			m.catchItem(74l);
			monkeys.add(m);
			break;
		default:
			break;
		}
		return monkeys;
	}
	
	private static class Monkey {
		private ArrayList<Long> items;
		private Function<Long, Long> inspect;
		private Function<Long, Boolean> test;
		private int monkeyIndexTrue;
		private int monkeyIndexFalse;
		private int inspectCount;
		private long monkeyLcm;
		
		public Monkey() {
			items = new ArrayList<Long>();
			inspectCount = 0;
		}
		public Monkey(Function<Long, Long> inspect, Function<Long, Boolean> test, int monkeyTrue, int monkeyFalse, int scenario) {
			this();
			this.inspect = inspect;
			this.test = test;
			this.monkeyIndexFalse = monkeyFalse;
			this.monkeyIndexTrue = monkeyTrue;
			this.monkeyLcm = scenario == 1 ? 23l * 19 * 13 * 17 : 2l * 7 * 13 * 19 * 11 * 5 * 3 * 17;
		}
		
		public static class ThrowItem {
			Long item;
			int monkeyIndex;
		}
		
		public void catchItem(Long item) {
			items.add(item);
		}
		
		public ThrowItem throwItem(int section) {
			ThrowItem doThrow = new ThrowItem();
			doThrow.item = items.get(0);
			doThrow.item = inspect.apply(doThrow.item);
			if (section == 1) doThrow.item /= 3;
			else doThrow.item %= this.monkeyLcm;
			doThrow.monkeyIndex = test.apply(doThrow.item) ? monkeyIndexTrue : monkeyIndexFalse;
			items.remove(0);
			inspectCount++;
			return doThrow;
		}
		
		public int getItemCount() {
			return items.size();
		}
		
		public int getInspections() {
			return inspectCount;
		}
	}
	
	private static Function<Long, Long> buildMultiplyFunction(int multiplicand) {
		Function<Long, Long> mult = i -> i * multiplicand;
		return mult;
	}
	
	private static Function<Long, Boolean> buildDivisibleTest(int divisor) {
		Function<Long, Boolean> test = i -> (i % divisor) == 0;
		return test;
	}
	
	private static Function<Long, Long> buildAdditionFunction(int addend) {
		Function<Long, Long> add = i -> i + addend;
		return add;
	}
	
	private static Function<Long, Long> buildSquareFunction() {
		Function<Long, Long> square = i -> i * i;
		return square;
	}
}
