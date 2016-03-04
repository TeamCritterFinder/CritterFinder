package com.codepath.apps.critterfinder;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.services.LocationService;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class FindActivity extends AppCompatActivity implements LocationService.OnLocationListener {
	TextView petNameView;
	TextView petSexView;
	ImageView petImage;
	ArrayList<PetModel> petsList;
	Integer currentPet = 0;
	LocationService mLocationService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		petNameView = (TextView)findViewById(R.id.petName);
		petSexView = (TextView)findViewById(R.id.petSex);
		petImage = (ImageView)findViewById(R.id.petImage);
		onFindPets();
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void onFindPets() {
		PetFinderHttpClient client = new PetFinderHttpClient();
		try {

			client.findPetList(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
					String jsonString = json.toString();
					Log.d("DEBUG", "SUCCESS loading: " + jsonString);
					try {
						JSONObject petsJsonObject = json.getJSONObject("petfinder").getJSONObject("pets");
						if (petsJsonObject != null) {
							JSONArray petsArray = petsJsonObject.getJSONArray("pet");
							if (petsArray != null && petsArray.length() > 0 ) {
								petsList = PetModel.fromJSONArray(petsArray);
								updateViewWithPet(petsList.get(currentPet));
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					Log.d("DEBUG", "ERROR loading: " + errorResponse.toString());
//				pb.setVisibility(ProgressBar.INVISIBLE);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
	protected void onStart() {
		super.onStart();
		mLocationService = new LocationService(this, this);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		FindActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
	}

	@Override
	public void onLocationAvailable(String postalCode) {
		Snackbar.make(findViewById(android.R.id.content),
				"Location found: " + postalCode,
				Snackbar.LENGTH_LONG).
				show();
	}

	@Override
	public void onLocationFailed() {
		Snackbar.make(findViewById(android.R.id.content),
				"Make sure you have google play services installed",
				Snackbar.LENGTH_LONG).
				show();
	}

	private void updateViewWithPet(PetModel petModel) {
		this.petNameView.setText(petModel.getName());
		this.petSexView.setText(petModel.getSex());
	}

	public void onLike(View v) {
		updateViewWithPet(petsList.get(++currentPet));
	}

	public void onDislike(View v) {
		updateViewWithPet(petsList.get(++currentPet));
	}
}
