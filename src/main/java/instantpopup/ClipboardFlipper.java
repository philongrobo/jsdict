package instantpopup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;
/*
*クラス名： ClibpardFlipper
*バージョン情報: 
*日付: 20140224
*著作権表示: NA
*変更ログ: NA
* 日		 	著者 		説明
* --------------------------------------------------------* 
20140224 	CongTT 	create ClipboardFilpper */
public class ClipboardFlipper extends ViewFlipper {

	// declare mInterceptTouchListenter = null
    private View.OnTouchListener mInterceptTouchListenter = null;
    
    public ClipboardFlipper(Context context) {
        super(context);
    }

    public ClipboardFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setInterceptTouchListener(View.OnTouchListener l) {
        mInterceptTouchListenter = l;
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mInterceptTouchListenter != null) {
            mInterceptTouchListenter.onTouch(null, ev);
        }
        return super.onInterceptTouchEvent(ev);
    }
}
