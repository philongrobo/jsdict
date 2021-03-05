package pagerview;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;
import jsdictmain.StartActivity;

import com.actionbarsherlock.app.SherlockFragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ToggleButton;
/*
 *クラス名：  RowAdapterForHistory
 *日付: 2014/02/20
 *著作権表示: N/A
 *変更ログ:
 *20140301  LINHNC update  function getView
 *			LINHNC update  function RowAdapterForHistory
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140220 HoangLA 説明 */
public class RowAdapterForHistory extends ArrayAdapter<RowObjectForHistory> {
	Context _context;
	int _layoutResourceId;
	RowObjectForHistory[] _arrayData;

	public RowAdapterForHistory(Context context, int layoutResourceId,
			RowObjectForHistory[] arrayData) {
		super(context, layoutResourceId, arrayData);
		this._context = context;
		this._layoutResourceId = layoutResourceId;
		this._arrayData = arrayData;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View _row = convertView;
		RowObjectViewHolder _viewHolder = null;

		if (_row == null) {
			LayoutInflater _inflater = ((Activity) _context)
					.getLayoutInflater();
			_row = _inflater.inflate(_layoutResourceId, parent, false);

			_viewHolder = new RowObjectViewHolder();
			_viewHolder._imgIcon = (ImageView) _row.findViewById(R.id.delete_btn);
			_viewHolder._txtTitle = (TextView) _row.findViewById(R.id.txtTitle);
			_viewHolder._imgIcon1 = (ToggleButton) _row.findViewById(R.id.favourite_add_btn);
			_row.setTag(_viewHolder);
		} else {
			_viewHolder = (RowObjectViewHolder) _row.getTag();
		}
		RowObjectForHistory _objRow = _arrayData[position];
		 //stored table value and position value
		String[] tag = new String[2];
		tag[0] = _objRow.table;
		tag[1] = Integer.toString(position);
		_viewHolder._txtTitle.setText(_objRow.title);
		_viewHolder._imgIcon.setImageResource(_objRow.icon);
		_viewHolder._imgIcon1.setBackgroundResource(_objRow.icon1);
		_viewHolder._imgIcon.setTag(tag);
		_viewHolder._imgIcon1.setTag(tag);
		if(_objRow.table.equals("myDict"))
		{
			_viewHolder._imgIcon1.setVisibility(View.INVISIBLE);
		}
		else
		{
			if(_objRow.icon1 == R.drawable.favourite_added)
			{
				_viewHolder._imgIcon1.setChecked(true);
			}
			else
			{
				_viewHolder._imgIcon1.setChecked(false);
			}
		}
		_viewHolder._imgIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//  //stored table value and position value
				String[] tag = (String[]) v.getTag();
				int position = Integer.parseInt(tag[1]);
				ResultViewController.db.deleteHistory(_arrayData[position].title, tag[0]);
				FragmentActivity fm = (FragmentActivity) _context;
				SherlockFragment frg = null;
				frg = (SherlockFragment) fm.getSupportFragmentManager().findFragmentById(R.id.content_frame);
				final android.support.v4.app.FragmentTransaction ft = fm.getSupportFragmentManager().beginTransaction();
				ft.detach(frg);
				ft.attach(frg);
				ft.replace(R.id.content_frame, new StartActivity());
				ft.commit();
			}
		});
		_viewHolder._imgIcon1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//change favorite state of word clicked
				ToggleButton button = (ToggleButton)v.findViewById(R.id.favourite_add_btn);
				String[] tag = (String[]) v.getTag();
				int position = Integer.parseInt(tag[1]);
				if((button.isChecked()))
				{	
					button.setBackgroundResource(R.drawable.favourite_added);
					ResultViewController.db.addFavorite(_arrayData[position].title,tag[0]);
					  FragmentActivity fm = (FragmentActivity) _context;
                      SherlockFragment frg = null;
                      frg = (SherlockFragment) fm.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                      final android.support.v4.app.FragmentTransaction ft = fm.getSupportFragmentManager().beginTransaction();
                      ft.detach(frg);
                      ft.attach(frg);
                      ft.replace(R.id.content_frame, new StartActivity());
                      ft.commit();

				}
				else
				{
				    button.setBackgroundResource(R.drawable.favourite_not_add);
				    ResultViewController.db.deleteFavorite(_arrayData[position].title,tag[0]);
					  FragmentActivity fm = (FragmentActivity) _context;
                      SherlockFragment frg = null;
                      frg = (SherlockFragment) fm.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                      final android.support.v4.app.FragmentTransaction ft = fm.getSupportFragmentManager().beginTransaction();
                      ft.detach(frg);
                      ft.attach(frg);
                      ft.replace(R.id.content_frame, new StartActivity());
                      ft.commit();
				}
			}
		});		
		return _row;
	}

	static class RowObjectViewHolder {
		ImageView _imgIcon;
		ToggleButton _imgIcon1;
		TextView _txtTitle;
	}
}
