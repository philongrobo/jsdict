package translation;

import android.os.AsyncTask;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class TransClass extends AsyncTask<Void, Integer, Boolean> {
	protected String translatedText;
	Language language1;
	Language language2;
	String word;
	public TransClass(){
		
	}
	public TransClass(Language _lang1,Language _lang2, String _word){
		this.language1 = _lang1;
		this.language2 = _lang2;
		this.word = _word;
	}
	public String startTran(String lang1, String lang2){
		doInBackground(null);
		return translatedText;
	}
	 @Override
     protected Boolean doInBackground(Void... arg0) {
     	Translate.setClientId("MicrosoftTranslatorJavaAPI");
         Translate.setClientSecret("0VHbhXQnJrZ7OwVqcoX/PDZlyLJS9co3cVev1TPr8iM=");
		try {
         	translatedText = Translate.execute(word, language1, language2);
         } catch(Exception e) {
         	translatedText = "No network connection";
         }
         return true;
     }	
 }