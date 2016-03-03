package com.codepath.apps.critterfinder.services;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by smacgregor on 2/29/16.
 */
public class LocationService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private OnLocationListener mLocationListener;

    public LocationService(Context context, OnLocationListener onLocationListener) {
        mLocationListener = onLocationListener;
        setupGoogleApiClient(context);
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException ex) {}

        if (mLastLocation != null) {

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mLocationListener != null) {
            mLocationListener.onLocationFailed();
        }
    }

    private void setupGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();
    }

    public interface OnLocationListener {
        void onLocationAvailable();
        void onLocationFailed();
    }
}
