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

import inputmethod.DrawKanjiViewController;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import sk.baka.aedict.dict.DictEntry;

import sk.baka.aedict.dict.EdictEntry;
import sk.baka.aedict.dict.KanjidicEntry;
import sk.baka.aedict.dict.LuceneSearch;
import sk.baka.aedict.dict.MatcherEnum;
import sk.baka.aedict.dict.SearchQuery;
import sk.baka.aedict.kanji.KanjiUtils;
import sk.baka.autils.AbstractTask;
import sk.baka.autils.MiscUtils;
import sk.baka.autils.Progress;
import vn.team5b1.jsdict.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.TextView;

/**
 * Analyzes each kanji in given word.
 * 
 * @author Martin Vysny
 */
@SuppressLint("NewApi")
/*クラス名： KanjiAnalyzeActivity
*日付: 20140228
*著作権表示: Aedict Project
*変更ログ: NA
* 日 著者 説明: 20140228 Hoppd create KanjiAnalyzeActivity class
* */
public class KanjiAnalyzeActivity extends ListActivity {
	/**
	 * The string word to analyze.
	 */
	public static final String INTENTKEY_WORD = "word";
	public static final String RADICAL_SEARCH ="Radical";
	public static String StringToSearch = "";
	public static String radical="";
	int mode;
	
	public static final String INTENTKEY_ENTRYLIST = "entrylist";
	
	public static final String INTENTKEY_WORD_ANALYSIS = "wordAnalysis";


	/*
	* メソッド名: launch
	* 説明: 
	* パラメータ: activity,  word, isWordAnalysis
	*グローバル変数: NA
	* 値を返す: String 
	* 修正: NA
	* 説明: 20140228 Hoppd create launch method
* */
	
	

	public static void launch(final Activity activity, final String word,
			final boolean isWordAnalysis) {
		if (word == null) {
			throw new IllegalArgumentException("word is null");
		}
		
		final Intent i = new Intent(activity, KanjiAnalyzeActivity.class);
		i.putExtra(INTENTKEY_WORD, word);
		i.putExtra(INTENTKEY_WORD_ANALYSIS, isWordAnalysis);
		activity.startActivity(i);
	}

	public static void launch(final Activity activity,
			final List<? extends DictEntry> entries,
			final String radical,
			final int mode,
			final boolean isWordAnalysis) {
		if (entries == null) {
			throw new IllegalArgumentException("entries is null");
		}
	
		final Intent i = new Intent(activity, KanjiAnalyzeActivity.class);
		i.putExtra(INTENTKEY_ENTRYLIST, (Serializable) entries);
		i.putExtra(INTENTKEY_WORD_ANALYSIS, isWordAnalysis);
		i.putExtra("Radical", radical);
		i.putExtra("MODE_DICT", mode);
		activity.startActivity(i);
	}

	private List<DictEntry> model = null;
	/**
	 * The word to analyze. If null then we were simply given a list of
	 * EdictEntry directly.
	 */
	private String word;
	/**
	 * True if we parsed given word on a per-character basis, false on a
	 * per-word basis.
	 */
	private boolean isAnalysisPerCharacter = true;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
			
