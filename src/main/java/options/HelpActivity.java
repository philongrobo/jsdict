package options;

import pagerview.RowAdapter;
import pagerview.RowObject;
import vn.team5b1.jsdict.R;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HelpActivity extends SherlockFragment  {
	private ListView _listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_help, container,
				false);
		ListView _listView;
		RowObject _arrayRowObject[] = new RowObject[] {
				new RowObject(R.drawable.purple_next, "Video tutorials",""),
				new RowObject(R.drawable.purple_next, "Image Slide Tutorial",""),
				new RowObject(R.drawable.purple_next, "FAQs",""),
				new RowObject(R.drawable.purple_next, "Online Supports","")};

		RowAdapter adapter = new RowAdapter(getActivity(),
				R.layout.listview_item_row_help, _arrayRowObject);
		_listView = (ListView) rootView.findViewById(R.id.listViewHelp);

		_listView.setAdapter(adapter);
		return rootView;
	}

}
