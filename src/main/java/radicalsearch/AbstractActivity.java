

package radicalsearch;

import sk.baka.autils.AndroidUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


/*クラス名： AbstractActivity
*日付: 20140303
*著作権表示: Aedict Project
*変更ログ: NA
* 日 著者 説明: 
* Properly initializes the application.  
* 20140303 Hoppd create AbstractActivity class
* */

public abstract class AbstractActivity extends Activity {

	/* 
	 * メソッド名:addActivityLauncher
	* 説明: Adds a menu item to given menu which launches given activity.
	* the version string or "unknown" if the version is not available.      
	* パラメータ: final Menu menu, final int caption, final int icon, final Class
	* 値を返す: void
	* 修正: NA
	* 説明: 20140303 Hoppd create addActivityLauncher method
* */
	
	
	protected final void addActivityLauncher(final Menu menu, final int caption, final int icon, final Class<? extends Activity> activity) {
		addActivityLauncher(this, menu, caption, icon, activity);
	}

	
	/* 
	 * メソッド名:addActivityLauncher
	* 説明: 	 * Adds a menu item to given menu which launches given activity.
	* パラメータ: final Menu menu, final int caption, final int icon, final Class
	* 値を返す: void
	* 修正: NA
	* 説明: 20140303 Hoppd create addActivityLauncher method
* */
	public static void addActivityLauncher(final Activity context, final Menu menu, final int caption, final int icon, final Class<? extends Activity> activity) {
		final MenuItem item2 = menu.add(caption);
		item2.setIcon(icon);
		item2.setOnMenuItemClickListener(AndroidUtils.safe(context, new MenuItem.OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {
				final Intent intent = new Intent(context, activity);
				context.startActivity(intent);
				return true;
			}
		}));
	}


	/* 
	 * メソッド名:setButtonActivityLauncher
	* 説明: 	 Sets given button to be an activity launcher.
	* パラメータ: buttonId, activityToLaunch
	* 値を返す: void
	* 修正: NA
	* 説明: 20140303 Hoppd create setButtonActivityLauncher method
* */
	
	public void setButtonActivityLauncher(final int buttonId, final Class<? extends Activity> activityToLaunch) {
		setButtonActivityLauncher(this, buttonId, activityToLaunch);
	}

	
	/* 
	 * メソッド名:setButtonActivityLauncher
	* 説明: 	 Sets given button to be an activity launcher.
	* パラメータ: activity,buttonId, activityToLaunch
	* 値を返す: void
	* 修正: NA
	* 説明: 20140303 Hoppd create setButtonActivityLauncher method
* */
	public static void setButtonActivityLauncher(final Activity activity, final int buttonId, final Class<? extends Activity> activityToLaunch) {
		setButtonActivityLauncher(activity, (Button) activity.findViewById(buttonId), activityToLaunch);
	}


	/* 
	 * メソッド名:setButtonActivityLauncher
	* 説明: 	 Sets given button to be an activity launcher.
	* パラメータ: buttonId, activityToLaunch
	* 値を返す: void
	* 修正: NA
	* 説明: 20140303 Hoppd create setButtonActivityLauncher method
* */
	
	public void setButtonActivityLauncher(final Button button, final Class<? extends Activity> activityToLaunch) {
		setButtonActivityLauncher(this, button, activityToLaunch);
	}


	/* 
	 * メソッド名:setButtonActivityLauncher
	* 説明: 	 Sets given button to be an activity launcher.
	* パラメータ: buttonId, activityToLaunch,activity
	* 値を返す: void
	* 修正: NA
	* 説明: 20140303 Hoppd create setButtonActivityLauncher method
* */
	public static void setButtonActivityLauncher(final Activity activity, final Button button, final Class<? extends Activity> activityToLaunch) {
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				final Intent intent = new Intent(activity, activityToLaunch);
				activity.startActivity(intent);
			}
		});
	}

	
}
