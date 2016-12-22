package com.srgiovine.seshopping.data;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;
import com.srgiovine.seshopping.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemProvider {

    BackgroundTask getItems(Set<Gender> genders, Set<Category> categories, Callback<List<Item>> callback);

    interface BackgroundTask {
        void cancel();
    }

    interface Callback<T> {

        void success(T result);

        void failed();
    }

}
