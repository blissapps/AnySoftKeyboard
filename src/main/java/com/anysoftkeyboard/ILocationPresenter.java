package com.anysoftkeyboard;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 18/04/16
 * Time: 13:00
 */
public interface ILocationPresenter
{

	void aboveSpeedThreshold();

	void belowSpeedThreshold();

	void onLocationResponderDisabled();

	void onLocationResponderEnabled();
}
