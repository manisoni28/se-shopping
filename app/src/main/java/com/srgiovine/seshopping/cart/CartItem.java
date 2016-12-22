package com.srgiovine.seshopping.cart;

import com.srgiovine.seshopping.model.Item;

public class CartItem {

    private final Item item;

    private final int count;

    CartItem(int count, Item item) {
        this.count = count;
        this.item = item;
    }

    public int count() {
        return count;
    }

    public Item item() {
        return item;
    }
}
