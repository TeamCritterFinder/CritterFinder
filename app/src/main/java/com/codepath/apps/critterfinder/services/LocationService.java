package com.codepath.apps.critterfinder.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by smacgregor on 2/29/16.
 */
public class LocationService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    /*
     * Define a request code to send to Google Play services This code is
     * returned in Activity.onActivityResult
     */
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    // for tracking the async task to convert a lat/long into a zipcode
    private AddressResultReceiver mAddressLookupResultsReceiver;

    // for notifying our activity about the zip code
    private OnLocationListener mLocationListener;

    public LocationService(Context context, OnLocationListener onLocationListener) {
        mLocationListener = onLocationListener;
        mAddressLookupResultsReceiver = new AddressResultReceiver(new Handler());
        mContext = context;
        setupGoogleApiClient(context);
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException ex) {}

        if (mLastLocation != null) {
            Intent intent = FetchAddressIntentService.getStartIntent(mContext, mLastLocation, mAddressLookupResultsReceiver);
            mContext.startService(intent);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mLocationListener.onLocationFailed();
    }

    private void setupGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();
    }

    private void connectClient() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    /**
     * Receiver to handle geo decoding a location into a postal code
     */
    @SuppressLint("ParcelCreator")
    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == FetchAddressIntentService.LOCATION_SUCCESS_RESULT) {
                mLocationListener.onLocationAvailable(resultData.getString(FetchAddressIntentService.LOCATION_RESULT, ""));
            } else {
                mLocationListener.onLocationFailed();
            }
        }
    }

    public interface OnLocationListener {
        void onLocationAvailable(String postalCode);
        void onLocationFailed();
    }
}
