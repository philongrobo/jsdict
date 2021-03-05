package jsdictmain;

import database.DatabaseController;
import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/*
 *クラス名：  DictionaryProvider
 *日付: 2014/02/19
 *著作権表示: N/A
 *変更ログ:
 *20140301  LINHNC update  function buildUriMatcher
 *			LINHNC update  function query
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140229 LINHNC 説明 */
public class DictionaryProvider extends ContentProvider {
	String TAG = "DictionaryProvider";

	public static String AUTHORITY = "jsdictmain.DictionaryProvider";
	// MIME types used for searching words or looking up a single definition
	public static final String WORDS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/jsdictmain";
	public static final String DEFINITION_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/jsdictmain";

	private DatabaseController mDictionary;

	// UriMatcher stuff
	private static final int SEARCH_WORDS = 0;
	private static final int GET_WORD = 1;
	private static final int SEARCH_SUGGEST = 2;
	private static final int REFRESH_SHORTCUT = 3;
	private static final UriMatcher sURIMatcher = buildUriMatcher();

	/**
	 * Builds up a UriMatcher for search suggestion and shortcut refresh
	 * queries.
	 */
	private static UriMatcher buildUriMatcher() {
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		// to get definitions...
		matcher.addURI(AUTHORITY, "jv", SEARCH_WORDS);
		matcher.addURI(AUTHORITY, "jv/#", GET_WORD);
		matcher.addURI(AUTHORITY, "vj", SEARCH_WORDS);
		matcher.addURI(AUTHORITY, "vj/#", GET_WORD);
		matcher.addURI(AUTHORITY, "kj", SEARCH_WORDS);
		matcher.addURI(AUTHORITY, "kj/#", GET_WORD);
		// to get suggestions...
		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY,
				SEARCH_SUGGEST);
		return matcher;
	}

	@Override
	public boolean onCreate() {
		mDictionary = new DatabaseController(getContext());

		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		// Use the UriMatcher to see what kind of query we have and format the
		// db query accordingly
		switch (sURIMatcher.match(uri)) {
		case SEARCH_SUGGEST:
			return getSuggestions(selectionArgs[0],
					ResultViewController.Table_Mode[ResultViewController.mode]);
		case SEARCH_WORDS:
			if (selectionArgs == null) {
				throw new IllegalArgumentException(
						"selectionArgs must be provided for the Uri: " + uri);
			}
		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	private Cursor getSuggestions(String query, String sqlTables) {
		query = query.toLowerCase();
		return mDictionary.getFullWord(query, sqlTables);
	}

	/**
	 * This method is required in order to query the supported types. It's also
	 * useful in our own query() method to determine the type of Uri received.
	 */
	@Override
	public String getType(Uri uri) {
		switch (sURIMatcher.match(uri)) {
		case SEARCH_WORDS:
			return WORDS_MIME_TYPE;
		case GET_WORD:
			return DEFINITION_MIME_TYPE;
		case SEARCH_SUGGEST:
			return SearchManager.SUGGEST_MIME_TYPE;
		case REFRESH_SHORTCUT:
			return SearchManager.SHORTCUT_MIME_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	// Other required implementations...

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}
}
