package Year2024.day09;

import java.io.IOException;
import java.util.ArrayList;
import tools.RunPuzzle;
import tools.TestCase;

public class DiskFragmenter extends tools.RunPuzzle {
    private ArrayList<Long> blocks;

    public DiskFragmenter(int dayNumber, String dayTitle, Object puzzleInput) {
        super(dayNumber, dayTitle, puzzleInput);
        debug = false;
    }

    public static void main(String[] args) throws IOException {
        RunPuzzle p = new DiskFragmenter(9, "Disk Fragmenter", puzzleInput);
        //p.setLogFile("src\\Year2024\\day09\\data\\log.txt");
        p.run();
    }

    @Override
    public ArrayList<TestCase> createTestCases() {
        ArrayList<TestCase> tests = new ArrayList<>();
        tests.add(new TestCase<>(1, test1input, 1928l));
        tests.add(new TestCase<>(1, test2input, 60l));
        return tests;
    }

    @Override
    public void printResult(Object result) {
        log(defaultOutputIndent + (Long)result);
    }

    @Override
    public Object doProcessing(int section, Object input) {
        String diskMap = (String)input;
        blocks = new ArrayList<>();

        long inputFileId = 0l;
        boolean isFile = true;
        for (char c : diskMap.toCharArray()) {
            int size = Integer.parseInt("" + c);
            if (isFile) {
                for (int i = 0; i < size; i++) {
                    blocks.add(inputFileId);
                }
                isFile = false;
                inputFileId++;
            }
            else {
                for (int i = 0; i < size; i++) {
                    blocks.add(null);
                }
                isFile = true;
            }
        }

        if (section == 1) {
            int firstNull = blocks.indexOf(null);
            int lastFile = getLastFileBlock();
            while (lastFile > firstNull) {
                blocks.set(firstNull, blocks.get(lastFile));
                blocks.set(lastFile, null);
                firstNull = blocks.indexOf(null);
                lastFile = getLastFileBlock();
            }
            long checksum = 0;
            for (int i = 0; i < blocks.size(); i++) {
                if (blocks.get(i) == null) break;
                else {
                    checksum += i * blocks.get(i);
                }
            }
            return checksum;
        }
        else {
            return null;
        }
    }

    private int getLastFileBlock() {
        if (blocks == null || blocks.size() == 0) {
            return -1;
        }
        else {
            for (int i = blocks.size() - 1; i >= 0; i--) {
                if (blocks.get(i) != null) return i;
            }
            return -1;
        }
    }

