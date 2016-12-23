package com.srgiovine.seshopping.cart;

import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;
import com.srgiovine.seshopping.util.ItemsLoaderView;

import java.util.List;

class CartItemsPresenter {

    private final CartManager cartManager;

    private final CartItemsAdapter adapter;

    private final ItemsLoaderView itemsLoaderView;

    private BackgroundTask getCartItemsTask;

    private final Callback<List<CartItem>> getCartItemsCallback = new SimpleCallback<List<CartItem>>() {
        @Override
        public void onSuccess(List<CartItem> items) {
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

    CartItemsPresenter(CartItemsAdapter adapter, CartManager cartManager,
                       ItemsLoaderView itemsLoaderView) {
        this.adapter = adapter;
        this.cartManager = cartManager;
        this.itemsLoaderView = itemsLoaderView;
    }

    void loadCartItems() {
        itemsLoaderView.showLoadingIndicator();
        getCartItemsTask = cartManager.getItemsInCart(getCartItemsCallback);
    }

    void onItemRemoved() {
        if (adapter.getItemCount() == 0) {
            itemsLoaderView.showEmptyIndicator();
        }
    }

    void onDestroy() {
        if (getCartItemsTask != null) {
            getCartItemsTask.cancel();
            getCartItemsTask = null;
        }
    }
}
