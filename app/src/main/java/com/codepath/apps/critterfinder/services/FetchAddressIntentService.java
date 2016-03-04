package com.codepath.apps.critterfinder.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by smacgregor on 3/3/16.
 */
public class FetchAddressIntentService extends IntentService {

    public static int LOCATION_SUCCESS_RESULT = 0;
    public static int LOCATION_FAILED_RESULT = 1;
    public static final String LOCATION_RESULT = "com.codepath.apps.critterfinder.services.LOCATION_RESULT";

    private static final String LOCATION_DATA_EXTRA = "com.codepath.apps.critterfinder.services.LOCATION_DATA_EXTRA";
    private static final String LOCATION_RECEIVER = "com.codepath.apps.critterfinder.services.LOCATION_RECEIVER";

    protected ResultReceiver mResultReceiver;

    public static Intent getStartIntent(Context context, Location location, ResultReceiver resultReceiver) {
        Intent startIntent = new Intent(context, FetchAddressIntentService.class);
        startIntent.putExtra(LOCATION_RECEIVER, resultReceiver);
        startIntent.putExtra(LOCATION_DATA_EXTRA, location);
        return startIntent;
    }

    public FetchAddressIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);
        mResultReceiver = intent.getParcelableExtra(LOCATION_RECEIVER);

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException ioException) {
            Log.e("DEBUG", "Geocoder not available");
        } catch (IllegalArgumentException illegalArumentException) {
            Log.e("DEBUG", "Invalid location");
        }

        if (addresses == null || addresses.size() == 0) {
            deliverResultToReceiver(LOCATION_FAILED_RESULT, "");
        } else {
            String zipCode = addresses.get(0).getPostalCode();
            if (TextUtils.isEmpty(zipCode)) {
                deliverResultToReceiver(LOCATION_SUCCESS_RESULT, zipCode);
            }
        }
    }

    private void deliverResultToReceiver(int resultCode, String postalCode) {
        Bundle bundle = new Bundle();
        bundle.putString(LOCATION_RESULT, postalCode);
        mResultReceiver.send(resultCode, bundle);
    }
}
