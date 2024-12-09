package Year2015.day07;

import java.util.ArrayList;
import java.util.HashMap;

import tools.RunPuzzle;
import tools.TestCase;

public class SomeAssemblyRequired extends tools.RunPuzzle {
	public SomeAssemblyRequired(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}
	
	public static void main (String[] args) {
		RunPuzzle puzzle = new SomeAssemblyRequired(7, "Some Assembly Required", puzzleInput1);
		puzzle.run();
	}

	private HashMap<String, Integer> buildCircuit(Object[] input) {
		HashMap<String, Integer> wires = new HashMap<String, Integer>();
		ArrayList<Instruction> backlog = new ArrayList<Instruction>();
		
		for (Object o : input) {
			String s = (String)o;
			backlog.add(Instruction.parseInstruction(s));
		}
		while (!backlog.isEmpty()) {
			backlog = doInstructions(wires, backlog);
		}
		
		return wires;
	}
	
	enum BitOp {
		SIGNAL, BIT_AND, BIT_OR, BIT_NOT, BIT_LEFT, BIT_RIGHT;
	}
	
	private ArrayList<Instruction> doInstructions(HashMap<String, Integer> wires, ArrayList<Instruction> instructions) {
		ArrayList<Instruction> backlog = new ArrayList<Instruction>();
		
		for (Instruction i : instructions) {
			switch (i.op) {
			case SIGNAL:
				if (getInputValue(i.in1, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_AND:
				if (getInputValue(i.in1, wires) != null && getInputValue(i.in2, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires) & getInputValue(i.in2, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_OR:
				if (getInputValue(i.in1, wires) != null && getInputValue(i.in2, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires) | getInputValue(i.in2, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_NOT:
				if (getInputValue(i.in1, wires) != null) {
					wires.put(i.out, ~getInputValue(i.in1, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_LEFT:
				if (getInputValue(i.in1, wires) != null && getInputValue(i.in2, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires) << getInputValue(i.in2, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			case BIT_RIGHT:
				if (getInputValue(i.in1, wires) != null && getInputValue(i.in2, wires) != null) {
					wires.put(i.out, getInputValue(i.in1, wires) >> getInputValue(i.in2, wires));
				}
				else {
					backlog.add(i);
				}
				break;
			}
			
			if (wires.containsKey(i.out) && wires.get(i.out)< 0) {
				wires.put(i.out, wires.get(i.out)+ 65536);
			}
		}
		
		return backlog;
	}
	
	private Integer getInputValue(String input, HashMap<String, Integer> wires) {
		if (isNumber(input)) {
			return Integer.parseInt(input);
		}
		else if (wires.containsKey(input)) {
			return wires.get(input);
		}
		else {
			return null;
		}
	}
	
	private static class Instruction {
		public BitOp op;
		public String in1, in2, out;
		
		public static Instruction parseInstruction(String s) {
			Instruction i = new Instruction();
			
			if (s.contains("AND")) {
				i.op = BitOp.BIT_AND;
				int ind1 = s.indexOf("AND");
				i.in1 = s.substring(0, ind1).trim();
				int ind2 = s.indexOf("->");
				i.in2 = s.substring(ind1 + 3, ind2).trim();
				i.out = s.substring(ind2 + 2).trim();
			}
			else if (s.contains("OR")) {
				i.op = BitOp.BIT_OR;
				int ind1 = s.indexOf("OR");
				i.in1 = s.substring(0, ind1).trim();
				int ind2 = s.indexOf("->");
				i.in2 = s.substring(ind1 + 3, ind2).trim();
				i.out = s.substring(ind2 + 2).trim();
			}
			else if (s.contains("NOT")) {
				i.op = BitOp.BIT_NOT;
				int ind1 = s.indexOf("->");
				i.in1 = s.substring(3, ind1).trim();
				i.in2 = "";
				i.out = s.substring(ind1 + 2).trim();
			}
			else if (s.contains("LSHIFT")) {
				i.op = BitOp.BIT_LEFT;
				int ind1 = s.indexOf("LSHIFT");
				i.in1 = s.substring(0, ind1).trim();
				int ind2 = s.indexOf("->");
				i.in2 = s.substring(ind1 + 6, ind2).trim();
				i.out = s.substring(ind2 + 2).trim();
			}
			else if (s.contains("RSHIFT")) {
				i.op = BitOp.BIT_RIGHT;
				int ind1 = s.indexOf("RSHIFT");
				i.in1 = s.substring(0, ind1).trim();
				int ind2 = s.indexOf("->");
				i.in2 = s.substring(ind1 + 6, ind2).trim();
				i.out = s.substring(ind2 + 2).trim();
			}
			else {
				i.op = BitOp.SIGNAL;
				int ind1 = s.indexOf("->");
				i.in1 = s.substring(0, ind1).trim();
				i.in2 = "";
				i.out = s.substring(ind1 + 2).trim();
			}
			
			return i;
		}
	}
	
	boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], HashMap<String, Integer>>(1, test1, buildTestResult1()));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		HashMap<String, Integer> r = (HashMap<String, Integer>)result;
		if (r.containsKey("a")) {
			log("\t\t\t\ta: " + r.get("a"));
		}
		else {
			for (String s : r.keySet()) {
				log("\t\t\t\t" + s + ": " + r.get(s));
			}
		}
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String[] i = (String[])input;
		if (section == 1) {
			return buildCircuit(i);
		}
		else {
			// No test cases for section 2, only puzzle
			ArrayList<String> puzzleInput2 = new ArrayList<String>();
			for (String s : i) {
				if (s.trim().substring(s.trim().length() - 2).equals(" b")) {
					puzzleInput2.add("16076 -> b");
				}
				else {
					puzzleInput2.add(s);
				}
			}
			return buildCircuit(puzzleInput2.toArray());
		}
	}
	
	private static String[] test1 = {
			"123 -> x             ",
			"456 -> y             ",
			"x AND y -> d         ",
			"x OR y -> e          ",
			"x LSHIFT 2 -> f      ",
			"y RSHIFT 2 -> g      ",
			"NOT x -> h           ",
			"NOT y -> i           "
	};
	private HashMap<String, Integer> buildTestResult1() {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		result.put("d", 72);
		result.put("e", 507);
		result.put("f", 492);
		result.put("g", 114);
		result.put("h", 65412);
		result.put("i", 65079);
		result.put("x", 123);
		result.put("y", 456);
		return result;
	}
	private static String[] puzzleInput1 = {
			"lf AND lq -> ls         ",
			"iu RSHIFT 1 -> jn       ",
			"bo OR bu -> bv          ",
			"gj RSHIFT 1 -> hc       ",
			"et RSHIFT 2 -> eu       ",
			"bv AND bx -> by         ",
			"is OR it -> iu          ",
			"b OR n -> o             ",
			"gf OR ge -> gg          ",
			"NOT kt -> ku            ",
			"ea AND eb -> ed         ",
			"kl OR kr -> ks          ",
			"hi AND hk -> hl         ",
			"au AND av -> ax         ",
			"lf RSHIFT 2 -> lg       ",
			"dd RSHIFT 3 -> df       ",
			"eu AND fa -> fc         ",
			"df AND dg -> di         ",
			"ip LSHIFT 15 -> it      ",
			"NOT el -> em            ",
			"et OR fe -> ff          ",
			"fj LSHIFT 15 -> fn      ",
			"t OR s -> u             ",
			"ly OR lz -> ma          ",
			"ko AND kq -> kr         ",
			"NOT fx -> fy            ",
			"et RSHIFT 1 -> fm       ",
			"eu OR fa -> fb          ",
			"dd RSHIFT 2 -> de       ",
			"NOT go -> gp            ",
			"kb AND kd -> ke         ",
			"hg OR hh -> hi          ",
			"jm LSHIFT 1 -> kg       ",
			"NOT cn -> co            ",
			"jp RSHIFT 2 -> jq       ",
			"jp RSHIFT 5 -> js       ",
			"1 AND io -> ip          ",
			"eo LSHIFT 15 -> es      ",
			"1 AND jj -> jk          ",
			"g AND i -> j            ",
			"ci RSHIFT 3 -> ck       ",
			"gn AND gp -> gq         ",
			"fs AND fu -> fv         ",
			"lj AND ll -> lm         ",
			"jk LSHIFT 15 -> jo      ",
			"iu RSHIFT 3 -> iw       ",
			"NOT ii -> ij            ",
			"1 AND cc -> cd          ",
			"bn RSHIFT 3 -> bp       ",
			"NOT gw -> gx            ",
			"NOT ft -> fu            ",
			"jn OR jo -> jp          ",
			"iv OR jb -> jc          ",
			"hv OR hu -> hw          ",
			"19138 -> b              ",
			"gj RSHIFT 5 -> gm       ",
			"hq AND hs -> ht         ",
			"dy RSHIFT 1 -> er       ",
			"ao OR an -> ap          ",
			"ld OR le -> lf          ",
			"bk LSHIFT 1 -> ce       ",
			"bz AND cb -> cc         ",
			"bi LSHIFT 15 -> bm      ",
			"il AND in -> io         ",
			"af AND ah -> ai         ",
			"as RSHIFT 1 -> bl       ",
			"lf RSHIFT 3 -> lh       ",
			"er OR es -> et          ",
			"NOT ax -> ay            ",
			"ci RSHIFT 1 -> db       ",
			"et AND fe -> fg         ",
			"lg OR lm -> ln          ",
			"k AND m -> n            ",
			"hz RSHIFT 2 -> ia       ",
			"kh LSHIFT 1 -> lb       ",
			"NOT ey -> ez            ",
			"NOT di -> dj            ",
			"dz OR ef -> eg          ",
			"lx -> a                 ",
			"NOT iz -> ja            ",
			"gz LSHIFT 15 -> hd      ",
			"ce OR cd -> cf          ",
			"fq AND fr -> ft         ",
			"at AND az -> bb         ",
			"ha OR gz -> hb          ",
			"fp AND fv -> fx         ",
			"NOT gb -> gc            ",
			"ia AND ig -> ii         ",
			"gl OR gm -> gn          ",
			"0 -> c                  ",
			"NOT ca -> cb            ",
			"bn RSHIFT 1 -> cg       ",
			"c LSHIFT 1 -> t         ",
			"iw OR ix -> iy          ",
			"kg OR kf -> kh          ",
			"dy OR ej -> ek          ",
			"km AND kn -> kp         ",
			"NOT fc -> fd            ",
			"hz RSHIFT 3 -> ib       ",
			"NOT dq -> dr            ",
			"NOT fg -> fh            ",
			"dy RSHIFT 2 -> dz       ",
			"kk RSHIFT 2 -> kl       ",
			"1 AND fi -> fj          ",
			"NOT hr -> hs            ",
			"jp RSHIFT 1 -> ki       ",
			"bl OR bm -> bn          ",
			"1 AND gy -> gz          ",
			"gr AND gt -> gu         ",
			"db OR dc -> dd          ",
			"de OR dk -> dl          ",
			"as RSHIFT 5 -> av       ",
			"lf RSHIFT 5 -> li       ",
			"hm AND ho -> hp         ",
			"cg OR ch -> ci          ",
			"gj AND gu -> gw         ",
			"ge LSHIFT 15 -> gi      ",
			"e OR f -> g             ",
			"fp OR fv -> fw          ",
			"fb AND fd -> fe         ",
			"cd LSHIFT 15 -> ch      ",
			"b RSHIFT 1 -> v         ",
			"at OR az -> ba          ",
			"bn RSHIFT 2 -> bo       ",
			"lh AND li -> lk         ",
			"dl AND dn -> do         ",
			"eg AND ei -> ej         ",
			"ex AND ez -> fa         ",
			"NOT kp -> kq            ",
			"NOT lk -> ll            ",
			"x AND ai -> ak          ",
			"jp OR ka -> kb          ",
			"NOT jd -> je            ",
			"iy AND ja -> jb         ",
			"jp RSHIFT 3 -> jr       ",
			"fo OR fz -> ga          ",
			"df OR dg -> dh          ",
			"gj RSHIFT 2 -> gk       ",
			"gj OR gu -> gv          ",
			"NOT jh -> ji            ",
			"ap LSHIFT 1 -> bj       ",
			"NOT ls -> lt            ",
			"ir LSHIFT 1 -> jl       ",
			"bn AND by -> ca         ",
			"lv LSHIFT 15 -> lz      ",
			"ba AND bc -> bd         ",
			"cy LSHIFT 15 -> dc      ",
			"ln AND lp -> lq         ",
			"x RSHIFT 1 -> aq        ",
			"gk OR gq -> gr          ",
			"NOT kx -> ky            ",
			"jg AND ji -> jj         ",
			"bn OR by -> bz          ",
			"fl LSHIFT 1 -> gf       ",
			"bp OR bq -> br          ",
			"he OR hp -> hq          ",
			"et RSHIFT 5 -> ew       ",
			"iu RSHIFT 2 -> iv       ",
			"gl AND gm -> go         ",
			"x OR ai -> aj           ",
			"hc OR hd -> he          ",
			"lg AND lm -> lo         ",
			"lh OR li -> lj          ",
			"da LSHIFT 1 -> du       ",
			"fo RSHIFT 2 -> fp       ",
			"gk AND gq -> gs         ",
			"bj OR bi -> bk          ",
			"lf OR lq -> lr          ",
			"cj AND cp -> cr         ",
			"hu LSHIFT 15 -> hy      ",
			"1 AND bh -> bi          ",
			"fo RSHIFT 3 -> fq       ",
			"NOT lo -> lp            ",
			"hw LSHIFT 1 -> iq       ",
			"dd RSHIFT 1 -> dw       ",
			"dt LSHIFT 15 -> dx      ",
			"dy AND ej -> el         ",
			"an LSHIFT 15 -> ar      ",
			"aq OR ar -> as          ",
			"1 AND r -> s            ",
			"fw AND fy -> fz         ",
			"NOT im -> in            ",
			"et RSHIFT 3 -> ev       ",
			"1 AND ds -> dt          ",
			"ec AND ee -> ef         ",
			"NOT ak -> al            ",
			"jl OR jk -> jm          ",
			"1 AND en -> eo          ",
			"lb OR la -> lc          ",
			"iu AND jf -> jh         ",
			"iu RSHIFT 5 -> ix       ",
			"bo AND bu -> bw         ",
			"cz OR cy -> da          ",
			"iv AND jb -> jd         ",
			"iw AND ix -> iz         ",
			"lf RSHIFT 1 -> ly       ",
			"iu OR jf -> jg          ",
			"NOT dm -> dn            ",
			"lw OR lv -> lx          ",
			"gg LSHIFT 1 -> ha       ",
			"lr AND lt -> lu         ",
			"fm OR fn -> fo          ",
			"he RSHIFT 3 -> hg       ",
			"aj AND al -> am         ",
			"1 AND kz -> la          ",
			"dy RSHIFT 5 -> eb       ",
			"jc AND je -> jf         ",
			"cm AND co -> cp         ",
			"gv AND gx -> gy         ",
			"ev OR ew -> ex          ",
			"jp AND ka -> kc         ",
			"fk OR fj -> fl          ",
			"dy RSHIFT 3 -> ea       ",
			"NOT bs -> bt            ",
			"NOT ag -> ah            ",
			"dz AND ef -> eh         ",
			"cf LSHIFT 1 -> cz       ",
			"NOT cv -> cw            ",
			"1 AND cx -> cy          ",
			"de AND dk -> dm         ",
			"ck AND cl -> cn         ",
			"x RSHIFT 5 -> aa        ",
			"dv LSHIFT 1 -> ep       ",
			"he RSHIFT 2 -> hf       ",
			"NOT bw -> bx            ",
			"ck OR cl -> cm          ",
			"bp AND bq -> bs         ",
			"as OR bd -> be          ",
			"he AND hp -> hr         ",
			"ev AND ew -> ey         ",
			"1 AND lu -> lv          ",
			"kk RSHIFT 3 -> km       ",
			"b AND n -> p            ",
			"NOT kc -> kd            ",
			"lc LSHIFT 1 -> lw       ",
			"km OR kn -> ko          ",
			"id AND if -> ig         ",
			"ih AND ij -> ik         ",
			"jr AND js -> ju         ",
			"ci RSHIFT 5 -> cl       ",
			"hz RSHIFT 1 -> is       ",
			"1 AND ke -> kf          ",
			"NOT gs -> gt            ",
			"aw AND ay -> az         ",
			"x RSHIFT 2 -> y         ",
			"ab AND ad -> ae         ",
			"ff AND fh -> fi         ",
			"ci AND ct -> cv         ",
			"eq LSHIFT 1 -> fk       ",
			"gj RSHIFT 3 -> gl       ",
			"u LSHIFT 1 -> ao        ",
			"NOT bb -> bc            ",
			"NOT hj -> hk            ",
			"kw AND ky -> kz         ",
			"as AND bd -> bf         ",
			"dw OR dx -> dy          ",
			"br AND bt -> bu         ",
			"kk AND kv -> kx         ",
			"ep OR eo -> eq          ",
			"he RSHIFT 1 -> hx       ",
			"ki OR kj -> kk          ",
			"NOT ju -> jv            ",
			"ek AND em -> en         ",
			"kk RSHIFT 5 -> kn       ",
			"NOT eh -> ei            ",
			"hx OR hy -> hz          ",
			"ea OR eb -> ec          ",
			"s LSHIFT 15 -> w        ",
			"fo RSHIFT 1 -> gh       ",
			"kk OR kv -> kw          ",
			"bn RSHIFT 5 -> bq       ",
			"NOT ed -> ee            ",
			"1 AND ht -> hu          ",
			"cu AND cw -> cx         ",
			"b RSHIFT 5 -> f         ",
			"kl AND kr -> kt         ",
			"iq OR ip -> ir          ",
			"ci RSHIFT 2 -> cj       ",
			"cj OR cp -> cq          ",
			"o AND q -> r            ",
			"dd RSHIFT 5 -> dg       ",
			"b RSHIFT 2 -> d         ",
			"ks AND ku -> kv         ",
			"b RSHIFT 3 -> e         ",
			"d OR j -> k             ",
			"NOT p -> q              ",
			"NOT cr -> cs            ",
			"du OR dt -> dv          ",
			"kf LSHIFT 15 -> kj      ",
			"NOT ac -> ad            ",
			"fo RSHIFT 5 -> fr       ",
			"hz OR ik -> il          ",
			"jx AND jz -> ka         ",
			"gh OR gi -> gj          ",
			"kk RSHIFT 1 -> ld       ",
			"hz RSHIFT 5 -> ic       ",
			"as RSHIFT 2 -> at       ",
			"NOT jy -> jz            ",
			"1 AND am -> an          ",
			"ci OR ct -> cu          ",
			"hg AND hh -> hj         ",
			"jq OR jw -> jx          ",
			"v OR w -> x             ",
			"la LSHIFT 15 -> le      ",
			"dh AND dj -> dk         ",
			"dp AND dr -> ds         ",
			"jq AND jw -> jy         ",
			"au OR av -> aw          ",
			"NOT bf -> bg            ",
			"z OR aa -> ab           ",
			"ga AND gc -> gd         ",
			"hz AND ik -> im         ",
			"jt AND jv -> jw         ",
			"z AND aa -> ac          ",
			"jr OR js -> jt          ",
			"hb LSHIFT 1 -> hv       ",
			"hf OR hl -> hm          ",
			"ib OR ic -> id          ",
			"fq OR fr -> fs          ",
			"cq AND cs -> ct         ",
			"ia OR ig -> ih          ",
			"dd OR do -> dp          ",
			"d AND j -> l            ",
			"ib AND ic -> ie         ",
			"as RSHIFT 3 -> au       ",
			"be AND bg -> bh         ",
			"dd AND do -> dq         ",
			"NOT l -> m              ",
			"1 AND gd -> ge          ",
			"y AND ae -> ag          ",
			"fo AND fz -> gb         ",
			"NOT ie -> if            ",
			"e AND f -> h            ",
			"x RSHIFT 3 -> z         ",
			"y OR ae -> af           ",
			"hf AND hl -> hn         ",
			"NOT h -> i              ",
			"NOT hn -> ho            ",
			"he RSHIFT 5 -> hh       "
	};
}
