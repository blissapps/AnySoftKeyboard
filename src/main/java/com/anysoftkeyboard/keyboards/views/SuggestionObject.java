package com.anysoftkeyboard.keyboards.views;

import java.util.ArrayList;
import java.util.List;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 18/04/16
 * Time: 16:34
 */
public class SuggestionObject
{

	private CharSequence suggestedWord;
	private int foregroundColor;
	private int backgroundColor;
	private boolean useDefaultColors = true;

	public CharSequence getSuggestedWord()
	{
		return suggestedWord;
	}

	public void setSuggestedWord(CharSequence suggestedWord)
	{
		this.suggestedWord = suggestedWord;
	}

	public int getForegroundColor()
	{
		return foregroundColor;
	}

	public void setForegroundColor(int foregroundColor)
	{
		this.foregroundColor = foregroundColor;
	}

	public int getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}


	public static List<SuggestionObject> createFromStringList(List<String> list, int foregroundColor, int backgroundColor){
		ArrayList<SuggestionObject> suggestionObjects = new ArrayList<>(list.size());

		for (String suggestion :
			list)
		{
			SuggestionObject object = SuggestionObject.create(suggestion, foregroundColor, backgroundColor);
			suggestionObjects.add(object);
		}
		return suggestionObjects;
	}

	public static List<SuggestionObject> createFromCharSequenceList(List<CharSequence> list, int foregroundColor, int backgroundColor){
		ArrayList<SuggestionObject> suggestionObjects = new ArrayList<>(list.size());

		for (CharSequence suggestion :
			list)
		{
			SuggestionObject object = SuggestionObject.create(suggestion.toString(), foregroundColor, backgroundColor);
			suggestionObjects.add(object);
		}
		return suggestionObjects;
	}

	public static List<SuggestionObject> createFromCharSequenceListUsingDefaultColor(List<CharSequence> list){
		ArrayList<SuggestionObject> suggestionObjects = new ArrayList<>(list.size());

		for (CharSequence suggestion :
			list)
		{
			SuggestionObject object = SuggestionObject.createUsingDefaultColors(suggestion.toString());
			suggestionObjects.add(object);
		}
		return suggestionObjects;
	}

	public static List<SuggestionObject> createFromStringListUsingDefaultColor(List<CharSequence> list)
	{
		ArrayList<SuggestionObject> suggestionObjects = new ArrayList<>(list.size());

		for (CharSequence suggestion : list)
		{
			SuggestionObject object = SuggestionObject.createUsingDefaultColors(suggestion.toString());
			suggestionObjects.add(object);
		}
		return suggestionObjects;

	}

	private static SuggestionObject createUsingDefaultColors(String suggestion)
	{
		SuggestionObject result = new SuggestionObject();
		result.setUseDefaultColors(true);
		result.setSuggestedWord(suggestion);
		return result;
	}

	private static SuggestionObject create(String suggestion, int foregroundColor, int backgroundColor)
	{
		SuggestionObject result = new SuggestionObject();
		result.setUseDefaultColors(false);
		result.setBackgroundColor(backgroundColor);
		result.setForegroundColor(foregroundColor);
		result.setSuggestedWord(suggestion);
		return result;
	}


	public boolean isUseDefaultColors()
	{
		return useDefaultColors;
	}

	public void setUseDefaultColors(boolean useDefaultColors)
	{
		this.useDefaultColors = useDefaultColors;
	}
}
