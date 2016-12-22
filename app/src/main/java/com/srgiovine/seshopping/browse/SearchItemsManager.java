package com.srgiovine.seshopping.browse;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class SearchItemsManager implements TextWatcher {

    private static final long SEARCH_DELAY_AFTER_TEXT_CHANGED_MILLIS = 500L;

    private final BrowseItemsManager browseItemsManager;

    private final EditText searchField;

    private final Handler handler = new Handler();

    private SearchRunnable searchRunnable;

    public SearchItemsManager(BrowseItemsManager browseItemsManager, EditText searchField) {
        this.browseItemsManager = browseItemsManager;
        this.searchField = searchField;
    }

    public void initialize() {
        searchField.requestFocus();
        searchField.addTextChangedListener(this);
        searchField.setOnTouchListener(new ClearSearchFieldOnTouchListener());
    }

    public void onDestroy() {
        handler.removeCallbacks(searchRunnable);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        handler.removeCallbacks(searchRunnable);
        searchRunnable = new SearchRunnable(editable.toString());
        handler.postDelayed(searchRunnable, SEARCH_DELAY_AFTER_TEXT_CHANGED_MILLIS);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    private class SearchRunnable implements Runnable {

        private final String name;

        private SearchRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            browseItemsManager.showItemsWithName(name);
        }
    }

    private class ClearSearchFieldOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Drawable clearIcon = searchField.getCompoundDrawables()[2];
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (searchField.getRight() - clearIcon.getBounds().width())) {
                    searchField.setText(null);
                    return true;
                }
            }
            return false;
        }
    }
}
