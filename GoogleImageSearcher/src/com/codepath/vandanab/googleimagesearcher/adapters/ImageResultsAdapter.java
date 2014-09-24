package com.codepath.vandanab.googleimagesearcher.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.vandanab.googleimagesearcher.R;
import com.codepath.vandanab.googleimagesearcher.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	// View lookup cache.
	private static class ViewHolder {
		ImageView ivImage;
		TextView tvTitle;
	}

	public ImageResultsAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position.
		ImageResult imageResult = getItem(position);

		ViewHolder viewHolder;

		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result,
					parent, false);
			// Lookup view for data population
			viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		// Populate the data into the template view using the data object
		viewHolder.ivImage.setImageResource(0);
		viewHolder.tvTitle.setText(Html.fromHtml(imageResult.title));
		Picasso.with(getContext()).load(imageResult.thumbUrl).into(viewHolder.ivImage);
		// Return the completed view to render on screen
		return convertView;
	}

	
}
