package com.srgiovine.seshopping.cart;

import android.content.SharedPreferences;
import android.support.annotation.IntRange;

import com.srgiovine.seshopping.model.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Cart {

    private static final String CART = "cart";

    private final SharedPreferences sharedPreferences;

    /**
     * Key is {@link Item#id()}. Value is the count.
     */
    private JSONObject itemCountMap;

    Cart(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        restore();
    }

    boolean addItem(long itemId, @IntRange(from = 0) int count) {
        String itemIdStr = String.valueOf(itemId);
        int currentCount = itemCountMap.optInt(itemIdStr);
        try {
            itemCountMap.put(itemIdStr, currentCount + count);
            save();
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    void removeItem(long itemId) {
        itemCountMap.remove(String.valueOf(itemId));
        save();
    }

    void clear() {
        itemCountMap = new JSONObject();
        save();
    }

    Set<Long> ids() {
        Set<Long> ids = new HashSet<>();
        Iterator<String> keys = itemCountMap.keys();
        while (keys.hasNext()) {
            ids.add(Long.valueOf(keys.next()));
        }
        return ids;
    }

    int itemCount(long itemId) {
        return itemCountMap.optInt(String.valueOf(itemId));
    }

    private void save() {
        sharedPreferences.edit()
                .putString(CART, itemCountMap.toString())
                .apply();
    }

    private void restore() {
        try {
            itemCountMap = new JSONObject(sharedPreferences.getString(CART, ""));
        } catch (JSONException e) {
            itemCountMap = new JSONObject();
        }
    }
}
