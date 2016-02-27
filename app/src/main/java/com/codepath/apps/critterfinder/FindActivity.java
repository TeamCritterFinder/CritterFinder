package com.codepath.apps.critterfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critterfinder.models.PetModel;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class FindActivity extends AppCompatActivity {
	TextView petNameView;
	ImageView petImage;
	ArrayList<PetModel> petsList;
	Integer currentPet = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		petNameView = (TextView)findViewById(R.id.petName);
		petImage = (ImageView)findViewById(R.id.petImage);

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
	public void onFindPets(View view) {
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

	private void updateViewWithPet(PetModel petModel) {
		this.petNameView.setText(petModel.getName());
		
	}
}
