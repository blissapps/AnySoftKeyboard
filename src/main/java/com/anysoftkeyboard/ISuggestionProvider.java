package com.anysoftkeyboard;

import com.anysoftkeyboard.base.dictionaries.WordComposer;
import com.anysoftkeyboard.keyboards.views.SuggestionObject;

import java.util.List;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 18/04/16
 * Time: 17:34
 */
public interface ISuggestionProvider
{
	
	List<SuggestionObject> getSuggestions(WordComposer word, boolean includeTypedWord);
	List<SuggestionObject> getSuggestions(CharSequence word, boolean includeTypedWord);
}
