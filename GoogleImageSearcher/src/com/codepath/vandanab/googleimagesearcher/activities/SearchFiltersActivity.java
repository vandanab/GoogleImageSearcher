package com.codepath.vandanab.googleimagesearcher.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.vandanab.googleimagesearcher.R;
import com.codepath.vandanab.googleimagesearcher.models.SearchFilters;

public class SearchFiltersActivity extends Activity {

	private Spinner spnrImageSize;
	private Spinner spnrColorFilter;
	private Spinner spnrImageType;
	private EditText etSiteFilter;
	private ArrayAdapter<CharSequence> imageSizeAdapter;
	private ArrayAdapter<CharSequence> colorFilterAdapter;
	private ArrayAdapter<CharSequence> imageTypeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_filters);
		setUpSpinners();
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
	}

	private void setUpSpinners() {
		spnrImageSize = (Spinner) findViewById(R.id.spnrImageSize);
		imageSizeAdapter = ArrayAdapter.createFromResource(
				this, R.array.image_size_options, R.layout.item_spinner);
		// Specify the layout to use when the list of choices appears.
		imageSizeAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
		spnrImageSize.setAdapter(imageSizeAdapter);

		spnrColorFilter = (Spinner) findViewById(R.id.spnrColorFilter);
		colorFilterAdapter = ArrayAdapter.createFromResource(
				this, R.array.color_filter_options, R.layout.item_spinner);
		colorFilterAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
		spnrColorFilter.setAdapter(colorFilterAdapter);

		spnrImageType = (Spinner) findViewById(R.id.spnrImageType);
		imageTypeAdapter = ArrayAdapter.createFromResource(
				this, R.array.image_type_options, R.layout.item_spinner);
		imageTypeAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
		spnrImageType.setAdapter(imageTypeAdapter);

		SearchFilters searchFilters = (SearchFilters) getIntent().getSerializableExtra("search_filters");

		// init dropdowns with filter values.
		if (searchFilters != null) {
			spnrImageSize.setSelection(imageSizeAdapter.getPosition(searchFilters.imageSize));
			spnrColorFilter.setSelection(colorFilterAdapter.getPosition(searchFilters.colorFilter));
			spnrImageType.setSelection(imageTypeAdapter.getPosition(searchFilters.imageType));
		} else {
			// last item of every dropdown list is any.
			spnrImageSize.setSelection(imageSizeAdapter.getPosition("any"));
			spnrColorFilter.setSelection(colorFilterAdapter.getPosition("any"));
			spnrImageType.setSelection(imageTypeAdapter.getPosition("any"));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_filters, menu);
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

	public void onSave(View v) {
		SearchFilters filters = new SearchFilters();
		filters.imageSize = imageSizeAdapter.getItem(
				spnrImageSize.getSelectedItemPosition()).toString();
		filters.colorFilter = colorFilterAdapter.getItem(
				spnrColorFilter.getSelectedItemPosition()).toString();
		filters.imageType = imageTypeAdapter.getItem(
				spnrImageType.getSelectedItemPosition()).toString();
		filters.siteFilter = etSiteFilter.getText().toString();

		Intent data = new Intent();
		data.putExtra("search_filters", filters);
		setResult(RESULT_OK, data);
		finish();
	}
}
