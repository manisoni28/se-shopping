package com.srgiovine.seshopping.browse;

import android.view.View;

import com.srgiovine.seshopping.data.ItemRepository;
import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrowseItemsManager {

    private final ItemRepository itemRepository;

    private final BrowseItemsAdapter adapter;

    private final View resultsView;

    private final View loadingIndicator;

    private final View emptyIndicator;

    private final Set<Gender> genders = new HashSet<>();

    private final Set<Category> categories = new HashSet<>();

    private BackgroundTask getItemsTask;

    private final Callback<List<Item>> getItemsCallback = new SimpleCallback<List<Item>>() {
        @Override
        public void onSuccess(List<Item> result) {
            if (result.isEmpty()) {
                onFailed();
                return;
            }

            setLoading(false);
            adapter.setItems(result);
            showResults();
        }

        @Override
        public void onFailed() {
            setLoading(false);
            setEmpty(true);
        }
    };

    public BrowseItemsManager(BrowseItemsAdapter adapter, ItemRepository itemRepository,
                              View resultsView, View loadingIndicator, View emptyIndicator) {
        this.adapter = adapter;
        this.itemRepository = itemRepository;
        this.resultsView = resultsView;
        this.loadingIndicator = loadingIndicator;
        this.emptyIndicator = emptyIndicator;
    }

    public void initialize() {
        updateAdapterWithFilteredItems();
    }

    public void setGenderVisible(Gender gender, boolean isVisible) {
        if (isVisible) {
            genders.add(gender);
        } else {
            genders.remove(gender);
        }
        updateAdapterWithFilteredItems();
    }

    public void setCategoryVisible(Category category, boolean isVisible) {
        if (isVisible) {
            categories.add(category);
        } else {
            categories.remove(category);
        }
        updateAdapterWithFilteredItems();
    }

    public void cancelBackgroundTasks() {
        if (getItemsTask != null) {
            getItemsTask.cancel();
            getItemsTask = null;
        }
    }

    private void updateAdapterWithFilteredItems() {
        cancelBackgroundTasks();
        hideResults();
        setLoading(true);
        itemRepository.getItems(genders, categories, getItemsCallback);
    }

    private void showResults() {
        resultsView.setVisibility(View.VISIBLE);
    }

    private void hideResults() {
        resultsView.setVisibility(View.INVISIBLE);
    }

    private void setLoading(boolean loading) {
        loadingIndicator.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }

    private void setEmpty(boolean empty) {
        emptyIndicator.setVisibility(empty ? View.VISIBLE : View.INVISIBLE);
    }
}
