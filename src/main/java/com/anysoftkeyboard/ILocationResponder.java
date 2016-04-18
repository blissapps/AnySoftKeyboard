package com.anysoftkeyboard;

import android.content.Context;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 18/04/16
 * Time: 13:00
 */
public interface ILocationResponder
{
	StartLocationResponderStatus startLocationResponder(Context ctx);
	EndLocationResponderStatus endLocationResponder(Context ctx);

	void setLocationPresenter(ILocationPresenter locationPresenter);
	ILocationPresenter getLocationPresenter();
}
