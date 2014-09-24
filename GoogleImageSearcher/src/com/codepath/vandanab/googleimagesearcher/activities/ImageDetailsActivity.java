package com.codepath.vandanab.googleimagesearcher.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

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
		ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("result");
		// find the image view and load it with the image
		ImageView ivImageDetails = (ImageView) findViewById(R.id.ivImageDetails);
		Picasso.with(this).load(imageResult.fullUrl)
			.resize(600, 600).centerInside().into(ivImageDetails);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_image_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_share) {
			shareImage();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void shareImage() {
		ImageView ivImage = (ImageView) findViewById(R.id.ivImageDetails);
		Uri uri = getLocalBitmapUri(ivImage);

		if (uri != null ) {
			// Implicit intents.
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM, uri.toString());
			shareIntent.setType("image/*");
			startActivity(Intent.createChooser(shareIntent, "Share image"));
		} else {
			Toast.makeText(this, "Sharing failed!", Toast.LENGTH_SHORT).show();
		}
	}

	// Returns the URI path to the Bitmap displayed in specified ImageView
	private Uri getLocalBitmapUri(ImageView imageView) {
	    // Extract Bitmap from ImageView drawable
	    Drawable drawable = imageView.getDrawable();
	    Bitmap bmp = null;
	    if (drawable instanceof BitmapDrawable){
	       bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    } else {
	       return null;
	    }
	    // Store image to default external storage directory
	    Uri bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
		        Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
	        file.getParentFile().mkdirs();
	        FileOutputStream out = new FileOutputStream(file);
	        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return bmpUri;
	}
}
