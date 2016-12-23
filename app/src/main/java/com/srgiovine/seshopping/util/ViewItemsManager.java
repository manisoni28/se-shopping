package com.srgiovine.seshopping.util;

import android.view.View;

import srgiovine.com.seshopping.R;

public abstract class ViewItemsManager {

    private final View emptyIndicator;
    private final View loadingIndicator;
    private final View items;

    protected ViewItemsManager(View contentView) {
        emptyIndicator = contentView.findViewById(R.id.empty);
        loadingIndicator = contentView.findViewById(R.id.loading_indicator);
        items = contentView.findViewById(R.id.items);
    }

    protected void showEmptyIndicator() {
        setVisibilities(View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
    }

    protected void showLoadingIndicator() {
        setVisibilities(View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
    }

    protected void showItems() {
        setVisibilities(View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
    }

    private void setVisibilities(int loading, int empty, int results) {
        loadingIndicator.setVisibility(loading);
        emptyIndicator.setVisibility(empty);
        items.setVisibility(results);
    }
}
