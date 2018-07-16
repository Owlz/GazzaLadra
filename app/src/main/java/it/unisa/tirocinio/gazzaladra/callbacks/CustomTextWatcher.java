package it.unisa.tirocinio.gazzaladra.callbacks;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;

public class CustomTextWatcher implements TextWatcher {
	private WriteDataCallback callback;
	private String lastString;              //la stringa prima che si attivi di nuovo il watcher
	private int ch;                         //-1 se è autocomplete, ascii code altrimenti

	private String[] words;
	private String[] lastWords;

	public CustomTextWatcher(WriteDataCallback callback) {
		this.callback = callback;
		this.lastString = "";
		this.ch = -1;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		words = getWords(s.toString());

		int len1 = words.length;
		int len2 = lastWords.length;

		boolean isAutocorrected = false;
		//TODO: refactor
		if (len1 == 0 || len2 == 0) return;

		if (len1 == len2) {
			final String w1 = words[len1 - 1];
			final String w2 = lastWords[len2 - 1];

			if (w1.length() - w2.length() > 1)
				isAutocorrected = true;

		} else if (len1 > len2) {

			if (words[len1 - 1].length() > 1 && before == 0)
				isAutocorrected = true;
		}

		int position = -1;
		int newChar = -1;
		if (!isAutocorrected) {
			for (position = 0; position < lastString.length() && position < s.length(); position++) {
				if (lastString.charAt(position) != s.charAt(position))
					break;
			}
			if (lastString.length() > s.length() && (lastString.length() - s.length()) == 1) {
				newChar = 127;
			} else if (lastString.length() < s.length() && (s.length() - lastString.length()) == 1) {
				newChar = s.charAt(position);
			}
		}

		//Log.e("WATCHER", "ch = " + newChar);
		callback.fireKeyPress(newChar, position);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		lastString = s.toString();
		lastWords = getWords(lastString);
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	private static String[] getWords(final String s) {
		final String[] temp = s.split(reg_whitespaces);
		final ArrayList<String> arr = new ArrayList<>();

		for (String w : temp) {
			if (w.trim().length() > 0)
				arr.add(w);
		}

		return arr.toArray(new String[]{});
	}

	private static final String reg_whitespaces = "[ ]+";
}
