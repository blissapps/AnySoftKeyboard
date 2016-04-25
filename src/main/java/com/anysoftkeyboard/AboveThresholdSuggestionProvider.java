package com.anysoftkeyboard;

import android.content.Context;
import android.graphics.Color;
import com.anysoftkeyboard.base.dictionaries.WordComposer;
import com.anysoftkeyboard.keyboards.views.SuggestionObject;
import com.anysoftkeyboard.utils.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 18/04/16
 * Time: 17:34
 */
public class AboveThresholdSuggestionProvider implements ISuggestionProvider
{

	public static void loadSuggestions(Context context)
	{
		try
		{
			InputStream json = context.getAssets().open("speed_suggestions.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
			String str;
			while ((str = in.readLine()) != null)
			{
				String letter = str.substring(0, 1).toLowerCase();
				String phrase = str.substring(3);
				String[] tokens = phrase.split(" - ");
				List<String> list = Arrays.asList(tokens);
				List<SuggestionObject> suggestions = SuggestionObject.createFromStringList(list, Color.WHITE, mRedColor);
				SUGGESTION_MAP.put(letter, suggestions);

			}
			SUGGESTION_MAP.put("", SuggestionObject.createFromStringListUsingDefaultColor(Arrays.asList((CharSequence)"","","")));
			in.close();
		}catch ( Exception ex){
			Log.e("SpeedSugg", "error loading speed suggestions",ex);
		}
	}

	static Map<String, List<SuggestionObject>> SUGGESTION_MAP = new HashMap<>();
	static int mRedColor = Color.rgb(0xe4, 0x36, 0x48);
	
	@Override
	public List<SuggestionObject> getSuggestions(WordComposer mWord, boolean includeTypedWord)
	{
		return getSuggestions(mWord.getTypedWord(), includeTypedWord);

	}

	@Override
	public List<SuggestionObject> getSuggestions(CharSequence word, boolean includeTypedWord)
	{
		if (word == null || word.length() == 0)
		{
			return SUGGESTION_MAP.get("");
		}
		String firstLetter = word.subSequence(0, 1).toString().toLowerCase();

		if (SUGGESTION_MAP.containsKey(firstLetter))
		{
			return SUGGESTION_MAP.get(firstLetter);
		}
		else
		{
			return SUGGESTION_MAP.get("a");
		}
	}
}
