package com.srgiovine.seshopping;

import android.app.ActionBar;
import android.widget.EditText;

import com.srgiovine.seshopping.browse.BrowseItemsManager;
import com.srgiovine.seshopping.browse.SearchItemsManager;

import srgiovine.com.seshopping.R;

public class SearchActivity extends BrowseActivity {

    private SearchItemsManager searchItemsManager;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchItemsManager.onDestroy();
    }

    @Override
    void onInitializeBrowseItemsManager(BrowseItemsManager browseItemsManager) {
        browseItemsManager.initializeWithNoItems();
        initializeSearchField();
    }

    @Override
    int layoutRes() {
        return R.layout.activity_search;
    }

    @Override
    int optionsMenuRes() {
        return R.menu.search_menu;
    }

    @Override
    boolean hasNavigationDrawer() {
        return false;
    }

    private void initializeSearchField() {
        EditText searchField = (EditText) getLayoutInflater().inflate(R.layout.search_field, null);

        getActionBar().setTitle(null);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setCustomView(searchField, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT));

        searchItemsManager = new SearchItemsManager(browseItemsManager, searchField);
        searchItemsManager.initialize();
    }
}
