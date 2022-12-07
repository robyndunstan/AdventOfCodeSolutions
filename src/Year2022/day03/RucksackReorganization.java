package Year2022.day03;

import java.util.ArrayList;

import tools.RunPuzzle;
import tools.TestCase;

public class RucksackReorganization extends RunPuzzle {

	public RucksackReorganization(int dayNumber, String dayTitle, Object puzzleInput) {
		super(dayNumber, dayTitle, puzzleInput);
	}

	@Override
	public ArrayList<TestCase> createTestCases() {
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		tests.add(new TestCase<String[], Integer>(1, test1Input, 157));
		tests.add(new TestCase<String[], Integer>(2, test1Input, 70));
		return tests;
	}

	@Override
	public void printResult(Object result) {
		System.out.println(this.defaultResultIndent + (Integer)result);
	}

	@Override
	public Object doProcessing(int section, Object input) {
		String[] packs = (String[])input;
		
		if (section == 1) {
			int totalPriority = 0;
			for (String pack : packs) {
				pack = pack.trim();
				String comp1 = pack.substring(0, pack.length() / 2);
				String comp2 = pack.substring(pack.length() / 2);
				totalPriority += getPriority(findCommonItem(comp1, comp2));
			}
			return totalPriority;
		}
		else { // section 2
			int badgePriority = 0;
			int[] index = {0, 1, 2};
			do {
				String pack1 = packs[index[0]].trim();
				String pack2 = packs[index[1]].trim();
				String pack3 = packs[index[2]].trim();
				badgePriority += getPriority(findCommonItem(pack1, pack2, pack3));
				for (int i = 0; i < 3; i++) index[i] += 3;
			} while (index[0] < packs.length);
			return badgePriority;
		}
	}

	public static void main(String[] args) {
		RunPuzzle puzzle = new RucksackReorganization(3, "Rucksack Reorganization", puzzleInput);
		puzzle.run();
	}
	
	private int getPriority(char c) {
		if (Character.isLowerCase(c)) {
			return c - 'a' + 1;
		}
		else { // uppercase
			return c - 'A' + 27;
		}
	}
	
	private char findCommonItem(String comp1, String comp2) {
		for (char c : comp1.toCharArray()) {
			if (comp2.indexOf(c) > -1) {
				return c;
			}
		}
		return ' ';
	}
	
	private char findCommonItem(String s1, String s2, String s3) {
		for (char c : s1.toCharArray()) {
			if (s2.indexOf(c) > -1 && s3.indexOf(c) > -1) {
				return c;
			}
		}
		return ' ';
	}

	static String[] test1Input = {
			"vJrwpWtwJgWrhcsFMMfFFhFp          ",
			"jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL  ",
			"PmmdzqPrVvPwwTWBwg                ",
			"wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn    ",
			"ttgJtRGJQctTZtZT                  ",
			"CrZsJsPPZsGzwwsLwLmpwMDw          "
	};
	
