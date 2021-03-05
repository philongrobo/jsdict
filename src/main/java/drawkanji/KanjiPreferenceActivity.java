package drawkanji;

import vn.team5b1.jsdict.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class KanjiPreferenceActivity extends PreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
