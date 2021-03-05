package translation;

import vn.team5b1.jsdict.R;

import com.actionbarsherlock.app.SherlockFragment;
import com.memetix.mst.language.Language;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class TranslationViewController extends SherlockFragment implements
		OnItemSelectedListener, OnClickListener {
	Language language1;
	Language language2;
	Spinner spinner1;
	Spinner spinner2;
	EditText edtLang1;
	EditText edtLang2;
	Button btnSearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//
		View view = inflater.inflate(R.layout.activity_translation, container,
				false);
		spinner1 = (Spinner) view.findViewById(R.id.language1_spinner);
		spinner2 = (Spinner) view.findViewById(R.id.language2_spinner);
		edtLang1 = (EditText) view.findViewById(R.id.edtLang1);
		edtLang2 = (EditText) view.findViewById(R.id.edtLang2);
		btnSearch = (Button) view.findViewById(R.id.btnTrans);
		btnSearch.setOnClickListener(this);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity().getBaseContext(), R.array.language1_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(this);
		spinner2.setAdapter(adapter);
		spinner2.setOnItemSelectedListener(this);
		return view;
	}

	public void RunTranslate() {
		edtLang2.setText(R.string.strTranslating);
		new TransClass(language1, language2, edtLang1.getText().toString()) {
			protected void onPostExecute(Boolean result) {
				edtLang2.setText(translatedText);
			}
		}.execute();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		switch (spinner1.getSelectedItemPosition()) {
		case 0:
			language1 = Language.ENGLISH;
			break;
		case 1:
			language1 = Language.VIETNAMESE;
			break;
		case 2:
			language1 = Language.JAPANESE;
			break;
		}
		switch (spinner2.getSelectedItemPosition()) {
		case 0:
			language2 = Language.ENGLISH;
			break;
		case 1:
			language2 = Language.VIETNAMESE;
			break;
		case 2:
			language2 = Language.JAPANESE;
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnTrans:
			RunTranslate();
			break;
		}
	}
}
