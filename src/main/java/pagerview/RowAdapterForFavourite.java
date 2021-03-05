package pagerview;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ToggleButton;
/*
 *クラス名：  RowAdapterForFavourite
 *日付: 2014/02/20
 *著作権表示: N/A
 *変更ログ:
 *20140301  LINHNC update  function getView
 *			LINHNC update  function RowAdapterForFavourite
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140220 HoangLA 説明 */
public class RowAdapterForFavourite extends ArrayAdapter<RowObject> {
	Context _context;
	int _layoutResourceId;
	RowObject[] _arrayData;
	String _favoriteTable;
	
	public RowAdapterForFavourite(Context context, int layoutResourceId,
			RowObject[] arrayData,String favoriteTable) {
		super(context, layoutResourceId, arrayData);
		this._context = context;
		this._layoutResourceId = layoutResourceId;
		this._arrayData = arrayData;
		this._favoriteTable = favoriteTable;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View _row = convertView;
		RowObjectViewHolder _viewHolder = null;

		if (_row == null) {
			LayoutInflater _inflater = ((Activity) _context)
					.getLayoutInflater();
			_row = _inflater.inflate(_layoutResourceId, parent, false);

			_viewHolder = new RowObjectViewHolder();
			_viewHolder._txtTitle = (TextView) _row.findViewById(R.id.txtTitle);
			_viewHolder._imgIcon = (ToggleButton) _row.findViewById(R.id.favourite_remove_btn);
			_row.setTag(_viewHolder);
		} else {
			_viewHolder = (RowObjectViewHolder) _row.getTag();
		}
		RowObject _objRow = _arrayData[position];
		 //stored table value and position value
		String[] tag = new String[2]; 
		tag[0] = _objRow.table;
		tag[1] = Integer.toString(position);
		_viewHolder._txtTitle.setText(_objRow.title);
		_viewHolder._imgIcon.setBackgroundResource(_objRow.icon);
		_viewHolder._imgIcon.setTag(tag);
		//set state for toggel button
		if(_objRow.icon == R.drawable.favourite_added) 
		{
			_viewHolder._imgIcon.setChecked(true);
		}
		else
		{
			_viewHolder._imgIcon.setChecked(false);
		}
		_viewHolder._imgIcon.requestLayout();
		_viewHolder._imgIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO change favorite state of word clicked
				ToggleButton button = (ToggleButton)v.findViewById(R.id.favourite_remove_btn);
				String[] tag = (String[]) v.getTag();
				 int position = Integer.parseInt(tag[1]);
				if((button.isChecked()))
				{
					button.setBackgroundResource(R.drawable.favourite_added);
					ResultViewController.db.addFavorite(_arrayData[position].title,tag[0]); // delete word
				}
				else
				{			    
					button.setBackgroundResource(R.drawable.favourite_not_add);
					ResultViewController.db.deleteFavorite(_arrayData[position].title,tag[0]); //add word
				}
			}
		});		
		return _row;
	}

	static class RowObjectViewHolder {
		ToggleButton _imgIcon;
		TextView _txtTitle;
	}
}
