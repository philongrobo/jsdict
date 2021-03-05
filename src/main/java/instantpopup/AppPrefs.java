package instantpopup;

public class AppPrefs implements LogTag {
	private AppPrefs() {
	}

	public static final String NAME = "AppPrefs";
	public static final String KEY_MONITOR_INTERVAL = "monitor.interval";
	public static final String KEY_OPERATING_CLIPBOARD = "clipboard";
	public static final int DEF_MONITOR_INTERVAL = 3000;
	public static final int DEF_OPERATING_CLIPBOARD = 1; // 1 = default
															// clipboard
	public static volatile int operatingClipboardId;
}
