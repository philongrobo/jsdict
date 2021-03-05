package pagerview;

import instantpopup.TogglePopup;
import vn.team5b1.jsdict.R;
import wei.mark.standout.StandOutWindow;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PagerViewController extends BaseAdapter {

	// Declare Variables
	Context context;
	String[] mTitle;
	int[] mSwitch;
	int[] mIcon;
	LayoutInflater inflater;
	boolean statePopup;

	public PagerViewController(Context context, String[] title, int[] mswitch,
			int[] icon) {
		this.context = context;
		this.mTitle = title;
		this.mSwitch = mswitch;
		this.mIcon = icon;
	}

	@Override
	public int getCount() {
		return mTitle.length;
	}

	@Override
	public Object getItem(int position) {
		return mTitle[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView txtTitle;
		Switch imgSwitch;
		ImageView imgIcon;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.drawer_list_item, parent,
				false);

		// Locate the TextViews in drawer_list_item.xml
		txtTitle = (TextView) itemView.findViewById(R.id.title);
		imgSwitch = (Switch) itemView.findViewById(R.id.switchPopup);

		// Locate the ImageView in drawer_list_item.xml
		imgIcon = (ImageView) itemView.findViewById(R.id.icon);

		// Set the results into TextViews
		txtTitle.setText(mTitle[position]);

		// Set the results into ImageView
		imgIcon.setImageResource(mIcon[position]);

		// Set Switch for instant lookup
		imgSwitch.setVisibility(mSwitch[position]);
		SharedPreferences prefs = context.getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		final SharedPreferences.Editor editor = prefs.edit();
		statePopup = prefs.getBoolean("POPUP", false);
		imgSwitch.setChecked(statePopup);
		imgSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					startClipboardMonitor();
					editor.putBoolean("POPUP", true);
					editor.commit();
				} else {
					stopClipboardMonitor();
					editor.putBoolean("POPUP", false);
					editor.commit();
				}
			}
		});
		return itemView;
	}

	public void startClipboardMonitor() {
		StandOutWindow.closeAll(context, TogglePopup.class);
		StandOutWindow.show(context, TogglePopup.class,
				StandOutWindow.DEFAULT_ID);
	}

	public void stopClipboardMonitor() {
		StandOutWindow.closeAll(context, TogglePopup.class);
	}
}