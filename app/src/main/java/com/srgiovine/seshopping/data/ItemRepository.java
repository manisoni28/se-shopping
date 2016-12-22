package com.srgiovine.seshopping.data;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

import java.util.List;
import java.util.Set;

public interface ItemRepository {

    BackgroundTask getItemById(long itemId, Callback<Item> callback);

    BackgroundTask getItemsWithIds(Set<Long> itemIds, Callback<List<Item>> callback);

    BackgroundTask getItemsWithName(String title, Callback<List<Item>> callback);

    BackgroundTask getItemsWithGendersAndCategories(Set<Gender> genders, Set<Category> categories,
                                                    Callback<List<Item>> callback);

}
