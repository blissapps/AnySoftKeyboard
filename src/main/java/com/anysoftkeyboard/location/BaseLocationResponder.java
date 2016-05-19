package com.anysoftkeyboard.location;

import android.content.Context;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 19/05/16
 * Time: 10:43
 */
public abstract class BaseLocationResponder implements ILocationResponder
{
	protected static final float SPEED_THRESHOLD_M_S = (15.0f * 1000) / 3600;
	protected float mSpeedThreshold = SPEED_THRESHOLD_M_S;
	protected static final long LOCATION_PROVIDER_MIN_TIME_MS = 5000;
	protected static final long LOCATION_PROVIDER_LOCATION_TTL_MS = 10000;
	protected static final float LOCATION_PROVIDER_MIN_DISTANCE_M = 1;

	protected float mSpeed = -1;
	protected long mLastLocationUpdate = 0;
	protected ILocationPresenter mLocationPresenter;
	
	@Override
	public abstract StartLocationResponderStatus startLocationResponder(Context ctx);

	@Override
	public abstract EndLocationResponderStatus endLocationResponder(Context ctx);

	@Override
	public void setLocationPresenter(ILocationPresenter locationPresenter)
	{
		this.mLocationPresenter = locationPresenter;
		ensureLocationPresenter();
	}

	@Override
	public ILocationPresenter getLocationPresenter()
	{
		ensureLocationPresenter();
		return mLocationPresenter;
	}

	protected void ensureLocationPresenter()
	{
		if (mLocationPresenter == null)
		{
			throw new LocationPresenterIsNullException();
		}
	}

	public float getSpeedTreshold()
	{
		return mSpeedThreshold;
	}

	public void setSpeedTreshold(float speedTreshold)
	{
		this.mSpeedThreshold = speedTreshold;
	}
}
