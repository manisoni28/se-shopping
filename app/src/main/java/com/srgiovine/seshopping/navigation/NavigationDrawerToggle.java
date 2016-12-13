package com.srgiovine.seshopping.navigation;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import srgiovine.com.seshopping.R;

@SuppressWarnings("ConstantConditions")
public class NavigationDrawerToggle extends ActionBarDrawerToggle {

    private final Activity activity;

    public NavigationDrawerToggle(Activity activity, DrawerLayout drawerLayout) {
        super(activity, drawerLayout, R.string.options, R.string.browse);
        this.activity = activity;
        activity.getActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getActionBar().setHomeButtonEnabled(true);
        setHomeAsUpIndicator(R.drawable.ic_menu_options);
    }

    /**
     * Called when a drawer has settled in a completely closed state.
     */
    public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
        activity.getActionBar().setTitle(R.string.browse);
    }

    /**
     * Called when a drawer has settled in a completely open state.
     */
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        activity.getActionBar().setTitle(R.string.options);
    }
}
