package database;

import android.annotation.SuppressLint;
import java.util.HashMap;

/*
 *クラス名：  Converter
 *日付: 2014/02/13
 *著作権表示: N/A
 *変更ログ:
 *20140215 LINHNC add new function Html_Converter
 *20140220 LINHNC add new function Html_ConverterKJ , Html_converterIns, Hira2Kata
 *20140305 LINHNC add new function makePlaceHolder
 *	      LINHNC update  function RomajiToHiragana
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140213 LINHNC 説明 */
public class Converter {
	public static StringBuilder sTemp = new StringBuilder();
	/* A hash map stored index use for search japanese-vietnamese */
	public static final HashMap<String, Integer> mapPositionJv = new HashMap<String, Integer>();
	static {
		mapPositionJv.put("1", 1);
		mapPositionJv.put("2", 3);
		mapPositionJv.put("3", 8);
		mapPositionJv.put("4", 9);
		mapPositionJv.put("a", 10);
		mapPositionJv.put("c", 22);
		mapPositionJv.put("d", 23);
		mapPositionJv.put("f", 24);
		mapPositionJv.put("h", 25);
		mapPositionJv.put("i", 26);
		mapPositionJv.put("j", 27);
		mapPositionJv.put("n", 29);
		mapPositionJv.put("o", 34);
		mapPositionJv.put("r", 35);
		mapPositionJv.put("s", 36);
		mapPositionJv.put("t", 40);
		mapPositionJv.put("w", 43);
		mapPositionJv.put("x", 44);
		mapPositionJv.put("y", 45);
		mapPositionJv.put("〜", 46);
		mapPositionJv.put("あ", 48);
		mapPositionJv.put("い", 1160);
		mapPositionJv.put("う", 1916);
		mapPositionJv.put("え", 2408);
		mapPositionJv.put("お", 2689);
		mapPositionJv.put("か", 3955);
		mapPositionJv.put("が", 5542);
		mapPositionJv.put("き", 5874);
		mapPositionJv.put("ぎ", 7006);
		mapPositionJv.put("く", 7216);
		mapPositionJv.put("ぐ", 7763);
		mapPositionJv.put("け", 7905);
		mapPositionJv.put("げ", 8572);
		mapPositionJv.put("こ", 8897);
		mapPositionJv.put("ご", 10547);
		mapPositionJv.put("さ", 10911);
		mapPositionJv.put("ざ", 11585);
		mapPositionJv.put("し", 11735);
		mapPositionJv.put("じ", 13678);
		mapPositionJv.put("す", 14643);
		mapPositionJv.put("ず", 15019);
		mapPositionJv.put("せ", 15077);
		mapPositionJv.put("ぜ", 15891);
		mapPositionJv.put("そ", 16069);
		mapPositionJv.put("ぞ", 16583);
		mapPositionJv.put("た", 16659);
		mapPositionJv.put("だ", 17327);
		mapPositionJv.put("ち", 17614);
		mapPositionJv.put("ぢ", 18670);
		mapPositionJv.put("つ", 18671);
		mapPositionJv.put("て", 19239);
		mapPositionJv.put("で", 20167);
		mapPositionJv.put("と", 20552);
		mapPositionJv.put("ど", 21651);
		mapPositionJv.put("な", 21991);
		mapPositionJv.put("に", 22827);
		mapPositionJv.put("ぬ", 23793);
		mapPositionJv.put("ね", 23905);
		mapPositionJv.put("の", 24343);
		mapPositionJv.put("は", 24666);
		mapPositionJv.put("ば", 25704);
		mapPositionJv.put("ぱ", 25957);
		mapPositionJv.put("ひ", 25978);
		mapPositionJv.put("び", 27014);
		mapPositionJv.put("ぴ", 27232);
		mapPositionJv.put("ふ", 27251);
		mapPositionJv.put("ぶ", 28079);
		mapPositionJv.put("ぷ", 28383);
		mapPositionJv.put("へ", 28397);
		mapPositionJv.put("べ", 28593);
		mapPositionJv.put("ぺ", 28684);
		mapPositionJv.put("ほ", 28695);
		mapPositionJv.put("ぼ", 29210);
		mapPositionJv.put("ぽ", 29431);
		mapPositionJv.put("ま", 29440);
		mapPositionJv.put("み", 29843);
		mapPositionJv.put("む", 30227);
		mapPositionJv.put("め", 30461);
		mapPositionJv.put("も", 30709);
		mapPositionJv.put("や", 30987);
		mapPositionJv.put("ゆ", 31238);
		mapPositionJv.put("よ", 31622);
		mapPositionJv.put("ら", 32021);
		mapPositionJv.put("り", 32112);
		mapPositionJv.put("る", 32419);
		mapPositionJv.put("れ", 32437);
		mapPositionJv.put("ろ", 32594);
		mapPositionJv.put("わ", 32737);
		mapPositionJv.put("を", 32968);
		mapPositionJv.put("ア", 32970);
		mapPositionJv.put("イ", 33876);
		mapPositionJv.put("ウ", 34240);
		mapPositionJv.put("エ", 34414);
		mapPositionJv.put("オ", 34580);
		mapPositionJv.put("カ", 34720);
		mapPositionJv.put("ガ", 34895);
		mapPositionJv.put("キ", 34963);
		mapPositionJv.put("ギ", 35066);
		mapPositionJv.put("ク", 35093);
		mapPositionJv.put("グ", 35217);
		mapPositionJv.put("ケ", 35279);
		mapPositionJv.put("ゲ", 35300);
		mapPositionJv.put("コ", 35322);
		mapPositionJv.put("ゴ", 35542);
		mapPositionJv.put("サ", 35593);
		mapPositionJv.put("ザ", 35725);
		mapPositionJv.put("シ", 35730);
		mapPositionJv.put("ジ", 35921);
		mapPositionJv.put("ス", 36014);
		mapPositionJv.put("ズ", 36278);
		mapPositionJv.put("セ", 36292);
		mapPositionJv.put("ゼ", 36360);
		mapPositionJv.put("ソ", 36371);
		mapPositionJv.put("ゾ", 36402);
		mapPositionJv.put("タ", 36403);
		mapPositionJv.put("ダ", 36477);
		mapPositionJv.put("チ", 36527);
		mapPositionJv.put("ツ", 36624);
		mapPositionJv.put("テ", 36635);
		mapPositionJv.put("デ", 36708);
		mapPositionJv.put("ト", 36817);
		mapPositionJv.put("ド", 36933);
		mapPositionJv.put("ナ", 37006);
		mapPositionJv.put("ニ", 37054);
		mapPositionJv.put("ヌ", 37094);
		mapPositionJv.put("ネ", 37099);
		mapPositionJv.put("ノ", 37125);
		mapPositionJv.put("ハ", 37181);
		mapPositionJv.put("バ", 37325);
		mapPositionJv.put("パ", 37539);
		mapPositionJv.put("ヒ", 37728);
		mapPositionJv.put("ビ", 37776);
		mapPositionJv.put("ピ", 37894);
		mapPositionJv.put("フ", 37965);
		mapPositionJv.put("ブ", 38282);
		mapPositionJv.put("プ", 38401);
		mapPositionJv.put("ヘ", 38544);
		mapPositionJv.put("ベ", 38587);
		mapPositionJv.put("ペ", 38668);
		mapPositionJv.put("ホ", 38713);
		mapPositionJv.put("ボ", 38773);
		mapPositionJv.put("ポ", 38852);
		mapPositionJv.put("マ", 38909);
		mapPositionJv.put("ミ", 39078);
		mapPositionJv.put("ム", 39141);
		mapPositionJv.put("メ", 39151);
		mapPositionJv.put("モ", 39254);
		mapPositionJv.put("ヤ", 39321);
		mapPositionJv.put("ユ", 39331);
		mapPositionJv.put("ヨ", 39361);
		mapPositionJv.put("ラ", 39375);
		mapPositionJv.put("リ", 39511);
		mapPositionJv.put("ル", 39652);
		mapPositionJv.put("レ", 39683);
		mapPositionJv.put("ロ", 39785);
		mapPositionJv.put("ワ", 39862);
		mapPositionJv.put("ン", 39913);
		mapPositionJv.put("ヴ", 39914);
		mapPositionJv.put("ー", 39924);
		mapPositionJv.put("一", 39925);
		mapPositionJv.put("丁", 40129);
		mapPositionJv.put("七", 40148);
		mapPositionJv.put("万", 40161);
		mapPositionJv.put("丈", 40198);
		mapPositionJv.put("三", 40202);
		mapPositionJv.put("上", 40225);
		mapPositionJv.put("下", 40315);
		mapPositionJv.put("不", 40381);
		mapPositionJv.put("与", 40640);
		mapPositionJv.put("且", 40642);
		mapPositionJv.put("世", 40643);
		mapPositionJv.put("丘", 40672);
		mapPositionJv.put("丙", 40675);
		mapPositionJv.put("両", 40677);
		mapPositionJv.put("並", 40700);
		mapPositionJv.put("中", 40736);
		mapPositionJv.put("串", 40944);
		mapPositionJv.put("丸", 40945);
		mapPositionJv.put("丹", 40962);
		mapPositionJv.put("主", 40971);
		mapPositionJv.put("丼", 41021);
		mapPositionJv.put("乃", 41022);
		mapPositionJv.put("久", 41024);
		mapPositionJv.put("之", 41028);
		mapPositionJv.put("乎", 41029);
		mapPositionJv.put("乏", 41030);
		mapPositionJv.put("乗", 41031);
		mapPositionJv.put("乙", 41060);
		mapPositionJv.put("九", 41062);
		mapPositionJv.put("乞", 41068);
		mapPositionJv.put("乱", 41070);
		mapPositionJv.put("乳", 41083);
		mapPositionJv.put("乾", 41134);
		mapPositionJv.put("亀", 41159);
		mapPositionJv.put("了", 41163);
		mapPositionJv.put("予", 41168);
		mapPositionJv.put("争", 41214);
		mapPositionJv.put("事", 41220);
		mapPositionJv.put("二", 41253);
		mapPositionJv.put("云", 41456);
		mapPositionJv.put("互", 41457);
		mapPositionJv.put("五", 41468);
		mapPositionJv.put("井", 41501);
		mapPositionJv.put("些", 41503);
		mapPositionJv.put("亜", 41506);
		mapPositionJv.put("亡", 41517);
		mapPositionJv.put("交", 41534);
		mapPositionJv.put("享", 41623);
		mapPositionJv.put("京", 41629);
		mapPositionJv.put("亭", 41633);
		mapPositionJv.put("人", 41636);
		mapPositionJv.put("仁", 41840);
		mapPositionJv.put("仄", 41848);
		mapPositionJv.put("今", 41850);
		mapPositionJv.put("介", 41882);
		mapPositionJv.put("仏", 41887);
		mapPositionJv.put("仔", 41937);
		mapPositionJv.put("仕", 41941);
		mapPositionJv.put("他", 41998);
		mapPositionJv.put("付", 42014);
		mapPositionJv.put("仙", 42053);
		mapPositionJv.put("代", 42058);
		mapPositionJv.put("令", 42104);
		mapPositionJv.put("以", 42106);
		mapPositionJv.put("仮", 42119);
		mapPositionJv.put("仰", 42138);
		mapPositionJv.put("仲", 42147);
		mapPositionJv.put("件", 42194);
		mapPositionJv.put("任", 42195);
		mapPositionJv.put("企", 42218);
		mapPositionJv.put("伊", 42230);
		mapPositionJv.put("伏", 42232);
		mapPositionJv.put("伐", 42239);
		mapPositionJv.put("休", 42241);
		mapPositionJv.put("会", 42271);
		mapPositionJv.put("伝", 42331);
		mapPositionJv.put("伯", 42398);
		mapPositionJv.put("伴", 42407);
		mapPositionJv.put("伸", 42410);
		mapPositionJv.put("伺", 42424);
		mapPositionJv.put("似", 42426);
		mapPositionJv.put("佃", 42438);
		mapPositionJv.put("但", 42439);
		mapPositionJv.put("佇", 42442);
		mapPositionJv.put("位", 42443);
		mapPositionJv.put("低", 42449);
		mapPositionJv.put("住", 42503);
		mapPositionJv.put("佐", 42517);
		mapPositionJv.put("体", 42518);
		mapPositionJv.put("何", 42555);
		mapPositionJv.put("余", 42606);
		mapPositionJv.put("作", 42623);
		mapPositionJv.put("佝", 42663);
		mapPositionJv.put("佞", 42665);
		mapPositionJv.put("佳", 42673);
		mapPositionJv.put("併", 42678);
		mapPositionJv.put("使", 42689);
		mapPositionJv.put("例", 42714);
		mapPositionJv.put("侍", 42725);
		mapPositionJv.put("侘", 42730);
		mapPositionJv.put("供", 42731);
		mapPositionJv.put("依", 42741);
		mapPositionJv.put("価", 42749);
		mapPositionJv.put("侮", 42763);
		mapPositionJv.put("侯", 42769);
		mapPositionJv.put("侵", 42773);
		mapPositionJv.put("便", 42783);
		mapPositionJv.put("係", 42795);
		mapPositionJv.put("促", 42802);
		mapPositionJv.put("俄", 42806);
		mapPositionJv.put("俊", 42809);
		mapPositionJv.put("俎", 42815);
		mapPositionJv.put("俗", 42816);
		mapPositionJv.put("保", 42823);
		mapPositionJv.put("信", 42904);
		mapPositionJv.put("修", 42964);
		mapPositionJv.put("俯", 42993);
		mapPositionJv.put("俳", 42994);
		mapPositionJv.put("俵", 42999);
		mapPositionJv.put("俸", 43000);
		mapPositionJv.put("俺", 43002);
		mapPositionJv.put("倉", 43003);
		mapPositionJv.put("個", 43026);
		mapPositionJv.put("倍", 43047);
		mapPositionJv.put("倒", 43056);
		mapPositionJv.put("倖", 43072);
		mapPositionJv.put("候", 43073);
		mapPositionJv.put("借", 43076);
		mapPositionJv.put("倣", 43103);
		mapPositionJv.put("値", 43104);
		mapPositionJv.put("倦", 43136);
		mapPositionJv.put("倫", 43138);
		mapPositionJv.put("倹", 43140);
		mapPositionJv.put("偃", 43143);
		mapPositionJv.put("偉", 43144);
		mapPositionJv.put("偏", 43154);
		mapPositionJv.put("偕", 43163);
		mapPositionJv.put("偖", 43165);
		mapPositionJv.put("停", 43166);
		mapPositionJv.put("健", 43191);
		mapPositionJv.put("側", 43209);
		mapPositionJv.put("偵", 43212);
		mapPositionJv.put("偶", 43220);
		mapPositionJv.put("偸", 43230);
		mapPositionJv.put("偽", 43231);
		mapPositionJv.put("傀", 43244);
		mapPositionJv.put("傍", 43247);
		mapPositionJv.put("傑", 43257);
		mapPositionJv.put("傘", 43263);
		mapPositionJv.put("備", 43267);
		mapPositionJv.put("催", 43277);
		mapPositionJv.put("傲", 43286);
		mapPositionJv.put("傴", 43291);
		mapPositionJv.put("債", 43292);
		mapPositionJv.put("傷", 43300);
		mapPositionJv.put("傾", 43318);
		mapPositionJv.put("僅", 43328);
		mapPositionJv.put("働", 43329);
		mapPositionJv.put("像", 43337);
		mapPositionJv.put("僕", 43338);
		mapPositionJv.put("僚", 43340);
		mapPositionJv.put("僧", 43341);
		mapPositionJv.put("僵", 43349);
		mapPositionJv.put("僻", 43350);
		mapPositionJv.put("儀", 43352);
		mapPositionJv.put("億", 43357);
		mapPositionJv.put("儒", 43360);
		mapPositionJv.put("儘", 43370);
		mapPositionJv.put("儚", 43371);
		mapPositionJv.put("償", 43372);
		mapPositionJv.put("優", 43377);
		mapPositionJv.put("儲", 43408);
		mapPositionJv.put("儺", 43413);
		mapPositionJv.put("兀", 43414);
		mapPositionJv.put("元", 43415);
		mapPositionJv.put("兄", 43439);
		mapPositionJv.put("充", 43443);
		mapPositionJv.put("兆", 43461);
		mapPositionJv.put("兇", 43465);
		mapPositionJv.put("先", 43467);
		mapPositionJv.put("光", 43514);
		mapPositionJv.put("克", 43548);
		mapPositionJv.put("免", 43553);
		mapPositionJv.put("兎", 43577);
		mapPositionJv.put("児", 43586);
		mapPositionJv.put("兔", 43591);
		mapPositionJv.put("党", 43592);
		mapPositionJv.put("入", 43615);
		mapPositionJv.put("全", 43768);
		mapPositionJv.put("八", 43822);
		mapPositionJv.put("公", 43838);
		mapPositionJv.put("六", 43984);
		mapPositionJv.put("共", 43990);
		mapPositionJv.put("兵", 44025);
		mapPositionJv.put("其", 44046);
		mapPositionJv.put("具", 44060);
		mapPositionJv.put("典", 44070);
		mapPositionJv.put("兼", 44079);
		mapPositionJv.put("内", 44084);
		mapPositionJv.put("円", 44281);
		mapPositionJv.put("冊", 44303);
		mapPositionJv.put("再", 44305);
		mapPositionJv.put("冒", 44347);
		mapPositionJv.put("冗", 44355);
		mapPositionJv.put("写", 44363);
		mapPositionJv.put("冠", 44377);
		mapPositionJv.put("冤", 44380);
		mapPositionJv.put("冥", 44384);
		mapPositionJv.put("冬", 44385);
		mapPositionJv.put("冴", 44399);
		mapPositionJv.put("冶", 44400);
		mapPositionJv.put("冷", 44401);
		mapPositionJv.put("凄", 44449);
		mapPositionJv.put("准", 44451);
		mapPositionJv.put("凋", 44453);
		mapPositionJv.put("凌", 44456);
		mapPositionJv.put("凍", 44458);
		mapPositionJv.put("凝", 44481);
		mapPositionJv.put("几", 44490);
		mapPositionJv.put("凡", 44491);
		mapPositionJv.put("処", 44499);
		mapPositionJv.put("凧", 44517);
		mapPositionJv.put("凪", 44519);
		mapPositionJv.put("凭", 44520);
		mapPositionJv.put("凱", 44521);
		mapPositionJv.put("凶", 44525);
		mapPositionJv.put("凸", 44529);
		mapPositionJv.put("凹", 44538);
		mapPositionJv.put("出", 44544);
		mapPositionJv.put("函", 44698);
		mapPositionJv.put("刀", 44702);
		mapPositionJv.put("刃", 44712);
		mapPositionJv.put("分", 44718);
		mapPositionJv.put("切", 44783);
		mapPositionJv.put("刈", 44842);
		mapPositionJv.put("刊", 44849);
		mapPositionJv.put("刑", 44850);
		mapPositionJv.put("列", 44858);
		mapPositionJv.put("初", 44866);
		mapPositionJv.put("判", 44901);
		mapPositionJv.put("別", 44918);
		mapPositionJv.put("利", 44941);
		mapPositionJv.put("到", 44968);
		mapPositionJv.put("刳", 44982);
		mapPositionJv.put("制", 44983);
		mapPositionJv.put("刷", 45011);
		mapPositionJv.put("券", 45016);
		mapPositionJv.put("刺", 45017);
		mapPositionJv.put("刻", 45038);
		mapPositionJv.put("剃", 45043);
		mapPositionJv.put("削", 45047);
		mapPositionJv.put("前", 45055);
		mapPositionJv.put("剔", 45108);
		mapPositionJv.put("剖", 45109);
		mapPositionJv.put("剛", 45110);
		mapPositionJv.put("剣", 45115);
		mapPositionJv.put("剤", 45122);
		mapPositionJv.put("剥", 45123);
		mapPositionJv.put("副", 45129);
		mapPositionJv.put("剰", 45155);
		mapPositionJv.put("割", 45159);
		mapPositionJv.put("剳", 45203);
		mapPositionJv.put("創", 45204);
		mapPositionJv.put("剽", 45219);
		mapPositionJv.put("劇", 45220);
		mapPositionJv.put("力", 45228);
		mapPositionJv.put("功", 45246);
		mapPositionJv.put("加", 45251);
		mapPositionJv.put("劣", 45270);
		mapPositionJv.put("助", 45278);
		mapPositionJv.put("努", 45292);
		mapPositionJv.put("劫", 45297);
		mapPositionJv.put("励", 45299);
		mapPositionJv.put("労", 45303);
		mapPositionJv.put("効", 45340);
		mapPositionJv.put("勁", 45348);
		mapPositionJv.put("勃", 45350);
		mapPositionJv.put("勅", 45352);
		mapPositionJv.put("勇", 45360);
		mapPositionJv.put("勉", 45373);
		mapPositionJv.put("動", 45380);
		mapPositionJv.put("勘", 45409);
		mapPositionJv.put("務", 45419);
		mapPositionJv.put("勝", 45421);
		mapPositionJv.put("募", 45438);
		mapPositionJv.put("勢", 45443);
		mapPositionJv.put("勤", 45447);
		mapPositionJv.put("勧", 45460);
		mapPositionJv.put("勲", 45470);
		mapPositionJv.put("勺", 45472);
		mapPositionJv.put("勾", 45473);
		mapPositionJv.put("勿", 45474);
		mapPositionJv.put("匂", 45477);
		mapPositionJv.put("包", 45484);
		mapPositionJv.put("匈", 45515);
		mapPositionJv.put("匐", 45517);
		mapPositionJv.put("匕", 45518);
		mapPositionJv.put("化", 45521);
		mapPositionJv.put("北", 45546);
		mapPositionJv.put("匙", 45581);
		mapPositionJv.put("匠", 45582);
		mapPositionJv.put("匣", 45583);
		mapPositionJv.put("匪", 45584);
		mapPositionJv.put("匹", 45585);
		mapPositionJv.put("区", 45590);
		mapPositionJv.put("医", 45603);
		mapPositionJv.put("匿", 45626);
		mapPositionJv.put("十", 45630);
		mapPositionJv.put("千", 45654);
		mapPositionJv.put("升", 45663);
		mapPositionJv.put("午", 45664);
		mapPositionJv.put("半", 45670);
		mapPositionJv.put("卑", 45730);
		mapPositionJv.put("卒", 45746);
		mapPositionJv.put("卓", 45753);
		mapPositionJv.put("協", 45762);
		mapPositionJv.put("南", 45781);
		mapPositionJv.put("単", 45872);
		mapPositionJv.put("博", 45896);
		mapPositionJv.put("占", 45906);
		mapPositionJv.put("印", 45920);
		mapPositionJv.put("危", 45938);
		mapPositionJv.put("即", 45957);
		mapPositionJv.put("却", 45969);
		mapPositionJv.put("卵", 45972);
		mapPositionJv.put("卸", 45980);
		mapPositionJv.put("厄", 45988);
		mapPositionJv.put("厘", 45994);
		mapPositionJv.put("厚", 45996);
		mapPositionJv.put("原", 46014);
		mapPositionJv.put("厨", 46108);
		mapPositionJv.put("厭", 46109);
		mapPositionJv.put("厳", 46111);
		mapPositionJv.put("去", 46138);
		mapPositionJv.put("参", 46141);
		mapPositionJv.put("又", 46168);
		mapPositionJv.put("及", 46177);
		mapPositionJv.put("友", 46181);
		mapPositionJv.put("双", 46199);
		mapPositionJv.put("反", 46212);
		mapPositionJv.put("収", 46309);
		mapPositionJv.put("叔", 46338);
		mapPositionJv.put("取", 46342);
		mapPositionJv.put("受", 46432);
		mapPositionJv.put("叙", 46485);
		mapPositionJv.put("叛", 46492);
		mapPositionJv.put("口", 46497);
		mapPositionJv.put("古", 46592);
		mapPositionJv.put("句", 46624);
		mapPositionJv.put("叩", 46627);
		mapPositionJv.put("只", 46631);
		mapPositionJv.put("叫", 46633);
		mapPositionJv.put("召", 46636);
		mapPositionJv.put("可", 46642);
		mapPositionJv.put("台", 46659);
		mapPositionJv.put("叱", 46671);
		mapPositionJv.put("史", 46673);
		mapPositionJv.put("右", 46680);
		mapPositionJv.put("叶", 46695);
		mapPositionJv.put("号", 46697);
		mapPositionJv.put("司", 46705);
		mapPositionJv.put("吃", 46716);
		mapPositionJv.put("各", 46719);
		mapPositionJv.put("合", 46739);
		mapPositionJv.put("吉", 46841);
		mapPositionJv.put("吊", 46847);
		mapPositionJv.put("同", 46860);
		mapPositionJv.put("名", 46930);
		mapPositionJv.put("后", 46994);
		mapPositionJv.put("吐", 46995);
		mapPositionJv.put("向", 47011);
		mapPositionJv.put("君", 47030);
		mapPositionJv.put("吝", 47035);
		mapPositionJv.put("吟", 47036);
		mapPositionJv.put("吠", 47039);
		mapPositionJv.put("否", 47040);
		mapPositionJv.put("含", 47051);
		mapPositionJv.put("吸", 47060);
		mapPositionJv.put("吹", 47072);
		mapPositionJv.put("呆", 47082);
		mapPositionJv.put("呈", 47087);
		mapPositionJv.put("呉", 47088);
		mapPositionJv.put("告", 47095);
		mapPositionJv.put("呑", 47109);
		mapPositionJv.put("呟", 47111);
		mapPositionJv.put("周", 47112);
		mapPositionJv.put("呪", 47124);
		mapPositionJv.put("味", 47127);
		mapPositionJv.put("呵", 47142);
		mapPositionJv.put("呻", 47143);
		mapPositionJv.put("呼", 47144);
		mapPositionJv.put("命", 47164);
		mapPositionJv.put("咄", 47185);
		mapPositionJv.put("和", 47186);
		mapPositionJv.put("咎", 47218);
		mapPositionJv.put("咬", 47220);
		mapPositionJv.put("咲", 47223);
		mapPositionJv.put("咳", 47226);
		mapPositionJv.put("咽", 47231);
		mapPositionJv.put("哀", 47240);
		mapPositionJv.put("品", 47263);
		mapPositionJv.put("哄", 47302);
		mapPositionJv.put("員", 47305);
		mapPositionJv.put("哲", 47306);
		mapPositionJv.put("哺", 47311);
		mapPositionJv.put("唆", 47315);
		mapPositionJv.put("唇", 47316);
		mapPositionJv.put("唐", 47320);
		mapPositionJv.put("唖", 47333);
		mapPositionJv.put("唯", 47338);
		mapPositionJv.put("唱", 47347);
		mapPositionJv.put("唸", 47350);
		mapPositionJv.put("唾", 47352);
		mapPositionJv.put("啄", 47354);
		mapPositionJv.put("商", 47355);
		mapPositionJv.put("問", 47452);
		mapPositionJv.put("啓", 47468);
		mapPositionJv.put("喀", 47473);
		mapPositionJv.put("善", 47474);
		mapPositionJv.put("喉", 47499);
		mapPositionJv.put("喋", 47509);
		mapPositionJv.put("喘", 47510);
		mapPositionJv.put("喚", 47511);
		mapPositionJv.put("喜", 47515);
		mapPositionJv.put("喝", 47531);
		mapPositionJv.put("喧", 47532);
		mapPositionJv.put("喪", 47535);
		mapPositionJv.put("喫", 47543);
		mapPositionJv.put("営", 47548);
		mapPositionJv.put("嗅", 47564);
		mapPositionJv.put("嗟", 47568);
		mapPositionJv.put("嗣", 47569);
		mapPositionJv.put("嘆", 47570);
		mapPositionJv.put("嘔", 47577);
		mapPositionJv.put("嘗", 47578);
		mapPositionJv.put("嘘", 47579);
		mapPositionJv.put("嘱", 47583);
		mapPositionJv.put("嘲", 47587);
		mapPositionJv.put("嘴", 47591);
		mapPositionJv.put("嘸", 47592);
		mapPositionJv.put("噂", 47593);
		mapPositionJv.put("噛", 47596);
		mapPositionJv.put("器", 47600);
		mapPositionJv.put("噴", 47607);
		mapPositionJv.put("嚇", 47616);
		mapPositionJv.put("嚏", 47617);
		mapPositionJv.put("嚮", 47618);
		mapPositionJv.put("囀", 47619);
		mapPositionJv.put("囁", 47620);
		mapPositionJv.put("囚", 47621);
		mapPositionJv.put("四", 47622);
		mapPositionJv.put("回", 47646);
		mapPositionJv.put("因", 47699);
		mapPositionJv.put("団", 47704);
		mapPositionJv.put("困", 47714);
		mapPositionJv.put("囲", 47732);
		mapPositionJv.put("図", 47736);
		mapPositionJv.put("固", 47755);
		mapPositionJv.put("国", 47781);
		mapPositionJv.put("圏", 47968);
		mapPositionJv.put("園", 47970);
		mapPositionJv.put("土", 47972);
		mapPositionJv.put("圧", 48021);
		mapPositionJv.put("在", 48041);
		mapPositionJv.put("地", 48061);
		mapPositionJv.put("坂", 48131);
		mapPositionJv.put("均", 48136);
		mapPositionJv.put("坊", 48143);
		mapPositionJv.put("坐", 48147);
		mapPositionJv.put("坑", 48151);
		mapPositionJv.put("坩", 48156);
		mapPositionJv.put("坪", 48157);
		mapPositionJv.put("垂", 48160);
		mapPositionJv.put("型", 48165);
		mapPositionJv.put("垢", 48167);
		mapPositionJv.put("垣", 48169);
		mapPositionJv.put("埃", 48172);
		mapPositionJv.put("埋", 48173);
		mapPositionJv.put("城", 48189);
		mapPositionJv.put("域", 48195);
		mapPositionJv.put("埠", 48197);
		mapPositionJv.put("執", 48205);
		mapPositionJv.put("培", 48215);
		mapPositionJv.put("基", 48220);
		mapPositionJv.put("堀", 48238);
		mapPositionJv.put("堂", 48242);
		mapPositionJv.put("堅", 48244);
		mapPositionJv.put("堆", 48256);
		mapPositionJv.put("堕", 48257);
		mapPositionJv.put("堡", 48263);
		mapPositionJv.put("堤", 48264);
		mapPositionJv.put("堪", 48268);
		mapPositionJv.put("報", 48274);
		mapPositionJv.put("場", 48298);
		mapPositionJv.put("塀", 48312);
		mapPositionJv.put("塁", 48313);
		mapPositionJv.put("塊", 48314);
		mapPositionJv.put("塑", 48318);
		mapPositionJv.put("塔", 48319);
		mapPositionJv.put("塗", 48321);
		mapPositionJv.put("塚", 48342);
		mapPositionJv.put("塞", 48343);
		mapPositionJv.put("塩", 48345);
		mapPositionJv.put("塵", 48359);
		mapPositionJv.put("塾", 48363);
		mapPositionJv.put("境", 48365);
		mapPositionJv.put("墓", 48371);
		mapPositionJv.put("増", 48380);
		mapPositionJv.put("墜", 48401);
		mapPositionJv.put("墨", 48406);
		mapPositionJv.put("墳", 48410);
		mapPositionJv.put("墾", 48411);
		mapPositionJv.put("壁", 48412);
		mapPositionJv.put("壇", 48417);
		mapPositionJv.put("壊", 48419);
		mapPositionJv.put("壕", 48426);
		mapPositionJv.put("壜", 48427);
		mapPositionJv.put("壟", 48428);
		mapPositionJv.put("士", 48430);
		mapPositionJv.put("壮", 48432);
		mapPositionJv.put("声", 48438);
		mapPositionJv.put("壱", 48450);
		mapPositionJv.put("売", 48451);
		mapPositionJv.put("壷", 48491);
		mapPositionJv.put("変", 48492);
		mapPositionJv.put("夏", 48540);
		mapPositionJv.put("夕", 48550);
		mapPositionJv.put("外", 48564);
		mapPositionJv.put("多", 48655);
		mapPositionJv.put("夜", 48696);
		mapPositionJv.put("夢", 48723);
		mapPositionJv.put("夥", 48732);
		mapPositionJv.put("大", 48733);
		mapPositionJv.put("天", 48946);
		mapPositionJv.put("太", 49171);
		mapPositionJv.put("夫", 49193);
		mapPositionJv.put("失", 49217);
		mapPositionJv.put("夷", 49252);
		mapPositionJv.put("夾", 49253);
		mapPositionJv.put("奇", 49255);
		mapPositionJv.put("奈", 49273);
		mapPositionJv.put("奉", 49279);
		mapPositionJv.put("奏", 49288);
		mapPositionJv.put("契", 49289);
		mapPositionJv.put("奔", 49330);
		mapPositionJv.put("奠", 49334);
		mapPositionJv.put("奢", 49335);
		mapPositionJv.put("奥", 49336);
		mapPositionJv.put("奨", 49347);
		mapPositionJv.put("奪", 49352);
		mapPositionJv.put("奮", 49358);
		mapPositionJv.put("女", 49366);
		mapPositionJv.put("奴", 49400);
		mapPositionJv.put("奸", 49404);
		mapPositionJv.put("好", 49406);
		mapPositionJv.put("如", 49458);
		mapPositionJv.put("妃", 49471);
		mapPositionJv.put("妄", 49472);
		mapPositionJv.put("妊", 49475);
		mapPositionJv.put("妖", 49485);
		mapPositionJv.put("妙", 49486);
		mapPositionJv.put("妥", 49490);
		mapPositionJv.put("妨", 49496);
		mapPositionJv.put("妬", 49499);
		mapPositionJv.put("妹", 49502);
		mapPositionJv.put("妻", 49503);
		mapPositionJv.put("妾", 49511);
		mapPositionJv.put("姉", 49512);
		mapPositionJv.put("始", 49518);
		mapPositionJv.put("姑", 49533);
		mapPositionJv.put("姓", 49534);
		mapPositionJv.put("委", 49536);
		mapPositionJv.put("姦", 49550);
		mapPositionJv.put("姪", 49552);
		mapPositionJv.put("姫", 49553);
		mapPositionJv.put("姻", 49559);
		mapPositionJv.put("姿", 49560);
		mapPositionJv.put("威", 49565);
		mapPositionJv.put("娘", 49579);
		mapPositionJv.put("娯", 49585);
		mapPositionJv.put("娶", 49590);
		mapPositionJv.put("婆", 49591);
		mapPositionJv.put("婉", 49593);
		mapPositionJv.put("婚", 49594);
		mapPositionJv.put("婦", 49610);
		mapPositionJv.put("婿", 49624);
		mapPositionJv.put("媒", 49627);
		mapPositionJv.put("媚", 49633);
		mapPositionJv.put("媾", 49635);
		mapPositionJv.put("嫁", 49637);
		mapPositionJv.put("嫉", 49644);
		mapPositionJv.put("嫋", 49648);
		mapPositionJv.put("嫌", 49649);
		mapPositionJv.put("嫡", 49656);
		mapPositionJv.put("嬉", 49662);
		mapPositionJv.put("嬌", 49664);
		mapPositionJv.put("嬢", 49669);
		mapPositionJv.put("子", 49670);
		mapPositionJv.put("孔", 49722);
		mapPositionJv.put("孕", 49726);
		mapPositionJv.put("字", 49727);
		mapPositionJv.put("存", 49735);
		mapPositionJv.put("孝", 49742);
		mapPositionJv.put("季", 49751);
		mapPositionJv.put("孤", 49758);
		mapPositionJv.put("学", 49769);
		mapPositionJv.put("孫", 49836);
		mapPositionJv.put("孰", 49839);
		mapPositionJv.put("孵", 49840);
		mapPositionJv.put("宅", 49843);
		mapPositionJv.put("宇", 49846);
		mapPositionJv.put("守", 49859);
		mapPositionJv.put("安", 49867);
		mapPositionJv.put("完", 49907);
		mapPositionJv.put("宗", 49931);
		mapPositionJv.put("官", 49942);
		mapPositionJv.put("宙", 49953);
		mapPositionJv.put("定", 49954);
		mapPositionJv.put("宛", 50040);
		mapPositionJv.put("宜", 50043);
		mapPositionJv.put("宝", 50045);
		mapPositionJv.put("実", 50057);
		mapPositionJv.put("客", 50131);
		mapPositionJv.put("宣", 50142);
		mapPositionJv.put("室", 50158);
		mapPositionJv.put("宥", 50162);
		mapPositionJv.put("宦", 50164);
		mapPositionJv.put("宮", 50165);
		mapPositionJv.put("宰", 50170);
		mapPositionJv.put("害", 50171);
		mapPositionJv.put("宴", 50177);
		mapPositionJv.put("宵", 50181);
		mapPositionJv.put("家", 50185);
		mapPositionJv.put("容", 50249);
		mapPositionJv.put("宿", 50263);
		mapPositionJv.put("寂", 50275);
		mapPositionJv.put("寄", 50279);
		mapPositionJv.put("寅", 50301);
		mapPositionJv.put("密", 50303);
		mapPositionJv.put("富", 50335);
		mapPositionJv.put("寒", 50347);
		mapPositionJv.put("寓", 50364);
		mapPositionJv.put("寛", 50365);
		mapPositionJv.put("寝", 50371);
		mapPositionJv.put("察", 50441);
		mapPositionJv.put("寡", 50442);
		mapPositionJv.put("實", 50447);
		mapPositionJv.put("寧", 50448);
		mapPositionJv.put("審", 50450);
		mapPositionJv.put("寮", 50466);
		mapPositionJv.put("寵", 50471);
		mapPositionJv.put("寸", 50476);
		mapPositionJv.put("寺", 50482);
		mapPositionJv.put("対", 50490);
		mapPositionJv.put("寿", 50531);
		mapPositionJv.put("封", 50534);
		mapPositionJv.put("専", 50548);
		mapPositionJv.put("射", 50572);
		mapPositionJv.put("将", 50584);
		mapPositionJv.put("尉", 50594);
		mapPositionJv.put("尊", 50595);
		mapPositionJv.put("尋", 50607);
		mapPositionJv.put("導", 50611);
		mapPositionJv.put("小", 50616);
		mapPositionJv.put("少", 50720);
		mapPositionJv.put("尖", 50749);
		mapPositionJv.put("尚", 50752);
		mapPositionJv.put("尤", 50756);
		mapPositionJv.put("就", 50758);
		mapPositionJv.put("尺", 50767);
		mapPositionJv.put("尻", 50771);
		mapPositionJv.put("尼", 50773);
		mapPositionJv.put("尽", 50777);
		mapPositionJv.put("尾", 50781);
		mapPositionJv.put("尿", 50790);
		mapPositionJv.put("局", 50802);
		mapPositionJv.put("居", 50813);
		mapPositionJv.put("屈", 50828);
		mapPositionJv.put("届", 50832);
		mapPositionJv.put("屋", 50841);
		mapPositionJv.put("屍", 50848);
		mapPositionJv.put("屏", 50849);
		mapPositionJv.put("屑", 50851);
		mapPositionJv.put("展", 50852);
		mapPositionJv.put("属", 50874);
		mapPositionJv.put("屠", 50876);
		mapPositionJv.put("屡", 50880);
		mapPositionJv.put("層", 50881);
		mapPositionJv.put("履", 50884);
		mapPositionJv.put("屯", 50893);
		mapPositionJv.put("山", 50895);
		mapPositionJv.put("屹", 50936);
		mapPositionJv.put("岐", 50937);
		mapPositionJv.put("岡", 50938);
		mapPositionJv.put("岩", 50940);
		mapPositionJv.put("岬", 50947);
		mapPositionJv.put("岳", 50949);
		mapPositionJv.put("岸", 50951);
		mapPositionJv.put("峠", 50954);
		mapPositionJv.put("峡", 50957);
		mapPositionJv.put("峰", 50959);
		mapPositionJv.put("島", 50960);
		mapPositionJv.put("崇", 50972);
		mapPositionJv.put("崎", 50976);
		mapPositionJv.put("崖", 50978);
		mapPositionJv.put("崩", 50979);
		mapPositionJv.put("嵌", 50984);
		mapPositionJv.put("嵐", 50986);
		mapPositionJv.put("巌", 50987);
		mapPositionJv.put("川", 50988);
		mapPositionJv.put("州", 51021);
		mapPositionJv.put("巡", 51022);
		mapPositionJv.put("巣", 51037);
		mapPositionJv.put("工", 51039);
		mapPositionJv.put("左", 51092);
		mapPositionJv.put("巧", 51119);
		mapPositionJv.put("巨", 51129);
		mapPositionJv.put("差", 51138);
		mapPositionJv.put("己", 51174);
		mapPositionJv.put("巳", 51176);
		mapPositionJv.put("巴", 51177);
		mapPositionJv.put("巻", 51178);
		mapPositionJv.put("市", 51190);
		mapPositionJv.put("布", 51228);
		mapPositionJv.put("帆", 51245);
		mapPositionJv.put("希", 51251);
		mapPositionJv.put("帝", 51261);
		mapPositionJv.put("師", 51280);
		mapPositionJv.put("席", 51287);
		mapPositionJv.put("帯", 51290);
		mapPositionJv.put("帰", 51294);
		mapPositionJv.put("帳", 51318);
		mapPositionJv.put("常", 51326);
		mapPositionJv.put("帽", 51354);
		mapPositionJv.put("幄", 51359);
		mapPositionJv.put("幅", 51360);
		mapPositionJv.put("幌", 51367);
		mapPositionJv.put("幕", 51369);
		mapPositionJv.put("幟", 51373);
		mapPositionJv.put("幣", 51374);
		mapPositionJv.put("干", 51375);
		mapPositionJv.put("平", 51392);
		mapPositionJv.put("年", 51473);
		mapPositionJv.put("幸", 51615);
		mapPositionJv.put("幹", 51635);
		mapPositionJv.put("幻", 51640);
		mapPositionJv.put("幼", 51653);
		mapPositionJv.put("幽", 51666);
		mapPositionJv.put("幾", 51673);
		mapPositionJv.put("庁", 51681);
		mapPositionJv.put("広", 51683);
		mapPositionJv.put("庇", 51723);
		mapPositionJv.put("床", 51727);
		mapPositionJv.put("序", 51734);
		mapPositionJv.put("底", 51743);
		mapPositionJv.put("店", 51750);
		mapPositionJv.put("庚", 51760);
		mapPositionJv.put("府", 51761);
		mapPositionJv.put("度", 51767);
		mapPositionJv.put("座", 51775);
		mapPositionJv.put("庫", 51800);
		mapPositionJv.put("庭", 51801);
		mapPositionJv.put("庶", 51814);
		mapPositionJv.put("廃", 51819);
		mapPositionJv.put("廉", 51845);
		mapPositionJv.put("廊", 51849);
		mapPositionJv.put("廏", 51850);
		mapPositionJv.put("廐", 51851);
		mapPositionJv.put("廟", 51853);
		mapPositionJv.put("延", 51854);
		mapPositionJv.put("廷", 51878);
		mapPositionJv.put("建", 51879);
		mapPositionJv.put("廻", 51908);
		mapPositionJv.put("廼", 51909);
		mapPositionJv.put("弁", 51910);
		mapPositionJv.put("弄", 51935);
		mapPositionJv.put("弊", 51936);
		mapPositionJv.put("式", 51938);
		mapPositionJv.put("弐", 51943);
		mapPositionJv.put("弓", 51944);
		mapPositionJv.put("弔", 51949);
		mapPositionJv.put("引", 51968);
		mapPositionJv.put("弗", 52106);
		mapPositionJv.put("弘", 52108);
		mapPositionJv.put("弛", 52110);
		mapPositionJv.put("弟", 52111);
		mapPositionJv.put("弦", 52116);
		mapPositionJv.put("弧", 52118);
		mapPositionJv.put("弱", 52119);
		mapPositionJv.put("張", 52137);
		mapPositionJv.put("強", 52143);
		mapPositionJv.put("弾", 52209);
		mapPositionJv.put("彎", 52224);
		mapPositionJv.put("当", 52225);
		mapPositionJv.put("彗", 52280);
		mapPositionJv.put("形", 52281);
		mapPositionJv.put("彩", 52297);
		mapPositionJv.put("彫", 52300);
		mapPositionJv.put("影", 52319);
		mapPositionJv.put("彷", 52326);
		mapPositionJv.put("役", 52328);
		mapPositionJv.put("彼", 52341);
		mapPositionJv.put("往", 52351);
		mapPositionJv.put("征", 52360);
		mapPositionJv.put("待", 52363);
		mapPositionJv.put("律", 52381);
		mapPositionJv.put("後", 52382);
		mapPositionJv.put("徐", 52459);
		mapPositionJv.put("徒", 52465);
		mapPositionJv.put("従", 52480);
		mapPositionJv.put("得", 52501);
		mapPositionJv.put("御", 52519);
		mapPositionJv.put("復", 52560);
		mapPositionJv.put("循", 52589);
		mapPositionJv.put("微", 52590);
		mapPositionJv.put("徳", 52616);
		mapPositionJv.put("徴", 52626);
		mapPositionJv.put("徹", 52638);
		mapPositionJv.put("徽", 52643);
		mapPositionJv.put("心", 52644);
		mapPositionJv.put("必", 52708);
		mapPositionJv.put("忌", 52736);
		mapPositionJv.put("忍", 52744);
		mapPositionJv.put("志", 52754);
		mapPositionJv.put("忘", 52763);
		mapPositionJv.put("忙", 52773);
		mapPositionJv.put("応", 52774);
		mapPositionJv.put("忠", 52799);
		mapPositionJv.put("快", 52812);
		mapPositionJv.put("念", 52829);
		mapPositionJv.put("忽", 52845);
		mapPositionJv.put("怒", 52846);
		mapPositionJv.put("怖", 52855);
		mapPositionJv.put("思", 52859);
		mapPositionJv.put("怠", 52890);
		mapPositionJv.put("急", 52901);
		mapPositionJv.put("性", 52942);
		mapPositionJv.put("怨", 52962);
		mapPositionJv.put("怪", 52965);
		mapPositionJv.put("恋", 52977);
		mapPositionJv.put("恐", 53005);
		mapPositionJv.put("恒", 53024);
		mapPositionJv.put("恥", 53038);
		mapPositionJv.put("恨", 53050);
		mapPositionJv.put("恩", 53054);
		mapPositionJv.put("恬", 53067);
		mapPositionJv.put("恭", 53069);
		mapPositionJv.put("息", 53074);
		mapPositionJv.put("恰", 53085);
		mapPositionJv.put("恵", 53086);
		mapPositionJv.put("悉", 53089);
		mapPositionJv.put("悍", 53090);
		mapPositionJv.put("悔", 53091);
		mapPositionJv.put("悟", 53098);
		mapPositionJv.put("悠", 53100);
		mapPositionJv.put("患", 53105);
		mapPositionJv.put("悦", 53112);
		mapPositionJv.put("悩", 53115);
		mapPositionJv.put("悪", 53122);
		mapPositionJv.put("悲", 53211);
		mapPositionJv.put("悼", 53247);
		mapPositionJv.put("情", 53249);
		mapPositionJv.put("惇", 53267);
		mapPositionJv.put("惑", 53268);
		mapPositionJv.put("惚", 53272);
		mapPositionJv.put("惜", 53275);
		mapPositionJv.put("惨", 53279);
		mapPositionJv.put("惰", 53288);
		mapPositionJv.put("想", 53291);
		mapPositionJv.put("惹", 53298);
		mapPositionJv.put("愁", 53299);
		mapPositionJv.put("愈", 53301);
		mapPositionJv.put("愉", 53302);
		mapPositionJv.put("意", 53306);
		mapPositionJv.put("愚", 53346);
		mapPositionJv.put("愛", 53361);
		mapPositionJv.put("感", 53429);
		mapPositionJv.put("慈", 53475);
		mapPositionJv.put("態", 53483);
		mapPositionJv.put("慌", 53487);
		mapPositionJv.put("慎", 53490);
		mapPositionJv.put("慕", 53494);
		mapPositionJv.put("慢", 53496);
		mapPositionJv.put("慣", 53498);
		mapPositionJv.put("慨", 53506);
		mapPositionJv.put("慰", 53508);
		mapPositionJv.put("慶", 53516);
		mapPositionJv.put("慷", 53521);
		mapPositionJv.put("憂", 53522);
		mapPositionJv.put("憎", 53532);
		mapPositionJv.put("憤", 53545);
		mapPositionJv.put("憧", 53548);
		mapPositionJv.put("憩", 53550);
		mapPositionJv.put("憫", 53553);
		mapPositionJv.put("憲", 53554);
		mapPositionJv.put("憶", 53559);
		mapPositionJv.put("懇", 53560);
		mapPositionJv.put("懈", 53573);
		mapPositionJv.put("懐", 53574);
		mapPositionJv.put("懲", 53594);
		mapPositionJv.put("懸", 53604);
		mapPositionJv.put("懺", 53612);
		mapPositionJv.put("戊", 53613);
		mapPositionJv.put("成", 53614);
		mapPositionJv.put("我", 53654);
		mapPositionJv.put("戒", 53667);
		mapPositionJv.put("或", 53672);
		mapPositionJv.put("戦", 53674);
		mapPositionJv.put("戯", 53729);
		mapPositionJv.put("戴", 53733);
		mapPositionJv.put("戸", 53734);
		mapPositionJv.put("戻", 53748);
		mapPositionJv.put("房", 53751);
		mapPositionJv.put("所", 53753);
		mapPositionJv.put("扁", 53778);
		mapPositionJv.put("扇", 53780);
		mapPositionJv.put("扉", 53788);
		mapPositionJv.put("手", 53790);
		mapPositionJv.put("才", 54002);
		mapPositionJv.put("打", 54011);
		mapPositionJv.put("払", 54043);
		mapPositionJv.put("扣", 54052);
		mapPositionJv.put("扱", 54053);
		mapPositionJv.put("扶", 54054);
		mapPositionJv.put("批", 54061);
		mapPositionJv.put("承", 54071);
		mapPositionJv.put("技", 54081);
		mapPositionJv.put("抄", 54096);
		mapPositionJv.put("把", 54101);
		mapPositionJv.put("抑", 54108);
		mapPositionJv.put("抒", 54118);
		mapPositionJv.put("抓", 54119);
		mapPositionJv.put("投", 54120);
		mapPositionJv.put("抗", 54198);
		mapPositionJv.put("折", 54210);
		mapPositionJv.put("抜", 54223);
		mapPositionJv.put("択", 54259);
		mapPositionJv.put("披", 54260);
		mapPositionJv.put("抱", 54267);
		mapPositionJv.put("抵", 54277);
		mapPositionJv.put("抹", 54291);
		mapPositionJv.put("押", 54296);
		mapPositionJv.put("抽", 54321);
		mapPositionJv.put("担", 54333);
		mapPositionJv.put("拍", 54348);
		mapPositionJv.put("拒", 54356);
		mapPositionJv.put("拓", 54362);
		mapPositionJv.put("拘", 54364);
		mapPositionJv.put("拙", 54376);
		mapPositionJv.put("招", 54379);
		mapPositionJv.put("拝", 54384);
		mapPositionJv.put("拠", 54395);
		mapPositionJv.put("拡", 54397);
		mapPositionJv.put("括", 54411);
		mapPositionJv.put("拭", 54413);
		mapPositionJv.put("拱", 54416);
		mapPositionJv.put("拳", 54417);
		mapPositionJv.put("拵", 54421);
		mapPositionJv.put("拷", 54422);
		mapPositionJv.put("拾", 54426);
		mapPositionJv.put("持", 54436);
		mapPositionJv.put("指", 54461);
		mapPositionJv.put("按", 54502);
		mapPositionJv.put("挑", 54504);
		mapPositionJv.put("挙", 54511);
		mapPositionJv.put("挟", 54520);
		mapPositionJv.put("挨", 54527);
		mapPositionJv.put("挫", 54532);
		mapPositionJv.put("振", 54533);
		mapPositionJv.put("挺", 54558);
		mapPositionJv.put("挽", 54560);
		mapPositionJv.put("挾", 54565);
		mapPositionJv.put("挿", 54566);
		mapPositionJv.put("捉", 54573);
		mapPositionJv.put("捏", 54575);
		mapPositionJv.put("捕", 54576);
		mapPositionJv.put("捗", 54598);
		mapPositionJv.put("捜", 54599);
		mapPositionJv.put("捧", 54606);
		mapPositionJv.put("捨", 54607);
		mapPositionJv.put("捩", 54612);
		mapPositionJv.put("据", 54614);
		mapPositionJv.put("捲", 54618);
		mapPositionJv.put("捺", 54620);
		mapPositionJv.put("捻", 54623);
		mapPositionJv.put("掃", 54631);
		mapPositionJv.put("授", 54638);
		mapPositionJv.put("掌", 54655);
		mapPositionJv.put("掏", 54660);
		mapPositionJv.put("排", 54661);
		mapPositionJv.put("掘", 54691);
		mapPositionJv.put("掛", 54695);
		mapPositionJv.put("掟", 54711);
		mapPositionJv.put("掠", 54712);
		mapPositionJv.put("採", 54713);
		mapPositionJv.put("探", 54730);
		mapPositionJv.put("接", 54753);
		mapPositionJv.put("控", 54783);
		mapPositionJv.put("推", 54796);
		mapPositionJv.put("措", 54818);
		mapPositionJv.put("掬", 54819);
		mapPositionJv.put("掲", 54820);
		mapPositionJv.put("掴", 54828);
		mapPositionJv.put("掻", 54830);
		mapPositionJv.put("揃", 54837);
		mapPositionJv.put("揉", 54839);
		mapPositionJv.put("描", 54841);
		mapPositionJv.put("提", 54845);
		mapPositionJv.put("揚", 54873);
		mapPositionJv.put("換", 54892);
		mapPositionJv.put("握", 54900);
		mapPositionJv.put("揮", 54908);
		mapPositionJv.put("援", 54910);
		mapPositionJv.put("揶", 54919);
		mapPositionJv.put("揺", 54920);
		mapPositionJv.put("損", 54937);
		mapPositionJv.put("搗", 54971);
		mapPositionJv.put("搬", 54972);
		mapPositionJv.put("搭", 54977);
		mapPositionJv.put("携", 54984);
		mapPositionJv.put("搾", 54991);
		mapPositionJv.put("摂", 54999);
		mapPositionJv.put("摘", 55005);
		mapPositionJv.put("摩", 55017);
		mapPositionJv.put("撃", 55023);
		mapPositionJv.put("撒", 55032);
		mapPositionJv.put("撚", 55034);
		mapPositionJv.put("撤", 55035);
		mapPositionJv.put("撫", 55045);
		mapPositionJv.put("播", 55049);
		mapPositionJv.put("撮", 55050);
		mapPositionJv.put("撲", 55059);
		mapPositionJv.put("擁", 55061);
		mapPositionJv.put("操", 55065);
		mapPositionJv.put("擦", 55074);
		mapPositionJv.put("擬", 55081);
		mapPositionJv.put("擯", 55085);
		mapPositionJv.put("擲", 55087);
		mapPositionJv.put("擽", 55090);
		mapPositionJv.put("擾", 55092);
		mapPositionJv.put("攣", 55093);
		mapPositionJv.put("支", 55094);
		mapPositionJv.put("改", 55150);
		mapPositionJv.put("攻", 55202);
		mapPositionJv.put("放", 55216);
		mapPositionJv.put("政", 55249);
		mapPositionJv.put("故", 55290);
		mapPositionJv.put("敏", 55302);
		mapPositionJv.put("救", 55308);
		mapPositionJv.put("敗", 55325);
		mapPositionJv.put("教", 55334);
		mapPositionJv.put("敢", 55386);
		mapPositionJv.put("散", 55390);
		mapPositionJv.put("敦", 55409);
		mapPositionJv.put("敬", 55411);
		mapPositionJv.put("数", 55420);
		mapPositionJv.put("整", 55438);
		mapPositionJv.put("敵", 55455);
		mapPositionJv.put("敷", 55488);
		mapPositionJv.put("文", 55495);
		mapPositionJv.put("斎", 55545);
		mapPositionJv.put("斑", 55546);
		mapPositionJv.put("斗", 55548);
		mapPositionJv.put("料", 55550);
		mapPositionJv.put("斜", 55558);
		mapPositionJv.put("斡", 55568);
		mapPositionJv.put("斤", 55570);
		mapPositionJv.put("斧", 55572);
		mapPositionJv.put("斬", 55573);
		mapPositionJv.put("断", 55575);
		mapPositionJv.put("新", 55599);
		mapPositionJv.put("方", 55678);
		mapPositionJv.put("施", 55696);
		mapPositionJv.put("旁", 55706);
		mapPositionJv.put("旅", 55707);
		mapPositionJv.put("旋", 55730);
		mapPositionJv.put("族", 55737);
		mapPositionJv.put("旗", 55739);
		mapPositionJv.put("既", 55746);
		mapPositionJv.put("日", 55757);
		mapPositionJv.put("旦", 55971);
		mapPositionJv.put("旧", 55972);
		mapPositionJv.put("旨", 55985);
		mapPositionJv.put("早", 55988);
		mapPositionJv.put("旬", 56015);
		mapPositionJv.put("旭", 56016);
		mapPositionJv.put("旱", 56017);
		mapPositionJv.put("昆", 56019);
		mapPositionJv.put("昇", 56025);
		mapPositionJv.put("明", 56034);
		mapPositionJv.put("昏", 56086);
		mapPositionJv.put("易", 56088);
		mapPositionJv.put("昔", 56092);
		mapPositionJv.put("星", 56100);
		mapPositionJv.put("映", 56106);
		mapPositionJv.put("春", 56118);
		mapPositionJv.put("昨", 56140);
		mapPositionJv.put("昭", 56146);
		mapPositionJv.put("是", 56148);
		mapPositionJv.put("昼", 56153);
		mapPositionJv.put("時", 56171);
		mapPositionJv.put("晃", 56226);
		mapPositionJv.put("晒", 56227);
		mapPositionJv.put("晩", 56228);
		mapPositionJv.put("普", 56247);
		mapPositionJv.put("景", 56271);
		mapPositionJv.put("晴", 56280);
		mapPositionJv.put("晶", 56287);
		mapPositionJv.put("智", 56288);
		mapPositionJv.put("暁", 56290);
		mapPositionJv.put("暇", 56293);
		mapPositionJv.put("暈", 56297);
		mapPositionJv.put("暑", 56299);
		mapPositionJv.put("暖", 56309);
		mapPositionJv.put("暗", 56318);
		mapPositionJv.put("暢", 56342);
		mapPositionJv.put("暦", 56344);
		mapPositionJv.put("暫", 56345);
		mapPositionJv.put("暮", 56350);
		mapPositionJv.put("暴", 56356);
		mapPositionJv.put("曇", 56374);
		mapPositionJv.put("曖", 56382);
		mapPositionJv.put("曙", 56384);
		mapPositionJv.put("曜", 56385);
		mapPositionJv.put("曝", 56386);
		mapPositionJv.put("曲", 56387);
		mapPositionJv.put("更", 56396);
		mapPositionJv.put("書", 56412);
		mapPositionJv.put("曽", 56459);
		mapPositionJv.put("曾", 56460);
		mapPositionJv.put("替", 56464);
		mapPositionJv.put("最", 56467);
		mapPositionJv.put("月", 56532);
		mapPositionJv.put("有", 56558);
		mapPositionJv.put("服", 56622);
		mapPositionJv.put("朕", 56641);
		mapPositionJv.put("朗", 56643);
		mapPositionJv.put("望", 56649);
		mapPositionJv.put("朝", 56655);
		mapPositionJv.put("期", 56715);
		mapPositionJv.put("木", 56725);
		mapPositionJv.put("未", 56798);
		mapPositionJv.put("末", 56830);
		mapPositionJv.put("本", 56843);
		mapPositionJv.put("札", 56915);
		mapPositionJv.put("朱", 56918);
		mapPositionJv.put("朴", 56922);
		mapPositionJv.put("机", 56923);
		mapPositionJv.put("朽", 56929);
		mapPositionJv.put("杉", 56932);
		mapPositionJv.put("杏", 56934);
		mapPositionJv.put("材", 56935);
		mapPositionJv.put("村", 56937);
		mapPositionJv.put("杖", 56942);
		mapPositionJv.put("杜", 56943);
		mapPositionJv.put("杞", 56944);
		mapPositionJv.put("束", 56945);
		mapPositionJv.put("条", 56950);
		mapPositionJv.put("来", 56968);
		mapPositionJv.put("杭", 56985);
		mapPositionJv.put("杯", 56989);
		mapPositionJv.put("東", 56990);
		mapPositionJv.put("杵", 57063);
		mapPositionJv.put("松", 57064);
		mapPositionJv.put("板", 57070);
		mapPositionJv.put("枇", 57077);
		mapPositionJv.put("枕", 57078);
		mapPositionJv.put("林", 57082);
		mapPositionJv.put("枚", 57089);
		mapPositionJv.put("果", 57093);
		mapPositionJv.put("枝", 57106);
		mapPositionJv.put("枠", 57115);
		mapPositionJv.put("枢", 57121);
		mapPositionJv.put("枯", 57124);
		mapPositionJv.put("架", 57137);
		mapPositionJv.put("枷", 57148);
		mapPositionJv.put("枸", 57150);
		mapPositionJv.put("柄", 57151);
		mapPositionJv.put("某", 57154);
		mapPositionJv.put("染", 57157);
		mapPositionJv.put("柔", 57165);
		mapPositionJv.put("柩", 57178);
		mapPositionJv.put("柱", 57179);
		mapPositionJv.put("柳", 57182);
		mapPositionJv.put("柵", 57184);
		mapPositionJv.put("査", 57185);
		mapPositionJv.put("柿", 57192);
		mapPositionJv.put("栄", 57194);
		mapPositionJv.put("栓", 57208);
		mapPositionJv.put("栗", 57210);
		mapPositionJv.put("校", 57212);
		mapPositionJv.put("株", 57229);
		mapPositionJv.put("栴", 57236);
		mapPositionJv.put("核", 57237);
		mapPositionJv.put("根", 57246);
		mapPositionJv.put("格", 57293);
		mapPositionJv.put("栽", 57304);
		mapPositionJv.put("桁", 57306);
		mapPositionJv.put("桂", 57307);
		mapPositionJv.put("桃", 57308);
		mapPositionJv.put("案", 57317);
		mapPositionJv.put("桑", 57328);
		mapPositionJv.put("桜", 57334);
		mapPositionJv.put("桟", 57345);
		mapPositionJv.put("桧", 57347);
		mapPositionJv.put("桶", 57348);
		mapPositionJv.put("桿", 57350);
		mapPositionJv.put("梃", 57351);
		mapPositionJv.put("梅", 57354);
		mapPositionJv.put("梔", 57366);
		mapPositionJv.put("梗", 57367);
		mapPositionJv.put("梟", 57369);
		mapPositionJv.put("梢", 57372);
		mapPositionJv.put("梧", 57373);
		mapPositionJv.put("梨", 57374);
		mapPositionJv.put("梯", 57377);
		mapPositionJv.put("梱", 57382);
		mapPositionJv.put("棄", 57384);
		mapPositionJv.put("棋", 57391);
		mapPositionJv.put("棍", 57393);
		mapPositionJv.put("棒", 57394);
		mapPositionJv.put("棕", 57400);
		mapPositionJv.put("棗", 57401);
		mapPositionJv.put("棘", 57403);
		mapPositionJv.put("棚", 57405);
		mapPositionJv.put("棟", 57412);
		mapPositionJv.put("森", 57415);
		mapPositionJv.put("棺", 57420);
		mapPositionJv.put("椀", 57422);
		mapPositionJv.put("椅", 57424);
		mapPositionJv.put("植", 57425);
		mapPositionJv.put("椎", 57440);
		mapPositionJv.put("検", 57441);
		mapPositionJv.put("椰", 57497);
		mapPositionJv.put("椴", 57500);
		mapPositionJv.put("椿", 57501);
		mapPositionJv.put("楊", 57503);
		mapPositionJv.put("楔", 57505);
		mapPositionJv.put("楕", 57508);
		mapPositionJv.put("楠", 57510);
		mapPositionJv.put("業", 57512);
		mapPositionJv.put("楯", 57518);
		mapPositionJv.put("極", 57519);
		mapPositionJv.put("楷", 57538);
		mapPositionJv.put("楼", 57539);
		mapPositionJv.put("楽", 57541);
		mapPositionJv.put("概", 57566);
		mapPositionJv.put("榴", 57577);
		mapPositionJv.put("構", 57578);
		mapPositionJv.put("槌", 57599);
		mapPositionJv.put("槍", 57600);
		mapPositionJv.put("様", 57601);
		mapPositionJv.put("樋", 57607);
		mapPositionJv.put("標", 57608);
		mapPositionJv.put("模", 57628);
		mapPositionJv.put("権", 57640);
		mapPositionJv.put("横", 57672);
		mapPositionJv.put("樫", 57695);
		mapPositionJv.put("樹", 57696);
		mapPositionJv.put("樽", 57700);
		mapPositionJv.put("橈", 57701);
		mapPositionJv.put("橋", 57702);
		mapPositionJv.put("橙", 57709);
		mapPositionJv.put("機", 57712);
		mapPositionJv.put("檄", 57754);
		mapPositionJv.put("檜", 57755);
		mapPositionJv.put("檻", 57756);
		mapPositionJv.put("櫂", 57757);
		mapPositionJv.put("櫛", 57758);
		mapPositionJv.put("櫻", 57760);
		mapPositionJv.put("欄", 57761);
		mapPositionJv.put("欠", 57764);
		mapPositionJv.put("次", 57788);
		mapPositionJv.put("欧", 57809);
		mapPositionJv.put("欲", 57850);
		mapPositionJv.put("欺", 57859);
		mapPositionJv.put("歌", 57860);
		mapPositionJv.put("歎", 57876);
		mapPositionJv.put("歓", 57878);
		mapPositionJv.put("歔", 57891);
		mapPositionJv.put("止", 57892);
		mapPositionJv.put("正", 57903);
		mapPositionJv.put("此", 57954);
		mapPositionJv.put("武", 57960);
		mapPositionJv.put("歩", 58001);
		mapPositionJv.put("歪", 58019);
		mapPositionJv.put("歯", 58021);
		mapPositionJv.put("歳", 58047);
		mapPositionJv.put("歴", 58055);
		mapPositionJv.put("死", 58065);
		mapPositionJv.put("殆", 58091);
		mapPositionJv.put("殉", 58092);
		mapPositionJv.put("殊", 58099);
		mapPositionJv.put("残", 58103);
		mapPositionJv.put("殖", 58139);
		mapPositionJv.put("殲", 58143);
		mapPositionJv.put("殴", 58144);
		mapPositionJv.put("段", 58150);
		mapPositionJv.put("殺", 58155);
		mapPositionJv.put("殻", 58177);
		mapPositionJv.put("殿", 58178);
		mapPositionJv.put("毀", 58189);
		mapPositionJv.put("母", 58190);
		mapPositionJv.put("毎", 58204);
		mapPositionJv.put("毒", 58214);
		mapPositionJv.put("比", 58230);
		mapPositionJv.put("毛", 58255);
		mapPositionJv.put("氏", 58279);
		mapPositionJv.put("民", 58283);
		mapPositionJv.put("気", 58325);
		mapPositionJv.put("水", 58414);
		mapPositionJv.put("氷", 58525);
		mapPositionJv.put("永", 58553);
		mapPositionJv.put("氾", 58564);
		mapPositionJv.put("汁", 58566);
		mapPositionJv.put("求", 58568);
		mapPositionJv.put("汎", 58577);
		mapPositionJv.put("汗", 58580);
		mapPositionJv.put("汚", 58587);
		mapPositionJv.put("江", 58602);
		mapPositionJv.put("池", 58610);
		mapPositionJv.put("汲", 58611);
		mapPositionJv.put("決", 58613);
		mapPositionJv.put("汽", 58653);
		mapPositionJv.put("沃", 58656);
		mapPositionJv.put("沈", 58657);
		mapPositionJv.put("沖", 58683);
		mapPositionJv.put("没", 58692);
		mapPositionJv.put("沢", 58700);
		mapPositionJv.put("河", 58702);
		mapPositionJv.put("沸", 58713);
		mapPositionJv.put("油", 58722);
		mapPositionJv.put("治", 58737);
		mapPositionJv.put("沼", 58753);
		mapPositionJv.put("沿", 58757);
		mapPositionJv.put("況", 58763);
		mapPositionJv.put("泉", 58764);
		mapPositionJv.put("泊", 58766);
		mapPositionJv.put("泌", 58773);
		mapPositionJv.put("法", 58775);
		mapPositionJv.put("泡", 58819);
		mapPositionJv.put("波", 58824);
		mapPositionJv.put("泣", 58850);
		mapPositionJv.put("泥", 58859);
		mapPositionJv.put("注", 58878);
		mapPositionJv.put("泰", 58914);
		mapPositionJv.put("泳", 58918);
		mapPositionJv.put("洋", 58920);
		mapPositionJv.put("洒", 58936);
		mapPositionJv.put("洗", 58938);
		mapPositionJv.put("洞", 58965);
		mapPositionJv.put("津", 58969);
		mapPositionJv.put("洩", 58971);
		mapPositionJv.put("洪", 58972);
		mapPositionJv.put("活", 58978);
		mapPositionJv.put("派", 58996);
		mapPositionJv.put("流", 59008);
		mapPositionJv.put("浄", 59068);
		mapPositionJv.put("浅", 59070);
		mapPositionJv.put("浚", 59086);
		mapPositionJv.put("浜", 59087);
		mapPositionJv.put("浣", 59089);
		mapPositionJv.put("浦", 59090);
		mapPositionJv.put("浪", 59092);
		mapPositionJv.put("浮", 59101);
		mapPositionJv.put("浴", 59129);
		mapPositionJv.put("海", 59135);
		mapPositionJv.put("浸", 59230);
		mapPositionJv.put("涅", 59239);
		mapPositionJv.put("消", 59240);
		mapPositionJv.put("涎", 59283);
		mapPositionJv.put("涙", 59285);
		mapPositionJv.put("涜", 59298);
		mapPositionJv.put("液", 59299);
		mapPositionJv.put("涼", 59302);
		mapPositionJv.put("淋", 59307);
		mapPositionJv.put("淑", 59308);
		mapPositionJv.put("淘", 59311);
		mapPositionJv.put("淡", 59312);
		mapPositionJv.put("淫", 59320);
		mapPositionJv.put("深", 59329);
		mapPositionJv.put("淵", 59358);
		mapPositionJv.put("混", 59359);
		mapPositionJv.put("添", 59403);
		mapPositionJv.put("清", 59415);
		mapPositionJv.put("渇", 59444);
		mapPositionJv.put("済", 59449);
		mapPositionJv.put("渉", 59456);
		mapPositionJv.put("渋", 59457);
		mapPositionJv.put("渓", 59463);
		mapPositionJv.put("渚", 59466);
		mapPositionJv.put("減", 59467);
		mapPositionJv.put("渡", 59491);
		mapPositionJv.put("渦", 59506);
		mapPositionJv.put("温", 59512);
		mapPositionJv.put("測", 59533);
		mapPositionJv.put("港", 59544);
		mapPositionJv.put("渾", 59557);
		mapPositionJv.put("湖", 59559);
		mapPositionJv.put("湧", 59564);
		mapPositionJv.put("湯", 59566);
		mapPositionJv.put("湾", 59577);
		mapPositionJv.put("湿", 59580);
		mapPositionJv.put("満", 59589);
		mapPositionJv.put("溌", 59620);
		mapPositionJv.put("源", 59621);
		mapPositionJv.put("準", 59629);
		mapPositionJv.put("溜", 59641);
		mapPositionJv.put("溝", 59645);
		mapPositionJv.put("溢", 59646);
		mapPositionJv.put("溶", 59647);
		mapPositionJv.put("溺", 59666);
		mapPositionJv.put("滅", 59671);
		mapPositionJv.put("滋", 59677);
		mapPositionJv.put("滑", 59681);
		mapPositionJv.put("滝", 59689);
		mapPositionJv.put("滞", 59692);
		mapPositionJv.put("滲", 59698);
		mapPositionJv.put("滴", 59701);
		mapPositionJv.put("漁", 59708);
		mapPositionJv.put("漂", 59720);
		mapPositionJv.put("漆", 59736);
		mapPositionJv.put("漏", 59743);
		mapPositionJv.put("演", 59752);
		mapPositionJv.put("漕", 59770);
		mapPositionJv.put("漠", 59771);
		mapPositionJv.put("漢", 59773);
		mapPositionJv.put("漣", 59784);
		mapPositionJv.put("漫", 59785);
		mapPositionJv.put("漬", 59789);
		mapPositionJv.put("漱", 59795);
		mapPositionJv.put("漸", 59796);
		mapPositionJv.put("潅", 59801);
		mapPositionJv.put("潔", 59802);
		mapPositionJv.put("潜", 59806);
		mapPositionJv.put("潟", 59818);
		mapPositionJv.put("潤", 59819);
		mapPositionJv.put("潮", 59826);
		mapPositionJv.put("潰", 59842);
		mapPositionJv.put("澄", 59847);
		mapPositionJv.put("澱", 59853);
		mapPositionJv.put("激", 59854);
		mapPositionJv.put("濁", 59874);
		mapPositionJv.put("濃", 59886);
		mapPositionJv.put("濠", 59911);
		mapPositionJv.put("濡", 59912);
		mapPositionJv.put("濫", 59928);
		mapPositionJv.put("瀬", 59933);
		mapPositionJv.put("灌", 59938);
		mapPositionJv.put("火", 59942);
		mapPositionJv.put("灯", 59998);
		mapPositionJv.put("灰", 60013);
		mapPositionJv.put("災", 60019);
		mapPositionJv.put("炉", 60026);
		mapPositionJv.put("炊", 60028);
		mapPositionJv.put("炎", 60034);
		mapPositionJv.put("炒", 60039);
		mapPositionJv.put("炬", 60042);
		mapPositionJv.put("炭", 60044);
		mapPositionJv.put("炸", 60058);
		mapPositionJv.put("点", 60060);
		mapPositionJv.put("為", 60105);
		mapPositionJv.put("烈", 60115);
		mapPositionJv.put("烏", 60120);
		mapPositionJv.put("焚", 60122);
		mapPositionJv.put("焜", 60123);
		mapPositionJv.put("無", 60124);
		mapPositionJv.put("焦", 60283);
		mapPositionJv.put("然", 60292);
		mapPositionJv.put("焼", 60296);
		mapPositionJv.put("煉", 60315);
		mapPositionJv.put("煌", 60320);
		mapPositionJv.put("煎", 60323);
		mapPositionJv.put("煙", 60325);
		mapPositionJv.put("煤", 60337);
		mapPositionJv.put("煥", 60339);
		mapPositionJv.put("照", 60340);
		mapPositionJv.put("煩", 60352);
		mapPositionJv.put("煮", 60362);
		mapPositionJv.put("煽", 60386);
		mapPositionJv.put("熊", 60391);
		mapPositionJv.put("熏", 60393);
		mapPositionJv.put("熔", 60396);
		mapPositionJv.put("熟", 60397);
		mapPositionJv.put("熨", 60409);
		mapPositionJv.put("熱", 60411);
		mapPositionJv.put("燃", 60505);
		mapPositionJv.put("燐", 60521);
		mapPositionJv.put("燕", 60523);
		mapPositionJv.put("燗", 60524);
		mapPositionJv.put("燥", 60525);
		mapPositionJv.put("燭", 60526);
		mapPositionJv.put("燻", 60527);
		mapPositionJv.put("爆", 60532);
		mapPositionJv.put("爪", 60548);
		mapPositionJv.put("爬", 60556);
		mapPositionJv.put("爵", 60558);
		mapPositionJv.put("父", 60559);
		mapPositionJv.put("爺", 60579);
		mapPositionJv.put("爽", 60580);
		mapPositionJv.put("片", 60581);
		mapPositionJv.put("版", 60596);
		mapPositionJv.put("牙", 60601);
		mapPositionJv.put("牛", 60602);
		mapPositionJv.put("牝", 60613);
		mapPositionJv.put("牡", 60616);
		mapPositionJv.put("牧", 60620);
		mapPositionJv.put("物", 60627);
		mapPositionJv.put("特", 60672);
		mapPositionJv.put("牽", 60804);
		mapPositionJv.put("犇", 60805);
		mapPositionJv.put("犠", 60806);
		mapPositionJv.put("犬", 60810);
		mapPositionJv.put("犯", 60823);
		mapPositionJv.put("状", 60834);
		mapPositionJv.put("狂", 60837);
		mapPositionJv.put("狐", 60851);
		mapPositionJv.put("狒", 60857);
		mapPositionJv.put("狗", 60859);
		mapPositionJv.put("狙", 60860);
		mapPositionJv.put("狡", 60866);
		mapPositionJv.put("狩", 60871);
		mapPositionJv.put("独", 60878);
		mapPositionJv.put("狭", 60934);
		mapPositionJv.put("狸", 60943);
		mapPositionJv.put("狼", 60944);
		mapPositionJv.put("猛", 60946);
		mapPositionJv.put("猟", 60953);
		mapPositionJv.put("猥", 60959);
		mapPositionJv.put("猩", 60962);
		mapPositionJv.put("猪", 60964);
		mapPositionJv.put("猫", 60966);
		mapPositionJv.put("献", 60985);
		mapPositionJv.put("猶", 61002);
		mapPositionJv.put("猿", 61004);
		mapPositionJv.put("獄", 61007);
		mapPositionJv.put("獅", 61008);
		mapPositionJv.put("獏", 61009);
		mapPositionJv.put("獣", 61010);
		mapPositionJv.put("獰", 61015);
		mapPositionJv.put("獲", 61016);
		mapPositionJv.put("獺", 61020);
		mapPositionJv.put("玄", 61021);
		mapPositionJv.put("率", 61032);
		mapPositionJv.put("玉", 61037);
		mapPositionJv.put("王", 61046);
		mapPositionJv.put("玩", 61058);
		mapPositionJv.put("玻", 61060);
		mapPositionJv.put("珊", 61061);
		mapPositionJv.put("珍", 61063);
		mapPositionJv.put("珠", 61078);
		mapPositionJv.put("班", 61080);
		mapPositionJv.put("現", 61082);
		mapPositionJv.put("球", 61163);
		mapPositionJv.put("理", 61168);
		mapPositionJv.put("琥", 61197);
		mapPositionJv.put("琴", 61199);
		mapPositionJv.put("琵", 61200);
		mapPositionJv.put("琺", 61201);
		mapPositionJv.put("瑞", 61203);
		mapPositionJv.put("瑪", 61204);
		mapPositionJv.put("環", 61205);
		mapPositionJv.put("璽", 61217);
		mapPositionJv.put("瓜", 61219);
		mapPositionJv.put("瓢", 61220);
		mapPositionJv.put("瓦", 61222);
		mapPositionJv.put("瓶", 61225);
		mapPositionJv.put("甘", 61233);
		mapPositionJv.put("甚", 61246);
		mapPositionJv.put("甜", 61251);
		mapPositionJv.put("生", 61252);
		mapPositionJv.put("産", 61432);
		mapPositionJv.put("甥", 61455);
		mapPositionJv.put("甦", 61456);
		mapPositionJv.put("用", 61457);
		mapPositionJv.put("田", 61486);
		mapPositionJv.put("由", 61509);
		mapPositionJv.put("甲", 61513);
		mapPositionJv.put("申", 61531);
		mapPositionJv.put("男", 61556);
		mapPositionJv.put("町", 61572);
		mapPositionJv.put("画", 61596);
		mapPositionJv.put("畏", 61612);
		mapPositionJv.put("畑", 61613);
		mapPositionJv.put("留", 61614);
		mapPositionJv.put("畜", 61633);
		mapPositionJv.put("畝", 61636);
		mapPositionJv.put("畢", 61640);
		mapPositionJv.put("略", 61642);
		mapPositionJv.put("番", 61652);
		mapPositionJv.put("異", 61659);
		mapPositionJv.put("畳", 61685);
		mapPositionJv.put("疎", 61690);
		mapPositionJv.put("疑", 61699);
		mapPositionJv.put("疣", 61709);
		mapPositionJv.put("疥", 61710);
		mapPositionJv.put("疫", 61711);
		mapPositionJv.put("疲", 61714);
		mapPositionJv.put("疵", 61723);
		mapPositionJv.put("疼", 61726);
		mapPositionJv.put("疾", 61727);
		mapPositionJv.put("病", 61735);
		mapPositionJv.put("症", 61789);
		mapPositionJv.put("痒", 61793);
		mapPositionJv.put("痔", 61794);
		mapPositionJv.put("痕", 61795);
		mapPositionJv.put("痘", 61797);
		mapPositionJv.put("痙", 61800);
		mapPositionJv.put("痛", 61802);
		mapPositionJv.put("痢", 61821);
		mapPositionJv.put("痣", 61822);
		mapPositionJv.put("痩", 61823);
		mapPositionJv.put("痰", 61825);
		mapPositionJv.put("痴", 61828);
		mapPositionJv.put("痺", 61831);
		mapPositionJv.put("瘋", 61833);
		mapPositionJv.put("瘤", 61834);
		mapPositionJv.put("療", 61836);
		mapPositionJv.put("癇", 61838);
		mapPositionJv.put("癈", 61841);
		mapPositionJv.put("癌", 61842);
		mapPositionJv.put("癒", 61844);
		mapPositionJv.put("癖", 61845);
		mapPositionJv.put("癩", 61847);
		mapPositionJv.put("癪", 61848);
		mapPositionJv.put("癲", 61849);
		mapPositionJv.put("癸", 61850);
		mapPositionJv.put("発", 61852);
		mapPositionJv.put("登", 61992);
		mapPositionJv.put("白", 62028);
		mapPositionJv.put("百", 62087);
		mapPositionJv.put("的", 62117);
		mapPositionJv.put("皆", 62122);
		mapPositionJv.put("皇", 62130);
		mapPositionJv.put("皐", 62149);
		mapPositionJv.put("皓", 62150);
		mapPositionJv.put("皮", 62151);
		mapPositionJv.put("皸", 62175);
		mapPositionJv.put("皹", 62176);
		mapPositionJv.put("皺", 62177);
		mapPositionJv.put("皿", 62178);
		mapPositionJv.put("盆", 62180);
		mapPositionJv.put("益", 62185);
		mapPositionJv.put("盗", 62190);
		mapPositionJv.put("盛", 62219);
		mapPositionJv.put("盟", 62227);
		mapPositionJv.put("監", 62230);
		mapPositionJv.put("盤", 62241);
		mapPositionJv.put("盥", 62242);
		mapPositionJv.put("盪", 62243);
		mapPositionJv.put("目", 62244);
		mapPositionJv.put("盲", 62314);
		mapPositionJv.put("直", 62323);
		mapPositionJv.put("相", 62424);
		mapPositionJv.put("盾", 62481);
		mapPositionJv.put("省", 62482);
		mapPositionJv.put("眇", 62488);
		mapPositionJv.put("眉", 62489);
		mapPositionJv.put("看", 62493);
		mapPositionJv.put("県", 62503);
		mapPositionJv.put("真", 62512);
		mapPositionJv.put("眠", 62560);
		mapPositionJv.put("眩", 62574);
		mapPositionJv.put("眺", 62577);
		mapPositionJv.put("眼", 62580);
		mapPositionJv.put("着", 62593);
		mapPositionJv.put("睡", 62630);
		mapPositionJv.put("督", 62634);
		mapPositionJv.put("睨", 62643);
		mapPositionJv.put("睫", 62644);
		mapPositionJv.put("瞑", 62645);
		mapPositionJv.put("瞬", 62647);
		mapPositionJv.put("瞳", 62651);
		mapPositionJv.put("瞼", 62654);
		mapPositionJv.put("矍", 62655);
		mapPositionJv.put("矛", 62656);
		mapPositionJv.put("矜", 62660);
		mapPositionJv.put("矢", 62661);
		mapPositionJv.put("知", 62666);
		mapPositionJv.put("短", 62699);
		mapPositionJv.put("矮", 62728);
		mapPositionJv.put("矯", 62729);
		mapPositionJv.put("石", 62734);
		mapPositionJv.put("砂", 62766);
		mapPositionJv.put("砒", 62784);
		mapPositionJv.put("研", 62785);
		mapPositionJv.put("砕", 62808);
		mapPositionJv.put("砥", 62815);
		mapPositionJv.put("砦", 62817);
		mapPositionJv.put("砲", 62818);
		mapPositionJv.put("破", 62826);
		mapPositionJv.put("硅", 62859);
		mapPositionJv.put("硝", 62860);
		mapPositionJv.put("硫", 62863);
		mapPositionJv.put("硬", 62866);
		mapPositionJv.put("硼", 62881);
		mapPositionJv.put("碁", 62882);
		mapPositionJv.put("碇", 62887);
		mapPositionJv.put("碌", 62889);
		mapPositionJv.put("碑", 62890);
		mapPositionJv.put("碗", 62893);
		mapPositionJv.put("碧", 62894);
		mapPositionJv.put("確", 62895);
		mapPositionJv.put("磁", 62924);
		mapPositionJv.put("磔", 62931);
		mapPositionJv.put("磨", 62932);
		mapPositionJv.put("礎", 62938);
		mapPositionJv.put("礦", 62940);
		mapPositionJv.put("礫", 62942);
		mapPositionJv.put("示", 62944);
		mapPositionJv.put("礼", 62948);
		mapPositionJv.put("社", 62963);
		mapPositionJv.put("祈", 62997);
		mapPositionJv.put("祖", 63007);
		mapPositionJv.put("祝", 63018);
		mapPositionJv.put("神", 63032);
		mapPositionJv.put("票", 63084);
		mapPositionJv.put("祭", 63086);
		mapPositionJv.put("禁", 63093);
		mapPositionJv.put("禅", 63110);
		mapPositionJv.put("禍", 63120);
		mapPositionJv.put("禎", 63122);
		mapPositionJv.put("福", 63123);
		mapPositionJv.put("禿", 63142);
		mapPositionJv.put("秀", 63152);
		mapPositionJv.put("私", 63157);
		mapPositionJv.put("秋", 63182);
		mapPositionJv.put("科", 63203);
		mapPositionJv.put("秒", 63210);
		mapPositionJv.put("秘", 63214);
		mapPositionJv.put("租", 63250);
		mapPositionJv.put("秤", 63255);
		mapPositionJv.put("秩", 63256);
		mapPositionJv.put("称", 63257);
		mapPositionJv.put("移", 63262);
		mapPositionJv.put("稀", 63276);
		mapPositionJv.put("程", 63278);
		mapPositionJv.put("稍", 63282);
		mapPositionJv.put("税", 63283);
		mapPositionJv.put("稔", 63320);
		mapPositionJv.put("稚", 63321);
		mapPositionJv.put("稟", 63324);
		mapPositionJv.put("稠", 63325);
		mapPositionJv.put("種", 63326);
		mapPositionJv.put("稲", 63335);
		mapPositionJv.put("稼", 63344);
		mapPositionJv.put("稽", 63349);
		mapPositionJv.put("稿", 63350);
		mapPositionJv.put("穀", 63353);
		mapPositionJv.put("穂", 63359);
		mapPositionJv.put("積", 63361);
		mapPositionJv.put("穏", 63413);
		mapPositionJv.put("穢", 63419);
		mapPositionJv.put("穴", 63420);
		mapPositionJv.put("究", 63426);
		mapPositionJv.put("空", 63430);
		mapPositionJv.put("穿", 63492);
		mapPositionJv.put("突", 63495);
		mapPositionJv.put("窃", 63534);
		mapPositionJv.put("窒", 63540);
		mapPositionJv.put("窓", 63544);
		mapPositionJv.put("窪", 63551);
		mapPositionJv.put("窮", 63552);
		mapPositionJv.put("窯", 63560);
		mapPositionJv.put("窶", 63564);
		mapPositionJv.put("竈", 63565);
		mapPositionJv.put("立", 63566);
		mapPositionJv.put("竜", 63624);
		mapPositionJv.put("章", 63630);
		mapPositionJv.put("童", 63631);
		mapPositionJv.put("竪", 63637);
		mapPositionJv.put("端", 63638);
		mapPositionJv.put("競", 63645);
		mapPositionJv.put("竹", 63663);
		mapPositionJv.put("竿", 63677);
		mapPositionJv.put("笊", 63678);
		mapPositionJv.put("笑", 63679);
		mapPositionJv.put("笛", 63696);
		mapPositionJv.put("符", 63700);
		mapPositionJv.put("第", 63703);
		mapPositionJv.put("笹", 63723);
		mapPositionJv.put("筆", 63724);
		mapPositionJv.put("筈", 63747);
		mapPositionJv.put("等", 63748);
		mapPositionJv.put("筋", 63776);
		mapPositionJv.put("筍", 63782);
		mapPositionJv.put("筒", 63783);
		mapPositionJv.put("答", 63788);
		mapPositionJv.put("策", 63799);
		mapPositionJv.put("筮", 63803);
		mapPositionJv.put("筺", 63804);
		mapPositionJv.put("箇", 63805);
		mapPositionJv.put("箒", 63808);
		mapPositionJv.put("算", 63810);
		mapPositionJv.put("管", 63812);
		mapPositionJv.put("箪", 63829);
		mapPositionJv.put("箱", 63830);
		mapPositionJv.put("箸", 63838);
		mapPositionJv.put("節", 63840);
		mapPositionJv.put("範", 63851);
		mapPositionJv.put("築", 63854);
		mapPositionJv.put("篤", 63857);
		mapPositionJv.put("篭", 63864);
		mapPositionJv.put("簡", 63866);
		mapPositionJv.put("簪", 63879);
		mapPositionJv.put("簿", 63880);
		mapPositionJv.put("籐", 63883);
		mapPositionJv.put("籠", 63886);
		mapPositionJv.put("籤", 63889);
		mapPositionJv.put("米", 63893);
		mapPositionJv.put("籾", 63923);
		mapPositionJv.put("粉", 63924);
		mapPositionJv.put("粋", 63940);
		mapPositionJv.put("粒", 63944);
		mapPositionJv.put("粗", 63948);
		mapPositionJv.put("粘", 63960);
		mapPositionJv.put("粛", 63980);
		mapPositionJv.put("粟", 63984);
		mapPositionJv.put("粥", 63985);
		mapPositionJv.put("精", 63986);
		mapPositionJv.put("糊", 64027);
		mapPositionJv.put("糖", 64029);
		mapPositionJv.put("糞", 64038);
		mapPositionJv.put("糠", 64042);
		mapPositionJv.put("糧", 64048);
		mapPositionJv.put("糸", 64050);
		mapPositionJv.put("糺", 64056);
		mapPositionJv.put("系", 64057);
		mapPositionJv.put("糾", 64063);
		mapPositionJv.put("紀", 64069);
		mapPositionJv.put("約", 64076);
		mapPositionJv.put("紅", 64089);
		mapPositionJv.put("紊", 64107);
		mapPositionJv.put("紋", 64108);
		mapPositionJv.put("納", 64111);
		mapPositionJv.put("紐", 64139);
		mapPositionJv.put("純", 64143);
		mapPositionJv.put("紙", 64160);
		mapPositionJv.put("級", 64178);
		mapPositionJv.put("紛", 64179);
		mapPositionJv.put("素", 64191);
		mapPositionJv.put("紡", 64205);
		mapPositionJv.put("索", 64212);
		mapPositionJv.put("紫", 64214);
		mapPositionJv.put("累", 64222);
		mapPositionJv.put("細", 64226);
		mapPositionJv.put("紳", 64252);
		mapPositionJv.put("紹", 64256);
		mapPositionJv.put("紺", 64260);
		mapPositionJv.put("終", 64264);
		mapPositionJv.put("絃", 64290);
		mapPositionJv.put("組", 64291);
		mapPositionJv.put("経", 64317);
		mapPositionJv.put("結", 64381);
		mapPositionJv.put("絞", 64429);
		mapPositionJv.put("絡", 64437);
		mapPositionJv.put("給", 64441);
		mapPositionJv.put("絨", 64461);
		mapPositionJv.put("統", 64463);
		mapPositionJv.put("絵", 64498);
		mapPositionJv.put("絶", 64516);
		mapPositionJv.put("絹", 64547);
		mapPositionJv.put("継", 64557);
		mapPositionJv.put("続", 64571);
		mapPositionJv.put("綢", 64580);
		mapPositionJv.put("維", 64581);
		mapPositionJv.put("綱", 64585);
		mapPositionJv.put("網", 64590);
		mapPositionJv.put("綴", 64600);
		mapPositionJv.put("綺", 64606);
		mapPositionJv.put("綻", 64608);
		mapPositionJv.put("綽", 64609);
		mapPositionJv.put("綿", 64610);
		mapPositionJv.put("緊", 64618);
		mapPositionJv.put("総", 64632);
		mapPositionJv.put("緑", 64671);
		mapPositionJv.put("緒", 64676);
		mapPositionJv.put("緘", 64680);
		mapPositionJv.put("線", 64682);
		mapPositionJv.put("締", 64688);
		mapPositionJv.put("編", 64699);
		mapPositionJv.put("緩", 64715);
		mapPositionJv.put("緯", 64725);
		mapPositionJv.put("練", 64727);
		mapPositionJv.put("縁", 64736);
		mapPositionJv.put("縄", 64744);
		mapPositionJv.put("縊", 64756);
		mapPositionJv.put("縋", 64757);
		mapPositionJv.put("縛", 64758);
		mapPositionJv.put("縞", 64760);
		mapPositionJv.put("縦", 64761);
		mapPositionJv.put("縫", 64769);
		mapPositionJv.put("縮", 64792);
		mapPositionJv.put("縺", 64805);
		mapPositionJv.put("繁", 64806);
		mapPositionJv.put("繃", 64817);
		mapPositionJv.put("繊", 64818);
		mapPositionJv.put("繋", 64822);
		mapPositionJv.put("織", 64825);
		mapPositionJv.put("繕", 64829);
		mapPositionJv.put("繩", 64830);
		mapPositionJv.put("繭", 64831);
		mapPositionJv.put("繰", 64834);
		mapPositionJv.put("纏", 64840);
		mapPositionJv.put("缶", 64844);
		mapPositionJv.put("罅", 64848);
		mapPositionJv.put("罌", 64851);
		mapPositionJv.put("罠", 64852);
		mapPositionJv.put("罪", 64855);
		mapPositionJv.put("置", 64867);
		mapPositionJv.put("罰", 64882);
		mapPositionJv.put("署", 64891);
		mapPositionJv.put("罵", 64895);
		mapPositionJv.put("罷", 64898);
		mapPositionJv.put("罹", 64901);
		mapPositionJv.put("羅", 64902);
		mapPositionJv.put("羊", 64907);
		mapPositionJv.put("美", 64917);
		mapPositionJv.put("群", 64976);
		mapPositionJv.put("羨", 64985);
		mapPositionJv.put("義", 64987);
		mapPositionJv.put("羽", 65008);
		mapPositionJv.put("翁", 65019);
		mapPositionJv.put("翌", 65020);
		mapPositionJv.put("習", 65026);
		mapPositionJv.put("翡", 65037);
		mapPositionJv.put("翳", 65039);
		mapPositionJv.put("翻", 65040);
		mapPositionJv.put("翼", 65050);
		mapPositionJv.put("老", 65051);
		mapPositionJv.put("考", 65085);
		mapPositionJv.put("者", 65108);
		mapPositionJv.put("耐", 65109);
		mapPositionJv.put("耕", 65120);
		mapPositionJv.put("耳", 65127);
		mapPositionJv.put("耽", 65152);
		mapPositionJv.put("聊", 65153);
		mapPositionJv.put("聖", 65154);
		mapPositionJv.put("聞", 65167);
		mapPositionJv.put("聳", 65183);
		mapPositionJv.put("聴", 65184);
		mapPositionJv.put("職", 65207);
		mapPositionJv.put("聾", 65224);
		mapPositionJv.put("肇", 65226);
		mapPositionJv.put("肉", 65227);
		mapPositionJv.put("肋", 65280);
		mapPositionJv.put("肌", 65282);
		mapPositionJv.put("肖", 65290);
		mapPositionJv.put("肘", 65292);
		mapPositionJv.put("肚", 65294);
		mapPositionJv.put("肛", 65295);
		mapPositionJv.put("肝", 65296);
		mapPositionJv.put("股", 65306);
		mapPositionJv.put("肢", 65307);
		mapPositionJv.put("肥", 65308);
		mapPositionJv.put("肩", 65327);
		mapPositionJv.put("肯", 65345);
		mapPositionJv.put("育", 65347);
		mapPositionJv.put("肺", 65355);
		mapPositionJv.put("胃", 65369);
		mapPositionJv.put("胆", 65384);
		mapPositionJv.put("背", 65389);
		mapPositionJv.put("胎", 65415);
		mapPositionJv.put("胚", 65420);
		mapPositionJv.put("胞", 65423);
		mapPositionJv.put("胡", 65425);
		mapPositionJv.put("胴", 65437);
		mapPositionJv.put("胸", 65442);
		mapPositionJv.put("能", 65452);
		mapPositionJv.put("脂", 65478);
		mapPositionJv.put("脅", 65485);
		mapPositionJv.put("脆", 65492);
		mapPositionJv.put("脇", 65493);
		mapPositionJv.put("脈", 65502);
		mapPositionJv.put("脊", 65507);
		mapPositionJv.put("脚", 65509);
		mapPositionJv.put("脛", 65514);
		mapPositionJv.put("脱", 65516);
		mapPositionJv.put("脳", 65548);
		mapPositionJv.put("脹", 65584);
		mapPositionJv.put("脾", 65588);
		mapPositionJv.put("腋", 65590);
		mapPositionJv.put("腎", 65591);
		mapPositionJv.put("腐", 65598);
		mapPositionJv.put("腓", 65615);
		mapPositionJv.put("腕", 65616);
		mapPositionJv.put("腫", 65632);
		mapPositionJv.put("腰", 65638);
		mapPositionJv.put("腱", 65645);
		mapPositionJv.put("腸", 65646);
		mapPositionJv.put("腹", 65657);
		mapPositionJv.put("腿", 65683);
		mapPositionJv.put("膀", 65685);
		mapPositionJv.put("膏", 65687);
		mapPositionJv.put("膚", 65689);
		mapPositionJv.put("膜", 65692);
		mapPositionJv.put("膝", 65693);
		mapPositionJv.put("膠", 65695);
		mapPositionJv.put("膣", 65697);
		mapPositionJv.put("膨", 65698);
		mapPositionJv.put("膵", 65705);
		mapPositionJv.put("膿", 65707);
		mapPositionJv.put("臀", 65712);
		mapPositionJv.put("臆", 65713);
		mapPositionJv.put("臍", 65716);
		mapPositionJv.put("臓", 65717);
		mapPositionJv.put("臣", 65719);
		mapPositionJv.put("臨", 65721);
		mapPositionJv.put("自", 65733);
		mapPositionJv.put("臭", 65899);
		mapPositionJv.put("至", 65907);
		mapPositionJv.put("致", 65913);
		mapPositionJv.put("臼", 65917);
		mapPositionJv.put("舅", 65921);
		mapPositionJv.put("興", 65922);
		mapPositionJv.put("舌", 65937);
		mapPositionJv.put("舎", 65945);
		mapPositionJv.put("舐", 65946);
		mapPositionJv.put("舗", 65947);
		mapPositionJv.put("舞", 65952);
		mapPositionJv.put("舟", 65972);
		mapPositionJv.put("航", 65976);
		mapPositionJv.put("舵", 66019);
		mapPositionJv.put("舶", 66020);
		mapPositionJv.put("舷", 66023);
		mapPositionJv.put("船", 66025);
		mapPositionJv.put("艀", 66103);
		mapPositionJv.put("艇", 66106);
		mapPositionJv.put("艙", 66108);
		mapPositionJv.put("艦", 66112);
		mapPositionJv.put("艪", 66115);
		mapPositionJv.put("良", 66116);
		mapPositionJv.put("艱", 66124);
		mapPositionJv.put("色", 66125);
		mapPositionJv.put("艶", 66142);
		mapPositionJv.put("芋", 66144);
		mapPositionJv.put("芍", 66146);
		mapPositionJv.put("芝", 66147);
		mapPositionJv.put("芥", 66153);
		mapPositionJv.put("芯", 66154);
		mapPositionJv.put("花", 66155);
		mapPositionJv.put("芳", 66222);
		mapPositionJv.put("芸", 66227);
		mapPositionJv.put("芽", 66234);
		mapPositionJv.put("苔", 66239);
		mapPositionJv.put("苗", 66240);
		mapPositionJv.put("苛", 66250);
		mapPositionJv.put("若", 66254);
		mapPositionJv.put("苦", 66281);
		mapPositionJv.put("英", 66310);
		mapPositionJv.put("苺", 66326);
		mapPositionJv.put("茂", 66327);
		mapPositionJv.put("茄", 66329);
		mapPositionJv.put("茎", 66330);
		mapPositionJv.put("茣", 66331);
		mapPositionJv.put("茶", 66332);
		mapPositionJv.put("茸", 66346);
		mapPositionJv.put("茹", 66348);
		mapPositionJv.put("草", 66349);
		mapPositionJv.put("荒", 66377);
		mapPositionJv.put("荘", 66395);
		mapPositionJv.put("荷", 66398);
		mapPositionJv.put("莚", 66436);
		mapPositionJv.put("莫", 66437);
		mapPositionJv.put("菅", 66439);
		mapPositionJv.put("菊", 66440);
		mapPositionJv.put("菓", 66445);
		mapPositionJv.put("菖", 66448);
		mapPositionJv.put("菜", 66449);
		mapPositionJv.put("菠", 66459);
		mapPositionJv.put("菩", 66460);
		mapPositionJv.put("華", 66463);
		mapPositionJv.put("萎", 66474);
		mapPositionJv.put("落", 66477);
		mapPositionJv.put("葉", 66508);
		mapPositionJv.put("著", 66518);
		mapPositionJv.put("葡", 66538);
		mapPositionJv.put("葬", 66542);
		mapPositionJv.put("葱", 66556);
		mapPositionJv.put("蒔", 66557);
		mapPositionJv.put("蒜", 66558);
		mapPositionJv.put("蒸", 66559);
		mapPositionJv.put("蒿", 66573);
		mapPositionJv.put("蓄", 66574);
		mapPositionJv.put("蓋", 66581);
		mapPositionJv.put("蓮", 66583);
		mapPositionJv.put("蔑", 66586);
		mapPositionJv.put("蔓", 66588);
		mapPositionJv.put("蔦", 66590);
		mapPositionJv.put("蔬", 66591);
		mapPositionJv.put("蔵", 66592);
		mapPositionJv.put("蕎", 66598);
		mapPositionJv.put("蕩", 66600);
		mapPositionJv.put("蕾", 66603);
		mapPositionJv.put("薄", 66604);
		mapPositionJv.put("薇", 66635);
		mapPositionJv.put("薊", 66636);
		mapPositionJv.put("薔", 66637);
		mapPositionJv.put("薙", 66640);
		mapPositionJv.put("薦", 66641);
		mapPositionJv.put("薩", 66643);
		mapPositionJv.put("薪", 66644);
		mapPositionJv.put("薫", 66649);
		mapPositionJv.put("薬", 66655);
		mapPositionJv.put("薮", 66680);
		mapPositionJv.put("藁", 66681);
		mapPositionJv.put("藍", 66683);
		mapPositionJv.put("藜", 66686);
		mapPositionJv.put("藤", 66687);
		mapPositionJv.put("藩", 66689);
		mapPositionJv.put("藻", 66694);
		mapPositionJv.put("蘂", 66698);
		mapPositionJv.put("蘆", 66699);
		mapPositionJv.put("蘚", 66700);
		mapPositionJv.put("蘭", 66701);
		mapPositionJv.put("虎", 66702);
		mapPositionJv.put("虐", 66709);
		mapPositionJv.put("虚", 66713);
		mapPositionJv.put("虫", 66726);
		mapPositionJv.put("虱", 66735);
		mapPositionJv.put("虹", 66736);
		mapPositionJv.put("虻", 66739);
		mapPositionJv.put("蚊", 66740);
		mapPositionJv.put("蚋", 66748);
		mapPositionJv.put("蚕", 66749);
		mapPositionJv.put("蚤", 66752);
		mapPositionJv.put("蚯", 66753);
		mapPositionJv.put("蚰", 66754);
		mapPositionJv.put("蛆", 66756);
		mapPositionJv.put("蛇", 66757);
		mapPositionJv.put("蛋", 66767);
		mapPositionJv.put("蛍", 66768);
		mapPositionJv.put("蛔", 66773);
		mapPositionJv.put("蛙", 66774);
		mapPositionJv.put("蛞", 66778);
		mapPositionJv.put("蛤", 66779);
		mapPositionJv.put("蛭", 66780);
		mapPositionJv.put("蛮", 66781);
		mapPositionJv.put("蛯", 66786);
		mapPositionJv.put("蛸", 66787);
		mapPositionJv.put("蛾", 66788);
		mapPositionJv.put("蜂", 66789);
		mapPositionJv.put("蜘", 66791);
		mapPositionJv.put("蜜", 66794);
		mapPositionJv.put("蜥", 66797);
		mapPositionJv.put("蜻", 66798);
		mapPositionJv.put("蜿", 66799);
		mapPositionJv.put("蝉", 66800);
		mapPositionJv.put("蝋", 66801);
		mapPositionJv.put("蝎", 66803);
		mapPositionJv.put("蝕", 66804);
		mapPositionJv.put("蝗", 66805);
		mapPositionJv.put("蝙", 66807);
		mapPositionJv.put("蝟", 66808);
		mapPositionJv.put("蝦", 66809);
		mapPositionJv.put("蝶", 66811);
		mapPositionJv.put("蝸", 66821);
		mapPositionJv.put("蝿", 66822);
		mapPositionJv.put("融", 66823);
		mapPositionJv.put("螢", 66831);
		mapPositionJv.put("螺", 66836);
		mapPositionJv.put("蟆", 66842);
		mapPositionJv.put("蟇", 66843);
		mapPositionJv.put("蟯", 66844);
		mapPositionJv.put("蟷", 66845);
		mapPositionJv.put("蟹", 66846);
		mapPositionJv.put("蟻", 66847);
		mapPositionJv.put("蠍", 66849);
		mapPositionJv.put("蠱", 66851);
		mapPositionJv.put("血", 66852);
		mapPositionJv.put("衆", 66882);
		mapPositionJv.put("行", 66885);
		mapPositionJv.put("衒", 66945);
		mapPositionJv.put("術", 66946);
		mapPositionJv.put("街", 66949);
		mapPositionJv.put("衛", 66960);
		mapPositionJv.put("衝", 66967);
		mapPositionJv.put("衣", 66976);
		mapPositionJv.put("表", 66992);
		mapPositionJv.put("衰", 67026);
		mapPositionJv.put("衷", 67033);
		mapPositionJv.put("衿", 67035);
		mapPositionJv.put("袂", 67036);
		mapPositionJv.put("袋", 67037);
		mapPositionJv.put("袖", 67043);
		mapPositionJv.put("被", 67045);
		mapPositionJv.put("袴", 67070);
		mapPositionJv.put("裁", 67071);
		mapPositionJv.put("裂", 67082);
		mapPositionJv.put("装", 67086);
		mapPositionJv.put("裏", 67099);
		mapPositionJv.put("裕", 67112);
		mapPositionJv.put("補", 67114);
		mapPositionJv.put("裸", 67145);
		mapPositionJv.put("製", 67155);
		mapPositionJv.put("裾", 67185);
		mapPositionJv.put("複", 67187);
		mapPositionJv.put("褐", 67213);
		mapPositionJv.put("褒", 67214);
		mapPositionJv.put("褪", 67220);
		mapPositionJv.put("襖", 67222);
		mapPositionJv.put("襟", 67223);
		mapPositionJv.put("襤", 67228);
		mapPositionJv.put("襲", 67229);
		mapPositionJv.put("西", 67231);
		mapPositionJv.put("要", 67265);
		mapPositionJv.put("覆", 67291);
		mapPositionJv.put("覇", 67300);
		mapPositionJv.put("見", 67303);
		mapPositionJv.put("規", 67411);
		mapPositionJv.put("視", 67429);
		mapPositionJv.put("覗", 67437);
		mapPositionJv.put("覚", 67438);
		mapPositionJv.put("親", 67447);
		mapPositionJv.put("観", 67478);
		mapPositionJv.put("角", 67505);
		mapPositionJv.put("觝", 67515);
		mapPositionJv.put("解", 67517);
		mapPositionJv.put("触", 67565);
		mapPositionJv.put("言", 67573);
		mapPositionJv.put("訂", 67611);
		mapPositionJv.put("訃", 67614);
		mapPositionJv.put("計", 67615);
		mapPositionJv.put("訊", 67645);
		mapPositionJv.put("討", 67646);
		mapPositionJv.put("訓", 67660);
		mapPositionJv.put("託", 67675);
		mapPositionJv.put("記", 67679);
		mapPositionJv.put("訛", 67719);
		mapPositionJv.put("訪", 67722);
		mapPositionJv.put("設", 67730);
		mapPositionJv.put("許", 67745);
		mapPositionJv.put("訳", 67756);
		mapPositionJv.put("訴", 67761);
		mapPositionJv.put("診", 67769);
		mapPositionJv.put("註", 67784);
		mapPositionJv.put("証", 67786);
		mapPositionJv.put("詐", 67815);
		mapPositionJv.put("詔", 67823);
		mapPositionJv.put("評", 67826);
		mapPositionJv.put("詠", 67836);
		mapPositionJv.put("試", 67839);
		mapPositionJv.put("詩", 67883);
		mapPositionJv.put("詫", 67894);
		mapPositionJv.put("詭", 67898);
		mapPositionJv.put("詮", 67902);
		mapPositionJv.put("詰", 67903);
		mapPositionJv.put("話", 67912);
		mapPositionJv.put("該", 67935);
		mapPositionJv.put("詳", 67939);
		mapPositionJv.put("誂", 67948);
		mapPositionJv.put("誇", 67949);
		mapPositionJv.put("誉", 67957);
		mapPositionJv.put("誌", 67959);
		mapPositionJv.put("認", 67962);
		mapPositionJv.put("誓", 67983);
		mapPositionJv.put("誕", 67988);
		mapPositionJv.put("誘", 67993);
		mapPositionJv.put("語", 68007);
		mapPositionJv.put("誠", 68023);
		mapPositionJv.put("誣", 68031);
		mapPositionJv.put("誤", 68032);
		mapPositionJv.put("説", 68048);
		mapPositionJv.put("読", 68060);
		mapPositionJv.put("誰", 68077);
		mapPositionJv.put("課", 68079);
		mapPositionJv.put("誹", 68089);
		mapPositionJv.put("調", 68091);
		mapPositionJv.put("諄", 68155);
		mapPositionJv.put("談", 68156);
		mapPositionJv.put("請", 68163);
		mapPositionJv.put("諌", 68173);
		mapPositionJv.put("論", 68174);
		mapPositionJv.put("諜", 68200);
		mapPositionJv.put("諦", 68202);
		mapPositionJv.put("諫", 68203);
		mapPositionJv.put("諭", 68205);
		mapPositionJv.put("諮", 68207);
		mapPositionJv.put("諷", 68210);
		mapPositionJv.put("諸", 68212);
		mapPositionJv.put("諺", 68221);
		mapPositionJv.put("諾", 68222);
		mapPositionJv.put("謀", 68224);
		mapPositionJv.put("謁", 68229);
		mapPositionJv.put("謄", 68232);
		mapPositionJv.put("謎", 68237);
		mapPositionJv.put("謙", 68241);
		mapPositionJv.put("講", 68248);
		mapPositionJv.put("謝", 68264);
		mapPositionJv.put("謡", 68268);
		mapPositionJv.put("謬", 68269);
		mapPositionJv.put("謳", 68270);
		mapPositionJv.put("謹", 68271);
		mapPositionJv.put("識", 68280);
		mapPositionJv.put("警", 68283);
		mapPositionJv.put("譬", 68305);
		mapPositionJv.put("議", 68306);
		mapPositionJv.put("譲", 68321);
		mapPositionJv.put("譴", 68337);
		mapPositionJv.put("護", 68338);
		mapPositionJv.put("谷", 68344);
		mapPositionJv.put("豆", 68348);
		mapPositionJv.put("豊", 68353);
		mapPositionJv.put("豌", 68363);
		mapPositionJv.put("豐", 68364);
		mapPositionJv.put("豚", 68365);
		mapPositionJv.put("象", 68374);
		mapPositionJv.put("豪", 68386);
		mapPositionJv.put("豹", 68414);
		mapPositionJv.put("貝", 68416);
		mapPositionJv.put("貞", 68422);
		mapPositionJv.put("負", 68432);
		mapPositionJv.put("財", 68451);
		mapPositionJv.put("貢", 68473);
		mapPositionJv.put("貧", 68479);
		mapPositionJv.put("貨", 68512);
		mapPositionJv.put("販", 68520);
		mapPositionJv.put("貪", 68539);
		mapPositionJv.put("貫", 68543);
		mapPositionJv.put("責", 68550);
		mapPositionJv.put("貯", 68562);
		mapPositionJv.put("貰", 68581);
		mapPositionJv.put("貴", 68582);
		mapPositionJv.put("貶", 68601);
		mapPositionJv.put("買", 68602);
		mapPositionJv.put("貸", 68633);
		mapPositionJv.put("費", 68653);
		mapPositionJv.put("貼", 68662);
		mapPositionJv.put("貿", 68666);
		mapPositionJv.put("賀", 68707);
		mapPositionJv.put("賃", 68711);
		mapPositionJv.put("賄", 68728);
		mapPositionJv.put("資", 68733);
		mapPositionJv.put("賊", 68761);
		mapPositionJv.put("賑", 68762);
		mapPositionJv.put("賓", 68765);
		mapPositionJv.put("賛", 68767);
		mapPositionJv.put("賜", 68777);
		mapPositionJv.put("賞", 68779);
		mapPositionJv.put("賠", 68795);
		mapPositionJv.put("賢", 68801);
		mapPositionJv.put("賦", 68810);
		mapPositionJv.put("質", 68814);
		mapPositionJv.put("賭", 68829);
		mapPositionJv.put("購", 68837);
		mapPositionJv.put("贅", 68849);
		mapPositionJv.put("贈", 68851);
		mapPositionJv.put("贋", 68870);
		mapPositionJv.put("贓", 68872);
		mapPositionJv.put("贔", 68873);
		mapPositionJv.put("赤", 68874);
		mapPositionJv.put("赦", 68953);
		mapPositionJv.put("赫", 68955);
		mapPositionJv.put("走", 68957);
		mapPositionJv.put("赴", 68964);
		mapPositionJv.put("起", 68968);
		mapPositionJv.put("超", 68987);
		mapPositionJv.put("越", 69036);
		mapPositionJv.put("趣", 69041);
		mapPositionJv.put("趨", 69047);
		mapPositionJv.put("足", 69048);
		mapPositionJv.put("跋", 69103);
		mapPositionJv.put("距", 69104);
		mapPositionJv.put("跡", 69105);
		mapPositionJv.put("跣", 69107);
		mapPositionJv.put("跨", 69108);
		mapPositionJv.put("跪", 69110);
		mapPositionJv.put("路", 69111);
		mapPositionJv.put("跳", 69114);
		mapPositionJv.put("踊", 69124);
		mapPositionJv.put("踏", 69131);
		mapPositionJv.put("踝", 69142);
		mapPositionJv.put("踵", 69143);
		mapPositionJv.put("蹄", 69144);
		mapPositionJv.put("蹌", 69147);
		mapPositionJv.put("蹲", 69148);
		mapPositionJv.put("蹴", 69152);
		mapPositionJv.put("躊", 69154);
		mapPositionJv.put("躍", 69157);
		mapPositionJv.put("躑", 69162);
		mapPositionJv.put("躓", 69163);
		mapPositionJv.put("躙", 69164);
		mapPositionJv.put("身", 69165);
		mapPositionJv.put("躾", 69193);
		mapPositionJv.put("軈", 69194);
		mapPositionJv.put("車", 69195);
		mapPositionJv.put("軌", 69226);
		mapPositionJv.put("軍", 69229);
		mapPositionJv.put("軒", 69286);
		mapPositionJv.put("軟", 69293);
		mapPositionJv.put("転", 69323);
		mapPositionJv.put("軸", 69378);
		mapPositionJv.put("軽", 69379);
		mapPositionJv.put("載", 69408);
		mapPositionJv.put("輝", 69410);
		mapPositionJv.put("輩", 69414);
		mapPositionJv.put("輪", 69415);
		mapPositionJv.put("輸", 69424);
		mapPositionJv.put("轢", 69532);
		mapPositionJv.put("辛", 69533);
		mapPositionJv.put("辜", 69543);
		mapPositionJv.put("辞", 69544);
		mapPositionJv.put("辱", 69558);
		mapPositionJv.put("農", 69559);
		mapPositionJv.put("辺", 69628);
		mapPositionJv.put("辻", 69632);
		mapPositionJv.put("込", 69638);
		mapPositionJv.put("辿", 69640);
		mapPositionJv.put("迂", 69641);
		mapPositionJv.put("迅", 69642);
		mapPositionJv.put("迎", 69644);
		mapPositionJv.put("近", 69651);
		mapPositionJv.put("返", 69684);
		mapPositionJv.put("迚", 69708);
		mapPositionJv.put("迫", 69709);
		mapPositionJv.put("述", 69713);
		mapPositionJv.put("迷", 69716);
		mapPositionJv.put("追", 69727);
		mapPositionJv.put("退", 69809);
		mapPositionJv.put("送", 69839);
		mapPositionJv.put("逃", 69861);
		mapPositionJv.put("逆", 69887);
		mapPositionJv.put("透", 69910);
		mapPositionJv.put("逐", 69934);
		mapPositionJv.put("逓", 69938);
		mapPositionJv.put("途", 69944);
		mapPositionJv.put("逕", 69957);
		mapPositionJv.put("逗", 69958);
		mapPositionJv.put("這", 69959);
		mapPositionJv.put("通", 69960);
		mapPositionJv.put("逝", 70089);
		mapPositionJv.put("逞", 70091);
		mapPositionJv.put("速", 70092);
		mapPositionJv.put("造", 70107);
		mapPositionJv.put("逢", 70117);
		mapPositionJv.put("連", 70121);
		mapPositionJv.put("逮", 70173);
		mapPositionJv.put("週", 70176);
		mapPositionJv.put("進", 70183);
		mapPositionJv.put("逸", 70217);
		mapPositionJv.put("遁", 70221);
		mapPositionJv.put("遂", 70222);
		mapPositionJv.put("遅", 70226);
		mapPositionJv.put("遇", 70246);
		mapPositionJv.put("遊", 70247);
		mapPositionJv.put("運", 70267);
		mapPositionJv.put("遍", 70310);
		mapPositionJv.put("過", 70313);
		mapPositionJv.put("道", 70340);
		mapPositionJv.put("達", 70366);
		mapPositionJv.put("違", 70373);
		mapPositionJv.put("遠", 70385);
		mapPositionJv.put("遡", 70418);
		mapPositionJv.put("遣", 70419);
		mapPositionJv.put("遥", 70422);
		mapPositionJv.put("適", 70423);
		mapPositionJv.put("遭", 70477);
		mapPositionJv.put("遮", 70483);
		mapPositionJv.put("遵", 70488);
		mapPositionJv.put("選", 70493);
		mapPositionJv.put("遺", 70521);
		mapPositionJv.put("避", 70540);
		mapPositionJv.put("還", 70561);
		mapPositionJv.put("邦", 70564);
		mapPositionJv.put("邪", 70566);
		mapPositionJv.put("邯", 70575);
		mapPositionJv.put("邸", 70577);
		mapPositionJv.put("郊", 70579);
		mapPositionJv.put("郎", 70580);
		mapPositionJv.put("郡", 70581);
		mapPositionJv.put("部", 70584);
		mapPositionJv.put("郭", 70601);
		mapPositionJv.put("郵", 70602);
		mapPositionJv.put("郷", 70617);
		mapPositionJv.put("都", 70620);
		mapPositionJv.put("鄙", 70643);
		mapPositionJv.put("酉", 70645);
		mapPositionJv.put("配", 70648);
		mapPositionJv.put("酒", 70677);
		mapPositionJv.put("酔", 70696);
		mapPositionJv.put("酢", 70702);
		mapPositionJv.put("酪", 70706);
		mapPositionJv.put("酵", 70710);
		mapPositionJv.put("酷", 70714);
		mapPositionJv.put("酸", 70718);
		mapPositionJv.put("醗", 70728);
		mapPositionJv.put("醜", 70729);
		mapPositionJv.put("醤", 70735);
		mapPositionJv.put("醸", 70736);
		mapPositionJv.put("釈", 70740);
		mapPositionJv.put("里", 70747);
		mapPositionJv.put("重", 70751);
		mapPositionJv.put("野", 70804);
		mapPositionJv.put("量", 70856);
		mapPositionJv.put("金", 70859);
		mapPositionJv.put("釘", 70913);
		mapPositionJv.put("釜", 70919);
		mapPositionJv.put("針", 70920);
		mapPositionJv.put("釣", 70930);
		mapPositionJv.put("鈍", 70967);
		mapPositionJv.put("鈎", 70980);
		mapPositionJv.put("鈴", 70981);
		mapPositionJv.put("鉄", 70987);
		mapPositionJv.put("鉋", 71072);
		mapPositionJv.put("鉗", 71075);
		mapPositionJv.put("鉛", 71076);
		mapPositionJv.put("鉞", 71083);
		mapPositionJv.put("鉢", 71084);
		mapPositionJv.put("鉤", 71087);
		mapPositionJv.put("鉱", 71090);
		mapPositionJv.put("銀", 71111);
		mapPositionJv.put("銃", 71124);
		mapPositionJv.put("銅", 71134);
		mapPositionJv.put("銑", 71150);
		mapPositionJv.put("銘", 71151);
		mapPositionJv.put("銚", 71157);
		mapPositionJv.put("銜", 71158);
		mapPositionJv.put("銭", 71159);
		mapPositionJv.put("鋏", 71161);
		mapPositionJv.put("鋒", 71162);
		mapPositionJv.put("鋩", 71163);
		mapPositionJv.put("鋭", 71164);
		mapPositionJv.put("鋳", 71170);
		mapPositionJv.put("鋸", 71180);
		mapPositionJv.put("鋼", 71183);
		mapPositionJv.put("錆", 71194);
		mapPositionJv.put("錘", 71196);
		mapPositionJv.put("錠", 71198);
		mapPositionJv.put("錦", 71201);
		mapPositionJv.put("錨", 71204);
		mapPositionJv.put("錫", 71210);
		mapPositionJv.put("錬", 71212);
		mapPositionJv.put("錯", 71214);
		mapPositionJv.put("録", 71218);
		mapPositionJv.put("鍋", 71223);
		mapPositionJv.put("鍍", 71233);
		mapPositionJv.put("鍛", 71234);
		mapPositionJv.put("鍵", 71242);
		mapPositionJv.put("鍼", 71250);
		mapPositionJv.put("鍾", 71252);
		mapPositionJv.put("鎌", 71254);
		mapPositionJv.put("鎖", 71256);
		mapPositionJv.put("鎧", 71262);
		mapPositionJv.put("鎮", 71263);
		mapPositionJv.put("鏡", 71277);
		mapPositionJv.put("鐘", 71282);
		mapPositionJv.put("鑑", 71286);
		mapPositionJv.put("長", 71292);
		mapPositionJv.put("門", 71416);
		mapPositionJv.put("閂", 71427);
		mapPositionJv.put("閃", 71428);
		mapPositionJv.put("閉", 71429);
		mapPositionJv.put("開", 71444);
		mapPositionJv.put("閏", 71502);
		mapPositionJv.put("閑", 71503);
		mapPositionJv.put("間", 71507);
		mapPositionJv.put("関", 71536);
		mapPositionJv.put("閣", 71561);
		mapPositionJv.put("閥", 71566);
		mapPositionJv.put("閨", 71568);
		mapPositionJv.put("閲", 71570);
		mapPositionJv.put("闇", 71576);
		mapPositionJv.put("闖", 71578);
		mapPositionJv.put("闘", 71580);
		mapPositionJv.put("阪", 71597);
		mapPositionJv.put("防", 71599);
		mapPositionJv.put("阻", 71630);
		mapPositionJv.put("阿", 71635);
		mapPositionJv.put("附", 71637);
		mapPositionJv.put("降", 71644);
		mapPositionJv.put("限", 71668);
		mapPositionJv.put("陛", 71679);
		mapPositionJv.put("院", 71680);
		mapPositionJv.put("陣", 71681);
		mapPositionJv.put("除", 71687);
		mapPositionJv.put("陥", 71703);
		mapPositionJv.put("陪", 71707);
		mapPositionJv.put("陰", 71713);
		mapPositionJv.put("陳", 71729);
		mapPositionJv.put("陵", 71741);
		mapPositionJv.put("陶", 71742);
		mapPositionJv.put("陸", 71753);
		mapPositionJv.put("険", 71782);
		mapPositionJv.put("陽", 71790);
		mapPositionJv.put("隅", 71799);
		mapPositionJv.put("隆", 71801);
		mapPositionJv.put("隈", 71805);
		mapPositionJv.put("隊", 71806);
		mapPositionJv.put("階", 71811);
		mapPositionJv.put("随", 71824);
		mapPositionJv.put("隔", 71832);
		mapPositionJv.put("隕", 71839);
		mapPositionJv.put("隘", 71840);
		mapPositionJv.put("隙", 71841);
		mapPositionJv.put("際", 71843);
		mapPositionJv.put("障", 71846);
		mapPositionJv.put("隠", 71852);
		mapPositionJv.put("隣", 71867);
		mapPositionJv.put("隷", 71880);
		mapPositionJv.put("隻", 71882);
		mapPositionJv.put("雀", 71884);
		mapPositionJv.put("雄", 71885);
		mapPositionJv.put("雅", 71893);
		mapPositionJv.put("集", 71898);
		mapPositionJv.put("雇", 71920);
		mapPositionJv.put("雉", 71933);
		mapPositionJv.put("雌", 71935);
		mapPositionJv.put("雑", 71946);
		mapPositionJv.put("雛", 71976);
		mapPositionJv.put("離", 71982);
		mapPositionJv.put("難", 72000);
		mapPositionJv.put("雨", 72049);
		mapPositionJv.put("雪", 72069);
		mapPositionJv.put("雫", 72081);
		mapPositionJv.put("雰", 72082);
		mapPositionJv.put("雲", 72083);
		mapPositionJv.put("零", 72089);
		mapPositionJv.put("雷", 72099);
		mapPositionJv.put("雹", 72106);
		mapPositionJv.put("電", 72107);
		mapPositionJv.put("需", 72316);
		mapPositionJv.put("震", 72322);
		mapPositionJv.put("霊", 72332);
		mapPositionJv.put("霙", 72339);
		mapPositionJv.put("霜", 72340);
		mapPositionJv.put("霞", 72346);
		mapPositionJv.put("霧", 72348);
		mapPositionJv.put("露", 72353);
		mapPositionJv.put("靄", 72367);
		mapPositionJv.put("青", 72368);
		mapPositionJv.put("静", 72449);
		mapPositionJv.put("非", 72465);
		mapPositionJv.put("靡", 72548);
		mapPositionJv.put("面", 72549);
		mapPositionJv.put("靨", 72575);
		mapPositionJv.put("革", 72576);
		mapPositionJv.put("靴", 72585);
		mapPositionJv.put("鞄", 72600);
		mapPositionJv.put("鞍", 72601);
		mapPositionJv.put("鞘", 72603);
		mapPositionJv.put("鞦", 72604);
		mapPositionJv.put("鞭", 72605);
		mapPositionJv.put("韓", 72606);
		mapPositionJv.put("韜", 72608);
		mapPositionJv.put("韮", 72609);
		mapPositionJv.put("音", 72611);
		mapPositionJv.put("韻", 72637);
		mapPositionJv.put("響", 72641);
		mapPositionJv.put("頂", 72644);
		mapPositionJv.put("頃", 72654);
		mapPositionJv.put("項", 72656);
		mapPositionJv.put("順", 72658);
		mapPositionJv.put("預", 72674);
		mapPositionJv.put("頑", 72688);
		mapPositionJv.put("頒", 72697);
		mapPositionJv.put("頓", 72699);
		mapPositionJv.put("領", 72704);
		mapPositionJv.put("頬", 72715);
		mapPositionJv.put("頭", 72718);
		mapPositionJv.put("頷", 72749);
		mapPositionJv.put("頸", 72750);
		mapPositionJv.put("頻", 72758);
		mapPositionJv.put("頼", 72765);
		mapPositionJv.put("顆", 72771);
		mapPositionJv.put("題", 72772);
		mapPositionJv.put("額", 72777);
		mapPositionJv.put("顎", 72780);
		mapPositionJv.put("顔", 72784);
		mapPositionJv.put("顕", 72806);
		mapPositionJv.put("願", 72813);
		mapPositionJv.put("類", 72820);
		mapPositionJv.put("顧", 72829);
		mapPositionJv.put("風", 72836);
		mapPositionJv.put("颯", 72917);
		mapPositionJv.put("颶", 72918);
		mapPositionJv.put("飛", 72919);
		mapPositionJv.put("食", 72972);
		mapPositionJv.put("飢", 73038);
		mapPositionJv.put("飯", 73044);
		mapPositionJv.put("飲", 73050);
		mapPositionJv.put("飴", 73069);
		mapPositionJv.put("飼", 73071);
		mapPositionJv.put("飽", 73084);
		mapPositionJv.put("飾", 73093);
		mapPositionJv.put("餃", 73097);
		mapPositionJv.put("餅", 73098);
		mapPositionJv.put("養", 73099);
		mapPositionJv.put("餌", 73124);
		mapPositionJv.put("餓", 73126);
		mapPositionJv.put("餡", 73129);
		mapPositionJv.put("饂", 73131);
		mapPositionJv.put("饅", 73134);
		mapPositionJv.put("饑", 73135);
		mapPositionJv.put("饗", 73138);
		mapPositionJv.put("首", 73139);
		mapPositionJv.put("香", 73166);
		mapPositionJv.put("馬", 73185);
		mapPositionJv.put("馳", 73216);
		mapPositionJv.put("馴", 73217);
		mapPositionJv.put("駄", 73223);
		mapPositionJv.put("駅", 73227);
		mapPositionJv.put("駆", 73232);
		mapPositionJv.put("駐", 73243);
		mapPositionJv.put("駒", 73259);
		mapPositionJv.put("駱", 73260);
		mapPositionJv.put("駻", 73262);
		mapPositionJv.put("騎", 73263);
		mapPositionJv.put("騒", 73267);
		mapPositionJv.put("験", 73279);
		mapPositionJv.put("騙", 73280);
		mapPositionJv.put("騰", 73284);
		mapPositionJv.put("驕", 73287);
		mapPositionJv.put("驚", 73293);
		mapPositionJv.put("驢", 73302);
		mapPositionJv.put("骨", 73303);
		mapPositionJv.put("骸", 73321);
		mapPositionJv.put("高", 73322);
		mapPositionJv.put("髪", 73427);
		mapPositionJv.put("髭", 73446);
		mapPositionJv.put("鬘", 73447);
		mapPositionJv.put("鬱", 73448);
		mapPositionJv.put("鬼", 73449);
		mapPositionJv.put("魂", 73454);
		mapPositionJv.put("魅", 73457);
		mapPositionJv.put("魔", 73464);
		mapPositionJv.put("魚", 73473);
		mapPositionJv.put("鮃", 73494);
		mapPositionJv.put("鮎", 73495);
		mapPositionJv.put("鮑", 73496);
		mapPositionJv.put("鮪", 73497);
		mapPositionJv.put("鮫", 73498);
		mapPositionJv.put("鮭", 73499);
		mapPositionJv.put("鮮", 73501);
		mapPositionJv.put("鯉", 73509);
		mapPositionJv.put("鯏", 73510);
		mapPositionJv.put("鯖", 73511);
		mapPositionJv.put("鯛", 73512);
		mapPositionJv.put("鯨", 73513);
		mapPositionJv.put("鯰", 73518);
		mapPositionJv.put("鯵", 73519);
		mapPositionJv.put("鰈", 73520);
		mapPositionJv.put("鰉", 73521);
		mapPositionJv.put("鰊", 73522);
		mapPositionJv.put("鰌", 73523);
		mapPositionJv.put("鰐", 73524);
		mapPositionJv.put("鰓", 73525);
		mapPositionJv.put("鰕", 73526);
		mapPositionJv.put("鰥", 73527);
		mapPositionJv.put("鰭", 73528);
		mapPositionJv.put("鰯", 73529);
		mapPositionJv.put("鰹", 73530);
		mapPositionJv.put("鰺", 73531);
		mapPositionJv.put("鰻", 73532);
		mapPositionJv.put("鱈", 73535);
		mapPositionJv.put("鱒", 73536);
		mapPositionJv.put("鱗", 73537);
		mapPositionJv.put("鱚", 73538);
		mapPositionJv.put("鱶", 73539);
		mapPositionJv.put("鳥", 73540);
		mapPositionJv.put("鳩", 73564);
		mapPositionJv.put("鳳", 73572);
		mapPositionJv.put("鳴", 73573);
		mapPositionJv.put("鳶", 73581);
		mapPositionJv.put("鴛", 73585);
		mapPositionJv.put("鴨", 73586);
		mapPositionJv.put("鵜", 73587);
		mapPositionJv.put("鵞", 73588);
		mapPositionJv.put("鶏", 73589);
		mapPositionJv.put("鶯", 73602);
		mapPositionJv.put("鶴", 73603);
		mapPositionJv.put("鷲", 73607);
		mapPositionJv.put("鷹", 73608);
		mapPositionJv.put("鷺", 73609);
		mapPositionJv.put("鹹", 73610);
		mapPositionJv.put("鹿", 73612);
		mapPositionJv.put("麒", 73615);
		mapPositionJv.put("麓", 73616);
		mapPositionJv.put("麗", 73617);
		mapPositionJv.put("麦", 73619);
		mapPositionJv.put("麩", 73626);
		mapPositionJv.put("麹", 73627);
		mapPositionJv.put("麻", 73629);
		mapPositionJv.put("黄", 73657);
		mapPositionJv.put("黒", 73669);
		mapPositionJv.put("黙", 73738);
		mapPositionJv.put("黴", 73749);
		mapPositionJv.put("黶", 73757);
		mapPositionJv.put("黽", 73758);
		mapPositionJv.put("鼈", 73759);
		mapPositionJv.put("鼎", 73760);
		mapPositionJv.put("鼓", 73762);
		mapPositionJv.put("鼠", 73770);
		mapPositionJv.put("鼻", 73782);
		mapPositionJv.put("鼾", 73822);
		mapPositionJv.put("齎", 73825);

	}
	/* A hash map use for convert hiragana to kanakata character */
	public static final HashMap<Character, Character> mKanaTable = new HashMap<Character, Character>();
	static {
		mKanaTable.put('ぁ', 'ァ');
		mKanaTable.put('あ', 'ア');
		mKanaTable.put('ぃ', 'ィ');
		mKanaTable.put('い', 'イ');
		mKanaTable.put('ぅ', 'ゥ');
		mKanaTable.put('う', 'ウ');
		mKanaTable.put('ぇ', 'ェ');
		mKanaTable.put('え', 'エ');
		mKanaTable.put('ぉ', 'ォ');
		mKanaTable.put('お', 'オ');
		mKanaTable.put('か', 'カ');
		mKanaTable.put('が', 'ガ');
		mKanaTable.put('き', 'キ');
		mKanaTable.put('ぎ', 'ギ');
		mKanaTable.put('く', 'ク');
		mKanaTable.put('ぐ', 'グ');
		mKanaTable.put('け', 'ケ');
		mKanaTable.put('げ', 'ゲ');
		mKanaTable.put('こ', 'コ');
		mKanaTable.put('ご', 'ゴ');
		mKanaTable.put('さ', 'サ');
		mKanaTable.put('ざ', 'ザ');
		mKanaTable.put('し', 'シ');
		mKanaTable.put('じ', 'ジ');
		mKanaTable.put('す', 'ス');
		mKanaTable.put('ず', 'ズ');
		mKanaTable.put('せ', 'セ');
		mKanaTable.put('ぜ', 'ゼ');
		mKanaTable.put('そ', 'ソ');
		mKanaTable.put('ぞ', 'ゾ');
		mKanaTable.put('た', 'タ');
		mKanaTable.put('だ', 'ダ');
		mKanaTable.put('ち', 'チ');
		mKanaTable.put('ぢ', 'ヂ');
		mKanaTable.put('っ', 'ッ');
		mKanaTable.put('つ', 'ツ');
		mKanaTable.put('づ', 'ヅ');
		mKanaTable.put('て', 'テ');
		mKanaTable.put('で', 'デ');
		mKanaTable.put('と', 'ト');
		mKanaTable.put('ど', 'ド');
		mKanaTable.put('な', 'ナ');
		mKanaTable.put('に', 'ニ');
		mKanaTable.put('ぬ', 'ヌ');
		mKanaTable.put('ね', 'ネ');
		mKanaTable.put('の', 'ノ');
		mKanaTable.put('は', 'ハ');
		mKanaTable.put('ば', 'バ');
		mKanaTable.put('ぱ', 'パ');
		mKanaTable.put('ひ', 'ヒ');
		mKanaTable.put('び', 'ビ');
		mKanaTable.put('ぴ', 'ピ');
		mKanaTable.put('ふ', 'フ');
		mKanaTable.put('ぶ', 'ブ');
		mKanaTable.put('ぷ', 'プ');
		mKanaTable.put('へ', 'ヘ');
		mKanaTable.put('べ', 'ベ');
		mKanaTable.put('ぺ', 'ペ');
		mKanaTable.put('ほ', 'ホ');
		mKanaTable.put('ぼ', 'ボ');
		mKanaTable.put('ぽ', 'ポ');
		mKanaTable.put('ま', 'マ');
		mKanaTable.put('み', 'ミ');
		mKanaTable.put('む', 'ム');
		mKanaTable.put('め', 'メ');
		mKanaTable.put('も', 'モ');
		mKanaTable.put('ゃ', 'ャ');
		mKanaTable.put('や', 'ヤ');
		mKanaTable.put('ゅ', 'ュ');
		mKanaTable.put('ゆ', 'ユ');
		mKanaTable.put('ょ', 'ョ');
		mKanaTable.put('よ', 'ヨ');
		mKanaTable.put('ら', 'ラ');
		mKanaTable.put('り', 'リ');
		mKanaTable.put('る', 'ル');
		mKanaTable.put('れ', 'レ');
		mKanaTable.put('ろ', 'ロ');
		mKanaTable.put('ゎ', 'ヮ');
		mKanaTable.put('わ', 'ワ');
		mKanaTable.put('を', 'ヲ');
		mKanaTable.put('ん', 'ン');
		mKanaTable.put('ゑ', 'ヱ');
		mKanaTable.put('ゐ', 'ヰ');
	}
	/* A hash map stored index use for search vietnamese-japanese */
	public static final HashMap<String, Integer> mapPositionVj = new HashMap<String, Integer>();
	static {
		mapPositionVj.put("1", 1);
		mapPositionVj.put("2", 13);
		mapPositionVj.put("3", 19);
		mapPositionVj.put("5", 20);
		mapPositionVj.put("7", 26);
		mapPositionVj.put("9", 28);
		mapPositionVj.put("a", 31);
		mapPositionVj.put("b", 196);
		mapPositionVj.put("c", 4024);
		mapPositionVj.put("d", 10352);
		mapPositionVj.put("e", 11606);
		mapPositionVj.put("f", 11648);
		mapPositionVj.put("g", 11669);
		mapPositionVj.put("h", 13423);
		mapPositionVj.put("i", 15996);
		mapPositionVj.put("j", 16043);
		mapPositionVj.put("k", 16048);
		mapPositionVj.put("l", 18603);
		mapPositionVj.put("m", 21063);
		mapPositionVj.put("n", 23247);
		mapPositionVj.put("o", 28027);
		mapPositionVj.put("p", 28078);
		mapPositionVj.put("q", 29532);
		mapPositionVj.put("r", 30443);
		mapPositionVj.put("s", 31148);
		mapPositionVj.put("t", 39499);
		mapPositionVj.put("u", 47007);
		mapPositionVj.put("v", 47082);
		mapPositionVj.put("w", 49356);
		mapPositionVj.put("x", 49358);
		mapPositionVj.put("y", 50219);
		mapPositionVj.put("z", 50322);
		mapPositionVj.put("á", 50323);
		mapPositionVj.put("â", 50325);
		mapPositionVj.put("ê", 50327);
		mapPositionVj.put("ô", 50328);
		mapPositionVj.put("ú", 50330);
		mapPositionVj.put("à", 50331);
		mapPositionVj.put("á", 50338);
		mapPositionVj.put("â", 50522);
		mapPositionVj.put("è", 50582);
		mapPositionVj.put("é", 50583);
		mapPositionVj.put("ê", 50596);
		mapPositionVj.put("ì", 50614);
		mapPositionVj.put("í", 50616);
		mapPositionVj.put("ò", 50643);
		mapPositionVj.put("ó", 50646);
		mapPositionVj.put("ô", 50652);
		mapPositionVj.put("õ", 50745);
		mapPositionVj.put("ù", 50746);
		mapPositionVj.put("ú", 50751);
		mapPositionVj.put("ý", 50756);
		mapPositionVj.put("ă", 50802);
		mapPositionVj.put("đ", 50862);
		mapPositionVj.put("ơ", 54533);
		mapPositionVj.put("ư", 54543);
		mapPositionVj.put("ả", 54601);
		mapPositionVj.put("ấ", 54633);
		mapPositionVj.put("ầ", 54673);
		mapPositionVj.put("ẩ", 54678);
		mapPositionVj.put("ậ", 54694);
		mapPositionVj.put("ắ", 54695);
		mapPositionVj.put("ẵ", 54699);
		mapPositionVj.put("ẹ", 54700);
		mapPositionVj.put("ẻ", 54701);
		mapPositionVj.put("ế", 54702);
		mapPositionVj.put("ễ", 54706);
		mapPositionVj.put("ỉ", 54707);
		mapPositionVj.put("ị", 54711);
		mapPositionVj.put("ọ", 54712);
		mapPositionVj.put("ố", 54714);
		mapPositionVj.put("ồ", 54788);
		mapPositionVj.put("ổ", 54795);
		mapPositionVj.put("ỗ", 54810);
		mapPositionVj.put("ộ", 54811);
		mapPositionVj.put("ớ", 54812);
		mapPositionVj.put("ờ", 54821);
		mapPositionVj.put("ở", 54822);
		mapPositionVj.put("ợ", 54871);
		mapPositionVj.put("ụ", 54874);
		mapPositionVj.put("ủ", 54878);
		mapPositionVj.put("ứ", 54957);
		mapPositionVj.put("ừ", 54988);
		mapPositionVj.put("ử", 54990);
		mapPositionVj.put("ự", 54992);
		mapPositionVj.put("ỷ", 54993);

	}
	
