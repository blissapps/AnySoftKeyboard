package com.anysoftkeyboard;

import android.graphics.Color;
import com.anysoftkeyboard.base.dictionaries.WordComposer;
import com.anysoftkeyboard.keyboards.views.SuggestionObject;

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

	//A Atenção | ao | dirigir
	//D Digitar | dirigindo | mata
	//J Jamais | digite | dirigindo
	//N Não | tecle. | Dirija
	//O Olhe | o | trânsito
	//P Perigo. | Não | digite

	static Map<String, List<SuggestionObject>> SUGGESTION_MAP = new HashMap<>();
	static int mRedColor = Color.rgb(0xe4,0x36,0x48);
	static
	{

		List<CharSequence> list = Arrays.asList((CharSequence) "Atenção", "ao", "dirigir");
		List<SuggestionObject> suggestions = SuggestionObject.createFromStringList(list, Color.WHITE, mRedColor);
		SUGGESTION_MAP.put("a", suggestions);

		list = Arrays.asList((CharSequence) "Digitar", "dirigindo", "mata");
		suggestions = SuggestionObject.createFromStringList(list, Color.WHITE, mRedColor);
		SUGGESTION_MAP.put("d", suggestions);

		list = Arrays.asList((CharSequence) "Jamais", "digite", "dirigindo");
		suggestions = SuggestionObject.createFromStringList(list, Color.WHITE, mRedColor);
		SUGGESTION_MAP.put("j", suggestions);

		list = Arrays.asList((CharSequence) "Não", "tecle.", "Dirija");
		suggestions = SuggestionObject.createFromStringList(list, Color.WHITE, mRedColor);
		SUGGESTION_MAP.put("n", suggestions);

		list = Arrays.asList((CharSequence) "Olhe", "o", "trânsito");
		suggestions = SuggestionObject.createFromStringList(list, Color.WHITE, mRedColor);
		SUGGESTION_MAP.put("o", suggestions);

		list = Arrays.asList((CharSequence) "Perigo.", "Não", "digite");
		suggestions = SuggestionObject.createFromStringList(list, Color.WHITE, mRedColor);
		SUGGESTION_MAP.put("p", suggestions);

		list = Arrays.asList((CharSequence) "", "", "");
		suggestions = SuggestionObject.createFromStringListUsingDefaultColor(list);
		SUGGESTION_MAP.put("", suggestions);
	}
	
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
