package inputmethod;

import java.io.*;
import java.util.LinkedList;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;
import android.annotation.SuppressLint;
import android.app.*;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import drawkanji.*;
import drawkanji.KanjiDrawing.DrawnStroke;
import drawkanji.KanjiInfo.MatchAlgorithm;

@SuppressLint("ValidFragment")
public class RadicalKanjiSearchViewController extends SherlockFragment {
	private KanjiDrawing drawing;

	/**
	 * Selected kanji in result (if any).
	 */
	public final static String EXTRA_KANJI = "kanji";

	/**
	 * Current result stage in intent.
	 */
	public final static String EXTRA_STAGE = "stage";

	private final static int STAGE_EXACT = 1;
	public static Menu _menu;
	public static String firstString;
	public static View viewTopBar;
	public ResultViewController mainActivity;
	public static SearchView searchView;
	public static View view;
	public RadicalKanjiSearchViewController(ResultViewController _mainActivity) {
		this.mainActivity = _mainActivity;
	}

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// mainActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//
		view = inflater.inflate(R.layout.pickkanji, container, false);
		viewTopBar = inflater.inflate(R.layout.topbar, container, false);
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		// Make sure the list gets loaded
		new LoadThread();
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.drawcontainer);
		drawing = new KanjiDrawing(mainActivity);
		layout.addView(drawing);

		TextView strokesText = (TextView) view.findViewById(R.id.strokes);
		final int normalRgb = strokesText.getTextColors().getDefaultColor();

		drawing.setListener(new KanjiDrawing.Listener() {
			@Override
			public void strokes(DrawnStroke[] strokes) {
				view.findViewById(R.id.undo).setEnabled(
						strokes.length > 0);
				view.findViewById(R.id.clear).setEnabled(
						strokes.length > 0);

				boolean gotList;
				synchronized (listSynch) {
					gotList = list != null;
				}
				view.findViewById(R.id.done).setEnabled(
						strokes.length > 0 && gotList);
				TextView strokesText = (TextView) view
						.findViewById(R.id.strokes);
				strokesText.setText(strokes.length + "");
				if (strokes.length == KanjiDrawing.MAX_STROKES) {
					strokesText.setTextColor(Color.RED);
				} else {
					strokesText.setTextColor(normalRgb);
				}
			}
		});

		view.findViewById(R.id.undo).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				drawing.undo();
			}
		});
		view.findViewById(R.id.clear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				drawing.clear();
			}
		});

		view.findViewById(R.id.done).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new MatchThread(mainActivity, drawing.getStrokes(),
						MatchAlgorithm.STRICT, R.string.waitexact, drawing
								.getStrokes().length == 1 ? R.string.pickexact1
								: R.string.pickexact, R.string.fuzzy,
						STAGE_EXACT, false, new String[0]);
			}
		});
		searchView = (SearchView) this.mainActivity
				.findViewById(R.id.action_websearch);
		searchView.clearFocus();
		return view;
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Add your menu entries here
		_menu = menu;
		super.onCreateOptionsMenu(menu, inflater);
	}

	private void loaded() {
		DrawnStroke[] strokes = drawing.getStrokes();
		view.findViewById(R.id.done).setEnabled(strokes.length > 0);
	}

	private static KanjiList list;
	private static boolean listLoading;
	private static LinkedList<RadicalKanjiSearchViewController> waitingActivities = new LinkedList<RadicalKanjiSearchViewController>();
	private static Object listSynch = new Object();

	/**
	 * Thread that loads the kanji list in the background.
	 */
	private class LoadThread extends Thread {
		private LoadThread() {
			setPriority(MIN_PRIORITY);
			// Start loading the kanji list but only if it wasn't loaded already
			synchronized (listSynch) {
				if (list == null) {
					waitingActivities.add(RadicalKanjiSearchViewController.this);
					if (!listLoading) {
						listLoading = true;
						start();
					}
				}
			}
		}

		@Override
		public void run() {
			try {
				long start = System.currentTimeMillis();
				InputStream input = new MultiAssetInputStream(
						mainActivity.getAssets(), new String[] {
								"strokes-20100823.xml.1",
								"strokes-20100823.xml.2" });
				KanjiList loaded = new KanjiList(input);
				synchronized (listSynch) {
					list = loaded;
					for (RadicalKanjiSearchViewController listening : waitingActivities) {
						final RadicalKanjiSearchViewController current = listening;
						mainActivity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								current.loaded();
							}
						});
					}
					waitingActivities = null;
				}
				long time = System.currentTimeMillis() - start;
				Log.d(RadicalKanjiSearchViewController.class.getName(),
						"Kanji drawing dictionary loaded (" + time + "ms)");
			} catch (IOException e) {
				Log.e(RadicalKanjiSearchViewController.class.getName(),
						"Error loading dictionary", e);
			} catch (Exception e) {
			} finally {
				synchronized (listSynch) {
					listLoading = false;
				}
			}
		}
	}

	/**
	 * Do the match on another thread.
	 */

	@SuppressLint("NewApi")
	static class MatchThread extends Thread {
		private KanjiInfo info;
		private ProgressDialog dialog;
		private MatchAlgorithm algo;
		private KanjiList.Progress progress;
		private Activity activity;

		MatchThread(final Activity owner, DrawnStroke[] strokes,
				MatchAlgorithm algo, int waitString, int labelString,
				int otherString, int stageCode, boolean showMore,
				String[] alreadyShown) {
			this.activity = owner;
			dialog = new ProgressDialog(activity);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMessage(activity.getString(waitString));
			dialog.setCancelable(false);
			dialog.show();
			progress = new KanjiList.Progress() {
				@Override
				public void progress(final int done, final int max) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (done == 0) {
								dialog.setMax(max);
							}
							dialog.setProgress(done);
						}
					});
				}
			};
			OnClickListener clicks = new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Button btn = (Button) owner.findViewById(v.getId());
					String firstString = btn.getText().toString();
					 String str_id = ResultViewController.db.getId(firstString,ResultViewController.Table_Mode[ResultViewController.mode]);
					 searchView.setTag(str_id);
					searchView.onActionViewExpanded();
					searchView.setQuery(searchView.getQuery().toString()
							+ btn.getText().toString(), false);
				}
			};
			this.algo = algo;

			// Build info
			info = getKanjiInfo(strokes);
			final KanjiMatch[] matches1 = list.getTopMatches(info, algo,
					progress);
			String[] matches = new String[matches1.length];
			for (int i = 0; i < matches1.length; i++) {
				matches[i] = matches1[i].getKanji().getKanji();
			}
			int startFrom = 0;
			showMore = false;
			// System.arraycopy(ALL_IDS, 0, ids, 0, ids.length);
			int index = -startFrom;
			// Find the ScrollView
			HorizontalScrollView scrollView = (HorizontalScrollView) view
					.findViewById(R.id.horizonScroll);
			// Create a LinearLayout element
			/*
			 * LinearLayout linearLayout = new LinearLayout(this.activity);
			 * linearLayout.setOrientation(LinearLayout.HORIZONTAL);
			 */
			LinearLayout linearLayout = (LinearLayout) view
					.findViewById(R.id.linearScroll);
			linearLayout.removeAllViews();
			Button button;
			for (int match = 0; match < matches.length; match++) {
				/*
				 * if (shown.contains(matches[match])) { continue; }
				 */
				if (index >= 0) {
					// Add Buttons
					button = new Button(this.activity);
					button.setText(matches[match]);
					button.setId(match);
					button.setOnClickListener(clicks);
					linearLayout.addView(button);
				}
				index++;
			}
			scrollView.removeAllViews();
			scrollView.addView(linearLayout);
			start();
		}

		public void run() {
			boolean closedDialog = false;
			try {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog.dismiss();
					}
				});
			} finally {
				if (!closedDialog) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
						}
					});
				}
			}
		}
	}

	static KanjiInfo getKanjiInfo(DrawnStroke[] strokes) {
		KanjiInfo info = new KanjiInfo("?");
		for (DrawnStroke stroke : strokes) {
			InputStroke inputStroke = new InputStroke(stroke.getStartX(),
					stroke.getStartY(), stroke.getEndX(), stroke.getEndY());
			info.addStroke(inputStroke);
		}
		info.finish();
		return info;
	}
}