	/*
	* メソッド名: RomajiToHiragana(String wordRomajiTemp)
	* 説明: Convert Romanji string to hiragana string 
	* パラメータ: wordRomajiTemp
	*グローバル変数:
	* 値を返す: String
	* 修正: 20140213 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	@SuppressLint("DefaultLocale")
	public static final String RomajiToHiragana(String wordRomajiTemp) {
		String wordRomaji = new String(wordRomajiTemp);
		// Seion
		wordRomaji = wordRomaji.toLowerCase();
		if (wordRomaji.contains("kya")) {
			wordRomaji = wordRomaji.replace("kya", "きゃ");
		}
		if (wordRomaji.contains("kyu")) {
			wordRomaji = wordRomaji.replace("kyu", "きゅ");
		}
		if (wordRomaji.contains("kyo")) {
			wordRomaji = wordRomaji.replace("kyo", "きょ");
		}
		if (wordRomaji.contains("sha")) {
			wordRomaji = wordRomaji.replace("sha", "しゃ");
		}
		if (wordRomaji.contains("shu")) {
			wordRomaji = wordRomaji.replace("shu", "しゅ");
		}
		if (wordRomaji.contains("sho")) {
			wordRomaji = wordRomaji.replace("sho", "しょ");
		}
		if (wordRomaji.contains("cha")) {
			wordRomaji = wordRomaji.replace("cha", "ちゃ");
		}
		if (wordRomaji.contains("chu")) {
			wordRomaji = wordRomaji.replace("chu", "ちゅ");
		}
		if (wordRomaji.contains("cho")) {
			wordRomaji = wordRomaji.replace("cho", "ちょ");
		}
		if (wordRomaji.contains("nya")) {
			wordRomaji = wordRomaji.replace("nya", "にゃ");
		}
		if (wordRomaji.contains("nyu")) {
			wordRomaji = wordRomaji.replace("nyu", "にゅ");
		}
		if (wordRomaji.contains("nyo")) {
			wordRomaji = wordRomaji.replace("nyo", "にょ");
		}
		if (wordRomaji.contains("hya")) {
			wordRomaji = wordRomaji.replace("hya", "ひゃ");
		}
		if (wordRomaji.contains("hyu")) {
			wordRomaji = wordRomaji.replace("hyu", "ひゅ");
		}
		if (wordRomaji.contains("hyo")) {
			wordRomaji = wordRomaji.replace("hyo", "ひょ");
		}
		if (wordRomaji.contains("mya")) {
			wordRomaji = wordRomaji.replace("mya", "みゃ");
		}
		if (wordRomaji.contains("myu")) {
			wordRomaji = wordRomaji.replace("myu", "みゅ");
		}
		if (wordRomaji.contains("myo")) {
			wordRomaji = wordRomaji.replace("myo", "みょ");
		}
		if (wordRomaji.contains("rya")) {
			wordRomaji = wordRomaji.replace("rya", "りゃ");
		}
		if (wordRomaji.contains("ryu")) {
			wordRomaji = wordRomaji.replace("ryu", "りゅ");
		}
		if (wordRomaji.contains("ryo")) {
			wordRomaji = wordRomaji.replace("ryo", "りょ");
		}

		// Dakuon
		if (wordRomaji.contains("gya")) {
			wordRomaji = wordRomaji.replace("gya", "ぎゃ");
		}
		if (wordRomaji.contains("gyu")) {
			wordRomaji = wordRomaji.replace("gyu", "ぎゅ");
		}
		if (wordRomaji.contains("gyo")) {
			wordRomaji = wordRomaji.replace("gyo", "ぎょ");
		}
		if (wordRomaji.contains("ja")) {
			wordRomaji = wordRomaji.replace("ja", "じゃ");
		}
		if (wordRomaji.contains("ju")) {
			wordRomaji = wordRomaji.replace("ju", "じゅ");
		}
		if (wordRomaji.contains("jo")) {
			wordRomaji = wordRomaji.replace("jo", "じょ");
		}
		if (wordRomaji.contains("ja")) {
			wordRomaji = wordRomaji.replace("ja", "ぢゃ");
		}
		if (wordRomaji.contains("ju")) {
			wordRomaji = wordRomaji.replace("ju", "ぢゅ");
		}
		if (wordRomaji.contains("jo")) {
			wordRomaji = wordRomaji.replace("jo", "ぢょ");
		}
		if (wordRomaji.contains("bya")) {
			wordRomaji = wordRomaji.replace("bya", "びゃ");
		}
		if (wordRomaji.contains("byu")) {
			wordRomaji = wordRomaji.replace("byu", "びゅ");
		}
		if (wordRomaji.contains("byo")) {
			wordRomaji = wordRomaji.replace("byo", "びょ");
		}
		if (wordRomaji.contains("pya")) {
			wordRomaji = wordRomaji.replace("pya", "ぴゃ");
		}
		if (wordRomaji.contains("pyu")) {
			wordRomaji = wordRomaji.replace("pyu", "ぴゅ");
		}
		// HD
		if (wordRomaji.contains("pyo")) {
			wordRomaji = wordRomaji.replace("pyo", "ぴょ");
		}
		if (wordRomaji.contains("tsu")) {
			wordRomaji = wordRomaji.replace("tsu", "つ");
		}
		if (wordRomaji.contains("chi")) {
			wordRomaji = wordRomaji.replace("chi", "ち");
		}

		// Seion:Ka
		if (wordRomaji.contains("ka")) {
			wordRomaji = wordRomaji.replace("ka", "か");
		}
		if (wordRomaji.contains("ki")) {
			wordRomaji = wordRomaji.replace("ki", "き");
		}
		if (wordRomaji.contains("ku")) {
			wordRomaji = wordRomaji.replace("ku", "く");
		}
		if (wordRomaji.contains("ke")) {
			wordRomaji = wordRomaji.replace("ke", "け");
		}
		if (wordRomaji.contains("ko")) {
			wordRomaji = wordRomaji.replace("ko", "こ");
		}
		// Seion:Ga
		if (wordRomaji.contains("ga")) {
			wordRomaji = wordRomaji.replace("ga", "が");
		}
		if (wordRomaji.contains("gi")) {
			wordRomaji = wordRomaji.replace("gi", "ぎ");
		}
		if (wordRomaji.contains("gu")) {
			wordRomaji = wordRomaji.replace("gu", "ぐ");
		}
		if (wordRomaji.contains("ge")) {
			wordRomaji = wordRomaji.replace("ge", "げ");
		}
		if (wordRomaji.contains("go")) {
			wordRomaji = wordRomaji.replace("go", "ご");
		}
		// Seion:Sa
		if (wordRomaji.contains("sa")) {
			wordRomaji = wordRomaji.replace("sa", "さ");
		}
		if (wordRomaji.contains("shi")) {
			wordRomaji = wordRomaji.replace("shi", "し");
		}
		if (wordRomaji.contains("su")) {
			wordRomaji = wordRomaji.replace("su", "す");
		}
		if (wordRomaji.contains("se")) {
			wordRomaji = wordRomaji.replace("se", "せ");
		}
		if (wordRomaji.contains("so")) {
			wordRomaji = wordRomaji.replace("so", "そ");
		}
		// Seion:Za
		if (wordRomaji.contains("za")) {
			wordRomaji = wordRomaji.replace("za", "ざ");
		}
		if (wordRomaji.contains("ji")) {
			wordRomaji = wordRomaji.replace("ji", "じ");
		}
		if (wordRomaji.contains("zu")) {
			wordRomaji = wordRomaji.replace("zu", "ず");
		}
		if (wordRomaji.contains("ze")) {
			wordRomaji = wordRomaji.replace("ze", "ぜ");
		}
		if (wordRomaji.contains("zo")) {
			wordRomaji = wordRomaji.replace("zo", "ぞ");
		}
		// Seion:Ta
		if (wordRomaji.contains("ta")) {
			wordRomaji = wordRomaji.replace("ta", "た");
		}
		if (wordRomaji.contains("te")) {
			wordRomaji = wordRomaji.replace("te", "て");
		}
		if (wordRomaji.contains("to")) {
			wordRomaji = wordRomaji.replace("to", "と");
		}
		// Seion:Da
		if (wordRomaji.contains("da")) {
			wordRomaji = wordRomaji.replace("da", "だ");
		}
		if (wordRomaji.contains("di")) {
			wordRomaji = wordRomaji.replace("di", "ぢ");
		}
		if (wordRomaji.contains("du")) {
			wordRomaji = wordRomaji.replace("du", "づ");
		}
		if (wordRomaji.contains("de")) {
			wordRomaji = wordRomaji.replace("de", "で");
		}
		if (wordRomaji.contains("do")) {
			wordRomaji = wordRomaji.replace("do", "ど");
		}
		// Seion:Ha
		if (wordRomaji.contains("ha")) {
			wordRomaji = wordRomaji.replace("ha", "は");
		}
		if (wordRomaji.contains("hi")) {
			wordRomaji = wordRomaji.replace("hi", "ひ");
		}
		if (wordRomaji.contains("fu")) {
			wordRomaji = wordRomaji.replace("fu", "ふ");
		}
		if (wordRomaji.contains("he")) {
			wordRomaji = wordRomaji.replace("he", "へ");
		}
		if (wordRomaji.contains("ho")) {
			wordRomaji = wordRomaji.replace("ho", "ほ");
		}
		// Seion:Ba
		if (wordRomaji.contains("ba")) {
			wordRomaji = wordRomaji.replace("ba", "ば");
		}
		if (wordRomaji.contains("bi")) {
			wordRomaji = wordRomaji.replace("bi", "び");
		}
		if (wordRomaji.contains("bu")) {
			wordRomaji = wordRomaji.replace("bu", "ぶ");
		}
		if (wordRomaji.contains("be")) {
			wordRomaji = wordRomaji.replace("be", "べ");
		}
		if (wordRomaji.contains("bo")) {
			wordRomaji = wordRomaji.replace("bo", "ぼ");
		}
		// Seion:Pa
		if (wordRomaji.contains("pa")) {
			wordRomaji = wordRomaji.replace("pa", "ぱ");
		}
		if (wordRomaji.contains("pi")) {
			wordRomaji = wordRomaji.replace("pi", "ぴ");
		}
		if (wordRomaji.contains("pu")) {
			wordRomaji = wordRomaji.replace("pu", "ぷ");
		}
		if (wordRomaji.contains("pe")) {
			wordRomaji = wordRomaji.replace("pe", "ぺ");
		}
		if (wordRomaji.contains("po")) {
			wordRomaji = wordRomaji.replace("po", "ぽ");
		}
		// Seion:Na
		if (wordRomaji.contains("na")) {
			wordRomaji = wordRomaji.replace("na", "な");
		}
		if (wordRomaji.contains("ni")) {
			wordRomaji = wordRomaji.replace("ni", "に");
		}
		if (wordRomaji.contains("nu")) {
			wordRomaji = wordRomaji.replace("nu", "ぬ");
		}
		if (wordRomaji.contains("ne")) {
			wordRomaji = wordRomaji.replace("ne", "ね");
		}
		if (wordRomaji.contains("no")) {
			wordRomaji = wordRomaji.replace("no", "の");
		}
		// Seion:Ma
		if (wordRomaji.contains("ma")) {
			wordRomaji = wordRomaji.replace("ma", "ま");
		}
		if (wordRomaji.contains("mi")) {
			wordRomaji = wordRomaji.replace("mi", "み");
		}
		if (wordRomaji.contains("mu")) {
			wordRomaji = wordRomaji.replace("mu", "む");
		}
		if (wordRomaji.contains("me")) {
			wordRomaji = wordRomaji.replace("me", "め");
		}
		if (wordRomaji.contains("mo")) {
			wordRomaji = wordRomaji.replace("mo", "も");
		}
		// Seion:Ra
		if (wordRomaji.contains("ra")) {
			wordRomaji = wordRomaji.replace("ra", "ら");
		}
		if (wordRomaji.contains("ri")) {
			wordRomaji = wordRomaji.replace("ri", "り");
		}
		if (wordRomaji.contains("ru")) {
			wordRomaji = wordRomaji.replace("ru", "る");
		}
		if (wordRomaji.contains("re")) {
			wordRomaji = wordRomaji.replace("re", "れ");
		}
		if (wordRomaji.contains("ro")) {
			wordRomaji = wordRomaji.replace("ro", "ろ");
		}
		// Seion:Ya
		if (wordRomaji.contains("ya")) {
			wordRomaji = wordRomaji.replace("ya", "や");
		}
		if (wordRomaji.contains("yu")) {
			wordRomaji = wordRomaji.replace("yu", "ゆ");
		}
		if (wordRomaji.contains("yo")) {
			wordRomaji = wordRomaji.replace("yo", "よ");
		}
		// Seion:Wa
		if (wordRomaji.contains("wa")) {
			wordRomaji = wordRomaji.replace("wa", "わ");
		}
		if (wordRomaji.contains("wo")) {
			wordRomaji = wordRomaji.replace("wo", "を");
		}
		// Seion:n
		if (wordRomaji.contains("n")) {
			wordRomaji = wordRomaji.replace("n", "ん");
		}
		// Seion:a
		if (wordRomaji.contains("a")) {
			wordRomaji = wordRomaji.replace("a", "あ");
		}
		if (wordRomaji.contains("i")) {
			wordRomaji = wordRomaji.replace("i", "い");
		}
		if (wordRomaji.contains("u")) {
			wordRomaji = wordRomaji.replace("u", "う");
		}
		if (wordRomaji.contains("e")) {
			wordRomaji = wordRomaji.replace("e", "え");
		}
		if (wordRomaji.contains("o")) {
			wordRomaji = wordRomaji.replace("o", "お");
		}

		// Double letters with little tsu
		if (wordRomaji.contains("k")) {
			wordRomaji = wordRomaji.replace("k", "っ");
		}
		if (wordRomaji.contains("sh")) {
			wordRomaji = wordRomaji.replace("s", "っ");
		}
		if (wordRomaji.contains("t")) {
			wordRomaji = wordRomaji.replace("t", "っ");
		}
		if (wordRomaji.contains("n")) {
			wordRomaji = wordRomaji.replace("n", "っ");
		}
		if (wordRomaji.contains("h")) {
			wordRomaji = wordRomaji.replace("h", "っ");
		}
		if (wordRomaji.contains("m")) {
			wordRomaji = wordRomaji.replace("m", "っ");
		}
		if (wordRomaji.contains("y")) {
			wordRomaji = wordRomaji.replace("y", "っ");
		}
		if (wordRomaji.contains("r")) {
			wordRomaji = wordRomaji.replace("r", "っ");
		}
		if (wordRomaji.contains("w")) {
			wordRomaji = wordRomaji.replace("w", "っ");
		}
		if (wordRomaji.contains("g")) {
			wordRomaji = wordRomaji.replace("g", "っ");
		}
		if (wordRomaji.contains("z")) {
			wordRomaji = wordRomaji.replace("z", "っ");
		}
		if (wordRomaji.contains("d")) {
			wordRomaji = wordRomaji.replace("d", "っ");
		}
		if (wordRomaji.contains("s")) {
			wordRomaji = wordRomaji.replace("s", "っ");
		}
		if (wordRomaji.contains("j")) {
			wordRomaji = wordRomaji.replace("j", "っ");
		}
		if (wordRomaji.contains("ch")) {
			wordRomaji = wordRomaji.replace("ch", "っ");
		}
		if (wordRomaji.contains("b")) {
			wordRomaji = wordRomaji.replace("b", "っ");
		}
		if (wordRomaji.contains("p")) {
			wordRomaji = wordRomaji.replace("p", "っ");
		}

		if (wordRomaji.charAt(wordRomaji.length() - 1) == 'っ'
				|| wordRomaji.matches(".*[a-zA-Z0-9]+.*")
				|| wordRomaji.charAt(0) == 'っ')
			wordRomaji = "@";

		return wordRomaji;
	}
	
	/*
	* メソッド名: Hira2Katakana(String word)
	* 説明: Convert hiragana string to Katakana string
	* パラメータ: word
	*グローバル変数: sTemp
	* 値を返す: String
	* 修正: 20140213 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	public static final String Hira2Katakana(String word) {
		sTemp.setLength(0);
		for (int i = 0; i < word.length(); i++) {
			final char ch = word.charAt(i);
			Character kata = mKanaTable.get(ch);
			if (kata == null) {
				sTemp.append(ch);
			} else {
				sTemp.append(kata);
			}
		}
		return sTemp.toString();
	}

	/*
	* メソッド名: Html_Converter(String word, String meaning)
	* 説明: Convert meaning from Database to HTML string for Search on SearchView
	* パラメータ: word,meaning
	*グローバル変数:
	* 値を返す: String
	* 修正: 20140213 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	public static final String Html_Converter(String word, String meaning) {
		meaning = meaning.replaceAll("∴", "<br><font color=" + "#FF0019"
				+ "> ✍");
		meaning = meaning.replaceAll("☆", "<br><font color=" + "#C71585"
				+ "> ✭");
		meaning = meaning.replaceAll("◆", "<br><font color=" + "#228654"
				+ "> ➣");
		meaning = meaning.replaceAll("※", "<br><font color=" + "#9046B8"
				+ "> ☞ ");
		meaning = meaning.replaceAll(":", "<font color=" + "#37587d" + "> ↔ ");
		meaning = "<br>" + word + meaning;
		return meaning;
	}

	/*
	* メソッド名: Html_ConverterIns(String word, String meaning)
	* 説明: Convert meaning from Database to HTML string for Instant search
	* パラメータ: word,meaning
	*グローバル変数:
	* 値を返す: String
	* 修正: 20140213 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	public static final String Html_ConverterIns(String word, String meaning) {
		meaning = meaning.replaceAll("∴", "<br/><font color= #FF0019 >" + "✍");
		meaning = meaning.replaceAll("☆", "</font><br/><font color= #C71585 >"
				+ "✭");
		meaning = meaning.replaceAll("◆", "</font><br><font color=" + "#228654"
				+ "> ➣");
		meaning = meaning.replaceAll("※", "</font><br><font color=" + "#9046B8"
				+ "> ☞ ");
		meaning = meaning.replaceAll(":", "</font><font color=" + "#37587d"
				+ "> ↔ ");
		meaning = "<br>" + word + meaning;
		return meaning;
	}

	/*
	* メソッド名: Html_ConverterKJ(String meaning)
	* 説明: Convert meaning from Database to HTML string for Kanji search
	* パラメータ: meaning
	*グローバル変数:
	* 値を返す: String
	* 修正: 20140213 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	public static final String Html_ConverterKJ(String meaning) {
		meaning = meaning.replaceAll("∴", "	<br><font color=" + "#FF0019"
				+ "> ✍");
		meaning = meaning.replaceAll("☆", "<br><br><font color=" + "#C71585"
				+ "> ✭");
		meaning = meaning.replaceAll("◆", "<br><font color=" + "#228654"
				+ "> ➣");
		meaning = meaning.replaceAll("※", "<br><font color=" + "#9046B8"
				+ "> ☞ ");
		meaning = meaning.replaceAll(":", "<font color=" + "#37587d" + "> ↔ ");
		return meaning;
	}

	/*
	* メソッド名: getPosition(String word,
			HashMap<String, Integer> mapPosition)
	* 説明: get Index in database of word 
	* パラメータ: word,HashMap
	*グローバル変数:
	* 値を返す: int
	* 修正: 20140213 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	public static final int getPosition(String word,
			HashMap<String, Integer> mapPosition) {
		int position = 80000;
		if (word.length() > 1) {
			if (mapPosition.get(word.substring(0, 2)) != null) {
				position = mapPosition.get(word.substring(0, 2));
			} else {
				if (mapPosition.get(word.substring(0, 1)) != null) {
					position = mapPosition.get(word.substring(0, 1));
				}
			}
		}
		if (word.length() > 0)
			if (mapPosition.get(word.substring(0, 1)) != null) {
				position = mapPosition.get(word.substring(0, 1));
			}

		return position;
	}

	/*
	* メソッド名: String makePlaceholders(int len)
	* 説明: create '?' characters for query String 
	* パラメータ: len
	*グローバル変数:
	* 値を返す: String
	* 修正: 20140213 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	public static final String makePlaceholders(int len) {
		StringBuilder sb = new StringBuilder();
		sb.append("?");
		for (int i = 1; i < len; i++) {
			sb.append(",?");
		}
		return sb.toString();
	}
}