			word = getIntent().getStringExtra(INTENTKEY_WORD);
			radical = getIntent().getStringExtra("Radical");
			if(radical == null){
				radical = "";
			}
			mode = getIntent().getIntExtra("MODE_DICT", 0);
			model = (List<DictEntry>) getIntent().getSerializableExtra(
					INTENTKEY_ENTRYLIST);
			isAnalysisPerCharacter = !getIntent().getBooleanExtra(
					INTENTKEY_WORD_ANALYSIS, false);
			if (word == null && model == null) {
				throw new IllegalArgumentException(
						"Both word and entrylist are null");
			}
			getActionBar().setTitle(R.string.kanjiAnalysisOf);
			setTitle(AedictApp.format(R.string.kanjiAnalysisOf,
					word != null ? word : DictEntry.getJapaneseWord(model)));
			if (model == null) {
				recomputeModel();
			} else {
				setListAdapter(newAdapter());
			}
		}
	}
	private ArrayAdapter<DictEntry> newAdapter() {
		return new ArrayAdapter<DictEntry>(this, R.layout.kanjidic_list_item,
				model) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = convertView;
				if (v == null) {
					v = getLayoutInflater().inflate(
							R.layout.kanjidic_list_item, getListView(), false);
				}
				final DictEntry e = model.get(position);
				final StringBuilder sb = new StringBuilder();
				if (e instanceof KanjidicEntry) {
					final KanjidicEntry ee = (KanjidicEntry) e;
					sb.append(" Strokes:").append(ee.strokes);
				}
				if (sb.length() > 0) {
					sb.replace(0, 1, "\n");
				}
				((TextView) v.findViewById(android.R.id.text2)).setText(sb
						.toString());
				final TextView tv = (TextView) v.findViewById(R.id.kanjiBig);
				tv.setText(splitToRows(e.getJapanese()));
				return v;
			}

			/*
			* メソッド名: splitToRows
			* 説明: split to rows 
			* パラメータ: str
			*グローバル変数: NA
			* 値を返す: String 
			* 修正: NA
			* 説明: 20140228 Hoppd create splitToRows method
		* */
			private String splitToRows(final String str) {
				if (str == null) {
					return "";
				}
				final StringBuilder sb = new StringBuilder(str.length() * 4 / 3);
				for (int i = 0; i < str.length(); i++) {
					if ((i > 0) && (i % 3 == 0)) {
						sb.append('\n');
					}
					sb.append(str.charAt(i));
				}
				return sb.toString();
			}
		};
	}

	/*
	* メソッド名: onListItemClick
	* 説明: catch event click item
	* パラメータ: l, position, id
	*グローバル変数: NA
	* 値を返す: String 
	* 修正: NA
	* 説明: 20140228 Hoppd create onListItemClick method
* */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final DictEntry e = model.get(position);
		if (!e.isValid()) {
			return;
		}
		if (e instanceof KanjidicEntry) {
			StringToSearch = radical + e.getJapanese();
			// KanjiDetailActivity.launch(this, StringToSearch);
			Intent intent = new Intent(this, jsdictmain.ResultViewController.class);
			intent.putExtra("RADICAL_SEARCH", StringToSearch);
			intent.putExtra("MODE_DICT", mode);
			intent.putExtra("IS_OPEN_SEARCHVIEW", true);
			intent.putExtra("IS_NEW_ACTIVITY", true);
			startActivity(intent);
			finish();
		} else if (e instanceof EdictEntry) { 
			Intent myIntent1 = new Intent(this, KanjiAnalyzeActivity.class);
			startActivity(myIntent1);
		} else {
			Intent myIntent2 = new Intent(this, KanjiAnalyzeActivity.class);
			startActivity(myIntent2);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if (word == null) {
			return false;
		}
		final MenuItem item;
		if (!isAnalysisPerCharacter) {
			item = menu.add(R.string.analyzeCharacters);
			item.setIcon(android.R.drawable.ic_menu_zoom);
		} else {
			item = menu.add(R.string.analyzeWords);
			item.setIcon(android.R.drawable.ic_menu_search);
		}
		item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {
				isAnalysisPerCharacter = !isAnalysisPerCharacter;
				recomputeModel();
				return true;
			}
		});
		/* showRomaji.register(this, menu); */
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// showRomaji.onResume();
	}

	private void recomputeModel() {
		new RecomputeModel().execute(AedictApp.isInstrumentation, this, word);
	}

	private class RecomputeModel extends AbstractTask<String, List<DictEntry>> {

		@Override
		protected void cleanupAfterError(Exception ex) {
			// nothing to do
		}

		@Override
		protected void onSucceeded(List<DictEntry> result) {
			model = result;
			setListAdapter(newAdapter());
		}

		@Override
		public List<DictEntry> impl(String... params) throws Exception {
			publish(new Progress(AedictApp.getStr(R.string.analyzing), 0, 100));
			if (isAnalysisPerCharacter) {
				// remove all non-letter characters
				final String w = word.replaceAll("[^\\p{javaLetter}]+", "");
				return analyzeByCharacters(KanjiUtils.halfwidthToKatakana(w));
			} else {
				return analyzeByWords(KanjiUtils.halfwidthToKatakana(word));
			}
		}

		private List<DictEntry> analyzeByWords(final String sentence)
				throws IOException {
			final List<DictEntry> result = new ArrayList<DictEntry>();
			return result;
		}

		

		
		
		/*
		* メソッド名: analyzeByCharacters
		* 説明:  Tries to find longest word which is present in the  dictionary.
		* The search starts with given word, then cuts the last character off,
		* パラメータ: word
		*グローバル変数: NA
		* 値を返す: String 
		* 修正: NA
		* 説明: 20140228 Hoppd create analyzeByCharacters method
	* */
		

		private List<DictEntry> analyzeByCharacters(final String word)
				throws IOException {
			final List<DictEntry> result = new ArrayList<DictEntry>(
					word.length());
		
			try {
				LuceneSearch lsKanjidic = null;
				
				try {
					final String w = MiscUtils.removeWhitespaces(word);
					for (int i = 0; i < w.length(); i++) {
						publish(new Progress(null, i, w.length()));
						if (isCancelled()) {
							return null;
						}
						final char c = w.charAt(i);
						final boolean isKanji = KanjiUtils.isKanji(c);
						if (!isKanji) {
							result.add(new DictEntry(String.valueOf(c), String
									.valueOf(c), ""));
						} else {
							
							final SearchQuery q = SearchQuery.searchJpEdict(
									String.valueOf(c), MatcherEnum.Exact);
							List<DictEntry> matches = null;
							DictEntry ee = null;
							if (lsKanjidic != null) {
								matches = lsKanjidic.search(q, 1);
								DictEntry.removeInvalid(matches);
							}
							if (matches != null && !matches.isEmpty()) {
								ee = matches.get(0);
							}
							if (ee == null) {
								
								DictEntry.removeInvalid(matches);
								if (!matches.isEmpty()) {
									ee = matches.get(0);
								}
							}
							if (ee == null) {
								
								ee = new DictEntry(String.valueOf(c), "", "");
							}
							result.add(ee);
						}
					}
					return result;
				} finally {
					MiscUtils.closeQuietly(lsKanjidic);
				}
			} finally {
			
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			startActivity(new Intent(this, DrawKanjiViewController.class));
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}