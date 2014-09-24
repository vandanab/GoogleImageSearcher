package com.codepath.vandanab.googleimagesearcher.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.vandanab.googleimagesearcher.R;
import com.codepath.vandanab.googleimagesearcher.models.SearchFilters;

public class SearchFiltersDialog extends DialogFragment implements OnClickListener {
	private Spinner spnrImageSize;
	private Spinner spnrColorFilter;
	private Spinner spnrImageType;
	private EditText etSiteFilter;
	private Button btnSaveFilters;
	private Button btnCancel;
	private ArrayAdapter<CharSequence> imageSizeAdapter;
	private ArrayAdapter<CharSequence> colorFilterAdapter;
	private ArrayAdapter<CharSequence> imageTypeAdapter;

	public interface SearchFiltersDialogListener {
		void onSaveSearchFiltersDialog(SearchFilters searchFilters);
	}

	public SearchFiltersDialog() {}

	public static SearchFiltersDialog newInstance(SearchFilters searchFilters) {
		SearchFiltersDialog searchFiltersDialog = new SearchFiltersDialog();
		Bundle args = new Bundle();
		args.putSerializable("search_filters", searchFilters);
		searchFiltersDialog.setArguments(args);
		return searchFiltersDialog;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnSaveFilters) {
			SearchFiltersDialogListener listener = (SearchFiltersDialogListener) getActivity();
			SearchFilters filters = new SearchFilters();
			filters.imageSize = imageSizeAdapter.getItem(
					spnrImageSize.getSelectedItemPosition()).toString();
			filters.colorFilter = colorFilterAdapter.getItem(
					spnrColorFilter.getSelectedItemPosition()).toString();
			filters.imageType = imageTypeAdapter.getItem(
					spnrImageType.getSelectedItemPosition()).toString();
			filters.siteFilter = etSiteFilter.getText().toString();
	        listener.onSaveSearchFiltersDialog(filters);
		}
        dismiss();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_filters, container);
		setUpSpinners(view);
		etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
		getDialog().setTitle(R.string.search_options_label);
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		btnSaveFilters = (Button) view.findViewById(R.id.btnSaveFilters);
		btnSaveFilters.setOnClickListener(this);
		btnCancel = (Button) view.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		return view;
	}

	private void setUpSpinners(View view) {
		spnrImageSize = (Spinner) view.findViewById(R.id.spnrImageSize);
		imageSizeAdapter = ArrayAdapter.createFromResource(
				view.getContext(), R.array.image_size_options, R.layout.item_spinner);
		// Specify the layout to use when the list of choices appears.
		imageSizeAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
		spnrImageSize.setAdapter(imageSizeAdapter);

		spnrColorFilter = (Spinner) view.findViewById(R.id.spnrColorFilter);
		colorFilterAdapter = ArrayAdapter.createFromResource(
				view.getContext(), R.array.color_filter_options, R.layout.item_spinner);
		colorFilterAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
		spnrColorFilter.setAdapter(colorFilterAdapter);

		spnrImageType = (Spinner) view.findViewById(R.id.spnrImageType);
		imageTypeAdapter = ArrayAdapter.createFromResource(
				view.getContext(), R.array.image_type_options, R.layout.item_spinner);
		imageTypeAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
		spnrImageType.setAdapter(imageTypeAdapter);

		SearchFilters searchFilters = (SearchFilters) getArguments().getSerializable("search_filters");

		// init dropdowns with filter values.
		if (searchFilters != null) {
			spnrImageSize.setSelection(imageSizeAdapter.getPosition(searchFilters.imageSize));
			spnrColorFilter.setSelection(colorFilterAdapter.getPosition(searchFilters.colorFilter));
			spnrImageType.setSelection(imageTypeAdapter.getPosition(searchFilters.imageType));
		} else {
			spnrImageSize.setSelection(imageSizeAdapter.getPosition("any"));
			spnrColorFilter.setSelection(colorFilterAdapter.getPosition("any"));
			spnrImageType.setSelection(imageTypeAdapter.getPosition("any"));
		}
	}
}
