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

import vn.team5b1.jsdict.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.app.Activity;



/*クラス名： KanjiDetailActivity
*日付: 20140228
*著作権表示: NA
*変更ログ: NA
* 日 著者 説明: 20140228 Hoppd create KanjiDetailActivity class
* */
public class KanjiDetailActivity extends Activity {
	/*
	* メソッド名: launch
	* 説明: have a intent tp call and tranfer a string to KanjiDetailActivity class
	* パラメータ: activity,entry
	*グローバル変数: NA
	* 値を返す: Void 
	* 修正: NA
	* 説明: 20140228 Hoppd create launch method
* */
	

	public static void launch(final Context activity, String entry) {

		final Intent intent = new Intent(activity, KanjiDetailActivity.class);
		intent.putExtra("Key", entry);
		activity.startActivity(intent);

	}
	
	/*
	* メソッド名: onCreate
	* 説明: is called when KanjiDetailActivity start
	* パラメータ: savedInstanceState
	*グローバル変数: NA
	* 値を返す: void 
	* 修正: NA
	* 説明: 20140228 Hoppd create onCreate method
* */
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_test);
		EditText ed = (EditText) findViewById(R.id.editText1);

	

		String textToSearch = (String) getIntent().getSerializableExtra(
				"Key");

		ed.append(textToSearch);

		ed.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

}
