package com.mac.training.restfulexample.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mac.training.restfulexample.R;
import com.mac.training.restfulexample.activities.ItemFragment.OnListFragmentInteractionListener;
import com.mac.training.restfulexample.content.PlaceholderContent;
import com.mac.training.restfulexample.content.PlaceholderContent.PlaceholderItem;

import java.util.List;

public class ItemContentRecyclerViewAdapter
        extends RecyclerView.Adapter<ItemContentRecyclerViewAdapter.ViewHolder> {

    private final PlaceholderContent contentValues;
    private final OnListFragmentInteractionListener listener;

    public ItemContentRecyclerViewAdapter(
            PlaceholderContent contentValues, OnListFragmentInteractionListener listener) {
        this.contentValues = contentValues;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = contentValues.getItem(position);
        holder.idView.setText(contentValues.getItem(position).title);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    Log.d(getClass().getName(), "mListener is non-null");
                    listener.onListFragmentInteraction(holder.item);
                } else {
                    Log.d(getClass().getName(), "mListener is null");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentValues.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView idView;
        public PlaceholderItem item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.idView = (TextView) view.findViewById(R.id.id);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + idView.getText() + "'";
        }
    }
}
