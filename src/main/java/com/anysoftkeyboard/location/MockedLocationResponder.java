package com.anysoftkeyboard.location;

import android.content.Context;
import android.os.Handler;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 19/05/16
 * Time: 10:46
 */
public class MockedLocationResponder extends BaseLocationResponder
{

	private Handler mHandler = new Handler();
	@Override
	public StartLocationResponderStatus startLocationResponder(Context ctx)
	{
		mHandler.post(mockRunnable);

		return StartLocationResponderStatus.Success;
	}

	@Override
	public EndLocationResponderStatus endLocationResponder(Context ctx)
	{

		mHandler.removeCallbacks(mockRunnable);

		return EndLocationResponderStatus.Success;
	}


	private Runnable mockRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			ensureLocationPresenter();
			getLocationPresenter().aboveSpeedThreshold();
			mHandler.postDelayed(mockRunnable, 1000);
		}
	};
}
