package pagerview;


import vn.team5b1.jsdict.R;
import mydict.MyDictUpdateViewController;
import jsdictmain.ResultViewController;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
/*
 *クラス名：  RowAdapter
 *日付: 2014/02/19
 *著作権表示: N/A
 *変更ログ:
 *20140301  LINHNC update  function getView
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140229 HOANGLA 説明 */
public class RowAdapter extends ArrayAdapter<RowObject> {
	Context _context;
	int _layoutResourceId;
	RowObject[] _arrayData;

	public RowAdapter(Context context, int layoutResourceId,
			RowObject[] arrayData) {
		super(context, layoutResourceId, arrayData);
		this._context = context;
		this._layoutResourceId = layoutResourceId;
		this._arrayData = arrayData;
	}

	public View getView(int position, View convertView, final ViewGroup parent) {
		View _row = convertView;
		RowObjectViewHolder _viewHolder = null;

		if (_row == null) {
			LayoutInflater _inflater = ((Activity) _context)
					.getLayoutInflater();
			_row = _inflater.inflate(_layoutResourceId, parent, false);

			_viewHolder = new RowObjectViewHolder();
			 _viewHolder._imgIcon = (ImageView)_row.findViewById(R.id.imgIcon);			 
			_viewHolder._txtTitle = (TextView) _row.findViewById(R.id.txtTitle);
			_row.setTag(_viewHolder);
		} else {
			_viewHolder = (RowObjectViewHolder) _row.getTag();
		}
		RowObject _objRow = _arrayData[position];
		_viewHolder._txtTitle.setText(_objRow.title);		
		_viewHolder._imgIcon.setImageResource(_objRow.icon);
		_viewHolder._imgIcon.setTag(position);
		_viewHolder._imgIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// load MyDictUpdateView
				int position = (Integer) v.getTag();
				Cursor fullword  = ResultViewController.db.getFullWord(_arrayData[position].title, "myDict");
				if(fullword.moveToFirst())
				{
					MyDictUpdateViewController.word=fullword.getString(2);
					MyDictUpdateViewController.meaning=fullword.getString(3);
					MyDictUpdateViewController.isUpdate = true;
				}
				Activity a =(Activity) parent.getContext();
				Intent intent = new Intent(parent.getContext(), MyDictUpdateViewController.class);
				a.startActivityForResult(intent, 10001);
			}
		});
		return _row;
	}

	static class RowObjectViewHolder {
		ImageView _imgIcon;	
		TextView _txtTitle;
	}
}

