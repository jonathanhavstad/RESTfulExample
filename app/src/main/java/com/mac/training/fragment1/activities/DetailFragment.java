package com.mac.training.fragment1.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mac.training.fragment1.R;
import com.mac.training.fragment1.content.PlaceholderContent;

public class DetailFragment extends Fragment {
    private static final String FRAGMENT_ARG1_KEY = "placeholderitem";
    private PlaceholderContent.PlaceholderItem item;
    private OnFragmentInteractionListener listener;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(PlaceholderContent.PlaceholderItem param1) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(FRAGMENT_ARG1_KEY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = getArguments().getParcelable(FRAGMENT_ARG1_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView titleView = (TextView) fragmentView.findViewById(R.id.item_title_display);
        if (titleView != null) {
            titleView.setText(item.getTitle());
        }

        TextView idView = (TextView) fragmentView.findViewById(R.id.item_id_display);
        if (idView != null) {
            idView.setText(String.valueOf(item.getItemId()));
        }

        TextView userIdView = (TextView) fragmentView.findViewById(R.id.item_userid_display);
        if (userIdView != null) {
            userIdView.setText(String.valueOf(item.getUserId()));
        }

        TextView bodyView = (TextView) fragmentView.findViewById(R.id.item_body_display);
        if (bodyView != null) {
            bodyView.setText(item.getBody());
        }

        Log.d(getClass().getName(), "Setting content to: " + item.toString());
        return fragmentView;
    }

    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
