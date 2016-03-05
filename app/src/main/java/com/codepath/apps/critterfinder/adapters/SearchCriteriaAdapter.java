package com.codepath.apps.critterfinder.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.SearchCriteria;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by smacgregor on 3/4/16.
 */
public class SearchCriteriaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchCriteria> mSearchCriteria;
    private OnItemClickListener mOnClickListener;

    public SearchCriteriaAdapter(List<SearchCriteria> searchCriteria) {
        mSearchCriteria = searchCriteria;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View currentView = inflator.inflate(R.layout.item_search_criteria, parent, false);
        RecyclerView.ViewHolder viewHolder = new SearchCriteriaViewHolder(currentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setupSearchCriteriaViewHolder((SearchCriteriaViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mSearchCriteria.size();
    }

    public void setOnItemClickListener(final OnItemClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    private void setupSearchCriteriaViewHolder(SearchCriteriaViewHolder holder, int position) {
        holder.mTitle.setText(mSearchCriteria.get(position).title);

        String valueText = mSearchCriteria.get(position).value;
        holder.mValues.setText(valueText);
        holder.mValues.setVisibility(TextUtils.isEmpty(valueText) ? View.GONE : View.VISIBLE);
    }

    public class SearchCriteriaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.text_title) TextView mTitle;
        @Bind(R.id.text_values) TextView mValues;

        public SearchCriteriaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        /**
         * An item in the recycler view has been clicked
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);
    }
}