	static String[] puzzleInput = {
			"LHLRlCCvCLVgHPfCHtVjBGrBDNzWFBsBGBfscGsD             ",
			"nQwbnwwpbrJBrNWB                                     ",
			"hmnSdSdQpTpdnlPdvddPNglLjH                           ",
			"RZhwpDsNqVmQClwl                                     ",
			"TLJfLTPqcvTrvvLMLMlVzzvVVQQtmQCmtzmV                 ",
			"MJjccdfTMcbqjNSRSZsSDZ                               ",
			"LLrNNqCTCwLTttwcNctqFGmRBSBjzjbSzbBbjNbzjB           ",
			"GnhhZQPDGdldgQmQSjpzjzQssb                           ",
			"gDJZPMnPnhlhJWhZntLCLcTqVMLrGVtMfM                   ",
			"rrBgDBGnVnffDnfQQqngJhhSRQvhhCRRRSZbRpRzwQ           ",
			"NtLmcHPHMHHssFJphZpbhwpNRbbC                         ",
			"LJPHlmdJDgrrqrnl                                     ",
			"nJhrcNnfrFwNhPdMQSgZSCMjQn                           ",
			"LjqGWsGWllRRlHVsqGGWsZZSSHQgPmHZZSPvdPCmvQ           ",
			"zqqVTWjqBsTJprNbppFb                                 ",
			"zSMgWzlgFSWFcGZlCZGlrrTc                             ",
			"spnQHdQmHddNmpWrpWcChccTWc                           ",
			"BsRsnmBQdNWsvRPzbzbLzDVSPSbVLM                       ",
			"lDfbffptlrJZTBJHjjBWjT                               ",
			"LcwwgQLgzvztwtMQGCMVCHWmnmjWnGhFHnZjmZhjhT           ",
			"sCqtzsswCgccbSqrDSqbNNfN                             ",
			"snnnjwRRwGSSnVmhhVMhGFbgQgbzFFPPgQQmPbbgQd           ",
			"qCrccvcDDcvqDZlCcrcfQNQFdsbgWzFfQddQQPgQ             ",
			"ZcrvrBqBTCZnBBswjwpSRs                               ",
			"qSczBfBcjMZMfctsmsGmFJsmQQcQCr                       ",
			"wPhTLNVNGLNdGHPHwlQsnrnmnrQvHFFHQn                   ",
			"dLdwbNLRdgGbgTjZfDbqDWjftSzW                         ",
			"rZwlrtRtNtlHqVBtdqQgdq                               ",
			"fbwzpPwbhJzpwfTSHgdgqcJVcBjHvHdJ                     ",
			"LLPbhzPpTTbTshfGhPwSFWnNmMrrZZmNmZDmWNCCZs           ",
			"tMHgMWMQWgFJTHsWMvJrVdlmvlSvdvlpvG                   ",
			"RNfZZfRttBtdlZlmmmplSS                               ",
			"DzzNDDRwnwwbLnMFtsMntQFM                             ",
			"qHqBMNqgMwHMbnGStHSbndnt                             ",
			"PwWZPjpfsDsDsPfPfjdbSvbWhdFSbFGSWFtr                 ",
			"wfpjpJcfVsspzZRRszDpwcRggLTQQBLqNqcqcggLCgNmlq       ",
			"TmmFjtvFdDGjdFFJjFRDLNLHGBVcqgLcLgVBLqgV             ",
			"WbWSPSwQCWrWQSrCbwNVlLlBZLBvLlvZVqPl                 ",
			"SfwbhrwQQbbMwCwWCrbwJRvptJfjDTDRvzTttRjp             ",
			"jzqSMszqsbVVMVMgWhWCgMrpGgpB                         ",
			"wrwLcFQmPlFFlwLZmFGTfPvWGfPvhWWWvGgf                 ",
			"wRZtHFHmzNDHSqrs                                     ",
			"NprlCgrrnrNCjplSCtljpFrFZLzzgwmVgBzBZZPwzBPQBwVL     ",
			"HsDMvHTDfsfQZfZzmPWL                                 ",
			"JcPsDGTqcTqSdrSCtnCt                                 ",
			"cFcmfmJFtFmtlTNtLlCWTT                               ",
			"QPQzjRRsVsQqBqwlTlNBpLNSWDpN                         ",
			"QblgzRPgbgQsVvgPVQhgQqjvMnJfccnZddcGGfrFJMFGGF       ",
			"PWbWmFFnPFPWbDVVmmDHDFGdGhTQdLdnTZQZZcGSGGdQ         ",
			"ClzjNBlBJvlsBdcPLZdLPQjLQZ                           ",
			"vJMBpBzzzfNCCzCffJlzgMWDWwPDtVtmVHPMwVHD             ",
			"bJjWzWFlTMjjSNBrRcBrZR                               ",
			"mwnwqPwnGQPCqmJmPQJPCVNcRZBRRrrNrmrcVpSrRf           ",
			"PGvQQGPqvhWFWlJbDv                                   ",
			"PNPrdmPGRJlZCrCJlGQzjRFLpFRppjgppgcj                 ",
			"DwfVnssbVnSWShDwsnnhBLFjFgjFBzDBjHDLpHDj             ",
			"vSsMgbsTfTwwfMffnTvgNNCmrPJtCNrJrCrrtvGm             ",
			"cRnRplCzccVcrwcnppVVzRCNhfhgChNJfPgHJdHDNtNCtP       ",
			"WFmbLMZdLBqfJNbPTfttDD                               ",
			"BdMWdQsGsSsrpzrswr                                   ",
			"llhhZzSLqlzwRrffzwzT                                 ",
			"GvBbNjHbjjTGGHHFcsFvfRrtJQPvtRfwwfrPJD               ",
			"HGbcbTTjHFNpppmLnSdplWqZ                             ",
			"FhwFbPwsvtRgVCgvMT                                   ",
			"HJVHdHBWdBQSSSQnqSQLqZHtcCctppgBtRrgtMCgTprRMM       ",
			"QNzZLVSLLLDGPPzPmbFs                                 ",
			"VdTHmWCVZDTPBBWBQBFQQg                               ",
			"MzjMjzCjJsbJhhPz                                     ",
			"crfGGLwwLGrtrvCtdTmdDH                               ",
			"wRLvLmGQLwFPBRmnLCLmGQTzNNqVNZMMVzzQbzVbNZpMVb       ",
			"jsgJWjdHghsglHtWsjSfHzVNqzfpCCzqDpzDrVVrrD           ",
			"jJtWWsWhtSHsSSgchthHcjHCvcFCRvwvvTTFBGvBmmGLnL       ",
			"LpjWLNqWpwRWMqLRGjwJlStgbtrVgHFrGllDDrVH             ",
			"SQmmTcZZvSZBTmTSzhPTddbVDhHllgFCDHtrDHgDVVDr         ",
			"PzTTvznBncnfTmTTQcPdmzzMqWNfqwRLpWJsNfwLJjsSwJ       ",
			"lsGdGwBsflGrfsHvHwQLdFrmPhDhCFhhjWCVmhDzmbmPhC       ",
			"qtMSNNZZMpcnVzmVbCqjWjzB                             ",
			"ZZcgRJpBtTMNnntncwgQQdfGHHHlsQffLH                   ",
			"jBBtjjqfnwStBSrVVFwSVVvvWzHmcWvWbvPmPbWrbMRM         ",
			"GTdNDlpJhlCvPbHgcDmgDH                               ",
			"JZQdQhNldLdTpGdJGdNCpLZdSBnnBFfHHswqqjffZsqsFjns     ",
			"phJhDPQLDSJvpHhvDJhfrFQVRrnsslrgwrVrrRjg             ",
			"mWNWqZWWZBMdCGMNCdWmWCNCsnVFTRsVnZFlsrlFwFgVsgjr     ",
			"dWdjttGmNCBchJfhHvhPtvJt                             ",
			"CSFSFdfCzJhtSCHQFjQHQWFHRNHG                         ",
			"wnbrgZnwZgDLsLbwsLrsrNWQNjPZHHvPPQHNqHHvqB           ",
			"LbmTLDsgggQmzmCCppdtSJtM                             ",
			"SzSSchCdZgHbwHSZ                                     ",
			"GsMMmslnsfmNGNNNVVtZWdwbqQbpgWjjgWZjQm               ",
			"DvlMtflGGVGthhzdvLvhrTcc                             ",
			"dvfVNqHlQfGRcjDczlCDnC                               ",
			"PsPsStLprtTTFSTLmhSVSFSsCzRRjzDnMJDCMWWDjMnMnpjW     ",
			"hBFPhSBFttBhStLwmsPTtPsFHZNGfQgdgdZbdqZwdgNVvwvH     ",
			"rhjcChdgjdCrjLjLLSLmLFMmFtNnnbQNNNPMbbmtQF           ",
			"lwWRZDlsWzrbbQpN                                     ",
			"rTRqsqDRRRsDRVrqDgBCdCVShjCBHchjdh                   ",
			"PsspltlPsmTsmbmfTPSTTCGjhJJjCnpqJJNhhJwNJh           ",
			"BdrLVvgQLQVLHRZGnqRhRNdwNC                           ",
			"WrFVHDgDQFHVLVVDFQMLltmPsssPztwsPTzsWcmc             ",
			"CBvvSzFGSGGWfFZpcHqjvjcvcqccJq                       ",
			"bRQwgbbhrRhdwmQbWtdjnJHqVJccJqgHVJHJcl               ",
			"RRmbPrNRRPLLtmbQmbNwddCTSTzBSDBDSMFTBSSPZFFW         ",
			"cppsSgNrSgwrGRdHRrwd                                 ",
			"LzqqmCLCLWQvCzmzZwHnZZHSwvwnlDlS                     ",
			"hzFqFLLLFtSNVsFF                                     ",
			"zpZcZZZdppzDLWDtJGgfGbTGGJTGcc                       ",
			"qhvNSClCShRrRBBWTQfgBFbgtfgg                         ",
			"HlHqvjqmCvCvlSSHvVdsVDzjpVjMWdwLpP                   ",
			"qLdsfNsTHQwnSNSBNS                                   ",
			"gFhWzrhfbmlpmZhJWrFSvRMnwwvvpBpBSpQBMv               ",
			"rgWZrbmlmbzFfglgWzGggFJLccVPqLPqtPLGcPsPHcPLTd       ",
			"jTTWRCCbwJJNTHrffqNnzh                               ",
			"DZVmDpgGBVdcMZnqfhlNHQMlNNhl                         ",
			"sDcpsDZBcmgdssZcnmSWCPWRSRwJWwvFLvRwWj               ",
			"hWwhgQlQQgjPhFChZVdbcJ                               ",
			"zHsrMPNMtDDTmbcJbccmDb                               ",
			"znzPzrtHtHtMzqHHrsSSLwljqfgwGggjlQjQQgQBGj           ",
			"JpnRtqlJsqDJJBBNNmQmgdmRNGGmvv                       ",
			"hTCbTwMCwVhTWdmDgDvjWD                               ",
			"MDhhMSwZCbbLVhbLcDSwCwZtZznlzlnqBPBpHPPlBlHJ         ",
			"CtvnvqNNDchrhFVpwftmgQgpQfwS                         ",
			"MbdqPWGjBjMBbwlfVgdmfSfJJS                           ",
			"jWzbzjWWjWMMbRbMsjzBhChrNHcNqHcrNnhssnsc             ",
			"LlLJSWgWllSShRmRlBLJSVBzpTHzTTJcpTHzpTTcPpGpTr       ",
			"MfMqnvbvDfbFFZDfFNjsGrRppHpZGGcGrcPprz               ",
			"wfNFvtwMvbnntftjfNtVmlgmStBmlBWdQQRg                 ",
			"GpFRRPGWqzHwdqpzqbjjgfZptBBVMSjSfBZc                 ",
			"TClllrnsJvDMBgcjfmtssB                               ",
			"NlhNNchrNJlLvClNDrzbGRqwqqqwqPPFLGdd                 ",
			"qFmVtvmmVvzzFtzzGzzMNNMSSTjNJlStjSfNgf               ",
			"sWrPBCnCTMsTJfSM                                     ",
			"QTLbpnRpRppnRQdRzRZqGzFFVVRz                         ",
			"WGGPjFvMVNjlcQJr                                     ",
			"bslfldbgtpSmwmSNHQhLJhcwLcQrQV                       ",
			"gTltCsSsssPFnDzWPTMz                                 ",
			"hhRRhQgGrHjhRsrgqznbzncZjVVJVjncjd                   ",
			"DSFfNTBFSDmMSTDlFbBBdccCdJJZCbZCbW                   ",
			"DDLmdSmTvQQgsgvGHH                                   ",
			"dNqNgNvFnvdZHFWnZWNBTQlPTppPGlCTpBQppq               ",
			"LJrtLrsLjsGvTCTpQP                                   ",
			"mtLhjVjMhhmVMvtJmLfhFWHnngbRRdZHngnZWZ               ",
			"NzdVNzqqCtCHMMZBCGBW                                 ",
			"psjllRjFpjpbjspFmWmWnLBmMMQMmHbm                     ",
			"DhsTPDRTDHpsvRjdNtzJJJdhqwcdqc                       ",
			"VbhRbZgRHMFhQpHd                                     ",
			"fvlqPzmzJJqJSPsWmPTNddNFHbNFGHNTHSbc                 ",
			"CqzlfqrCnbrBZjBr                                     ",
			"SNSrDZFHnTqFsFddsCmsMC                               ",
			"ctVthlGjfhGljcCJmcqMCqcqBB                           ",
			"VtjvtjhhPPtWqVPLjvqjLVNRppRTvNSnbnZRZTHRnpTD         ",
			"fzsBSsNBMNMszNGGJvgjjPggzjdFPgpJ                     ",
			"bmrVVVrmRrrvRmwvqlbHTDgwdLFjQPJPFddwJPFggj           ",
			"HHHrZqhqbTMcZBCZfvcN                                 ",
			"tzsJsnsmBzlVqjssZZrg                                 ",
			"MQZHfNCffpMfpGSpPvpfCGCTTVwFTlwrggjSgqFjVjTwwg       ",
			"NfMGGGPZpvLDvLCGGfQHMpZRDRWchRRtBBzmJnmzmnBznb       ",
			"BSRBjtNjZrsjRjjNsVBjbrMwCgGCCwCdHrlcdccGcH           ",
			"DJTTJLpFnFLdJJqPLTWqLTpwHzCGccCzvvcHwMvWzggCMC       ",
			"PmpTTdndmmLqfLTTLDqJVBssbbStVmjjSsZNsBZm             ",
			"ddCnZvCDSgghFhbbmFVQ                                 ",
			"JzlMcJTMMPPfJJfsMsjWlHVhLbQVlFWmHbbb                 ",
			"BPwwsPfsqszfFqppwTsqzpntDSnCBnDRZrSZdnDdtvZD         ",
			"SllzzPplWldwLGlzbtPZZjVScnnNSjnNsqNqsc               ",
			"BrCfFDJFDHBhJCChQFhCCBDcTnNVpZZcNcvQTcjvvcTcZV       ",
			"RCmFHJDJhCmBmRCgDCFRpmGbWWbLPlbMWzzGttzgLMbt         ",
			"vGTfsZnfvfzTjsnfzTJlwqQjwmCqqMFjFFQMlq               ",
			"LHtHRVLRLNtWcmVbRrPbRcwgQwgwMwClwClwrgFpwqpw         ",
			"VDPtbVLBmLbLbDDNnnnzJJfJfBfvSGBn                     ",
			"lpPCRVVQppzHlZgzglgF                                 ",
			"rtfttLdLdscmGtzngPHHFHFH                             ",
			"LPLLhfhbTDLmPdcrcWdTcDSjjRqwwbqpRwNBNpBwjQwR         ",
			"dWQfCJrwvQCfFqNwRbbzVbVVLGTR                         ",
			"ZpZshPMzBjGjtVMN                                     ",
			"phpSlSlDlcZpcZPrdHCFzFzFWFDWQH                       ",
			"tfMMZhjLlChsdsds                                     ",
			"PHQRMHRwpRPBMvWvPRBpPdWDslGrbscTlTGcCsGddG           ",
			"FqPSvHPHPBzQRBBwwRJfVtgjzntMntjJLMtJ                 ",
			"VBwJvwVwNbVRdPwMgWggGMgH                             ",
			"jDhqflDDhrqshNhdgPGHphLg                             ",
			"FltsrtcFrclrNqDfqmzQJQQRBzBCvCFvBR                   ",
			"RZsSSJDJZLDWnGDMLD                                   ",
			"ClbnlfmpNtmgbtmMqWdjNGjLQjLqLj                       ",
			"gcblTlVCnVmcPrvRPFRrZs                               ",
			"mbJcScmbDWLmSBzwjPRTfjmmRhpl                         ",
			"tFFFtGttdClHVMCHFMMwTpwNjGpzPpNRRzzpPN               ",
			"gCCdvZCVHsFvJnnDSglSglDn                             ",
			"dSndnRRvVSpLSphfqvTgWqrzqvvw                         ",
			"PBFQbQbDhGfjTTFzqG                                   ",
			"tPmCJMtDDNcMVdhhVc                                   ",
			"QVRVHCQRmdTRqrZFCWrLZNZFbb                           ",
			"ncncsPnhslBRSSSbFhtbZDLMbLbtLb                       ",
			"lvflPcfPSsPzlJlPlcPBfHJQwRwHmqdVpRGGGdQmww           ",
			"GTCGMCcGdgRnnbbbMLwmMz                               ",
			"DQFZzllWDDLwDJLnJpnp                                 ",
			"qBVrNNlZfFNlWlWqfRzhgvhCqHRdCGGSvc                   ",
			"ZnMnGbLZfJcBcLTgWF                                   ",
			"dHJjdzqssHHNJwCHpHtDccvtBTtvccWWrrTWSg               ",
			"CmlqCNzCzHlmdsqzNzRhMhZRbZMPmRfRJQPb                 ",
			"LsLLrFLcFjrtmZhhmhHGhJGGhH                           ",
			"ffvbsbWpSBSSCCQbsSBSwwJHHvhZHHGdGVGlMlTVdZlT         ",
			"pSzWNPSfCwWNPBfsFqtFLtsqRzqFgj                       ",
			"hwwpvjVppGpwWGLrcPjrbrrdbjdL                         ",
			"FBqFFMFHHsHssNHtslqtFmldnnLrPhMnccrnMzZnbLgPLz       ",
			"mJBSstlJQpwGSVhC                                     ",
			"cgJDVWsrWggpcHhMzwwPnQMWMm                           ",
			"SZGBjdBqBBjGjjqNGfGNNHPnRSQFzhnwmnQzQnPHMR           ",
			"bjjCZddjZbZBCtLhCZhftrgJglcTlJgvTllJvDVDLg           ",
			"QpRJpCFdpqTQcqSTBBGBZVjZjVjFvwVB                     ",
			"nnnWfnHhPDlDnlLwGjBBbVVZGbCGbP                       ",
			"LhhLMLMrMWMrCprTqpJQpz                               ",
			"dqGGZJdZbTTMFFTGJFFbMdnCHSdWcmNmcCdWSggfSW           ",
			"QsjjtrzLrQwDPjrQLrCfSSnmCmHWlCgmzlNl                 ",
			"PQpQPjQPsBstLBPttDrjBwTZMFMZFvJFJhMhnMJqpTJJ         ",
			"JMLrSvHJdJvvJfrHMJRfWzWDFPwCcWqRRRcq                 ",
			"ljZsZTmmtTBlpTlTjQZCtNFPVqDRwWwWWVPcDRVpFRDz         ",
			"mBgQgNNTNvrvJSvgCb                                   ",
			"DWbWtzWDfDffbsMbZMffDDLncnnCJmLVsJJJnhgcngLs         ",
			"TjgNGSBRTRTQrFRjFGBVLwLnnNncCLLCCcmhPC               ",
			"GgGjvgddvvWqqfdZftWH                                 ",
			"zMmsQlMfQQMhjsmjfsmHlhncRRZnRRRJRvZWWnhccdRC         ",
			"BptFtDSSrTrpgtgqqgtZtvVVdVvccVnJdVnG                 ",
			"qpgPqBLDNTgqBrSLpDBMJfjzmbJMHjLMfjslzH               ",
			"DPgLgPhfNDRqhDFDsBTtrrrdbbztCbtf                     ",
			"MjGSScGVGSlJjbbrtTvdzsTq                             ",
			"JJwJGWMZwMlWnFFgqNQFpF                               ",
			"WRGDHmGqWHlrmtVVVRVqpNZvZvvvTNPMPjbPdM               ",
			"BwhBwsnzwhzSfCfswFvpvTzTdpMpjvPMZNTb                 ",
			"LFFQgnbfChSFBhFnftRRLrttDmmRJHtlGH                   ",
			"MhqhRHmDdRlRlGnfZbJVsNNZDnNb                         ",
			"QwvzgtwvFpmjwzLjFLJZrsZbPfPZbsVpfPsb                 ",
			"gvjTzBLztLTjwFjtgLTgtzwdWRqdqRTMSWcWTmWlqdhHHm       ",
			"ZfzzfmhdpNLNBDDsFfQVCDggfV                           ",
			"HPFjljSnHrqVDgtgQgQMqC                               ",
			"rGnSJHvjSwGzwFhGZG                                   ",
			"HqmHRDprrNTZTMbh                                     ",
			"CJvzQRQVQCgNzZbzgMNd                                 ",
			"vPCvFPcfQFlSJBcfRcPHmDGqWGDqpGtjjtGGHl               ",
			"wcfpJVHfJBffBBGWRprNRWWWNdhv                         ",
			"DzMzMPqjDnjgCMZPZjzjCjChGdvvbhNdSvrhNWSNqWRRdS       ",
			"jjCTtnMTDsMBtLRQwwQlFV                               ",
			"JqGnVqCTpDVCTnNLgmPzdgjcGmRg                         ",
			"HrSBJSHblsJthsBBSBhMsrzmdNRccjLzgcLmjgPPjlLL         ",
			"HSwSttbswWJrbrSWppvVqvvnQVppQQ                       ",
			"JDCHssRTTwcRJDcnCDzRHsHNPZGBtPzFPSPttZSZGBqPBZ       ",
			"vWhLmTlfrhFqGWSNQNqF                                 ",
			"vpMhhpfvmTfhvbLhhgvmgvvlCCJCCMnDnDCnsjRMVDMDswCC     ",
			"ZgjdlmlmmlJgHJlbZrSDrnrMrmLLDFprmp                   ",
			"TvqdTtdctvvDrGGSDn                                   ",
			"CWPBhtWqPPwcdVwlNJfVVNNbbb                           ",
			"vgmrrwlPPrwPBPtmvFcMMrsMSJHscJcMSHDH                 ",
			"TWdLnZjCLGLMQLHBLS                                   ",
			"jqVTTZqjdTVjNFNPqBvgvBNl                             ",
			"jmcgMzsmjmfvJwFpFfRWZRWp                             ",
			"drdSldTmCmTDCNCtbRRWqRwtttFZpZWZqw                   ",
			"NLCVLNLTbbTrQNQDvnzschgghnmnHQcH                     ",
			"sRVhVQDVDQRRMQhsqtRRNzqzbNzRqNGp                     ",
			"WdjCLHLjdFnCCnjnFnLHHmPmNJbztJJpprBpbGzbpJbqGWtB     ",
			"CCFFCnFjdnjCTHmCLTLLCnFnQVhQQVDMhQQVgZZNVVDDsTVh     ",
			"cGLzZgfzcNNzzRZvjvRmVDmmqCCDSdVVChVnDf               ",
			"QstsPlWHQlWhMMtpsbtpQtlpDqBVqPSCSVnTTqmVdDVDVdBV     ",
			"stptQFJrHptlQsFJMHtHhFbvNwvjLGvvNgzgjcwczzJcNJ       ",
			"SQHCrCFPJZcnWrqn                                     ",
			"vfJvJjfGGDggqZGcWD                                   ",
			"pjLpRwzhRFtHdMQJ                                     ",
			"HNSHNDvnvdffDNfqdZfdStcFFMGmmrRBcmFcrMrqrWFB         ",
			"VblwzwhwPLlCGGzgzhmFjbjFrMFrjFBmbcrM                 ",
			"hlVPTCCQCCzVlPzhGgPVJCpHtdtSpQSZNNfnZdSSdnSD         ",
			"cBVmfwqwmWggTRmQzTQl                                 ",
			"CDnnHjSDPLCSCLHLHCHNCDFgwJljFQRRhlglbzJFQQhl         ",
			"GptHLtNGHtCHSnCMtGWdcsqqWwMcqvdsVwfd                 ",
			"HsMFNRNWnbWfZLzWzQ                                   ",
			"PqrpjqNdjjhPcdbpvmfzfbffbzmv                         ",
			"GjhhcPjPccVrqcPCldCjssHNnTnNttRwwVMTHMnV             ",
			"jjCcBswcfnwgpPFPwFFGSFSwFb                           ",
			"VvmVhVvRRQqttRtQDLzLhqRlrBFMWrGPBSMSZSMFqBWZZP       ",
			"mvQmLRvJtBJVnTcJCjsjsdgp                             ",
			"qgqvPbdMDMMPwpbLFGwtNlNF                             ",
			"TTQmdJTnSllFGtJNtw                                   ",
			"CSSHmQHfmVcHjQmSvPBdBDDWMVhMhRMB                     ",
			"WCvQNZdhCDnnPfQPfTzjHcppsHjpsSjHNS                   ",
			"mFMgMBlMmBqHjjBfTjHzBc                               ",
			"grJbrqfqMVFJrlJrtCtvhPPQdnvnnvnwdW                   ",
			"FdQQJRdfSSfrJsRZfFsRSvtDBmDHGtGqbgvnmbDnvDGq         ",
			"lcMzjCPTVtMqgWWGqn                                   ",
			"VpLjcVPczhzznPLcPhTwFQFRZNfFNrNZFpZNsZJR             ",
			"VWgJhmdDdJDdVPggPSTSTWvvfRzfFfFbNb                   ",
			"jCQtnpGQrHMctnpzRbFfgSwHvgwfwv                       ",
			"ntcMcqLMcQccQLgjBPLdhZDVlJPVdDLJ                     ",
			"RnPnwtqHnJthjLMcWWncMn                               ",
			"msdCrCdNpBBsCrlNTpNBDNGzcLchQjFQzccQLQpzLzWtFS       ",
			"sTbdTBNrTCTTBCNBlVbwgVPJHtPgPvqgff                   ",
			"QmBsmpmcZQNqPqVnPFVpGh                               ",
			"gDDMDLMJgHfJwJMzfTfdGLhChPtvnGRPRRLFGPGv             ",
			"DTlzgwfDrrrMWlncbscNnlSW                             ",
			"tBwvGHFttrFrvRgRhCmCmwQmMg                           ",
			"JWbNJZjzfbVjWjBhqfmSnhqCqgnQ                         ",
			"ZZJJJbclzJcsTPdvTTPTBFtHDF                           ",
			"LszmFTFpTmszLrpqSmFpzcvQjtQjvLJgJtcBjgtJjj           ",
			"VHNwwNCVCChddfwHlWdnlnGRQPcQjRvMWBJJtMMWcvPJMM       ",
			"nGHNVHhnfnHDNhCfdhNNlwHvmpDrZDmpzmbZSZFsmmbqrrsz     "
	};
}
