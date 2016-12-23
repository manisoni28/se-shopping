package com.srgiovine.seshopping.search;

import android.app.ActionBar;
import android.widget.EditText;

import com.srgiovine.seshopping.browse.BrowseActivity;
import com.srgiovine.seshopping.browse.BrowseItemsPresenter;

import srgiovine.com.seshopping.R;

public class SearchActivity extends BrowseActivity {

    private SearchItemsPresenter searchItemsPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchItemsPresenter.onDestroy();
    }

    @Override
    protected void onInitializeBrowseItemsManager(BrowseItemsPresenter browseItemsPresenter) {
        browseItemsPresenter.initializeWithNoItems();
        initializeSearchField();
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected int optionsMenuRes() {
        return R.menu.search_menu;
    }

    @Override
    protected boolean hasNavigationDrawer() {
        return false;
    }

    private void initializeSearchField() {
        EditText searchField = (EditText) getLayoutInflater().inflate(R.layout.search_field, null);

        getActionBar().setTitle(null);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setCustomView(searchField, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT));

        searchItemsPresenter = new SearchItemsPresenter(browseItemsPresenter, searchField);
        searchItemsPresenter.initialize();
    }
}
