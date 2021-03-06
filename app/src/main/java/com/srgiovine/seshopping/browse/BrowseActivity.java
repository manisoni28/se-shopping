package com.srgiovine.seshopping.browse;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.srgiovine.seshopping.SEActivity;
import com.srgiovine.seshopping.cart.CartActivity;
import com.srgiovine.seshopping.details.DetailsActivity;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.navigation.CategoryNavigationItem;
import com.srgiovine.seshopping.navigation.GenderNavigationItem;
import com.srgiovine.seshopping.navigation.NavigationAdapter;
import com.srgiovine.seshopping.navigation.NavigationDrawerToggle;
import com.srgiovine.seshopping.navigation.NavigationItemFactory;
import com.srgiovine.seshopping.search.SearchActivity;
import com.srgiovine.seshopping.settings.SettingsActivity;
import com.srgiovine.seshopping.util.ItemsLoaderView;

import srgiovine.com.seshopping.R;

public class BrowseActivity extends SEActivity implements NavigationAdapter.EventListener, BrowseItemsAdapter.EventListener {

    protected BrowseItemsPresenter browseItemsPresenter;

    @Nullable
    private NavigationDrawerToggle navigationDrawerToggle;

    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = getLayoutInflater().inflate(layoutRes(), null);
        setContentView(contentView);
        setupBrowseItemsView();
        if (hasNavigationDrawer()) {
            setupNavigationDrawer();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (navigationDrawerToggle != null) {
            navigationDrawerToggle.syncState();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        browseItemsPresenter.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (navigationDrawerToggle != null) {
            navigationDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(optionsMenuRes(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (navigationDrawerToggle != null && navigationDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (R.id.action_open_cart == item.getItemId()) {
            onCartClicked();
        } else if (R.id.action_search == item.getItemId()) {
            onSearchClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(Item item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_ITEM_ID, item.id());
        startActivity(intent);
    }

    @Override
    public void onSettingsItemClicked() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void onGenderItemClicked(GenderNavigationItem item, boolean isChecked) {
        browseItemsPresenter.setItemsWithGenderVisible(item.gender(), isChecked);
    }

    @Override
    public void onCategoryItemClicked(CategoryNavigationItem item, boolean isChecked) {
        browseItemsPresenter.setItemsWithCategoryVisible(item.category(), isChecked);
    }

    protected void onInitializeBrowseItemsManager(BrowseItemsPresenter browseItemsPresenter) {
        browseItemsPresenter.initializeWithNoFilters();
    }

    @LayoutRes
    protected int layoutRes() {
        return R.layout.activity_browse;
    }

    @MenuRes
    protected int optionsMenuRes() {
        return R.menu.browse_menu;
    }

    protected boolean hasNavigationDrawer() {
        return true;
    }

    private void onSearchClicked() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    private void onCartClicked() {
        startActivity(new Intent(this, CartActivity.class));
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

    private void setupBrowseItemsView() {
        BrowseItemsAdapter browseItemsAdapter = new BrowseItemsAdapter(this);

        RecyclerView itemsView = (RecyclerView) findViewById(R.id.items);
        itemsView.setLayoutManager(new LinearLayoutManager(this));
        itemsView.setAdapter(browseItemsAdapter);

        browseItemsPresenter = new BrowseItemsPresenter(browseItemsAdapter, itemRepository(),
                new ItemsLoaderView(contentView));
        onInitializeBrowseItemsManager(browseItemsPresenter);
    }

}
