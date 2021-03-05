/**
 *     Aedict - an EDICT browser for Android
 Copyright (C) 2009 Martin Vysny
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package radicalsearch;

import java.io.InputStream;
import java.util.ArrayList;//
import java.util.Formatter;
import java.util.List;


import sk.baka.aedict.dict.DictEntry;
import sk.baka.aedict.dict.DictTypeEnum;
import sk.baka.aedict.dict.Dictionary;
import sk.baka.aedict.dict.DictionaryVersions;
//import sk.baka.aedict.dict.DownloaderService;

import sk.baka.autils.DialogUtils;
import sk.baka.autils.ListBuilder;
import sk.baka.autils.MiscUtils;
import vn.team5b1.jsdict.R;
import android.app.Application;

import android.content.Context;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

/*クラス名： AedictApp
*日付: 20140303
*著作権表示: NA
*変更ログ: NA
* 日 著者 説明: 20140303 Hoppd create AedictApp class
* */
public class AedictApp extends Application implements OnSharedPreferenceChangeListener {

	@Override
	public void onCreate() {
		super.onCreate();
		
		instance = this;
		DialogUtils.resError = R.string.error;
		PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
		apply(new Config(this));
	
	}

	@Override
	public void onTerminate() {
			super.onTerminate();
	}

	
	private static AedictApp instance;

	/**
	 * Returns the application instance.
	 * 
	 * @return the instance.
	 */
	public static AedictApp getApp() {
		if (instance == null) {
			throw new IllegalStateException("Not yet initialized");
		}
		return instance;
	}


	
	/* メソッド名:getStr
	* 説明: Returns string associated with given resource ID.
	* パラメータ: int resId
	*グローバル変数: NA
	* 値を返す: String 
	* 修正: NA
	* 説明: 20140303 Hoppd create launch method
* */
	public static String getStr(final int resId) {
		return getApp().getString(resId);
	}

	
	private static String version;

	
	/* 
	 * メソッド名:getVersion
	* 説明: Returns the Ambient version
	* the version string or "unknown" if the version is not available.
	* パラメータ: null
	*グローバル変数: NA
	* 値を返す: String 
	* 修正: NA
	* 説明: 20140303 Hoppd create launch method
* */
	public static String getVersion() {
		if (version != null) {
			return version;
		}
		final InputStream in = getApp().getClassLoader().getResourceAsStream("version");
		if (in != null) {
			try {
				version = new String(MiscUtils.readFully(in), "UTF-8");
			} catch (Exception ex) {
				Log.e(AedictApp.class.getSimpleName(), "Failed to get version", ex);
				version = "unknown";
			} finally {
				MiscUtils.closeQuietly(in);
			}
		}
		return version;
	}

	/*クラス名： Config
	*日付: 20140303
	*著作権表示: NA
	*変更ログ: NA
	* 日 著者 説明: 20140303 Hoppd create Config class
	* */
	
	
	public static class Config {
		private final SharedPreferences prefs;

	
		/* 
		 * メソッド名:Constructor Config
		* 説明: load default shared preferences from this context..
		* パラメータ: Context context
		*グローバル変数: NA
		* 値を返す: Config
		* 修正: NA
		* 説明: 20140303 Hoppd create launch method
	* */
		public Config(final Context context) {
			this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
		}

	
		
		public static final String KEY_RECENTLY_VIEWED_ITEMS = "recentlyViewed";

		
		/* 
		 * メソッド名:getRecentlyViewed
		* 説明: Recently viewed DictEntries.
		* パラメータ: null
		*グローバル変数: NA
		* 値を返す: DictEntry
		* 修正: NA
		* 説明: 20140303 Hoppd create getRecentlyViewed method
	* */
		
		public synchronized List<DictEntry> getRecentlyViewed() {
			return DictEntry.fromExternalList(prefs.getString(KEY_RECENTLY_VIEWED_ITEMS, ""));
		}

		
		/* 
		 * メソッド名:setRecentlyViewed
		* 説明: Set Recently viewed DictEntries.
		* パラメータ: null
		 *グローバル変数: NA
		* 値を返す: DictEntry
		* 修正: NA
		* 説明: 20140303 Hoppd create setRecentlyViewed method
	* */
		public synchronized void setRecentlyViewed(final List<? extends DictEntry> notepadItems) {
			commit(prefs.edit().putString(KEY_RECENTLY_VIEWED_ITEMS, DictEntry.toExternalList(notepadItems)));
		}

