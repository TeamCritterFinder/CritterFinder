package com.codepath.apps.critterfinder.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.services.PetSearch;
import com.codepath.apps.critterfinder.services.LocationService;
import com.squareup.picasso.Picasso;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class FindActivity extends AppCompatActivity implements LocationService.OnLocationListener, PetSearch.PetSearchCallbackInterface {
	TextView petNameView;
	TextView petSexView;
	ImageView petImage;
//	ArrayList<PetModel> petsList;
//	Integer currentPet = 0;
	LocationService mLocationService;
	ProgressBar pb;
	LinearLayout loadingProgress;
	PetSearch petSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		petNameView = (TextView)findViewById(R.id.petName);
		petSexView = (TextView)findViewById(R.id.petSex);
		petImage = (ImageView)findViewById(R.id.petImage);
		pb = (ProgressBar)findViewById(R.id.pb);
		loadingProgress = (LinearLayout)findViewById(R.id.loadingProgress);
		//onFindPets();
		petSearch = new PetSearch(this);
		//petSearch.setZipCode("94025");
		doPetSearch();
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
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

	// update the View with the image and data for a Pet
	private void updateViewWithPet(PetModel petModel) {
		this.petNameView.setText(petModel.getName());
		this.petSexView.setText(petModel.getSex());
		Picasso.with(this).load(petModel.getImageUrl()).into(petImage);
	}

	public void goToNextPet() {
		updateViewWithPet(petSearch.getNextPet());
	}
	public void onLike(View v) {
		goToNextPet();
	}

	public void onDislike(View v) {
		goToNextPet();
	}

	// Pet Search Interface calls

	// start a pet search call
	public void doPetSearch() {
		loadingProgress.setVisibility(View.VISIBLE);
	//	petSearch.doPetSearch();
	}
	public void onPetSearchSuccess(String result) {
		loadingProgress.setVisibility(View.GONE);
		updateViewWithPet(petSearch.getCurrentPet());
		Log.d("FindActivity", "SUCCESS");
	}
	public void onPetSearchError(String result) {
		loadingProgress.setVisibility(View.GONE);
		Log.d("FindActivity", "ERROR");
	}
}
