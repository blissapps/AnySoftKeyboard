package com.anysoftkeyboard.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import com.anysoftkeyboard.utils.Log;
import org.joda.time.DateTime;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 18/04/16
 * Time: 12:59
 */
public class GPSProviderLocationResponder extends BaseLocationResponder
{
	private final static String TAG = "LRSP";

	private LocationManager mLocationManager = null;
	private Handler mHandler = new Handler();

	private Runnable mTooLongWithoutUpdatesRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			mSpeed = -1;
			ensureLocationPresenter();
			mLocationPresenter.belowSpeedThreshold();

		}
	};

	private LocationListener mLocationListener = new LocationListener()
	{
		@Override
		public void onLocationChanged(Location location)
		{
			ensureLocationPresenter();

			mHandler.removeCallbacks(mTooLongWithoutUpdatesRunnable);

			if (location.hasSpeed())
			{
				mSpeed = location.getSpeed();
				mLastLocationUpdate = DateTime.now().toInstant().getMillis();
				if (mSpeed >= mSpeedThreshold)
				{
					getLocationPresenter().aboveSpeedThreshold();
				}
				else
				{
					getLocationPresenter().belowSpeedThreshold();
				}

			}
			else
			{
				mSpeed = -1;
			}
			mHandler.postDelayed(mTooLongWithoutUpdatesRunnable, LOCATION_PROVIDER_MIN_TIME_MS * 2);
			Log.d("AnySoft", "speed: " + mSpeed);

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle bundle)
		{
			ensureLocationPresenter();
			switch (status)
			{
				case LocationProvider.OUT_OF_SERVICE:
					getLocationPresenter().onLocationResponderDisabled();
					break;
				case LocationProvider.TEMPORARILY_UNAVAILABLE:
					getLocationPresenter().onLocationResponderDisabled();
					break;
				case LocationProvider.AVAILABLE:
					getLocationPresenter().onLocationResponderEnabled();
					break;
				default:
					break;
			}
		}

		@Override
		public void onProviderEnabled(String provider)
		{
			ensureLocationPresenter();
			getLocationPresenter().onLocationResponderEnabled();
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			ensureLocationPresenter();
			getLocationPresenter().onLocationResponderDisabled();
		}
	};

	@Override
	public StartLocationResponderStatus startLocationResponder(Context context)
	{
		ensureLocationPresenter();
		mSpeed = -1;
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		try
		{
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_PROVIDER_MIN_TIME_MS, LOCATION_PROVIDER_MIN_DISTANCE_M, mLocationListener);
		}
		catch (SecurityException e)
		{
			Log.e(TAG,"Can't request location updates", e);
			return StartLocationResponderStatus.Failed;
		}

		//mHandler.post(mockRunnable);


		return StartLocationResponderStatus.Success;
	}



	@Override
	public EndLocationResponderStatus endLocationResponder(Context ctx)
	{
		ensureLocationPresenter();
		mSpeed = -1;
		try
		{
			if(mLocationManager != null)
			{
				mLocationManager.removeUpdates(mLocationListener);
			}
			mLocationManager = null;
		}
		catch (SecurityException e)
		{
			mLocationManager = null;
			return EndLocationResponderStatus.Failed;
		}
		return EndLocationResponderStatus.Success;
	}


}
