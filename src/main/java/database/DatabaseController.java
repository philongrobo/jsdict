package database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Environment;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/*
 *クラス名：  DatabaseController
 *日付: 2014/02/12
 *著作権表示: N/A
 *変更ログ:
 *20140215 LINHNC add new function getFullword
 *20140220 LINHNC add new function getFullwordSuggestion , copyDatabaseFromAssests, copyFile
 *20140221 LINHNC add new function getID
 *	      LINHNC update  function getFullword
 *20140311 LINHNC add new function 	 addFavorite(String, String)
									 addHistory(String, String, String)
									 addMyDict(String, String)
									 checkExistMyDict(String)
									 checkFavorite(String, String)
									 checkFavoriteByWord(String, String)
									 deleteFavorite(String, String)
									 deleteHistory(String, String)
									 deleteMyDict(String)
									 getFavorite(String)
									 getFullWordKJ(String)
									 getHistory()
									 getMyDict()
									 updateMyDict(String, String, String)
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140212 LINHNC 説明 */
public class DatabaseController extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "nhat_viet.db";
	private static final int DATABASE_VERSION = 1;
	public static final String KEY_WORD = SearchManager.SUGGEST_COLUMN_TEXT_1;
	public static final String KEY_DEFINITION = SearchManager.SUGGEST_COLUMN_TEXT_2;
	public final static int MAX_MEANING_RECORD = 2000; // 7508
	SQLiteDatabase db = getWritableDatabase();
	SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	private HashMap<String, String> mAliasMap;
	private HashMap<String, String> mAliasMap_mD;
	private final Context mContext;
	private static final String FIELD_ID = "_id";
	private static final String FIELD_NAME = "word";
	private static final String FIELD_FLAG = "meaning";
	private static final String FIELD_NAME_KJ = "kanji";
	private static final String FIELD_FLAG_KJ = "hanviet";
	private static final HashMap<String, String> mColumnMap = buildColumnMap();
	
	/*
	* メソッド名:  HashMap<String, String> buildColumnMap()
	* 説明: create HashMap to make alias name for column in query 
	* パラメータ:
	*グローバル変数:
	* 値を返す: HashMap
	* 修正: 20140311 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	private static HashMap<String, String> buildColumnMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		// Unique id for the each Suggestions ( Mandatory )
		map.put("_ID", FIELD_ID + " as " + "_id");
		// Text for Suggestions ( Mandatory )
		map.put(SearchManager.SUGGEST_COLUMN_TEXT_1, FIELD_NAME_KJ + " as "
				+ SearchManager.SUGGEST_COLUMN_TEXT_1);
		map.put(SearchManager.SUGGEST_COLUMN_TEXT_2, FIELD_FLAG_KJ + " as "
				+ SearchManager.SUGGEST_COLUMN_TEXT_2);
		// Icon for Suggestions ( Optional )
		map.put(SearchManager.SUGGEST_COLUMN_ICON_1,
				R.drawable.popup_toggle + " as "
						+ SearchManager.SUGGEST_COLUMN_ICON_1);
		// This value will be appended to the Intent data on selecting an item
		// from Search result or Suggestions ( Optional )
		map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, FIELD_ID + " as "
				+ SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);

		return map;
	}

	public DatabaseController(Context context) {
		super(context, DATABASE_NAME, Environment.getExternalStorageDirectory()
				+ "/Android/data/" + context.getPackageName(), null,
				DATABASE_VERSION);

		// This HashMap is used to map table fields to Custom Suggestion fields
		mAliasMap = new HashMap<String, String>();
		{
			// Unique id for the each Suggestions ( Mandatory )
			mAliasMap.put("_ID", FIELD_ID + " as " + "_id");
			// Text for Suggestions ( Mandatory )
			mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, FIELD_NAME
					+ " as " + SearchManager.SUGGEST_COLUMN_TEXT_1);

			mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_2, FIELD_FLAG
					+ " as " + SearchManager.SUGGEST_COLUMN_TEXT_2);
			// Icon for Suggestions ( Optional )
			mAliasMap.put(SearchManager.SUGGEST_COLUMN_ICON_1,
					R.drawable.popup_toggle + " as "
							+ SearchManager.SUGGEST_COLUMN_ICON_1);
			// This value will be appended to the Intent data on selecting an
			// item from Search result or Suggestions ( Optional )
			mAliasMap.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, FIELD_ID
					+ " as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
		}
		mAliasMap_mD = new HashMap<String, String>();
		{
			// Unique id for the each Suggestions ( Mandatory )
			mAliasMap_mD.put("_ID", FIELD_ID + " as " + "_id");
			// Text for Suggestions ( Mandatory )
			mAliasMap_mD.put(SearchManager.SUGGEST_COLUMN_TEXT_1, FIELD_NAME
					+ " as " + SearchManager.SUGGEST_COLUMN_TEXT_1);
			mAliasMap_mD.put(SearchManager.SUGGEST_COLUMN_TEXT_2, FIELD_FLAG
					+ " as " + SearchManager.SUGGEST_COLUMN_TEXT_2);
			// Icon for Suggestions ( Optional )
			mAliasMap_mD.put(SearchManager.SUGGEST_COLUMN_ICON_1,
					R.drawable.action_my_dict + " as "
							+ SearchManager.SUGGEST_COLUMN_ICON_1);
			// This value will be appended to the Intent data on selecting an
			// item from Search result or Suggestions ( Optional )
			mAliasMap_mD.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
					FIELD_ID + " as "
							+ SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
		}
		mContext = context;
		copyDatabaseFromAssets();
	}

	/*
	* メソッド名: String getId(String word, String table)
	* 説明: Return _id of word in table
	* パラメータ:word,table
	*グローバル変数:
	* 値を返す: String
	* 修正: 20140221 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	public String getId(String word, String table) {
		SQLiteQueryBuilder qb_id = new SQLiteQueryBuilder();
		Cursor c = null;
		qb_id.setTables(table);
		if (!table.equals("kj"))
			c = qb_id.query(db, null, "word = ?", new String[] { word }, null,
					null, null, null);
		else {
			c = qb_id.query(db, null, "kanji = ?", new String[] { word }, null,
					null, null, null);
		}
		if (c.moveToFirst()) // Check if c is empty or not
		{
			if (table.equals("myDict"))
				return c.getString(1); // return _id
			else
				return c.getString(0); // return _id
		} else
			return null;
	}

	/*
	* メソッド名: Cursor getFullWordSuggestion(String id, String sqlTables)
	* 説明: Return record of word in table by id
	* パラメータ:id,sqlTables
	*グローバル変数:
	* 値を返す: Cursor
	* 修正: 20140220 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	public Cursor getFullWord2(String id, String sqlTables)
			throws SQLiteException {

		String[] sqlSelect = { "_id", "word", "meaning" }; // column in table to
															// get
		SQLiteQueryBuilder qb2 = new SQLiteQueryBuilder();
		Cursor c = null;
		try {
			if (id.length() < 2)
				id = "no" + id;
			if (id.substring(0, 2).equals("no"))
				id = id.substring(2, id.length());
			else if (id.substring(0, 2).equals("md")) {
				ResultViewController.myDict_mode = 0;
				qb2.setTables("myDict");
				c = qb2.query(db, sqlSelect, "_id = ?", new String[] { id },
						null, null, null, null);
				return c;
			}
			if (sqlTables.equals("jv")) {
				ResultViewController.myDict_mode = -1;
				qb2.setTables(sqlTables);
				c = qb2.query(db, sqlSelect, "_id = ?", new String[] { id },
						null, null, null, null);
			} else if (sqlTables.equals("vj")) {
				ResultViewController.myDict_mode = -1;
				qb2.setTables(sqlTables);
				c = qb2.query(db, sqlSelect, "_id = " + id, null, null, null,
						null, null);
			} else if (sqlTables.equals("kj")) {
				ResultViewController.myDict_mode = -1;
				qb2.setTables(sqlTables);
				c = qb2.query(db, null, "_id = " + id, null, null, null, null,
						null);
			}
		} catch (SQLiteException sqlE) {
			throw sqlE;
		} finally {
		}
		return c;
	}

	/*
	* メソッド名: Cursor getFullWord(String word, String sqlTables)
	* 説明: Return records like word in table by word and table
	* パラメータ:word,sqlTables
	*グローバル変数:
	* 値を返す: Cursor
	* 修正: 2014021 - LINHNC
	* 説明: Update for dictionary provider
	* --------------------------------------------------------*/
	@SuppressLint("NewApi")
	public Cursor getFullWord(String word, String sqlTables) {
		
		String select = null;
		String select0 = null;
		String select1 = null;
		String select2 = null;
		String select3 = null;
		String select4 = null;
		// set Alias for Dictionary Provider
		String[] IDString = new String[] { "_ID",
				SearchManager.SUGGEST_COLUMN_TEXT_1,
				SearchManager.SUGGEST_COLUMN_TEXT_2,
				SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
				SearchManager.SUGGEST_COLUMN_ICON_1, "2 as ord" };
		String[] IDStringMyDict = new String[] { "_ID",
				SearchManager.SUGGEST_COLUMN_TEXT_1,
				SearchManager.SUGGEST_COLUMN_TEXT_2,
				SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
				SearchManager.SUGGEST_COLUMN_ICON_1, "1 as ord" };
		String[] IDStringKanji = new String[] { "_ID",
				SearchManager.SUGGEST_COLUMN_TEXT_1,
				SearchManager.SUGGEST_COLUMN_TEXT_2,
				SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
				SearchManager.SUGGEST_COLUMN_ICON_1, "2 as ord" };
		Cursor c = null;
		String word2;
		// Japanese-Vietnamese mode
		if (sqlTables.equals("jv")) {
			HashMap<String, Integer> mapPosition = Converter.mapPositionJv;
			// check if word null
			if (word.equals(""))
				word2 = "!@#$%";
			else
				word2 = word.trim();

			// create query string in my dictionary
			qb.setTables("myDict");
			qb.setProjectionMap(mAliasMap_mD);
			select = qb.buildQuery(IDStringMyDict, "(word like '" + word2
					+ "%')", null, null, null, null);
			// hira form
			String str_hra = Converter.RomajiToHiragana(word2);
			select0 = qb.buildQuery(IDStringMyDict, "(word like '" + Converter.RomajiToHiragana(word2)
					+ "%')", null, null, null, null);
			select1 = qb.buildQuery(IDStringMyDict, "(word like '" + Converter.Hira2Katakana(str_hra)
					+ "%')", null, null, null, null);
			// create query string in japanese-vietnamese dictionary with word
			// in Hiragana.
			word2 = Converter.RomajiToHiragana(word);
			qb.setTables(sqlTables);
			qb.setProjectionMap(mAliasMap);
			int position = Converter.getPosition(word2, mapPosition);
			select2 = qb.buildQuery(IDString, "(word like '" + word2
					+ "%')  AND (_id BETWEEN " + position + " AND "
					+ (position + MAX_MEANING_RECORD) + ") ", null, null, null,
					null);

			// create query string in japanese-vietnamese dictionary with word
			// in Katakana.
			word2 = Converter.Hira2Katakana(word2);
			position = Converter.getPosition(word2, mapPosition);
			select3 = qb.buildQuery(IDString, "(word like '" + word2
					+ "%')  AND (_id BETWEEN " + position + " AND "
					+ (position + MAX_MEANING_RECORD) + ") ", null, null, null,
					null);
			// create query string in japanese-vietnamese dictionary with word
			// not converted.
			position = Converter.getPosition(word, mapPosition);
			select4 = qb.buildQuery(IDString, "(word like '" + word
					+ "%')  AND (_id BETWEEN " + position + " AND "
					+ (position + MAX_MEANING_RECORD) + ") ", null, null, null,
					null);
			// Union query for search
			String query = buildUnionQuery(new String[] {select,select0,select1, select2,
					select3, select4 }, " ord ASC", "50");
			c = db.rawQuery(query, null);

		}
		// Vietnamese-Japanese mode
		else if (sqlTables.equals("vj")) {
			HashMap<String, Integer> mapPosition = Converter.mapPositionVj;
			if (word.equals(""))
				word2 = "!@#$%";
			else
				word2 = word.trim();
			qb.setTables(sqlTables);
			qb.setProjectionMap(mAliasMap);
			int position = Converter.getPosition(word2, mapPosition);
			select1 = qb.buildQuery(IDString, "(word like '" + word2
					+ "%')  AND (_id BETWEEN " + position + " AND "
					+ (position + 7508) + ") ", null, null, null, null);
			qb.setTables("myDict");
			qb.setProjectionMap(mAliasMap_mD);
			select2 = qb.buildQuery(IDStringMyDict, "(word like '" + word2
					+ "%')", null, null, null, null);
			String query = buildUnionQuery(
					new String[] { select1, select2 }, "ord ASC", "50");
			c = db.rawQuery(query, null);
		}
		// Kanji mode
		else if (sqlTables.equals("kj")) {
			if (word.equals(""))
				word2 = "!@#$%";
			else
				word2 = word.trim();
			qb.setTables("myDict");
			qb.setProjectionMap(mAliasMap_mD);
			String[] args = word2.split("");
			args[0] = "!@#$%";
			int len = args.length;
			String[] dataBinding = new String[len*2];
			for(int i =0;i<len;i++)
			{
				dataBinding[i]=args[i];
				dataBinding[len+i] = args[i];
			}
			select1 = qb.buildQuery(IDStringMyDict,
					"word IN ( " + Converter.makePlaceholders(args.length)+
					") OR (word like '" + word2
					+ "%')", null, null, null, null);
			qb.setTables(sqlTables);
			qb.setProjectionMap(mColumnMap);
			select2 = qb.buildQuery(IDStringKanji,
					"kanji IN ( " + Converter.makePlaceholders(args.length)
					+ ") OR hanviet LIKE '" + word2 + "%'", null, null, null, null);
			String query = buildUnionQuery(
					new String[] { select1, select2 }, "ord ASC", "100");
			c = db.rawQuery(query, dataBinding);
/*			c = qb.query(db, IDStringKanji,
					"kanji IN ( " + Converter.makePlaceholders(args.length)
							+ ") OR hanviet LIKE '" + word2 + "%'", args, null,
					null, "hanviet ASC", Integer.toString(args.length));*/
		}
		// my dictionary mode
		else if (sqlTables.equals("myDict")) {
			SQLiteQueryBuilder qb_myDict = new SQLiteQueryBuilder();
			qb_myDict.setTables(sqlTables);
			String[] args = { word };
			c = qb_myDict.query(db, null, "word = ?", args, null, null, null,
					null);
		}

		return c;

	}

	/*
	* メソッド名: Cursor getFullWordKJ(String word)
	* 説明: Return records like word in Kanji table by word or hanviet
	* パラメータ:word,
	*グローバル変数:
	* 値を返す: Cursor
	* 修正: 20140311 - LINHNC
	* 説明:Created
	* --------------------------------------------------------*/
	public Cursor getFullWordKJ(String word) {

		Cursor c = null;
		String word2;
		if (word.equals(""))
			word2 = "!@#$%";
		else
			word2 = word.trim();
		SQLiteQueryBuilder qb_kj = new SQLiteQueryBuilder();
		qb_kj.setTables("kj");
		String[] args = word2.split("");
		args[0] = "!@#$%";
		c = qb_kj.query(db, null,
				"kanji IN ( " + Converter.makePlaceholders(args.length)
						+ ") OR hanviet LIKE '" + word2 + "%'", args, null,
				null, "hanviet ASC", Integer.toString(args.length));
		return c;

	}

	// TODO Function for Favorite
	// get
	public final Cursor getFavorite(String table) {
		Cursor c = null;
		SQLiteQueryBuilder qb_Favorite = new SQLiteQueryBuilder();
		if (table.equals("jv")) {
			String sqlTables = "FavoriteJV";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, null, null, null, null, "_id ASC",
					null);
		} else if (table.equals("vj")) {
			String sqlTables = "FavoriteVJ";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, null, null, null, null, "_id ASC",
					null);
		} else if (table.equals("kj")) {
			String sqlTables = "FavoriteKJ";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, null, null, null, null, "_id ASC",
					null);
		}
		return c;
	}

	// add
	public final void addFavorite(String word, String table) {

		Cursor c = null;
		SQLiteQueryBuilder qb_Favorite = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id", "word" };
		if (table.equals("FavoriteJV") || table.equals("jv")) {

			qb_Favorite.setTables("jv");
			c = qb_Favorite.query(db, sqlSelect, "word = ?",
					new String[] { word }, null, null, null, null);
			c.moveToFirst();
			ContentValues values = new ContentValues();
			values.put("_id", c.getString(0));
			values.put("word", c.getString(1));
			db.insert("FavoriteJV", null, values);

		} else if (table.equals("FavoriteVJ") || table.equals("vj")) {

			qb_Favorite.setTables("vj");
			c = qb_Favorite.query(db, sqlSelect, "word = ?",
					new String[] { word }, null, null, null, null);
			c.moveToFirst();
			ContentValues values = new ContentValues();
			values.put("_id", c.getString(0));
			values.put("word", c.getString(1));
			db.insert("FavoriteVJ", null, values);

		} else if (table.equals("FavoriteKJ") || table.equals("kj")) {

			qb_Favorite.setTables("kj");
			c = qb_Favorite.query(db, null, "kanji = ?", new String[] { word },
					null, null, null, null);
			c.moveToFirst();
			ContentValues values = new ContentValues();
			values.put("_id", c.getString(0));
			values.put("word", c.getString(1));
			db.insert("FavoriteKJ", null, values);

		}

	}

	// delete
	public final void deleteFavorite(String word, String table) {

		if (table.equals("FavoriteJV") || table.equals("jv")) {
			db.delete("FavoriteJV", "word = ?", new String[] { word });
		} else if (table.equals("FavoriteVJ") || table.equals("vj")) {
			db.delete("FavoriteVJ", "word = ?", new String[] { word });
		} else if (table.equals("FavoriteKJ") || table.equals("kj")) {
			db.delete("FavoriteKJ", "word = ?", new String[] { word });
		}
	}

	// check by id
	public boolean checkFavorite(String id, String table) {
		Cursor c = null;
		SQLiteQueryBuilder qb_Favorite = new SQLiteQueryBuilder();
		if (table.equals("jv")) {
			String sqlTables = "FavoriteJV";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, "_id = ?", new String[] { id },
					null, null, null, null);
		} else if (table.equals("vj")) {
			String sqlTables = "FavoriteVJ";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, "_id = ?", new String[] { id },
					null, null, null, null);
		} else if (table.equals("kj")) {
			String sqlTables = "FavoriteKJ";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, "_id = ?", new String[] { id },
					null, null, null, null);
		} else
			return false;
		if (c.moveToFirst())
			return true; // is favorite
		else
			return false;
	}

	// check by word
	public boolean checkFavoriteByWord(String word, String table) {
		Cursor c = null;
		SQLiteQueryBuilder qb_Favorite = new SQLiteQueryBuilder();
		if (table.equals("jv")) {
			String sqlTables = "FavoriteJV";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, "word = ?", new String[] { word },
					null, null, null, null);
		}
		if (table.equals("vj")) {
			String sqlTables = "FavoriteVJ";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, "word = ?", new String[] { word },
					null, null, null, null);
		}
		if (table.equals("kj")) {
			String sqlTables = "FavoriteKJ";
			qb_Favorite.setTables(sqlTables);
			c = qb_Favorite.query(db, null, "word = ?", new String[] { word },
					null, null, null, null);
		}

		if (c.moveToFirst())
			return true; // is favorite
		else
			return false;
	}

	// TODO Function for History
	// get
	public final Cursor getHistory() {
		Cursor c = null;
		SQLiteQueryBuilder qb_History = new SQLiteQueryBuilder();
		String sqlTables = "history";
		qb_History.setTables(sqlTables);
		c = qb_History.query(db, null, null, null, null, null, "his_id DESC",
				null);
		return c;

	}

	// add
	public final void addHistory(String id, String word, String table) {
		ContentValues values = new ContentValues();
		values.put("_id", id);
		values.put("word", word);
		values.put("_table", table);
		db.insert("history", null, values);
	}

	// delete
	public final void deleteHistory(String word, String table) {
		db.delete("history", "(word = ?) AND (_table = ?)", new String[] {
				word, table });
	}

	// TODO Function for MyDict
	// get
	public final Cursor getMyDict() {
		Cursor c = null;
		SQLiteQueryBuilder qb_myDict = new SQLiteQueryBuilder();
		String sqlTables = "myDict";
		qb_myDict.setTables(sqlTables);
		c = qb_myDict.query(db, null, null, null, null, null, "md_id DESC",
				null);
		return c;
	}

	// add
	public final void addMyDict(String word, String meaning) {
		ContentValues values = new ContentValues();
		values.put("word", word);
		values.put("meaning", meaning);
		db.insert("myDict", null, values);
	}

	// update
	public final void updateMyDict(String id, String word, String meaning) {
		ContentValues values = new ContentValues();
		values.put("word", word);
		values.put("meaning", meaning);
		db.update("myDict", values, "_id = ?", new String[] { id });
	}

	// delete
	public final void deleteMyDict(String word) {
		db.delete("myDict", "word = ?", new String[] { word });
	}

	// check exist
	public final boolean checkExistMyDict(String word) {
		Cursor c = null;
		SQLiteQueryBuilder qb_Favorite = new SQLiteQueryBuilder();
		qb_Favorite.setTables("myDict");
		c = qb_Favorite.query(db, null, "word = ?", new String[] { word },
				null, null, null, null);
		if (c.moveToFirst())
			return true;
		else
			return false;
	}

	// TODO extract database
	/*
	* メソッド名: void copyFile(InputStream in, OutputStream out)
	* 説明: copy file to SD card
	* パラメータ:InputStream,OutputStream
	*グローバル変数:
	* 値を返す: 
	* 修正: 20140220 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	/*
	* メソッド名: copyDatabaseFromAssets()
	* 説明: manage copy file to SD card action
	* パラメータ:
	*グローバル変数:
	* 値を返す: 
	* 修正: 20140220 - LINHNC
	* 説明: Created
	* --------------------------------------------------------*/
	private void copyDatabaseFromAssets() {

		InputStream in = null;
		OutputStream out = null;
		String[] filename = { "_0.cfs", "segments.gen", "segments_2" };
		AssetManager assetManager = mContext.getAssets();
		 File folder = new File( Environment.getExternalStorageDirectory().getAbsolutePath()+"/aedict/" );
			 boolean check = folder.mkdir();
			 File folder2 = new File( Environment.getExternalStorageDirectory().getAbsolutePath()+"/aedict/index-kanjidic" );
			  check = folder2.mkdir();
		for (int i = 0; i < 3; i++) {
			try {
				in = assetManager.open(filename[i]);
				File outFile = new File(
						Environment.getExternalStorageDirectory()
								+ "/aedict/index-kanjidic" + "/", filename[i]);
				out = new FileOutputStream(outFile);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String buildUnionQuery(String[] subQueries, String sortOrder, String limit)
	{
        StringBuilder query = new StringBuilder(128);
        int subQueryCount = subQueries.length;
        String unionOperator =" UNION ";

        for (int i = 0; i < subQueryCount; i++) {
            if (i > 0) {
                query.append(unionOperator);
            }
            query.append(subQueries[i]);
        }
        query.append(" ORDER BY "+ sortOrder);
		query.append(" LIMIT " + limit);
       /* appendClause(query, " ORDER BY ", sortOrder);
        appendClause(query, " LIMIT ", limit);*/
        return query.toString();
	}
}