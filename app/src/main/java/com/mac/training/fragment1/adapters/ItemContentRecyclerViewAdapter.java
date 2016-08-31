package com.mac.training.fragment1.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mac.training.fragment1.R;
import com.mac.training.fragment1.activities.ItemFragment.OnListFragmentInteractionListener;
import com.mac.training.fragment1.content.PlaceholderContent.PlaceholderItem;

import java.util.List;

public class ItemContentRecyclerViewAdapter
        extends RecyclerView.Adapter<ItemContentRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ItemContentRecyclerViewAdapter(
            List<PlaceholderItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Log.d(getClass().getName(), "mListener is non-null");
                    mListener.onListFragmentInteraction(holder.mItem);
                } else {
                    Log.d(getClass().getName(), "mListener is null");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public PlaceholderItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
