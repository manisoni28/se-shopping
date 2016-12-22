package com.srgiovine.seshopping;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.srgiovine.seshopping.browse.BrowseItemsAdapter;
import com.srgiovine.seshopping.browse.BrowseItemsManager;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.navigation.CategoryNavigationItem;
import com.srgiovine.seshopping.navigation.GenderNavigationItem;
import com.srgiovine.seshopping.navigation.NavigationAdapter;
import com.srgiovine.seshopping.navigation.NavigationDrawerToggle;
import com.srgiovine.seshopping.navigation.NavigationItemFactory;

import srgiovine.com.seshopping.R;

public class BrowseActivity extends SEActivity implements NavigationAdapter.EventListener, BrowseItemsAdapter.EventListener {

    private NavigationDrawerToggle navigationDrawerToggle;

    private BrowseItemsManager browseItemsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        setupNavigationDrawer();
        setupBrowseListView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navigationDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        browseItemsManager.cancelBackgroundTasks();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navigationDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browse_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (navigationDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (R.id.action_open_cart == item.getItemId()) {
            onCartClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(Item item) {

    }

    @Override
    public void onSettingsItemClicked() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void onGenderItemClicked(GenderNavigationItem item, boolean isChecked) {
        browseItemsManager.setGenderVisible(item.gender(), isChecked);
    }

    @Override
    public void onCategoryItemClicked(CategoryNavigationItem item, boolean isChecked) {
        browseItemsManager.setCategoryVisible(item.category(), isChecked);
    }

    private void setupNavigationDrawer() {
        NavigationAdapter navigationAdapter = new NavigationAdapter(this);

        RecyclerView navigationDrawer = (RecyclerView) findViewById(R.id.navigation_drawer);
        navigationDrawer.setLayoutManager(new LinearLayoutManager(this));
        navigationDrawer.setAdapter(navigationAdapter);
        navigationAdapter.setItems(NavigationItemFactory.createNavigationItems());

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerToggle = new NavigationDrawerToggle(this, drawerLayout);
        drawerLayout.addDrawerListener(navigationDrawerToggle);
    }

    private void setupBrowseListView() {
        BrowseItemsAdapter browseItemsAdapter = new BrowseItemsAdapter(this);

        RecyclerView resultsView = (RecyclerView) findViewById(R.id.browse_items);
        resultsView.setLayoutManager(new LinearLayoutManager(this));
        resultsView.setAdapter(browseItemsAdapter);

        View loadingIndicator = findViewById(R.id.progress);
        View emptyIndicator = findViewById(R.id.empty);

        browseItemsManager = new BrowseItemsManager(browseItemsAdapter, itemProvider(),
                resultsView, loadingIndicator, emptyIndicator);
        browseItemsManager.initialize();
    }

    private void onCartClicked() {
        startActivity(new Intent(this, CartActivity.class));
    }

}
