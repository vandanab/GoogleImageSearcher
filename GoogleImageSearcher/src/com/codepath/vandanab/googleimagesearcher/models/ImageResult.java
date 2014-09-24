package com.codepath.vandanab.googleimagesearcher.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ImageResult implements Serializable {
	private static final long serialVersionUID = -4149282185752394186L;
	public String fullUrl;
	public String thumbUrl;
	public String title;

	public ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
			this.title = json.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<ImageResult> fromJSONArray(JSONArray resultsJsonArray) {
		//Log.i("INFO", "Search Results: " + resultsJsonArray);
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i < resultsJsonArray.length(); i++) {
			try {
				results.add(new ImageResult(resultsJsonArray.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
