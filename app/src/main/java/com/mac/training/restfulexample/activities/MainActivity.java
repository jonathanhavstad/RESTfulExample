package com.mac.training.restfulexample.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mac.training.restfulexample.R;
import com.mac.training.restfulexample.content.PlaceholderContent;

public class MainActivity extends AppCompatActivity implements
       ItemFragment.OnListFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

    private boolean useTablet;
    private PlaceholderContent.PlaceholderItem currentItem;
    private ItemFragment itemFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            useTablet = true;

            itemFragment = ItemFragment.newInstance(this, 1);
            ft.replace(R.id.list_item_fragment_container, itemFragment);
            itemFragment.init(this);

            currentItem = itemFragment.getItem(0);
            if (currentItem != null) {
                Fragment detailFragment = DetailFragment.newInstance(currentItem);
                ft.replace(R.id.detail_item_fragment_container, detailFragment);
            }
        } else {
            useTablet = false;
            itemFragment = ItemFragment.newInstance(this, 1);
            ft.replace(R.id.fragment_container, itemFragment);
        }
        ft.commit();

        Log.d(getClass().getName(), "Using tablet: " + useTablet);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(
                getString(R.string.current_item_save_state_key), currentItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentItem = savedInstanceState.getParcelable(
                getString(R.string.current_item_save_state_key));
        if (currentItem != null) {
            onListFragmentInteraction(currentItem);
        }
    }

    @Override
    public void onListFragmentInteraction(PlaceholderContent.PlaceholderItem item) {
        Log.d(getClass().getName(), "Changing detail list item");
        currentItem = item;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left);
        DetailFragment detailFragment = DetailFragment.newInstance(item);
        if (useTablet) {
            ft.replace(R.id.detail_item_fragment_container, detailFragment);
        } else {
            currentFragment = detailFragment;
            ft.replace(R.id.fragment_container, detailFragment);
            ft.addToBackStack(getString(R.string.content_view_fragment_tag));
        }
        ft.commit();

        Log.d(getClass().getName(), "Using tablet: " + useTablet);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (!useTablet) {
            FragmentManager fm = getSupportFragmentManager();
            if (!itemFragment.isVisible()) {
                FragmentTransaction ft =
                        fm.beginTransaction();
                ft.setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right);
                ft.replace(R.id.fragment_container, itemFragment);
                ft.detach(itemFragment);
                fm.popBackStackImmediate();
                ft.attach(itemFragment);
                ft.commitNow();
            } else {
                fm.beginTransaction().remove(itemFragment).commit();
                super.onBackPressed();
            }
            currentItem = null;
            Log.d(getClass().getName(), "Backstack entry count: " + fm.getBackStackEntryCount());
        } else {
            super.onBackPressed();
        }
    }
}
