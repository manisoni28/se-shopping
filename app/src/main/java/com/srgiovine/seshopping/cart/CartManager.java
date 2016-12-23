package com.srgiovine.seshopping.cart;

import android.content.SharedPreferences;
import android.support.annotation.IntRange;

import com.srgiovine.seshopping.data.ItemRepository;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private final ItemRepository itemRepository;

    private final Cart cart;

    private CartManager(Cart cart, ItemRepository itemRepository) {
        this.cart = cart;
        this.itemRepository = itemRepository;
    }

    public BackgroundTask getItemsInCart(Callback<List<CartItem>> callback) {
        return itemRepository.getItemsWithIds(cart.ids(), new GetItemsWithIdsCallback(callback));
    }

    public boolean addItemToCart(long itemId, @IntRange(from = 0) int count) {
        return cart.addItem(itemId, count);
    }

    public void removeItemFromCart(long itemId) {
        cart.removeItem(itemId);
    }

    public void clearCart() {
        cart.clear();
    }

    public static CartManager create(ItemRepository itemRepository, SharedPreferences sharedPreferences) {
        return new CartManager(new Cart(sharedPreferences), itemRepository);
    }

    private class GetItemsWithIdsCallback implements Callback<List<Item>> {

        private final Callback<List<CartItem>> callback;

        private GetItemsWithIdsCallback(Callback<List<CartItem>> callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(List<Item> items) {
            List<CartItem> cartItems = new ArrayList<>();
            for (Item item : items) {
                cartItems.add(new CartItem(cart.itemCount(item.id()), item));
            }
            callback.onSuccess(cartItems);
        }

        @Override
        public void onFailed() {
            callback.onFailed();
        }

        @Override
        public void onCancelled() {
            callback.onCancelled();
        }
    }
}
