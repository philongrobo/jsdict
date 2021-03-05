package jsdictmain;
import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;


public class TextToSpeechClass {
	MediaPlayer mediaPlayer;
	Thread threadTST;
	ResultViewController searchScrAct;
	public TextToSpeechClass(ResultViewController _searchScrAct) {
		// TODO Auto-generated constructor stub
		this.searchScrAct = _searchScrAct;
	}
	public void startTST(final String word, final String language){
		threadTST = new Thread() {
			public void run() {
				try {
					String url1 = "http://www.translate.google.com/translate_tts?ie=UTF-8&q="
							+ word.replace(' ', '+').replace('\n', '.')
							+ "%0A&tl=" + language + "&prev=input";
					mediaPlayer = new MediaPlayer();
					mediaPlayer.reset();
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mediaPlayer.setDataSource(url1);
					mediaPlayer.prepare(); // might take long! (for buffering,
											// etc)
					mediaPlayer.start();
				} catch (IOException e) {
					Toast.makeText(searchScrAct, "No network connection", Toast.LENGTH_SHORT).show();
					mediaPlayer.reset();
				}

				finally {
				}
			}

		};
		threadTST.run();
	}
}
