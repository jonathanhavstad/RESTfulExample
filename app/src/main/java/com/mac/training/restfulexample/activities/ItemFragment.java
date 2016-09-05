package com.mac.training.restfulexample.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import com.mac.training.restfulexample.adapters.ItemContentRecyclerViewAdapter;
import com.mac.training.restfulexample.R;
import com.mac.training.restfulexample.content.PlaceholderContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener listFragmentInteractionListner;
    private PlaceholderContent placeholderContent;
    private RequestQueue requestQueue;
    private Context context;
    private SwipeRefreshLayout refreshView;
    private RecyclerView recyclerView;
    private ItemContentRecyclerViewAdapter recyclerViewAdapter;

    public PlaceholderContent.PlaceholderItem getItem(int index) {
        if (placeholderContent != null) {
           return placeholderContent.getItem(index);
        }
        return null;
    }

    private void retrieveItems() {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = context.getString(R.string.host_url) + context.getString(R.string.rest_operation);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(getClass().getName(), response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                PlaceholderContent.PlaceholderItem item =
                                        new PlaceholderContent.PlaceholderItem(
                                                jsonObject.getInt("id"),
                                                jsonObject.getInt("userId"),
                                                jsonObject.getString("title"),
                                                jsonObject.getString("body"));
                                placeholderContent.addItem(item);
                            } catch (JSONException e) {
                                Toast.makeText(
                                        context,
                                        "Error retrieving REST data!",
                                        Toast.LENGTH_LONG);
                                e.printStackTrace();
                            }
                        }
                        setIsRefreshing(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(getClass().getName(), error.toString());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    public ItemFragment() {
    }

    public static ItemFragment newInstance(Context context, int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_item_list, container, false);
        View view = parentView.findViewById(R.id.list);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if (listFragmentInteractionListner == null) {
                Log.d(getClass().getName(), "mListener is null");
            } else {
                Log.d(getClass().getName(), "mListener is non-null");
            }
            recyclerViewAdapter = new ItemContentRecyclerViewAdapter(
                    placeholderContent,
                    listFragmentInteractionListner);
            recyclerView.setAdapter(recyclerViewAdapter);

            if (parentView instanceof SwipeRefreshLayout) {
                refreshView = (SwipeRefreshLayout) parentView;
                refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        setIsRefreshing(true);
                    }
                });
            }
        }
        return parentView;
    }

    public void init(Context context) {
        if (context instanceof OnListFragmentInteractionListener) {
            this.context = context;
            this.listFragmentInteractionListner = (OnListFragmentInteractionListener) context;
            this.placeholderContent = new PlaceholderContent();
            this.retrieveItems();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        init(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttach((Activity) context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listFragmentInteractionListner = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(PlaceholderContent.PlaceholderItem item);
    }

    private void setIsRefreshing(boolean isRefreshing) {
        if (refreshView != null) {
            if (isRefreshing) {
                placeholderContent.clearAllItems();
                recyclerView.setAlpha(0.5f);
                refreshView.setRefreshing(true);
                retrieveItems();
            } else {
                refreshView.setRefreshing(false);
                recyclerView.setAlpha(1.0f);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }
}
