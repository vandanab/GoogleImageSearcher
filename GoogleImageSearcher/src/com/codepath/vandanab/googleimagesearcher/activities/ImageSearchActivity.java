package com.codepath.vandanab.googleimagesearcher.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.vandanab.googleimagesearcher.R;
import com.codepath.vandanab.googleimagesearcher.adapters.ImageResultsAdapter;
import com.codepath.vandanab.googleimagesearcher.listeners.EndlessScrollListener;
import com.codepath.vandanab.googleimagesearcher.models.ImageResult;
import com.codepath.vandanab.googleimagesearcher.models.SearchFilters;
import com.codepath.vandanab.googleimagesearcher.util.NetworkMonitor;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {
	private static final int REQUEST_CODE_FILTERS = 10;

	// views.
	private EditText etQuery;
	private GridView gvResults;

	// data.
	private ArrayList<ImageResult> searchResults;
	private ImageResultsAdapter searchResultsAdapter;
	private SearchFilters searchFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        setupViews();
        searchResults = new ArrayList<ImageResult>();
        searchResultsAdapter = new ImageResultsAdapter(this, searchResults);
        gvResults.setAdapter(searchResultsAdapter);
        setupListener();
    }

    private void setupListener() {
    	gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadMoreDataFromApi(totalItemsCount);	
			}
		});
    }

    private void loadMoreDataFromApi(int offset) {
    	/*if (!networkCheck()) {
    		return;
    	}*/

    	String query = etQuery.getText().toString();
    	AsyncHttpClient client = new AsyncHttpClient();

    	String searchUrl = constructUrl(query, offset);
        client.get(searchUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					searchResultsAdapter.addAll(ImageResult.fromJSONArray(imageResultsJson));
					// no need to notify the adapter as we are changing the data directly on the adapter.
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//Log.i("INFO", searchResults.toString());
			}
        });
    }

    private void setupViews() {
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults = (GridView) findViewById(R.id.gvResults);
    	gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Launch the image details activity
				Intent i = new Intent(ImageSearchActivity.this, ImageDetailsActivity.class);

				ImageResult result = searchResults.get(position);

				// TODO: use parcelable.
				i.putExtra("result", result); // need to either be serializable or parcelable.

				startActivity(i);
			}

		});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_search, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.miSettings:
    		setSearchFilters();
    		return true;
    	default: return super.onOptionsItemSelected(item);
    	}
    }

    private void setSearchFilters() {
		// Go the advanced search filters activity to set filters.
    	Intent i = new Intent(this, SearchFiltersActivity.class);
    	if (searchFilters != null) {
    		i.putExtra("search_filters", searchFilters);
    	}
        startActivityForResult(i, REQUEST_CODE_FILTERS);
	}

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_FILTERS) {
			if (resultCode == RESULT_OK) {
				searchFilters = (SearchFilters) data.getSerializableExtra("search_filters");
				//Toast.makeText(this, searchFilters.imageSize, Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

    private String constructUrl(String query, int startIndex) {
    	String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
        		+ query + "&rsz=8";
        if (searchFilters != null) {
        	if (!"any".equals(searchFilters.imageSize)) {
        		searchUrl += "&imgsz=" + searchFilters.imageSize;
        	}
        	if (!"any".equals(searchFilters.colorFilter)) {
        		searchUrl += "&imgcolor=" + searchFilters.colorFilter;
        	}
        	if (!"any".equals(searchFilters.imageType)) {
        		searchUrl += "&imgtype=" + searchFilters.imageType;
        	}
        	if(searchFilters.siteFilter != null && !"".equals(searchFilters.siteFilter)) {
        		searchUrl += "&as_sitesearch=" + searchFilters.siteFilter;
        	}
        }
        if (startIndex > 0) {
        	searchUrl += "&start=" + startIndex;
    	}
        return searchUrl;
    }

	// Fired when search button is clicked.
    public void onImageSearch(View v) {
    	/*if (!networkCheck()) {
    		return;
    	}*/

    	String query = etQuery.getText().toString();
    	AsyncHttpClient client = new AsyncHttpClient();

    	String searchUrl = constructUrl(query, 0);
        client.get(searchUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					searchResultsAdapter.clear(); // in cases when its a new search.
					searchResultsAdapter.addAll(ImageResult.fromJSONArray(imageResultsJson));
					// no need to notify the adapter as we are changing the data directly on the adapter.
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
        });
    }

    private boolean networkCheck() {
    	ConnectivityManager connectivityManager = 
    			(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkMonitor.Status status = NetworkMonitor.canSendNetworkRequest(connectivityManager);
    	if (status == NetworkMonitor.Status.OK) {
    		return true;
    	} else if (status == NetworkMonitor.Status.NOT_AVAILABLE) {
    		Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
    	} else if (status == NetworkMonitor.Status.NOT_CONNECTED) {
    		Toast.makeText(this, "Cannot connect to network", Toast.LENGTH_SHORT).show();
    	}
    	return false;
    }
}