    private static String test1input = "2333133121414131402";
    private static String test2input = "12345";
    private static String puzzleInput = "3549353826175593727639605576895360991849916040149461172019832754436447167429607846413517124723711398711477116857405326426432695894723016644061706477265293247616602076146680381449134564509131353067799791969880182245861616768938865025911498597759297182423966111211101318459835377385352325121896147385809313935997753569284564477262723028493154482856735959738582272363346870396874308431284958933067678511257923473928548616202173259572372029346667401034117477394641673944916325269017794579145349773525846452972452719771915025447654728246615415139327536425728386323652554419349219279360499383534429434311809952789529442234939155168070919855599068433949742292971761417053545035939415695252323313926238855561597239757156304073683910756799392665464668114626261384401875207588167033307178778620576559679248429078574944516849107384448068737922694026253419591384943594169070671627223620515261307662697371373124501212638323925840513446454434662522378316108537418353199117354686163446405371485697205481654384991022555390933496237855325394912236537587323088317417915114588678739939775079854470496257416480122466488694397157507861825723608865416335758765177997299961385487422879972039393781343728406597567494501873371478222390558467315188409590859526301432252690815143125031892118322515981775833188384261748577512726395981458028888573987575513692827260425373914997731169413074598820242980966218579752157726558075237071891684666691901496379718401451596411815652778586846480822871254154366499339536791222255836432327488356844844287997604971594650816411912066501829481416703040508066652426926599262984842373127311298485855674485649443539318789327939225126659773477532878057826539419140423464104624758651114667814082603154134852778353388319979159487014435056111286943213528236276539683582828651491470299999707649545663363357842956689061119195799052358145166912524099603040261542472831928444177951291753485263827490399271244015312073885782601624391485229114521235725620806998918567438342254658952367925623213954834527536342106596172242581722486836195424595742143746325169738930287021364953922536993930908340766145637068101867886186402147402672765799815892553270747374423460771733588041406097515077391841608283423857572673145717909284448432361693131931143354584994209460224131925245314620716182182227725355535995665571268369359941466992395796974185581180863857413390753993234662774622708335873033259619468347953716219231769477107564603321693880559138202348837448103359253971831961801639522241226719949183762929907124692557774343619457388714601566245658279942795379718594203183536371379540414966805151984377617649964927107219917921759063507729297114206355782776698272334859952117104836542757663022244438782926691257879465249871962877596877193051191944868491951865249296535261208613952123737252975987818841811976128615758566435732267129236096492767582374744694127477609110578237953918335420559587567299409716941753614839703693837534685242424066717353625147433548616480586095462844739062485469688865441572763342134357191552969080183637157668387263313934733731298035521788211649739468929037288379668480203187558030535424621546988978514753122772783077687061656370533776666334857169448930189919291416227232448329834994968933993052919245888344419327519155464851184926198085309617896185286965713297598325222449563555496068628045383938668386651073646978686163309370482585946884619427118067162066542518888043627183937988218258201461822135684753492284627731496697526214609124509431521589562550995419221863743525783691622895201572687347548371888075656734258687598364997041273343503454559185833767277479815090662818488810673854949959888695521766303934913752538935716065733727893398334910396786934798442638174270987925175060387852312638271558261896231018365055165127135057391165399846473944307647991661716279513333589479285994669255104795447282715634186257523798859265956584295037526066132582118190945626632138216592305255866444321494869362567089735812294180572648778412385914975561891233403595173675389272305759786247175353712415292448521258856255376287533717507188328955292138542161404279725616954275259563148351531868627587146884436399365214671563289620387755496821369187654980103262338873277457817610927567963038809523706627721118839933715175205845206414735363659815595671844761941366108538517174151539858915443626417326601147545976229818914468359569379717566487914479433250839167143798723585684037946295522480964870479638605113786599135264414633573231214946919623744856697616892378406685831169301752723399302745626286486039451080112368483875999181618811348589467522959853418053934888234127301832118472265889307623152278315287154341165025599814448338707259801693224377409141542385724796887182886284173933328887493353991092382331597555877014551939881858609244618817463075403777867939792878873495385516282210565621402652391889287094478559619975933779856739997663196154875168528058271551191010964515886898754054475193901938208370813558141454406139246073266123761059874878879928282784183365859245255640784875895175699715997491299340782355142488261744292136349216838427964651276845535390744310838477352442388976823515655814463439599840848450578977744590277216359852897273543422147472832832747712675577322257462688889236761541376699777632295477758736732092468038661592589012518875129316903763689332397621543448649731785088336811126921662615402223784038527524907058802876927140941977853988718725532994809357973738772615436345854774254645279994384092118029726674135884496568663859437182404181711437835819444356285385568674798919843295382051958475256934635151601738861184647544865064735942373571249442746575879883801243343226713371472550276877443161263087142175317510125280605121127786511057481458547553829331596767958084284011176239505697479788903783384424209619269212398281816890125549263731291636221660273764709053702016798387392019975454178559566425629920631942967073754983558785396713884769331828339689527934454483362151861381214954462864334035185789981080809039125336397823337728784786619115608685674867992055106116673256164018792710767096695885648397896851243048581572714258854115107325713449778358582767839669648480663037533078562135454064237757825762535717206075486897754867174763403514959374222556691385884798348278868240543673954613718221123260222141546499735418995341503197708941631637846946482561365071359924444847755513697295672150335491614492501587723061423372301681841480815623342652692937148521856660429895549378441921399279552439148472197162679341909077698769581383401792245421834262562816804519447825999641932690957054536081215565902049366624554691618148827721193177302973341095214031166113719032787533141161939129538672912397873098497768567460736447996565306218367376922441403383933522195511295532362212992857812557762777636848513424571713473081371515788527112436332428715122144326262773208225878889947265657574473665447597662733604998415215887615295598449559884614798628342190925952879857911149901446968273879886587870765740336079787390917194104250407198256838665360305972186430103733504969232639613566137934961888406273466290198215425374352863836566168685269529949388683734776568852675216051765537445933458489271779889943975393105558451835939325167991878071375778216922182534295655635912603353265744232567184554633717622719941713317349529969924770259618401428237249966083588542284283388923476471844655862641624576701453417952431770619328186789433499645478738524532348104727299628966424393022835582193244217057823870565647214964857918365085101983746954743761483346882879155823119521804984299133842828679242181130752350521560947946255875828577797446165123168435681415952135238342657135329053505832503729243860449964235410216391552882817141402843953770166536186753393299757413351426691396709456263522798181845244971087572161253554595653249876167840659092776073658126106012528580225347702852573236697860266784164664274362205339278434229661504120274557154821967764695423869893603117264285974588619637383048635878338039764771206944861926762537332570988374993848933972433624682074788328256672166393864116439743639284321078913796826176265038146890574094857468132761526732851616786441747094241641626746853110528618723535549269581436867940781455763676911419777670504443651261829461513549992810814341604314146425483316991173479517111041588196259253864011406311553478868145423013669352125175469017241087279838902253929924818596104830578479994749834842349058484638486658384551843682944176534319614412584147593873386635787234261637668246325426328317856044983081326557972330742277162669447010576214996386165681582842436249964675795388528570436025389886411244314096748926725931826765945322207316792360427541104074333121781625389427259727217318733524425247666454251821681923844739128186433211948854554821105318777096841153974956723367153693949787589686285645902599518784639040261112331440734324448057915569455785824926154766262896496367506068919756753846614857141039971739239634604736526694105775121496329192306180621544933016177271164831829331221332811327354884956127781383381244292284283645611338553389949243308716188439142629692592207484453912968291985447143022392820291841664063512435184612577765239298242180716196429688956311111258705832225768708453552797855535243424386587614573261769481745779542148759459063617973738843533673392710938710906994795637589339291077494583276152854628671648152946284454312039666191355051731356477927538666868179271456862455346173357914362245515627773270752592903441486750875729873114275596883498189499692835104162547936201261186043665935819679843194543689927651282645864214879975531542536948608395601315764443255132709160586489283289296780698497519924664068239822669335798681841098282848153284249137794075804725295264444093509639128332672695184396761915191796543517224992619478752327165886656561658473503396878114733470439085414484249974321847614566462051407992143676674428849289765087957163563298101199873836817766474944716734553945934059582679274293681584213110407925292930894327283562303171782385478224285048984877616487592798175018576855966298943340179650286213613831924739512566685269496186239242254852749219768232957510722135343622522266933449681351207445172481548289658051108613607710927448548739911179413243103235187636943838207886235845301059341089853940336633608668267957901848254272756450162965599236601536516499332384653481167585435046425826927928942744305552172647172539742191338891246818412946486096485986623216983316874282548648501693471145481312832264425937186532487421628816866460692746515319691775208181301237722779145980666831417513177250334030432566784893177016842196506668767557251918192530848581308886885958119529796099886040791694146351652595312374814097477414979958807070209683292347241849372180624340306855291594676356423057275718454536102689938435577894238038572575164968798593541141542669704715555818146591746761201299983066424389834018601412956175764125889320693555559550428357733114274737946672404374556938553019287313544872929160209731922013485856494834906255289382886344321181453024455643127231196164879755525257885262683059994186479764652153316065964184496245163512193586736648994335439162898967919742494935201997226863196553906251553622453486374680556652652555212778656530133745944943664162887184801080451070525799306343732810376488813947263273494926237966949450232280691161254638719852395749474940908163413051389545584435843286245188837783108175333775502336217431319142469894539128402740679135443017136217576255316828718369857023297516162948108161711221114156524532152065591128411140479889328151409328212786196443556293387247144139675211363675835118463559559821745116181746594211566271188481929880313915631579389936643069124230243018665787232521331792894999876811565438999967268825407961261030774369699619638725706385444216727588833699817550134582965064139433932099649293898645345173359485617538444742545149826976123769982133548892136571491888602359673784557876461387251824968728859519876415121353736118383856664524318793731430296687872154213874421323427896326065435293669985867648321296268256441989294850423743391262685789284069523425759316633522203069794588466431362272654980508721441547565861259295229943695412679512568437755769878365305356402693684140852794132418303482438146415189745092453934916755518637182696952225168018387820968245501654186064365797168518776224485979108640694769446635138429287338477871554897266842957888275488408558847214922886115847492964176671997226263646362170188434586386131829939613484975586655629175658451229813241933581413748838205950855545938178177458217515597677769839872331458328847046458593977935444689558322625634731496168146774648748449406357276640502075909751727083925422959243623178807852309563371945906920682369342539361062949147455574847369538171203122789859636420195338742853507052589766188457201877344895276594218760257036377629551188768928676076768217861352872186702746401246992011304453444557352264209565493131396814406892969789314360129278688554572415842478631217527655323592232722281769451044317465939742722456933918339333354950201872672263442594514515183864911938754991352396502349731410147636511487749021299143127896603495218432404072955120244271708634836997573974198711293154237945204386724485383632707777616726685496654595356321312824241377148669753257323890975198534413762284567258497498914454574643579865952659443167274218437367475058631725417018453054945571449597142310396458143755954240518796501412621520118445263382142032455693551718992788512157511915238641277638477564638572714545606674279345231376203933283456794469501045216746163770412368316560953366575497748051327842822298906253886884223473518769743771468096803933725460947828625918655015218013133569863121676466728036525832585611794574552979948391831298738631498562467271469875832873421430896182642862326254715711968534436187207735675242697522144977658361394439104962477499828014785167778669316519761780886827515180474851144221854038134242844799925864575611661356425689127824911133999166134340598476479812252525105945663521134629924961344893136931154726696824523313367558582310399413602924157294605773141395729061187141249029417287661087928847449073819425621852134261215589538813316025777032402648116714274049699340276449865651614213892761142640558636784335476583481351952030559064893230494425974672807814623898209141404659127549776882784461102147299348155891928177792781458420555579428688993734606181438351348010992998509357873852139210837166763226778057448623412046447418551118321639348190747439444155787161355233322212339557582876112667727057135089513245575936302056344083137925784498338996125259651291889949738150664165823822719644458517887720742888413145347413127124934788308238611023409164872489303266552928975795514366332166398772215646722790745840678756118628108574946795137811144871218553982789603193241588476136515931658149217422853230899674886910349489916863105986118045322917425390857126895775428329835952644737354413298220943357642485339268935465134227778579539634784027319059916998265935811112741191287994849840526621752554882222393149979684572937676940719348653545269824779956921749443545441839495743359666349382861161587712699843373331162597642196452675958567197755114874853274433730744575992834201850542961166379662066696125177052378033418083163732101973205432305815221783409587165227241099798489296693626430229866987560353292287875568852208021129082716632532599307448524679111361494051784883402018625587355642949960888997276627122086984958943241685922813959665138692894935413558264601395202081905980909037851374782479711769997897273413339025499617983083463830409678651860327848512119575538411890511495416014619024542717418586157321655958843729597989153698509535884529326151635565129875597486867287225723338528516627165391658635282274194028203054542170669266482496459738499759851495914271508759817244949573408459537283845344687847515316285994256562939776975256434529871235583372673173975596421552781344294242209485942867267719502579567921547319582335702596516612417412499485984172167266677017353072424398605349903257475933912842182552269436404374534424771788322192207964949734344316332997795653612865541731846737228363483219961688616259406868299759303974672457393171552984331356329645403055639699363491843458514641495393665596758635898574984287362762969320565762287965422938229675384451847896695077764491173690226478787491913036418774353791559543925243698817936131469842966213992364849051527269694856792094459570581223485452767412269335995165912561226434208516389843536735447082939990561471843830831254605932274864388794899428511623701521252874827722442759194388745049998747777186429169911097385912447772993625704968106928512912513666654970992638762674539727732049899127934954139724669283917481623032198438599232449946756065534625621321355691677082633394708971734711587765907168303120151288267357288618139189743989557759231157837647454625757240796132298623326343891251707878881051498522395792463168567294647393889253637975726634247849761545964112345462544181538662278223187641995029658876306234197944172732358171903168586776594493511361718525206847863240833728127162904085312551721787769324955632761869746226311833219064395746233988134970116621557085212596423928358874718047577984348044849815373519152637342170732578202771147787902876113732168047411375839899431373892313785683971618219279291175265639356993755161151914991581403735144542148587877841567633797056874889214212334010975529972698736733454547561552641521104760474418389074768156713043958962355575609418709692777828274538829886487443428082878712992910406136339774238996965969186923205912493097866999467429388768767963258187845025544415543227853670777260506240662215884691991032593699128874301621232236624951697841436016484399597831376635549450593643578438613877431621811053891586128892679874601353789876835324948757254049837256341169245713284321611418158812223317829687206064437996879612354636497381313511248182212726316156887050459158421361411160813441945680668558671471179059216063251618504868706284296188797181463982461222492460261526267166573744656867266270165376531458147845164924457097783757541811337490266442622331781382159457511581988324577234502083408451875759164534748747431597621259516044793915173016224253312889976042846045562397358120346792744188113239795679515364262673242540902897255017593167659438591272697472543529771015301085525064406413448193481092241517293938884229774151746684285461885516826011703683282420457013505328935912591366755827986841992720376538899096434779605144809317134964686850273268702988239446995519997038968558301313889699731833795053395447304091406092798187147177198248423962417664803294268699158756699484106584933034831961187399944245935048486882955339231167848829784112655166144713955674854439418343176753356938512463174590529774355837719127827939575882444836778166414588806421211246763581727193547913329957631938811037814066759931147037494772216479132323781594686471445842769262172659141055682339974315731585546682958862859411926723787236969791382730149115843477732035535356961343302318504228804296198610281718424455277635583541519417473950982529961147963859141595908619776266549967388791808722502563173761751777508277206634326372221438314220697876887944411447542266722447749987854367689025732386107789821846262431216253428854945419875822248274799022453216712677946398307312903611538493439167239923102556675691199651473096977283871739307767851624416494436826536212652540178610923238352298139066421542381110458310243351312286501652471658305913321968862275466634441066615130844020273228209628613292872288848554879895807194688129264491492286674687292265858112894872645619374675214596373860721677957088478156772854275228948355568263911327639369873476496462607151469697883998951512858058367161972693656983724583194024993192753890621770235469339981261051961454877278705364926050193499828559214776277440704617207666603662562495944365137335971337608569742030367964712269567329628243514397259637867312231972345549345725453513557062802631235832927827931024808611194256697293227540456255643722801546615812625565206547926285586658918523602449318353222472391572892078701489144432264178676993885537326736919631539716725981291065121385978895103038802";
}
