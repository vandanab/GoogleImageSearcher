package com.codepath.vandanab.googleimagesearcher.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.codepath.vandanab.googleimagesearcher.R;
import com.codepath.vandanab.googleimagesearcher.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_details);
		// Remove action bar on the image details activity.
		//getActionBar().hide();
		// pull out the url from the intent
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		// find the image view and load it with the image
		ImageView ivImageDetails = (ImageView) findViewById(R.id.ivImageDetails);
		Picasso.with(this).load(result.fullUrl).into(ivImageDetails);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
