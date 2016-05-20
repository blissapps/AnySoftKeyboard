package com.anysoftkeyboard.location;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.anysoftkeyboard.PermissionsRequestCodes;
import com.anysoftkeyboard.ui.settings.MainSettingsActivity;
import com.anysoftkeyboard.utils.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.menny.android.anysoftkeyboard.R;

import net.evendanan.chauffeur.lib.permissions.PermissionsFragmentChauffeurActivity;

import org.joda.time.DateTime;

/**
 * Bliss Applications
 * User: tjanela
 * Date: 19/05/16
 * Time: 10:42
 */
public class LocationClientLocationResponder
        extends BaseLocationResponder
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private final String TAG = "LocationClient";
    private GoogleApiClient googleApiClient;
    private Handler mHandler = new Handler();
    private Location lastLocation;

    @Override
    public StartLocationResponderStatus startLocationResponder(Context ctx) {
        final int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ctx);
        if (result != ConnectionResult.SUCCESS) {
            return StartLocationResponderStatus.Failed;
        }
        googleApiClient = new GoogleApiClient.Builder(ctx).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();

        googleApiClient.connect();

        return StartLocationResponderStatus.Success;
    }

    @Override
    public EndLocationResponderStatus endLocationResponder(Context ctx) {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        return EndLocationResponderStatus.Success;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected - isConnected ...............: " + googleApiClient.isConnected());

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_PROVIDER_MIN_TIME_MS);
        locationRequest.setFastestInterval(LOCATION_PROVIDER_MIN_TIME_MS / 2);
        locationRequest.setSmallestDisplacement(LOCATION_PROVIDER_MIN_DISTANCE_M);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        } catch (SecurityException ex) {
            Log.e(TAG, "Couldn't request location updates,", ex);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.e(TAG, "Connection suspended: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        ensureLocationPresenter();

        mHandler.removeCallbacks(mTooLongWithoutUpdatesRunnable);

        if (location.hasSpeed()) {
            mSpeed = location.getSpeed();


        } else if (lastLocation != null && (DateTime.now().getMillis() - lastLocation.getTime()) < LOCATION_PROVIDER_LOCATION_TTL_MS) {

            long elapsedMillis = DateTime.now().getMillis() - lastLocation.getTime();
            float distanceInMeters = location.distanceTo(lastLocation);

            mSpeed = distanceInMeters * 1.0f / (elapsedMillis / 1000);
        }

        mLastLocationUpdate = DateTime.now().toInstant().getMillis();

        if (mSpeed >= mSpeedThreshold) {
            getLocationPresenter().aboveSpeedThreshold();
        } else {
            getLocationPresenter().belowSpeedThreshold();
        }
        lastLocation = location;
        mHandler.postDelayed(mTooLongWithoutUpdatesRunnable, LOCATION_PROVIDER_MIN_TIME_MS * 2);
        Log.d(TAG, "speed: " + mSpeed + ", location.hasSpeed(): " + location.hasSpeed() + ", location.getSpeed(): " + location.getSpeed());
    }

    private Runnable mTooLongWithoutUpdatesRunnable = new Runnable() {
        @Override
        public void run() {
            mSpeed = -1;
            ensureLocationPresenter();
            mLocationPresenter.belowSpeedThreshold();

        }
    };


}
