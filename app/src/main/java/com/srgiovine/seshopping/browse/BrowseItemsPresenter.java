package com.srgiovine.seshopping.browse;

import com.srgiovine.seshopping.data.ItemRepository;
import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;
import com.srgiovine.seshopping.util.ItemsLoaderView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrowseItemsPresenter {

    private final ItemRepository itemRepository;

    private final BrowseItemsAdapter adapter;

    private final ItemsLoaderView itemsLoaderView;

    private final Set<Gender> genders = new HashSet<>();

    private final Set<Category> categories = new HashSet<>();

    private BackgroundTask getItemsTask;

    private final Callback<List<Item>> getItemsCallback = new SimpleCallback<List<Item>>() {
        @Override
        public void onSuccess(List<Item> items) {
            if (items.isEmpty()) {
                onFailed();
                return;
            }

            adapter.setItems(items);
            itemsLoaderView.showItems();
        }

        @Override
        public void onFailed() {
            itemsLoaderView.showEmptyIndicator();
        }
    };

    BrowseItemsPresenter(BrowseItemsAdapter adapter, ItemRepository itemRepository,
                         ItemsLoaderView itemsLoaderView) {
        this.adapter = adapter;
        this.itemRepository = itemRepository;
        this.itemsLoaderView = itemsLoaderView;
    }

    public void initializeWithNoItems() {
        itemsLoaderView.showEmptyIndicator();
    }

    void initializeWithNoFilters() {
        updateAdapterWithFilteredItems();
    }

    public void showItemsWithName(String name) {
        onPreStartBackgroundTask();
        getItemsTask = itemRepository.getItemsWithName(name, getItemsCallback);
    }

    void setItemsWithGenderVisible(Gender gender, boolean visible) {
        if (visible) {
            genders.add(gender);
        } else {
            genders.remove(gender);
        }
        updateAdapterWithFilteredItems();
    }

    void setItemsWithCategoryVisible(Category category, boolean visible) {
        if (visible) {
            categories.add(category);
        } else {
            categories.remove(category);
        }
        updateAdapterWithFilteredItems();
    }

    void onDestroy() {
        cancelBackgroundTasks();
    }

    private void updateAdapterWithFilteredItems() {
        onPreStartBackgroundTask();
        getItemsTask = itemRepository.getItemsWithGendersAndCategories(genders, categories, getItemsCallback);
    }

    private void onPreStartBackgroundTask() {
        itemsLoaderView.showLoadingIndicator();
        cancelBackgroundTasks();
    }

    private void cancelBackgroundTasks() {
        if (getItemsTask != null) {
            getItemsTask.cancel();
            getItemsTask = null;
        }
    }
}
