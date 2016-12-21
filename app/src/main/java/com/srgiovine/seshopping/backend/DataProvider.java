package com.srgiovine.seshopping.backend;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DataProvider {

    public void getItemsWithCategories(Set<Category> categories, GetItemsCallback callback) {

    }

    public interface GetItemsCallback {
        void onItemsRetrieved(List<Item> items);
    }

}
