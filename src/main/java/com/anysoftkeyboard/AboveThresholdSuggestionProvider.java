package com.anysoftkeyboard;

import android.graphics.Color;
import com.anysoftkeyboard.base.dictionaries.WordComposer;
import com.anysoftkeyboard.keyboards.views.SuggestionObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 18/04/16
 * Time: 17:34
 */
public class AboveThresholdSuggestionProvider implements ISuggestionProvider
{
	
	@Override
	public List<SuggestionObject> getSuggestions(WordComposer mWord, boolean includeTypedWord)
	{
		return getSuggestions(mWord.getTypedWord(),includeTypedWord);

	}

	@Override
	public List<SuggestionObject> getSuggestions(CharSequence word, boolean includeTypedWord)
	{
		List<CharSequence> list = new ArrayList<>();
		list.add("Don't");
		list.add("drive");
		list.add("and");
		list.add("type!");
		return SuggestionObject.createFromStringList(list, Color.WHITE,Color.RED);
	}
}
