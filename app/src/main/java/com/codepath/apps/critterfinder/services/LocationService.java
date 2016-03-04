package com.codepath.apps.critterfinder.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by smacgregor on 2/29/16.
 */
public class LocationService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    // for notifying our activity about the zip code
    private OnLocationListener mLocationListener;

    public LocationService(Context context, OnLocationListener onLocationListener) {
        mLocationListener = onLocationListener;
        mContext = context;
        setupGoogleApiClient(context);
        connectClient();
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // TODO - not getting a consistent last location out of the genymotion simulator
            // for now make sure we send something back...
            if (mLastLocation == null) {
                mLastLocation = new Location("Placeholder");
                mLastLocation.setLatitude(37.743242);
                mLastLocation.setLongitude(-122.497667);
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

        new GeoCoderAsyncTask().execute(mLastLocation);
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

    private class GeoCoderAsyncTask extends AsyncTask<Location, Void, String> {

        @Override
        protected String doInBackground(Location... params) {
            String postalCode = null;
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(params[0].getLatitude(), params[0].getLongitude(), 1);
            } catch (IOException ioException) {
                Log.e("DEBUG", "Geocoder not available");
            } catch (IllegalArgumentException illegalArumentException) {
                Log.e("DEBUG", "Invalid location");
            }

            if (addresses != null && addresses.size() > 0) {
                postalCode = addresses.get(0).getPostalCode();
            }
            return postalCode;
        }

        @Override
        protected void onPostExecute(String postalCode) {
            if (TextUtils.isEmpty(postalCode)) {
                mLocationListener.onLocationFailed();
            } else {
                mLocationListener.onLocationAvailable(postalCode);
            }
        }
    }
    public interface OnLocationListener {
        void onLocationAvailable(String postalCode);
        void onLocationFailed();
    }
}
