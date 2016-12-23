package com.srgiovine.seshopping.cart;

import com.srgiovine.seshopping.model.Item;

class CartItem {

    private final Item item;

    private int count;

    CartItem(int count, Item item) {
        this.count = count;
        this.item = item;
    }

    int count() {
        return count;
    }

    Item item() {
        return item;
    }

    void setCount(int count) {
        this.count = count;
    }
}
