package instantpopup;

import jsdictmain.ResultViewController;

import vn.team5b1.jsdict.R;
import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class TogglePopup extends StandOutWindow {
	public View view;
	public ImageView imgToggle;
	public boolean isChecked;

	@Override
	public String getAppName() {
		return "JSDict";
	}

	@Override
	public int getAppIcon() {
		return android.R.drawable.ic_menu_close_clear_cancel;
	}

	@SuppressLint("NewApi")
	@Override
	public void createAndAttachView(int id, final FrameLayout frame) {
		// create a new layout from body.xml
		final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.popup_toggle, frame, true);
		/*
		 * toggle = (ToggleButton) view .findViewById(R.id.toggle_popup);
		 */
		isChecked = false;
		imgToggle = (ImageView) view.findViewById(R.id.toggle_popup);
		imgToggle.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (isChecked == false) {
					imgToggle.setBackgroundResource(R.drawable.popup_toggle_on);

					ComponentName service = getApplicationContext()
							.startService(
									new Intent(getApplicationContext(),
											ClipboardMonitor.class));
					StandOutWindow.closeAll(getApplicationContext(),
							ClipboardMonitor.class);
					StandOutWindow.show(getApplicationContext(),
							ClipboardMonitor.class, StandOutWindow.DEFAULT_ID);
					isChecked = true;
				} else {
					isChecked = false;
					imgToggle
							.setBackgroundResource(R.drawable.popup_toggle_off);
					getApplicationContext().stopService(
							new Intent(getApplicationContext(),
									ClipboardMonitor.class));
					StandOutWindow.closeAll(getApplicationContext(),
							ClipboardMonitor.class);
				}
				return false;

			}
		});
	}

	@Override
	public void onReceiveData(int id, int requestCode, Bundle data,
			Class<? extends StandOutWindow> fromCls, int fromId) {
		// receive data from WidgetsWindow's button press
		// to show off the data sending framework
		switch (requestCode) {
		case 0:
			Window window = getWindow(id);
			if (window == null) {
				return;
			}
			imgToggle
			.setBackgroundResource(R.drawable.popup_toggle_off);
			break;
		default:
			Log.d("MultiWindow", "Unexpected data received.");
			break;
		}
	}

	// the window will be centered
	@Override
	public StandOutLayoutParams getParams(int id, Window window) {
		return new StandOutLayoutParams(id, 250, 300,
				StandOutLayoutParams.CENTER, StandOutLayoutParams.CENTER);
	}

	// move the window by dragging the view
	@Override
	public int getFlags(int id) {
		return super.getFlags(id) | StandOutFlags.FLAG_BODY_MOVE_ENABLE
				| StandOutFlags.FLAG_WINDOW_FOCUSABLE_DISABLE;
	}

	@Override
	public String getPersistentNotificationMessage(int id) {
		return "";
	}

	@Override
	public Intent getPersistentNotificationIntent(int id) {
		return new Intent(this, ResultViewController.class);
		/*
		 * StandOutWindow.show(this, WidgetsWindow.class,
		 * StandOutWindow.DEFAULT_ID);
		 */
	}

}