		private void commit(final Editor ed) {
			if (!ed.commit()) {
				throw new IllegalStateException("Failed to commit new SharedPreferences value");
			}
		}

		
		/* 
		 * メソッド名:setSamplesDictType
		* 説明: Sets the dictionary type used to retrieve the example sentences.
		* パラメータ: dict
		*グローバル変数: NA
		* 値を返す: DictTypeEnum
		* 修正: NA
		* 説明: 20140303 Hoppd create setSamplesDictType method
	* */
	
		public synchronized void setSamplesDictType(final DictTypeEnum dict) {
			if(dict!=DictTypeEnum.Tatoeba && dict!=DictTypeEnum.Tanaka) {
				throw new RuntimeException("Invalid dict type: "+dict);
			}
			
		}


		
		public static final String KEY_SORT = "sort";
		
		
		public synchronized void setSorted(final boolean sorted) {
			commit(prefs.edit().putBoolean(KEY_SORT, sorted));
		}

		/**
		 * True if the results should be sorted (the default), false otherwise.
		 * See http://code.google.com/p/aedict/issues/detail?id=56 for details.
		 * 
		 * @return sort
		 */
		
		/* 
		 * メソッド名:isSorted
		* 説明: True if the results should be sorted (the default), false otherwise.
		* パラメータ: null
		*グローバル変数: NA
		* 値を返す: boolean
		* 修正: NA
		* 説明: 20140303 Hoppd create isSorted method
	* */
		public synchronized boolean isSorted() {
			return prefs.getBoolean(KEY_SORT, true);
		}

		/**
		 * Returns the dictionary location on the SD card of currently selected EDICT
		 * dictionary.
		 * 
		 * @return absolute OS-specific location of the dictionary.
		 */
		
		/* 
		 * メソッド名:setCurrentDictVersions
		* 説明: Returns the dictionary location on the SD card of 
		* パラメータ: dv
		*グローバル変数: NA
		* 値を返す: DictionaryVersions
		* 修正: NA
		* 説明: 20140303 Hoppd create setCurrentDictVersions method
	* */
		
		private static final String KEY_CURRENT_DICT_VERSIONS = "currentDictVersions";
		public synchronized void setCurrentDictVersions(DictionaryVersions dv) {
			commit(prefs.edit().putString(KEY_CURRENT_DICT_VERSIONS, dv.toExternal()));
		}
		public synchronized DictionaryVersions getCurrentDictVersions() {
			return DictionaryVersions.fromExternal(prefs.getString(KEY_CURRENT_DICT_VERSIONS, ""));
		}
		private static final String KEY_SERVER_DICT_VERSIONS = "serverDictVersions";
		public synchronized void setServerDictVersions(DictionaryVersions dv) {
			commit(prefs.edit().putString(KEY_SERVER_DICT_VERSIONS, dv.toExternal()));
		}
		public synchronized DictionaryVersions getServerDictVersions() {
			return DictionaryVersions.fromExternal(prefs.getString(KEY_SERVER_DICT_VERSIONS, ""));
		}
	}

	/**
	 * Loads the configuration from a shared preferences.
	 * 
	 * @return the configuration.
	 */
	public static Config getConfig() {
		return new Config(instance);
	}

	//private static final int NOTIFICATION_ID = 1;

	/**
	 * Applies values from given configuration file, e.g. removes or adds the
	 * notification icon etc.
	 * 
	 * @param cfg
	 *            non-null configuration
	 */
	private static void apply(final Config cfg) {
	
	}

	/**
	 * If true then the instrumentation (testing) is in progress.
	 */
	public static boolean isInstrumentation = false;

	

	public static final DictionaryVersions MIN_REQUIRED = new DictionaryVersions();
	static {
		MIN_REQUIRED.versions.put(new Dictionary(DictTypeEnum.Kanjidic, null), "20110313");
		MIN_REQUIRED.versions.put(new Dictionary(DictTypeEnum.Edict, null), "20110313");
		MIN_REQUIRED.versions.put(new Dictionary(DictTypeEnum.Edict, "compdic"), "20110313");
		MIN_REQUIRED.versions.put(new Dictionary(DictTypeEnum.Edict, "enamdict"), "20110313");
		MIN_REQUIRED.versions.put(new Dictionary(DictTypeEnum.Edict, "wdjteuc"), "20110313");
		MIN_REQUIRED.versions.put(new Dictionary(DictTypeEnum.Edict, "french-fj"), "20110313");
		MIN_REQUIRED.versions.put(new Dictionary(DictTypeEnum.Edict, "hispadic"), "20110313");
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public static CharSequence format(int kanjianalysisof, String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
