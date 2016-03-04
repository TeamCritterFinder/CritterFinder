package com.codepath.apps.critterfinder.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.SearchFilter;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by smacgregor on 3/3/16.
 */
public class SearchFilterDialog extends DialogFragment {

    private static final String ARGUMENT_SEARCH_FILTER = "ARGUMENT_SEARCH_FILTER";
    @Bind(R.id.spinner_gender) Spinner mGenderSpinner;

    private SearchFilter mSearchFilter;
    private OnSearchFilterFragmentInteractionListener mListener;

    public static SearchFilterDialog newInstance(SearchFilter searchFilter) {
        SearchFilterDialog searchFilterDialog = new SearchFilterDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_SEARCH_FILTER, Parcels.wrap(searchFilter));
        searchFilterDialog.setArguments(args);
        return searchFilterDialog;
    }

    public SearchFilterDialog() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchFilter = Parcels.unwrap(getArguments().getParcelable(ARGUMENT_SEARCH_FILTER));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_filter_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setupGenderSpinner();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSearchFilterFragmentInteractionListener) {
            mListener = (OnSearchFilterFragmentInteractionListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button_save_search_filter)
    void onSaveSearchFilterClicked(Button button) {
        if (mListener != null) {
            mListener.onFinishedSavingSearchFilter(mSearchFilter);
        }
        dismiss();
    }

    @OnItemSelected(R.id.spinner_gender)
    void onGenderSelected(AdapterView<?> parent, View view, int position, long id) {
        mSearchFilter.setGender(SearchFilter.Gender.values()[position]);
    }
    private void setupGenderSpinner() {
        mGenderSpinner.setSelection(mSearchFilter.getGender().ordinal());
    }

    public interface OnSearchFilterFragmentInteractionListener {
        /**
         * The search filter has been updated
         * @param searchFilter
         */
        void onFinishedSavingSearchFilter(SearchFilter searchFilter);
    }
}
